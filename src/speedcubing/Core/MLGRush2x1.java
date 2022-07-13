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
import speedcubing.Core.MLGRushCategory.MLGRushDuels;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;
import speedcubing.Main;
import speedcubing.Member;
import speedcubing.lib.bukkit.PlayerUtils;
import speedcubing.lib.bukkit.SideBar;
import speedcubing.lib.bukkit.entity.Hologram;
import speedcubing.server.libs.User;
import speedcubing.server.speedcubingServer;

import java.util.*;

public class MLGRush2x1 extends MLGRushDuels {
    public static Inventory[] InventoryMap2x1 = new Inventory[4];
    public static final InventoryHolder Map2x1Holder = () -> null;

    public static Member Queue = null;

    public static boolean[] Rooms = new boolean[100];
    public static final Set<Member> QueueCoolDown = new HashSet<>();

    public static HashMap<BlockPosition, Integer> Diff = new HashMap<>();
    public static int id = 0;

    public static Material[][] mapsymbols = {{Material.STICK, Material.SANDSTONE}, {Material.STICK}};
    public final Member[] Players2;
    public boolean[] Preference;

    public static void Load() {
        for (int i = 0; i < 100; i++) {
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

    public MLGRush2x1(Member member1, Member member2, int map) {
        int room = -1;
        for (int h = 0; h < 100; h++) {
            if (Rooms[h]) {
                room = h;
                break;
            }
        }
        Rooms[room] = false;
        this.room = room;
        this.map = map;
        Players = new Random().nextBoolean() ? new Member[]{member1, member2} : new Member[]{member2, member1};
        Players2 = new Random().nextBoolean() ? new Member[]{member1, member2} : new Member[]{member2, member1};
        Player[] players = {Players[0].player, Players[1].player};
        String[] formats = {speedcubingServer.getFormat(User.getUser(players[0]).rank)[1], speedcubingServer.getFormat(User.getUser(players[1]).rank)[1]};
        actionbar = formats[0] + players[0].getName() + " §e0 §8x §e0 " + formats[1] + players[1].getName();
        World world = Bukkit.getWorld("MLGRush2x1");
        int[] Map = MapLocations(map);
        double z = Core.roomToBed(room);
        Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () -> {
            Players2[0].player.teleport(new Location(world, Map[0] + 0.5, Map[1] + 1, z + 0.5 + Map[3], 90, 0));
            Players2[1].player.teleport(new Location(world, Map[0] * -1 + 0.5, Map[1] + 1, z + 0.5 - Map[3], 270, 0));
            int[] settings = new int[5];
            for (Member p : Players) {
                p.game = this;
                p.player.setGameMode(GameMode.SURVIVAL);
                p.player.sendMessage(StringList.MLGRushOpponentFound[p.lang]);
                Lobby.DeleteChallenge(p);
//                Core.QuitAllGame(p);
                p.QuitAllQueue();
                p.inGame = true;
                p.PlacedBlocks = 0;
                int[] ints = p.integerCache;
                settings[0] += ints[5];
                settings[1] += ints[6];
                settings[2] += ints[7];
                settings[3] += ints[8];
                settings[4] += ints[9];
                Inventory inventory = p.player.getInventory();
                inventory.clear();
                inventory.setItem(ints[0], ItemStackList.Sticks[ints[3]][p.lang]);
                inventory.setItem(ints[1], ItemStackList.Blocks[ints[4]][p.lang]);
                inventory.setItem(ints[2], ItemStackList.Pickaxe[p.lang]);
                PlayerUtils.sendActionBar(p.player, actionbar);
                String[] sidebar = StringList.MLGRushSideBar[p.lang];
                new SideBar(p.player, sidebar[0].replace("%h%", "").replace("%m%", "00:").replace("%s%", "00"))
                        .staticLine(sidebar[1], 4)
                        .staticLine(sidebar[2], 3)
                        .changeableLine(formats[0] + players[0].getName() + sidebar[3] + "§e0", 2)
                        .changeableLine(formats[1] + players[1].getName() + sidebar[4] + "§e0", 1)
                        .staticLine(sidebar[5], 0).create();
            }

            Preference = new boolean[]{settings[0] > 1, settings[1] > 1, settings[2] > 1, settings[3] > 1, settings[4] > 1};
            if (settings[1] > 1) {
                for (Player p : players) {
                    p.setMaximumNoDamageTicks(0);
                }
            }
            if (settings[4] > 1) {
                Players[0].oldKb = true;
                Players[1].oldKb = true;
            }
            TimerTask = new TimerTask() {
                int x = 0;
                @Override
                public void run() {
                    Time = x;
                    Set<Member> targets = new HashSet<>();
                    targets.addAll(Arrays.asList(Players));
                    targets.addAll(Spectators);
                    for (Member m : targets) {
                        if (m != null) {
                            PlayerUtils.sendActionBar(m.player, actionbar);
                            m.player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(StringList.MLGRushSideBar[m.lang][0].replace("%h%", x / 3600 == 0 ? "" : x / 3600 + ":").replace("%m%", String.format("%02d", (x % 3600) / 60) + ":").replace("%s%", String.format("%02d", x % 60)));
                        }
                    }
                    x++;
                }
            };
            new Timer().schedule(TimerTask, 0, 1000);
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

    public static int[] MapLocations(int map) {
        switch (map) {
            case 0:
                return new int[]{13, 99, 112, 0};
            case 1:
                return null;
        }
        return null;
    }

    public static void queue(Player player) {
        Member member = Member.getMember(player);
        if (!QueueCoolDown.contains(member)) {
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
            QueueCoolDown.add(member);
            Hologram stand = EntityList.Lobbys[4];
            if (Queue == null) {
                member.QuitAllQueue();
                player.closeInventory();
                player.sendMessage(StringList.MLGRushQueue[User.getUser(player).lang]);
                Queue = member;
                stand.setName("§a1/2");
            } else {
                stand.setName("§a0/2");
                if (Queue.equals(member))
                    player.sendMessage(StringList.MLGRushUnQueue[User.getUser(player).lang]);
                else
                    new MLGRush2x1(member, Queue, 0);
                Queue = null;
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    QueueCoolDown.remove(member);
                }
            }, 2000);
        }
    }

    public void resetBlocks() {
        World world = Bukkit.getWorld("MLGRush2x1");
        for (BlockPosition pos : Blocks) {
            Diff.remove(pos);
            world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()).setType(Material.AIR);
        }
        Blocks.clear();
    }

    public void joinSpec(Player player) {
        UUID uuid = player.getUniqueId();
        Member member = Member.getMember(player);
        Core.QuitAllGame(uuid);
        member.QuitAllQueue();
        player.teleport(new Location(Bukkit.getWorld("MLGRush2x1"), 0.5, 110, Core.roomToBed(room), 0, 0));
        String[] sidebar = StringList.MLGRushSideBar[member.lang];
        String[] suffix = (Score[0] == Score[1]) ? new String[]{"§e" + Score[0], "§e" + Score[1]} : (Score[0] > Score[1] ? new String[]{"§a" + Score[0], "§c" + Score[1]} : new String[]{"§c" + Score[0], "§a" + Score[1]});
        new SideBar(player, sidebar[0].replace("%h%", Time / 3600 == 0 ? "" : Time / 3600 + ":").replace("%m%", String.format("%02d", (Time % 3600) / 60) + ":").replace("%s%", String.format("%02d", Time % 60)))
                .staticLine(sidebar[1], 4)
                .staticLine(sidebar[2], 3)
                .changeableLine(speedcubingServer.getFormat(Players[0].rank)[1] + Players[0].player.getName() + sidebar[3] + suffix[0], 2)
                .changeableLine(speedcubingServer.getFormat(Players[1].rank)[1] + Players[1].player.getName() + sidebar[4] + suffix[1], 1)
                .staticLine(sidebar[5], 0).create();
        player.getInventory().clear();
        player.getInventory().setItem(8, ItemStackList.HubSlime[member.lang]);
        Set<Member> targets = new HashSet<>();
        targets.addAll(Spectators);
        targets.addAll(Arrays.asList(Players));
        String name = speedcubingServer.getFormat(User.getUser(player).rank)[1] + player.getName();
        for (Member m : targets) {
            m.player.sendMessage(StringList.JoinSpectate[m.lang].replace("%player%", name));
        }
        Spectators.add(member);
        member.Spectate = room;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
            player.setAllowFlight(true);
            player.setFlying(true);
        }, 1);

    }

    public void fell(Player player) {
        Member member = Member.getMember(player);
        String[] format = speedcubingServer.getFormat(User.getUser(player).rank);
        int z = Core.randomToBed(player.getLocation().getZ());
        member.PlacedBlocks = 0;
        if (Preference[0])
            resetBlocks();
        int[] t = Players2[0].player == player ? new int[]{0, 1} : new int[]{1, -1};
        Set<Member> targets = new HashSet<>();
        targets.addAll(Spectators);
        targets.addAll(Arrays.asList(Players2));
        if (System.currentTimeMillis() - Kill[t[0]] < 2500) {
            Player killer = Players2[1 - t[0]].player;
            killer.playSound(killer.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
            String[] format1 = speedcubingServer.getFormat(Players2[1 - t[0]].rank);
            for (Member m : targets) {
                m.player.sendMessage(StringList.MLGRushKill[m.lang]
                        .replace("%format0%", format[1])
                        .replace("%name0%", player.getName())
                        .replace("%format1%", format1[1])
                        .replace("%name1%", killer.getName()));
            }
        } else
            for (Member m : targets) {
                m.player.sendMessage(StringList.MLGRushFell[m.lang].replace("%format0%", format[0]).replace("%name%", player.getName()));
            }
        WorldServer mlgrush2x1 = ((CraftWorld) Bukkit.getWorld("MLGRush2x1")).getHandle();
        int[] Map = MapLocations(map);
        for (int y = Map[2]; y >= Map[1]; y--) {
            if (mlgrush2x1.getType(new BlockPosition(Map[0] * t[1], y, z + t[1] * Map[3])) != net.minecraft.server.v1_8_R3.Blocks.AIR.getBlockData()) {
                player.teleport(new Location(player.getWorld(), 0.5 + Map[0] * t[1], y + 1, z + 0.5 + t[1] * Map[3], 180 - 90 * t[1], 0));
                break;
            }
        }
        Core.resetItems(player);
    }


    public void breakBed(Player breaker, int bedX) {
        Player[] players = {Players[0].player, Players[1].player};
        Player[] players2 = {Players2[0].player, Players2[1].player};
        if (bedX / Math.abs(bedX) == (players2[0] == breaker ? 1 : -1))
            breaker.sendMessage(StringList.MLGRushBreakOwnBed[User.getUser(breaker).lang]);
        else {
            resetBlocks();
            int w = players[0] == breaker ? 0 : 1;
            Score[w] += 1;
            String[] formats = {speedcubingServer.getFormat(Players[0].rank)[1] + players[0].getName(), speedcubingServer.getFormat(Players[1].rank)[1] + players[1].getName()};
            Set<Member> targets = new HashSet<>();
            targets.addAll(Spectators);
            targets.addAll(Arrays.asList(Players));
            String breakerName = speedcubingServer.getFormat(User.getUser(breaker).rank)[1] + breaker.getName();
            String name2 = speedcubingServer.getFormat(Players[1 - w].rank)[1] + players[1 - w].getName();
            String[] suffix = new String[2];
            if (Score[0] == Score[1]) {
                actionbar = formats[0] + " §e" + Score[0] + " §8x §e" + Score[1] + " " + formats[1];
                suffix[0] = "§e" + Score[0];
                suffix[1] = "§e" + Score[1];
            } else if (Score[0] > Score[1]) {
                actionbar = formats[0] + " §a" + Score[0] + " §8x §c" + Score[1] + " " + formats[1];
                suffix[0] = "§a" + Score[0];
                suffix[1] = "§c" + Score[1];
            } else {
                actionbar = formats[0] + " §c" + Score[0] + " §8x §a" + Score[1] + " " + formats[1];
                suffix[0] = "§c" + Score[0];
                suffix[1] = "§a" + Score[1];
            }
            for (Member m : Players2) {
                m.PlacedBlocks = 0;
            }
            for (Member m : targets) {
                String[] sidebar = StringList.MLGRushSideBar[m.lang];
                SideBar.getSidebar(m.player)
                        .setLine(formats[0] + sidebar[3] + suffix[0], 3)
                        .setLine(formats[1] + sidebar[4] + suffix[1], 4);
                m.player.sendMessage(StringList.MLGRushBedbreak[m.lang].replace("%player%", name2).replace("%player2%", breakerName));
                PlayerUtils.sendActionBar(m.player, actionbar);
            }
            int[] Map = MapLocations(map);
            int t = 1;
            for (Player p : players2) {
                p.teleport(new Location(p.getWorld(), 0.5 + Map[0] * t, Map[1] + 1, 0.5 + Core.roomToBed(room) + t * Map[3], 180 - 90 * t, 0));
                Core.resetItems(p);
                t -= 2;
            }
        }
    }

    public void gameEnd(Member member) {
        int quitter = Players[0].equals(member) ? 1 : 0;
        for (Member m : Players) {
            m.oldKb = false;
            m.inGame = false;
            m.PlacedBlocks = -1;
        }
        Player[] players = {Players[0].player, Players[1].player};
        players[quitter].sendMessage(StringList.MLGRushOpponentDisconnect[Players[quitter].lang]);
        resetBlocks();
        TimerTask.cancel();
        Location lb = new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0);
        players[quitter].teleport(lb);
        for (Member m : Spectators) {
            for (Player pp : players) {
                pp.showPlayer(m.player);
            }
            m.player.setAllowFlight(false);
            m.player.sendMessage(StringList.MLGRushGameEnd[m.lang]);
            m.player.teleport(lb);
            m.Spectate = -1;
        }
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
}
