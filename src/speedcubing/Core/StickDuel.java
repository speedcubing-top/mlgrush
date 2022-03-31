package speedcubing.Core;


import cubing.lib.bukkit.PlayerUtils;
import cubing.lib.bukkit.SideBarAPI;
import cubingserver.StringList.GlobalString;
import cubingserver.libs.User;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import speedcubing.All.Lobby;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;
import speedcubing.Main;

import java.util.*;

public class StickDuel {

    public static final int roomsize = 5;
    public static UUID Queue = null;
    public static boolean[] Rooms = new boolean[roomsize];
    public static Set<UUID> QueueCoolDown = new HashSet<>();
    static String[] actionbar = new String[roomsize];
    public static final Map<Integer, UUID[]> Players = new HashMap<>();
    public static final Map<Integer, Integer[]> Score = new HashMap<>();
    public static final Map<Integer, Set<UUID>> Spectators = new HashMap<>();
    public static final Map<Integer, Double[]> Kill = new HashMap<>();
    public static final Map<Integer, java.util.TimerTask> TimerTask = new HashMap<>();
    public static final Map<Integer, Set<BlockPosition>> Blocks = new HashMap<>();
    public static final Map<Integer, Integer> Time = new HashMap<>();

    public void Load() {
        for (int i = 0; i < roomsize; i++) {
            Rooms[i] = true;
        }
    }

    public static void Queue(Player player) {
        UUID uuid = player.getUniqueId();
        if (!QueueCoolDown.contains(uuid)) {
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
            QueueCoolDown.add(uuid);
            EntityArmorStand stand = EntityList.Lobbys[3];
            if (Queue == null) {
                Core.QuitAllQueue(uuid);
                player.closeInventory();
                player.sendMessage(StringList.StickDuelQueue[User.getLang(uuid)]);
                Queue = uuid;
                stand.setCustomName("§a1/2");
            } else {
                stand.setCustomName("§a0/2");
                if (Queue.equals(uuid))
                    player.sendMessage(StringList.StickDuelUnQueue[User.getLang(uuid)]);
                else
                    gameStart(uuid, Queue, 0);
                Queue = null;
            }
            PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true);
            for (Player p : Bukkit.getWorld("world").getPlayers()) {
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    QueueCoolDown.remove(uuid);
                }
            }, 2000);
        }
    }

    public static void ResetBlocks(int room) {
        World world = Bukkit.getWorld("StickDuel");
        for (BlockPosition pos : Blocks.get(room)) {
            world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()).setType(Material.AIR);
        }
        Blocks.get(room).clear();
    }

    public static int[] MapLocations(int map) { // X , Y , Y limit , +-Z
        return new int[]{10, 0};
    }

    public static void JoinSpec(Player player, int room) {
        UUID uuid = player.getUniqueId();
        int lang = User.getLang(uuid);
        Core.QuitAllGame(uuid);
        Core.QuitAllQueue(uuid);
        player.teleport(new Location(Bukkit.getWorld("StickDuel"), 0.5, 110, Core.roomToBed(room), 0, 0));
        int x = Time.get(room);
        UUID[] playeruuid = Players.get(room);
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("sidebar", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        String[] sidebar = StringList.StickDuelSideBar[lang];
        objective.setDisplayName(sidebar[5].replace("%h%", x / 3600 == 0 ? "" : x / 3600 + ":").replace("%m%", String.format("%02d", (x % 3600) / 60) + ":").replace("%s%", String.format("%02d", x % 60)));
        Integer[] score = Score.get(room);
        String[] suffix = (score[0].equals(score[1])) ? new String[]{"§e" + score[0], "§e" + score[1]} : (score[0] > score[1] ? new String[]{"§a" + score[0], "§c" + score[1]} : new String[]{"§c" + score[0], "§a" + score[1]});
        SideBarAPI.sidebarTeams(scoreboard, new String[][]{{sidebar[4]}, {sidebar[3]}, {"§2§f", User.getFormat(User.getRank(playeruuid[0]))[1] + Bukkit.getPlayer(playeruuid[0]).getName() + sidebar[2] + suffix[0]}, {"§1§f", User.getFormat(User.getRank(playeruuid[1]))[1] + Bukkit.getPlayer(playeruuid[1]).getName() + sidebar[1] + suffix[1]}, {sidebar[0]},}, new int[]{4, 3, 2, 1, 0});
        player.setScoreboard(scoreboard);
        player.getInventory().clear();
        player.getInventory().setItem(8, ItemStackList.HubSlime[lang]);
        Set<UUID> targets = new HashSet<>();
        targets.addAll(Spectators.get(room));
        targets.addAll(Arrays.asList(playeruuid));
        String name = User.getFormat(User.getRank(uuid))[1] + player.getName();
        for (UUID id : targets) {
            Bukkit.getPlayer(id).sendMessage(StringList.JoinSpectate[User.getLang(id)].replace("%player%", name));
        }
        Spectators.get(room).add(uuid);
        Core.Spectate.put(uuid, room);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
            player.setAllowFlight(true);
            player.setFlying(true);
        }, 1);
    }

    public static void Fell(Player player, double time) {
        UUID uuid = player.getUniqueId();
        int room = Core.PlayerRoom.get(uuid);
        UUID[] playeruuid = Players.get(room);
        Player[] players = {Bukkit.getPlayer(playeruuid[0]), Bukkit.getPlayer(playeruuid[1])};
        int w = players[0] == player ? 1 : 0;
        Core.PlacedBlocks.put(uuid, 0);
        Player killer = players[w];
        String[] loserformat = User.getFormat(User.getRank(uuid));
        Set<UUID> targets = new HashSet<>();
        targets.addAll(Spectators.get(room));
        targets.addAll(Arrays.asList(playeruuid));

        for (Player p : players) {
            Core.resetItems(p);
        }
        if (time - Kill.get(room)[1 - w] < 2500) {
            String[] winnerformat = User.getFormat(User.getRank(playeruuid[w]));
            String[] formats = new String[]{User.getFormat(User.getRank(playeruuid[0]))[1] + players[0].getName(), User.getFormat(User.getRank(playeruuid[1]))[1] + players[1].getName()};
            killer.playSound(killer.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
            Score.get(room)[w] += 1;
            Integer[] score = Score.get(room);
            String[] suffix = new String[2];
            if (score[0].equals(score[1])) {
                actionbar[room] = formats[0] + " §e" + score[0] + " §8x §e" + score[1] + " " + formats[1];
                suffix[0] = "§e" + score[0];
                suffix[1] = "§e" + score[1];
            } else if (score[0] > score[1]) {
                actionbar[room] = formats[0] + " §a" + score[0] + " §8x §c" + score[1] + " " + formats[1];
                suffix[0] = "§a" + score[0];
                suffix[1] = "§c" + score[1];
            } else {
                actionbar[room] = formats[0] + " §c" + score[0] + " §8x §a" + score[1] + " " + formats[1];
                suffix[0] = "§c" + score[0];
                suffix[1] = "§a" + score[1];
            }
            for (UUID id : targets) {
                Player p = Bukkit.getPlayer(id);
                int lang = User.getLang(id);
                String[] sidebar = StringList.StickDuelSideBar[lang];
                String[] split = SideBarAPI.prefixSuffixSplit(formats[0] + sidebar[2] + suffix[0]);
                String[] split2 = SideBarAPI.prefixSuffixSplit(formats[1] + sidebar[1] + suffix[1]);
                Scoreboard scoreboard = p.getScoreboard();
                scoreboard.getTeam("§2§f").setPrefix(split[0]);
                scoreboard.getTeam("§2§f").setSuffix(split[1]);
                scoreboard.getTeam("§1§f").setPrefix(split2[0]);
                scoreboard.getTeam("§1§f").setSuffix(split2[1]);
                PlayerUtils.sendActionBar(((CraftPlayer) p).getHandle().playerConnection, actionbar[room]);
                p.sendMessage(StringList.StickDuelKill[lang]
                        .replace("%format0%", loserformat[1])
                        .replace("%name0%", player.getName())
                        .replace("%format1%", winnerformat[1])
                        .replace("%name1%", killer.getName()));
            }
        } else {
            for (UUID id : targets) {
                Bukkit.getPlayer(id).sendMessage(StringList.StickDuelFell[User.getLang(uuid)].replace("%format0%", loserformat[0]).replace("%name%", player.getName()));
            }
        }
        int pos = killer.getLocation().getX() > 0 ? -1 : 1;
        int[] Map = MapLocations(room);
        PlayerUtils.teleport(((CraftPlayer) player).getHandle().playerConnection, 0.5 + Map[0] * pos, 101, Core.randomToBed(player.getLocation().getZ()) + 0.5 + pos * Map[1], 180 - 90 * pos, 0);
        ResetBlocks(room);
    }

    public static void gameStart(UUID uuid1, UUID uuid2, int map) {
        boolean full = true;
        for (boolean bool : Rooms) {
            if (bool) {
                full = false;
                break;
            }
        }
        if (full) {
            Bukkit.getPlayer(uuid1).sendMessage(GlobalString.NoEnoughServers[User.getLang(uuid1)]);
            Bukkit.getPlayer(uuid2).sendMessage(GlobalString.NoEnoughServers[User.getLang(uuid2)]);
            return;
        }
        Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () -> {
            int room = -1;
            for (int h = 0; h < roomsize; h++) {
                if (Rooms[h]) {
                    room = h;
                    break;
                }
            }
            Players.put(room, new Random().nextBoolean() ? new UUID[]{uuid1, uuid2} : new UUID[]{uuid2, uuid1});
            UUID[] playeruuid = Players.get(room);
            Player[] players = {Bukkit.getPlayer(playeruuid[0]), Bukkit.getPlayer(playeruuid[1])};
            String[] formats = {User.getFormat(User.getRank(playeruuid[0]))[1], User.getFormat(User.getRank(playeruuid[1]))[1]};
            actionbar[room] = formats[0] + players[0].getName() + " §e0 §8x §e0 " + formats[1] + players[1].getName();
            World world = Bukkit.getWorld("StickDuel");
            int[] Maploc = MapLocations(room);
            double z = Core.roomToBed(room);
            players[0].teleport(new Location(world, Maploc[0] + 0.5, 101, z + 0.5 + Maploc[1], 90, 0));
            players[1].teleport(new Location(world, Maploc[0] * -1 + 0.5, 101, z + 0.5 - Maploc[1], 270, 0));
            for (Player p : players) {
                p.setGameMode(GameMode.SURVIVAL);
                UUID id = p.getUniqueId();
                int lang = User.getLang(id);
                p.sendMessage(StringList.StickDuelOpponentFound[lang]);
                Lobby.DeleteChallenge(id);
                Core.PlayerRoom.put(id, room);
                Core.PlayerMap.put(id, map);
                Core.QuitAllGame(id);
                Core.QuitAllQueue(id);
                Core.PlacedBlocks.put(id, 0);
                Integer[] ints = Core.integerCache.get(id);
                Inventory inventory = p.getInventory();
                inventory.clear();
                inventory.setItem(ints[0], ItemStackList.Sticks[ints[3]][lang]);
                inventory.setItem(ints[1], ItemStackList.Blocks[ints[4]][lang]);
                inventory.setItem(ints[2], ItemStackList.Pickaxe[lang]);
                PlayerUtils.sendActionBar(((CraftPlayer) p).getHandle().playerConnection, actionbar[room]);
                Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                Objective objective = scoreboard.registerNewObjective("sidebar", "dummy");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                String[] sidebar = StringList.StickDuelSideBar[lang];
                objective.setDisplayName(sidebar[5].replace("%h%", "").replace("%m%", "00:").replace("%s%", "00"));
                SideBarAPI.sidebarTeams(scoreboard, new String[][]{
                        {sidebar[4]},
                        {sidebar[3]},
                        {"§2§f", formats[0] + players[0].getName() + sidebar[1] + "§e0"},
                        {"§1§f", formats[1] + players[1].getName() + sidebar[1] + "§e0"},
                        {sidebar[0]}
                }, new int[]{4, 3, 2, 1, 0});
                p.setScoreboard(scoreboard);
            }

            Blocks.put(room, new HashSet<>());
            Score.put(room, new Integer[]{0, 0});
            Spectators.put(room, new HashSet<>());
            Kill.put(room, new Double[]{0D, 0D});
            int r = room;
            TimerTask.put(room, new TimerTask() {
                int x = 0;

                @Override
                public void run() {
                    if (!Rooms[r]) {
                        Time.put(r, x);
                        Set<UUID> targets = new HashSet<>();
                        targets.addAll(Arrays.asList(Players.get(r)));
                        targets.addAll(Spectators.get(r));
                        for (UUID id : targets) {
                            if (id != null) {
                                Player p = Bukkit.getPlayer(id);
                                p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(StringList.StickDuelSideBar[User.getLang(id)][5].replace("%h%", x / 3600 == 0 ? "" : x / 3600 + ":").replace("%m%", String.format("%02d", (x % 3600) / 60) + ":").replace("%s%", String.format("%02d", x % 60)));
                                PlayerUtils.sendActionBar(((CraftPlayer) p).getHandle().playerConnection, actionbar[r]);
                            }
                        }
                        x++;
                    }
                }
            });
            new Timer().schedule(TimerTask.get(room), 0, 1000);
            Rooms[room] = false;
//            int size = MLGRush.specMenu2x1.size();
//            int size2 = MLGRush.specMenustick.size();
//            int size3 = MLGRush.specMenu4x1.size();
//            MLGRush.specMenustick.put(r, size + size2);
//            for (int i = 0; i < 4; i++) {
//                Inventory inventory = MLGRush.specinventory[i];
//                int length = size + size2;
//
//                for (int j = size + size2 + size3; j > length; j--) {
//                    inventory.setItem(j, inventory.getItem(j - 1));
//                }
//                ItemStack stack = new ItemStack(StickDuel.mapsymbols[Map[room]]);
//                ItemMeta meta = stack.getItemMeta();
//                meta.setDisplayName("StickDuel-" + r);
//                List<String> lore = new ArrayList<>();
//                lore.add(players[0].getName());
//                lore.add(players[1].getName());
//                meta.setLore(lore);
//                stack.setItemMeta(meta);
//                MLGRush.specinventory[i].setItem(length, stack);
//            }
//            for (Player p : Bukkit.getWorld("world").getPlayers()) {
//                if (p.getOpenInventory().getTopInventory().getHolder() == Core.specHolder)
//                    p.updateInventory();
//            }
        });
    }

    public static void GameEnd(UUID uuid) {
        int room = Core.PlayerRoom.get(uuid);
        UUID[] uuids = Players.get(room);
        int quitter = uuids[0].equals(uuid) ? 1 : 0;
        for (UUID id : uuids) {
            Core.PlayerMap.remove(id);
            Core.PlayerRoom.remove(id);
            Core.PlacedBlocks.remove(id);
        }
        Player[] players = {Bukkit.getPlayer(uuids[0]), Bukkit.getPlayer(uuids[1])};
        players[quitter].sendMessage(StringList.StickDuelOpponentDisconnect[User.getLang(uuids[quitter])]);
        ResetBlocks(room);
        Players.remove(room);
        TimerTask.get(room).cancel();
        TimerTask.remove(room);
        Blocks.remove(room);
        Score.remove(room);
        Time.remove(room);
        Location lb = new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0);
        players[quitter].teleport(lb);
        for (UUID id : Spectators.get(room)) {
            Player p = Bukkit.getPlayer(id);
            for (Player pp : players) {
                pp.showPlayer(p);
            }
            p.setAllowFlight(false);
            p.sendMessage(StringList.MLGRushGameEnd[User.getLang(id)]);
            p.teleport(lb);
            Core.Spectate.remove(id);
        }
        Spectators.remove(room);
//        int key = -1;
//        if (MLGRush.specMenustick.containsKey(room))
//            key = MLGRush.specMenustick.get(room);
//        for (Map.Entry<Integer, Integer> entry : MLGRush.specMenustick.entrySet()) {
//            if (entry.getValue() > key)
//                MLGRush.specMenustick.put(entry.getKey(), entry.getValue() - 1);
//        }
//        int size = MLGRush.specMenu2x1.size();
//        int size2 = MLGRush.specMenustick.size();
//        int size3 = MLGRush.specMenu4x1.size();
//        int length = size + size2 + size3;
//        ItemStack air = new ItemStack(Material.AIR);
//        for (int i = 0; i < 4; i++) {
//            Inventory inventory = MLGRush.specinventory[i];
//            inventory.setItem(key, air);
//            for (int j = key; j < length; j++) {
//                inventory.setItem(j, inventory.getItem(j + 1));
//            }
//        }
//        MLGRush.specMenustick.remove(room);
        Rooms[room] = true;
    }
}
