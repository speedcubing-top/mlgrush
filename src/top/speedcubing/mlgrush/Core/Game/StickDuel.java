package top.speedcubing.mlgrush.Core.Game;


import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import top.speedcubing.lib.bukkit.*;
import top.speedcubing.mlgrush.Core.Core;
import top.speedcubing.mlgrush.List.*;
import top.speedcubing.mlgrush.*;
import top.speedcubing.server.player.User;

import java.util.*;

public class StickDuel extends Duel {
    public static DuelCore inst = new DuelCore("StickDuel");

    public StickDuel(Member member1, Member member2, int map) {
        super(map, "StickDuel");
        Players = new Random().nextBoolean() ? new Member[]{member1, member2} : new Member[]{member2, member1};
        Player[] players = {Players[0].user.player, Players[1].user.player};
        String[] format = {User.getUser(players[0]).getFormatName(false), User.getUser(players[1]).getFormatName(false)};
        actionbar = format[0] + " §e0 §8x §e0 " + format[1];
        int[] Map = MapLocations(map);
        Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () -> {
            players[0].teleport(new Location(world, Map[0] + 0.5, 101, coord + 0.5 + Map[1], 90, 0));
            players[1].teleport(new Location(world, Map[0] * -1 + 0.5, 101, coord + 0.5 - Map[1], 270, 0));
            int[] settings = new int[5];
            for (Member p : Players) {
                p.user.sendLangMessage(StringList.StickDuelOpponentFound);
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
                String[] sidebar = StringList.StickDuelSideBar[p.user.lang];
                new SideBar(p.user.player, sidebar[0].replace("%t%", "00:00"))
                        .staticLine(sidebar[1], 4)
                        .staticLine(sidebar[2], 3)
                        .changeableLine(format[0] + sidebar[3] + "§e0", 2)
                        .changeableLine(format[1] + sidebar[4] + "§e0", 1)
                        .staticLine(sidebar[5], 0).create();
            }
            initStage2(settings, players, StringList.StickDuelSideBar, Players);
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

    public static int[] MapLocations(int map) { // X , Y , Y limit , +-Z
        return new int[]{10, 0};
    }

    public void JoinSpec(Player player, int room) {
//        UUID uuid = player.getUniqueId();
//        Member member = Member.getMember(player);
//        Core.QuitAllGame(uuid);
//        member.QuitAllQueue();
//        player.teleport(new Location(Bukkit.getWorld("StickDuel"), 0.5, 110, coord, 0, 0));
//        String[] sidebar = StringList.StickDuelSideBar[member.user.lang];
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
//        String name = speedcubingServer.getFormat(Member.getMember(player).user.rank)[1] + player.getName();
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
        Player[] players = {Players[0].user.player, Players[1].user.player};
        int w = players[0] == player ? 1 : 0;
        Player killer = players[w];
        String[] loserformat = Member.getMember(player).user.getFormat();
        Set<Member> targets = new HashSet<>();
        targets.addAll(Spectators);
        targets.addAll(Arrays.asList(Players));
        if (System.currentTimeMillis() - Kill[1 - w] < 2500) {
            resetBlocks();
            String[] winnerformat = Players[w].user.getFormat();
            String[] formats = new String[]{Players[0].user.getFormatName(false), Players[1].user.getFormatName(false)};
            killer.playSound(killer.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
            Score[w] += 1;
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
            for (Member m : Players) {
                m.PlacedBlocks = 0;
            }
            for (Member m : targets) {
                String[] sidebar = StringList.StickDuelSideBar[m.user.lang];
                SideBar.getSidebar(m.user.player)
                        .setLine(formats[0] + sidebar[3] + suffix[0], 3)
                        .setLine(formats[1] + sidebar[4] + suffix[1], 4);
                m.user.sendLangMessage(StringList.StickDuelKill, loserformat[1]
                        , player.getName()
                        , winnerformat[1]
                        , killer.getName());
                PlayerUtils.sendActionBar(m.user.player, actionbar);
            }
        } else {
            for (Member m : targets)
                m.user.sendLangMessage(StringList.StickDuelFell, loserformat[0], player.getName());
        }
        int pos = killer.getLocation().getX() > 0 ? -1 : 1;
        int[] Map = MapLocations(map);
        player.teleport(new Location(player.getWorld(), 0.5 + Map[0] * pos, 101, coord + 0.5 + pos * Map[1], 180 - 90 * pos, 0));
        for (Player p : players) {
            Core.resetItems(p);
        }
    }


    public void gameEnd(Member member) {
        int quitter = Players[0].equals(member) ? 1 : 0;
        for (Member m : Players) {
            m.game = null;
            m.PlacedBlocks = -1;
        }
        Players[quitter].user.sendLangMessage(StringList.StickDuelOpponentDisconnect);
        resetBlocks();
        timer.cancel();
        Location lb = new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0);
        Players[quitter].user.bTeleport(lb);
        for (Member m : Spectators) {
            for (Member pp : Players) {
                pp.user.bShowPlayer(m.user.player);
            }
            m.user.bSetAllowFlight(false);
            m.user.sendLangMessage(StringList.StickDuelGameEnd);
            m.user.bTeleport(lb);
            m.Spectate = -1;
        }
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
        inst.Rooms[map].remove(room);
    }
}
