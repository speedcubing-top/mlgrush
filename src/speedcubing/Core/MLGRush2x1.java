package speedcubing.Core;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scoreboard.DisplaySlot;
import speedcubing.All.Lobby;
import speedcubing.BukkitEventListener.EntityDamageByEntity;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;
import speedcubing.Main;
import speedcubing.lib.bukkit.PlayerUtils;
import speedcubing.lib.bukkit.SideBar;
import speedcubing.lib.bukkit.entity.Hologram;
import speedcubing.server.libs.GlobalString;
import speedcubing.server.libs.User;
import speedcubing.server.speedcubingServer;

import java.util.*;

public class MLGRush2x1 {
    public static Inventory[] InventoryMap2x1 = new Inventory[4];
    public static final InventoryHolder Map2x1Holder = () -> null;

    public static final int roomSize = 201;
    public static final int maps = 2;
    public static UUID Queue = null;

    public static boolean[] Rooms = new boolean[roomSize];
    public static final Set<UUID> QueueCoolDown = new HashSet<>();
    static String[] actionbar = new String[roomSize];
    public static final Map<Integer, UUID[]> Players = new HashMap<>();
    public static final Map<Integer, UUID[]> Players2 = new HashMap<>();
    public static final Map<Integer, Integer[]> Score = new HashMap<>();
    public static final Map<Integer, Set<UUID>> Spectators = new HashMap<>();
    public static final Map<Integer, Double[]> Kill = new HashMap<>();
    public static final Map<Integer, TimerTask> TimerTask = new HashMap<>();
    public static final Map<Integer, Set<BlockPosition>> Blocks = new HashMap<>();
    public static final Map<Integer, Integer> Time = new HashMap<>();
    public static final Map<Integer, Boolean[]> Preference = new HashMap<>();

    public static HashMap<BlockPosition, Integer> Diff = new HashMap<>();
    public static int id = 0;

    public static Material[][] mapsymbols = {{Material.STICK, Material.SANDSTONE}, {Material.STICK}};

    public static int[] MapLocations(int map) {
        switch (map) {
            case 0:
                return new int[]{13, 99, 112, 0};
            case 1:
                return null;
        }
        return null;
    }

    public void Load() {
        for (int i = 0; i < roomSize; i++) {
            Rooms[i] = true;
        }
        org.bukkit.World world = Bukkit.getWorld("MLGRush2x1");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), () -> {
            Set<BlockPosition> ba = new HashSet<>();
            for (Map.Entry<BlockPosition, Integer> x : Diff.entrySet()) {
                int age = x.getValue();
                if (age == 100) {
                    BlockPosition b = x.getKey();
                    world.getBlockAt(b.getX(), b.getY(), b.getZ()).setType(Material.REDSTONE_BLOCK);
                } else if (age == 120) {
                    BlockPosition b = x.getKey();
                    world.getBlockAt(b.getX(), b.getY(), b.getZ()).setType(Material.AIR);
                    ba.add(b);
                }
            }
            for (BlockPosition b : ba) {
                Diff.remove(b);
            }
            for (BlockPosition x : Diff.keySet()) {
                Diff.put(x, Diff.get(x) + 1);
            }
        }, 0, 1);
    }

    public static void queue(Player player) {
        UUID uuid = player.getUniqueId();
        if (!QueueCoolDown.contains(uuid)) {
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
            QueueCoolDown.add(uuid);
            Hologram stand = EntityList.Lobbys[4];
            if (Queue == null) {
                Core.QuitAllQueue(uuid);
                player.closeInventory();
                player.sendMessage(StringList.MLGRushQueue[User.getUser(uuid).lang]);
                Queue = uuid;
                stand.setName("§a1/2");
            } else {
                stand.setName("§a0/2");
                if (Queue.equals(uuid))
                    player.sendMessage(StringList.MLGRushUnQueue[User.getUser(uuid).lang]);
                else
                    gameStart(uuid, Queue, 0);
                Queue = null;
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    QueueCoolDown.remove(uuid);
                }
            }, 2000);
        }
    }

    public static void resetBlocks(int room) {
        World world = Bukkit.getWorld("MLGRush2x1");
        for (BlockPosition pos : Blocks.get(room)) {
            Diff.remove(pos);
            world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()).setType(Material.AIR);
        }
        Blocks.get(room).clear();
    }

    public static void joinSpec(Player player, int room) {
        UUID uuid = player.getUniqueId();
        int lang = User.getUser(uuid).lang;
        Core.QuitAllGame(uuid);
        Core.QuitAllQueue(uuid);
        player.teleport(new Location(Bukkit.getWorld("MLGRush2x1"), 0.5, 110, Core.roomToBed(room), 0, 0));
        int x = Time.get(room);
        UUID[] playeruuid = Players.get(room);
        String[] sidebar = StringList.MLGRushSideBar[lang];
        Integer[] score = Score.get(room);
        String[] suffix = (score[0].equals(score[1])) ? new String[]{"§e" + score[0], "§e" + score[1]} : (score[0] > score[1] ? new String[]{"§a" + score[0], "§c" + score[1]} : new String[]{"§c" + score[0], "§a" + score[1]});
        new SideBar(player, sidebar[0].replace("%h%", x / 3600 == 0 ? "" : x / 3600 + ":").replace("%m%", String.format("%02d", (x % 3600) / 60) + ":").replace("%s%", String.format("%02d", x % 60)))
                .staticLine(sidebar[1], 4)
                .staticLine(sidebar[2], 3)
                .changeableLine(speedcubingServer.getFormat(User.getUser(playeruuid[0]).rank)[1] + Bukkit.getPlayer(playeruuid[0]).getName() + sidebar[3] + suffix[0], 2)
                .changeableLine(speedcubingServer.getFormat(User.getUser(playeruuid[1]).rank)[1] + Bukkit.getPlayer(playeruuid[1]).getName() + sidebar[4] + suffix[1], 1)
                .staticLine(sidebar[5], 0).create();
        player.getInventory().clear();
        player.getInventory().setItem(8, ItemStackList.HubSlime[lang]);
        Set<UUID> targets = new HashSet<>();
        targets.addAll(Spectators.get(room));
        targets.addAll(Arrays.asList(playeruuid));
        String name = speedcubingServer.getFormat(User.getUser(uuid).rank)[1] + player.getName();
        for (UUID id : targets) {
            Bukkit.getPlayer(id).sendMessage(StringList.JoinSpectate[User.getUser(id).lang].replace("%player%", name));
        }
        Spectators.get(room).add(uuid);
        Core.Spectate.put(uuid, room);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
            player.setAllowFlight(true);
            player.setFlying(true);
        }, 1);

    }


    public static void breakBed(Player breaker, int bedX) {
        UUID uuid = breaker.getUniqueId();
        int room = Core.PlayerRoom.get(uuid);
        UUID[] playeruuid = Players.get(room);
        UUID[] playeruuid2 = Players2.get(room);
        Player[] players = {Bukkit.getPlayer(playeruuid[0]), Bukkit.getPlayer(playeruuid[1])};
        Player[] players2 = {Bukkit.getPlayer(playeruuid2[0]), Bukkit.getPlayer(playeruuid2[1])};
        if (bedX / Math.abs(bedX) == (players2[0] == breaker ? 1 : -1))
            breaker.sendMessage(StringList.MLGRushBreakOwnBed[User.getUser(uuid).lang]);
        else {
            resetBlocks(room);
            int w = players[0] == breaker ? 0 : 1;
            Score.get(room)[w] += 1;
            Integer[] score = Score.get(room);
            String[] formats = {speedcubingServer.getFormat(User.getUser(playeruuid[0]).rank)[1] + players[0].getName(), speedcubingServer.getFormat(User.getUser(playeruuid[1]).rank)[1] + players[1].getName()};
            Set<UUID> targets = new HashSet<>();
            targets.addAll(Spectators.get(room));
            targets.addAll(Arrays.asList(playeruuid));
            String breakerName = speedcubingServer.getFormat(User.getUser(breaker.getUniqueId()).rank)[1] + breaker.getName();
            String name2 = speedcubingServer.getFormat(User.getUser(playeruuid[1 - w]).rank)[1] + players[1 - w].getName();
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
                int lang = User.getUser(id).lang;
                String[] sidebar = StringList.MLGRushSideBar[lang];
                SideBar.getSidebar(p)
                        .setLine(formats[0] + sidebar[3] + suffix[0], 3)
                        .setLine(formats[1] + sidebar[4] + suffix[1], 4);
                p.sendMessage(StringList.MLGRushBedbreak[lang].replace("%player%", name2).replace("%player2%", breakerName));
                PlayerUtils.sendActionBar(p, actionbar[room]);
            }

            int[] Map = MapLocations(Core.PlayerMap.get(uuid));
            int t = 1;
            for (Player p : players2) {
                Core.PlacedBlocks.put(p.getUniqueId(), 0);
                p.teleport(new Location(p.getWorld(), 0.5 + Map[0] * t, Map[1] + 1, 0.5 + Core.roomToBed(room) + t * Map[3], 180 - 90 * t, 0));
                Core.resetItems(p);
                t -= 2;
            }
        }
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
            Bukkit.getPlayer(uuid1).sendMessage(GlobalString.NoEnoughServers[User.getUser(uuid1).lang]);
            Bukkit.getPlayer(uuid2).sendMessage(GlobalString.NoEnoughServers[User.getUser(uuid2).lang]);
            return;
        }
        Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () -> {
            int room = -1;
            for (int h = 0; h < roomSize; h++) {
                if (Rooms[h]) {
                    room = h;
                    break;
                }
            }
            Players.put(room, new Random().nextBoolean() ? new UUID[]{uuid1, uuid2} : new UUID[]{uuid2, uuid1});
            Players2.put(room, new Random().nextBoolean() ? new UUID[]{uuid1, uuid2} : new UUID[]{uuid2, uuid1});
            UUID[] playeruuid = Players.get(room);
            UUID[] playeruuid2 = Players2.get(room);
            Player[] players = {Bukkit.getPlayer(playeruuid[0]), Bukkit.getPlayer(playeruuid[1])};
            String[] formats = {speedcubingServer.getFormat(User.getUser(playeruuid[0]).rank)[1], speedcubingServer.getFormat(User.getUser(playeruuid[1]).rank)[1]};
            actionbar[room] = formats[0] + players[0].getName() + " §e0 §8x §e0 " + formats[1] + players[1].getName();
            org.bukkit.World world = Bukkit.getWorld("MLGRush2x1");
            int[] Map = MapLocations(map);
            double z = Core.roomToBed(room);
            Bukkit.getPlayer(playeruuid2[0]).teleport(new Location(world, Map[0] + 0.5, Map[1] + 1, z + 0.5 + Map[3], 90, 0));
            Bukkit.getPlayer(playeruuid2[1]).teleport(new Location(world, Map[0] * -1 + 0.5, Map[1] + 1, z + 0.5 - Map[3], 270, 0));
            int[] settings = new int[5];
            for (Player p : players) {
                p.setGameMode(GameMode.SURVIVAL);
                UUID id = p.getUniqueId();
                int lang = User.getUser(id).lang;
                p.sendMessage(StringList.MLGRushOpponentFound[lang]);
                Lobby.DeleteChallenge(id);
                Core.PlayerRoom.put(id, room);
                Core.PlayerMap.put(id, map);
                Core.QuitAllGame(id);
                Core.QuitAllQueue(id);
                Core.PlacedBlocks.put(id, 0);
                Integer[] ints = Core.integerCache.get(id);
                settings[0] += ints[5];
                settings[1] += ints[6];
                settings[2] += ints[7];
                settings[3] += ints[8];
                settings[4] += ints[9];
                Inventory inventory = p.getInventory();
                inventory.clear();
                inventory.setItem(ints[0], ItemStackList.Sticks[ints[3]][lang]);
                inventory.setItem(ints[1], ItemStackList.Blocks[ints[4]][lang]);
                inventory.setItem(ints[2], ItemStackList.Pickaxe[lang]);
                PlayerUtils.sendActionBar(p, actionbar[room]);
                String[] sidebar = StringList.MLGRushSideBar[lang];
                new SideBar(p, sidebar[0].replace("%h%", "").replace("%m%", "00:").replace("%s%", "00"))
                        .staticLine(sidebar[1], 4)
                        .staticLine(sidebar[2], 3)
                        .changeableLine(formats[0] + players[0].getName() + sidebar[3] + "§e0", 2)
                        .changeableLine(formats[1] + players[1].getName() + sidebar[4] + "§e0", 1)
                        .staticLine(sidebar[5], 0).create();
            }

            Preference.put(room, new Boolean[]{settings[0] > 1, settings[1] > 1, settings[2] > 1, settings[3] > 1, settings[4] > 1});
            if (settings[1] > 1) {
                for (Player p : players) {
                    p.setMaximumNoDamageTicks(0);
                }
            }
            if (settings[4] > 1) {
                EntityDamageByEntity.oldKb.add(uuid1);
                EntityDamageByEntity.oldKb.add(uuid2);
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
                    Time.put(r, x);
                    Set<UUID> targets = new HashSet<>();
                    targets.addAll(Arrays.asList(playeruuid));
                    targets.addAll(Spectators.get(r));
                    for (UUID id : targets) {
                        if (id != null) {
                            Player p = Bukkit.getPlayer(id);
                            PlayerUtils.sendActionBar(p, actionbar[r]);
                            p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(StringList.MLGRushSideBar[User.getUser(id).lang][0].replace("%h%", x / 3600 == 0 ? "" : x / 3600 + ":").replace("%m%", String.format("%02d", (x % 3600) / 60) + ":").replace("%s%", String.format("%02d", x % 60)));
                        }
                    }
                    x++;
                }
            });
            new Timer().schedule(TimerTask.get(r), 0, 1000);
            Rooms[r] = false;
//            int size = Core.specMenu2x1.size();
//            int size2 = Core.specMenustick.size();
//            int size3 = Core.specMenu4x1.size();
//            Core.specMenu2x1.put(r, size);
//            for (int i = 0; i < 4; i++) {
//                Inventory inventory = ItemStackList.specinventory[i];
//                int length = size + size2 + size3;
//                for (int j = length; j > size; j--) {
//                    inventory.setItem(j, inventory.getItem(j - 1));
//                }
//                ItemStack stack = new ItemStack(mapsymbols[0][r == 0 ? 0 : 1]);
//                ItemMeta meta = stack.getItemMeta();
//                meta.setDisplayName("MLGRush-" + r);
//                List<String> lore = new ArrayList<>();
//                lore.add(players[0].getName());
//                lore.add(players[1].getName());
//                meta.setLore(lore);
//                stack.setItemMeta(meta);
//                ItemStackList.specinventory[i].setItem(size, stack);
//            }
//            for (Player p : Bukkit.getWorld("world").getPlayers()) {
//                if (p.getOpenInventory().getTopInventory().getHolder() == Core.specHolder)
//                    p.updateInventory();
//            }
        });
    }

    public static void gameEnd(UUID uuid) {
        int room = Core.PlayerRoom.get(uuid);
        UUID[] uuids = Players.get(room);
        int quitter = uuids[0].equals(uuid) ? 1 : 0;
        for (UUID id : uuids) {
            EntityDamageByEntity.oldKb.remove(id);
            Core.PlayerMap.remove(id);
            Core.PlayerRoom.remove(id);
            Core.PlacedBlocks.remove(id);
        }
        Player[] players = {Bukkit.getPlayer(uuids[0]), Bukkit.getPlayer(uuids[1])};
        players[quitter].sendMessage(StringList.MLGRushOpponentDisconnect[User.getUser(uuids[quitter]).lang]);
        resetBlocks(room);
        Players.remove(room);
        Players2.remove(room);
        TimerTask.get(room).cancel();
        TimerTask.remove(room);
        Blocks.remove(room);
        Score.remove(room);
        Time.remove(room);
        Preference.remove(room);
        Kill.remove(room);
        Location lb = new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0);
        players[quitter].teleport(lb);
        for (UUID id : Spectators.get(room)) {
            Player p = Bukkit.getPlayer(id);
            for (Player pp : players) {
                pp.showPlayer(p);
            }
            p.setAllowFlight(false);
            p.sendMessage(StringList.MLGRushGameEnd[User.getUser(id).lang]);
            p.teleport(lb);
            Core.Spectate.remove(id);
        }
        Spectators.remove(room);
//        Core.specMenu2x1.remove(room);
//        int key = -1;
//        if (Core.specMenu2x1.containsKey(room))
//            key = Core.specMenu2x1.get(room);
//        for (Map.Entry<Integer, Integer> entry : Core.specMenu2x1.entrySet()) {
//            if (entry.getValue() > key)
//                Core.specMenu2x1.put(entry.getKey(), entry.getValue() - 1);
//        }
//        int size = Core.specMenu2x1.size();
//        int size2 = Core.specMenustick.size();
//        int size3 = Core.specMenu4x1.size();
//        for (int i = 0; i < 4; i++) {
//            Inventory inventory = ItemStackList.specinventory[i];
//            inventory.setItem(key, new ItemStack(Material.AIR));
//            int length = size + size2 + size3;
//            for (int j = key; j < length; j++) {
//                inventory.setItem(j, inventory.getItem(j + 1));
//            }
//        }
//        for (Player p : Bukkit.getWorld("world").getPlayers()) {
//            if (p.getOpenInventory().getTopInventory().getHolder() == Core.specHolder) {
//                p.updateInventory();
//            }
//        }
        Rooms[room] = true;
    }

    public static void fell(Player player) {
        UUID uuid = player.getUniqueId();
        String[] format = speedcubingServer.getFormat(User.getUser(uuid).rank);
        int z = Core.randomToBed(player.getLocation().getZ());
        int room = Core.PlayerRoom.get(uuid);
        Core.PlacedBlocks.put(player.getUniqueId(), 0);
        if (Preference.get(room)[0])
            resetBlocks(room);
        int[] Map = MapLocations(Core.PlayerMap.get(uuid));
        UUID[] uuids = Players2.get(room);
        int[] t = Bukkit.getPlayer(uuids[0]) == player ? new int[]{0, 1} : new int[]{1, -1};
        Set<UUID> targets = new HashSet<>();
        targets.addAll(Spectators.get(room));
        targets.addAll(Arrays.asList(uuids));
        if (System.currentTimeMillis() - Kill.get(room)[t[0]] < 2500) {
            Player killer = Bukkit.getPlayer(uuids[1 - t[0]]);
            killer.playSound(killer.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
            String[] format1 = speedcubingServer.getFormat(User.getUser(killer.getUniqueId()).rank);
            for (UUID id : targets) {
                Bukkit.getPlayer(id).sendMessage(StringList.MLGRushKill[User.getUser(id).lang]
                        .replace("%format0%", format[1])
                        .replace("%name0%", player.getName())
                        .replace("%format1%", format1[1])
                        .replace("%name1%", killer.getName()));
            }
        } else {
            for (UUID id : targets) {
                Bukkit.getPlayer(id).sendMessage(StringList.MLGRushFell[User.getUser(id).lang].replace("%format0%", format[0]).replace("%name%", player.getName()));
            }
        }
        WorldServer mlgrush2x1 = ((CraftWorld) Bukkit.getWorld("MLGRush2x1")).getHandle();
        for (int y = Map[2]; y >= Map[1]; y--) {
            if (mlgrush2x1.getType(new BlockPosition(Map[0] * t[1], y, z + t[1] * Map[3])) != net.minecraft.server.v1_8_R3.Blocks.AIR.getBlockData()) {
                player.teleport(new Location(player.getWorld(), 0.5 + Map[0] * t[1], y + 1, z + 0.5 + t[1] * Map[3], 180 - 90 * t[1], 0));
                break;
            }
        }
        Core.resetItems(player);
    }
}
