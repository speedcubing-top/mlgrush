package top.speedcubing.mlgrush.Core.Game;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import top.speedcubing.lib.bukkit.PlayerUtils;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.WorldUtils;
import top.speedcubing.mlgrush.Core.Core;
import top.speedcubing.mlgrush.List.ItemStackList;
import top.speedcubing.mlgrush.List.StringList;
import top.speedcubing.mlgrush.Main;
import top.speedcubing.mlgrush.Member;
import top.speedcubing.server.libs.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MLGRush2x1 extends Duel {
    public static DuelCore inst = new DuelCore("MLGRush2x1");

    public static Inventory[] InventoryMap2x1 = new Inventory[4];
    public static final InventoryHolder Map2x1Holder = () -> null;
    public static Material[][] mapsymbols = {{Material.STICK, Material.SANDSTONE}, {Material.STICK}};

    public final Member[] Players2;

    public MLGRush2x1(Member member1, Member member2, int map) {
        super(map, "MLGRush2x1");
        MLGRush2x1.inst.lore(map, inst.Rooms[map].size(), true);
        Players = new Random().nextBoolean() ? new Member[]{member1, member2} : new Member[]{member2, member1};
        Players2 = new Random().nextBoolean() ? new Member[]{member1, member2} : new Member[]{member2, member1};
        Player[] players = {Players[0].user.player, Players[1].user.player};
        String[] format = {User.getUser(players[0]).getFormatName(false), User.getUser(players[1]).getFormatName(false)};
        actionbar = format[0] + " §e0 §8x §e0 " + format[1];
        int[] Map = MapLocations(map);
        Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () -> {
            Players2[0].user.bTeleport(new Location(world, 500 * map + Map[0] + 0.5, Map[1] + 1, coord + 0.5 + Map[3] + Map[6], Map[4], 0));
            Players2[1].user.bTeleport(new Location(world, 500 * map + Map[0] * -1 + 0.5, Map[1] + 1, coord + 0.5 - Map[3], Map[5], 0));
            int[] settings = new int[5];
            for (Member p : Players) {
                p.user.bSendMessage(StringList.MLGRushOpponentFound[p.user.lang]);
//                Core.QuitAllGame(p);
                int[] ints = p.integerCache;
                settings[0] += ints[5];
                settings[1] += ints[6];
                settings[2] += ints[7];
                settings[3] += ints[8];
                settings[4] += ints[9];
                Inventory inventory = p.user.bGetInventory();
                inventory.clear();
                inventory.setItem(ints[0], ItemStackList.Sticks[ints[3]][p.user.lang]);
                inventory.setItem(ints[1], ItemStackList.Blocks[ints[4]][p.user.lang]);
                inventory.setItem(ints[2], ItemStackList.Pickaxe[p.user.lang]);
                PlayerUtils.sendActionBar(p.user.player, actionbar);
                String[] sidebar = StringList.MLGRushSideBar[p.user.lang];
                new SideBar(p.user.player, sidebar[0].replace("%t%", "00:00"))
                        .staticLine(sidebar[1], 4)
                        .staticLine(sidebar[2], 3)
                        .changeableLine(format[0] + sidebar[3] + "§e0", 2)
                        .changeableLine(format[1] + sidebar[4] + "§e0", 1)
                        .staticLine(sidebar[5], 0).create();
            }
            initStage2(settings, players, StringList.MLGRushSideBar, Players);
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
                return new int[]{13, 99, 112, 0, 90, -90, 0};
            case 1:
                return new int[]{8, 99, 112, 8, 135, -45, 1};
        }
        return null;
    }

    public void joinSpec(Player player) {
//        UUID uuid = player.getUniqueId();
//        Member member = Member.getMember(player);
//        Core.QuitAllGame(uuid);
//        member.QuitAllQueue();
//        player.teleport(new Location(Bukkit.getWorld("MLGRush2x1"), 0.5, 110, coord, 0, 0));
//        String[] sidebar = StringList.MLGRushSideBar[member.user.lang];
//        String[] suffix = (Score[0] == Score[1]) ? new String[]{"§e" + Score[0], "§e" + Score[1]} : (Score[0] > Score[1] ? new String[]{"§a" + Score[0], "§c" + Score[1]} : new String[]{"§c" + Score[0], "§a" + Score[1]});
//        new SideBar(player, sidebar[0].replace("%h%", Time / 3600 == 0 ? "" : Time / 3600 + ":").replace("%m%", String.format("%02d", (Time % 3600) / 60) + ":").replace("%s%", String.format("%02d", Time % 60)))
//                .staticLine(sidebar[1], 4)
//                .staticLine(sidebar[2], 3)
//                .changeableLine(speedcubingServer.getFormat(Players[0].user.rank)[1] + Players[0].user.bGetName() + sidebar[3] + suffix[0], 2)
//                .changeableLine(speedcubingServer.getFormat(Players[1].user.rank)[1] + Players[1].user.bGetName() + sidebar[4] + suffix[1], 1)
//                .staticLine(sidebar[5], 0).create();
//        player.getInventory().clear();
//        player.getInventory().setItem(8, ItemStackList.HubSlime[member.user.lang]);
//        Set<Member> targets = new HashSet<>();
//        targets.addAll(Spectators);
//        targets.addAll(Arrays.asList(Players));
//        String name = speedcubingServer.getFormat(User.getUser(player).rank)[1] + player.getName();
//        for (Member m : targets) {
//            m.user.bSendMessage(StringList.JoinSpectate[m.user.lang].replace("%player%", name));
//        }
//        Spectators.add(member);
//        member.Spectate = room;
//        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
//            player.setAllowFlight(true);
//            player.setFlying(true);
//        }, 1);

    }

    public void fell(Player player) {
        Member member = Member.getMember(player);
        String[] format = member.user.getFormat();
        member.PlacedBlocks = 0;
        if (Preference[0])
            resetBlocks();
        int[] t = Players2[0].user.player == player ? new int[]{0, 1} : new int[]{1, -1};
        Set<Member> targets = new HashSet<>();
        targets.addAll(Spectators);
        targets.addAll(Arrays.asList(Players2));
        if (System.currentTimeMillis() - Kill[t[0]] < 2500) {
            Player killer = Players2[1 - t[0]].user.player;
            killer.playSound(killer.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
            String[] format1 = Players2[1 - t[0]].user.getFormat();
            for (Member m : targets) {
                m.user.bSendMessage(StringList.MLGRushKill[m.user.lang]
                        .replace("%format0%", format[1])
                        .replace("%name0%", player.getName())
                        .replace("%format1%", format1[1])
                        .replace("%name1%", killer.getName()));
            }
        } else
            for (Member m : targets) {
                m.user.bSendMessage(StringList.MLGRushFell[m.user.lang].replace("%format0%", format[0]).replace("%name%", player.getName()));
            }
        WorldServer mlgrush2x1 = ((CraftWorld) Bukkit.getWorld("MLGRush2x1")).getHandle();
        int[] Map = MapLocations(map);
        for (int y = Map[2]; y >= Map[1]; y--) {
            if (mlgrush2x1.getType(new BlockPosition(map * 500 + Map[0] * t[1], y, coord + t[1] * Map[3])) != net.minecraft.server.v1_8_R3.Blocks.AIR.getBlockData()) {
                player.teleport(new Location(player.getWorld(), map * 500 + 0.5 + Map[0] * t[1], y + 1, coord + 0.5 + t[1] * Map[3] + (t[0] == 0 ? Map[6] : 0), Map[t[0] + 4], 0));
                break;
            }
        }
        Core.resetItems(player);
    }


    public void breakBed(Player breaker, int bedX) {
        Player[] players = {Players[0].user.player, Players[1].user.player};
        Player[] players2 = {Players2[0].user.player, Players2[1].user.player};
        if ((bedX - map * 500) / Math.abs(bedX - map * 500) == (players2[0] == breaker ? 1 : -1))
            breaker.sendMessage(StringList.MLGRushBreakOwnBed[User.getUser(breaker).lang]);
        else {
            resetBlocks();
            int w = players[0] == breaker ? 0 : 1;
            Score[w] += 1;
            String[] formats = {Players[0].user.getFormatName(false), Players[1].user.getFormatName(false)};
            Set<Member> targets = new HashSet<>();
            targets.addAll(Spectators);
            targets.addAll(Arrays.asList(Players));
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
                String[] sidebar = StringList.MLGRushSideBar[m.user.lang];
                SideBar.getSidebar(m.user.player)
                        .setLine(formats[0] + sidebar[3] + suffix[0], 3)
                        .setLine(formats[1] + sidebar[4] + suffix[1], 4);
                m.user.bSendMessage(StringList.MLGRushBedbreak[m.user.lang].replace("%player%", w == 0 ? formats[1] : formats[0]).replace("%player2%", w == 0 ? formats[0] : formats[1]));
                PlayerUtils.sendActionBar(m.user.player, actionbar);
            }
            int[] Map = MapLocations(map);
            int t = 1;
            for (Player p : players2) {
                p.teleport(new Location(p.getWorld(), map * 500 + 0.5 + Map[0] * t, Map[1] + 1, coord + 0.5 + t * Map[3] + (t == 1 ? Map[6] : 0), Map[(1 - t) / 2 + 4], 0));
                Core.resetItems(p);
                t -= 2;
            }
        }
    }

    public void gameEnd(Member member) {
        int quitter = Players[0].equals(member) ? 1 : 0;
        for (Member m : Players) {
            m.game = null;
            m.PlacedBlocks = -1;
        }
        Player[] players = {Players[0].user.player, Players[1].user.player};
        players[quitter].sendMessage(StringList.MLGRushOpponentDisconnect[Players[quitter].user.lang]);
        resetBlocks();
        timer.cancel();
        Location lb = new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0);
        players[quitter].teleport(lb);
        for (Member m : Spectators) {
            for (Player pp : players) {
                pp.showPlayer(m.user.player);
            }
            m.user.bSetAllowFlight(false);
            m.user.bSendMessage(StringList.MLGRushGameEnd[m.user.lang]);
            m.user.bTeleport(lb);
            m.Spectate = -1;
        }
        WorldUtils.fillAir(-30 + 500 * map, 80, -30 + coord, 30 + map * 500, 120, 30 + coord, world);
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
        inst.Rooms[map].remove(room);
        MLGRush2x1.inst.lore(map, inst.Rooms[map].size(), true);
    }
}
