package top.speedcubing.mlgrush.BukkitEventListener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import top.speedcubing.mlgrush.Core.BlockEditor;
import top.speedcubing.mlgrush.Core.Core;
import top.speedcubing.mlgrush.List.StringList;
import top.speedcubing.mlgrush.Member;

public class InventoryClick implements Listener {
    @EventHandler
    public void InventoryDragEvent(InventoryDragEvent e) {
        if (e.getCursor() != null && e.getCursor().getAmount() != 1)
            e.setCancelled(true);
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        int RawSlot = e.getRawSlot();
        if (e.getAction() == InventoryAction.PICKUP_HALF || e.getAction() == InventoryAction.PLACE_ONE)
            e.setCancelled(true);
        if (RawSlot != -999) {
            Player player = (Player) e.getWhoClicked();
            Inventory inventory = e.getInventory();
            InventoryHolder holder = inventory.getHolder();
            if (inventory.getName().equals("container.crafting")) {
                if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
                    e.setCancelled(true);
            } else if (holder == Core.specHolder) { // Spectate
//                e.setCancelled(true);
//                for (Map.Entry<Integer, Integer> entry : Core.specMenu2x1.entrySet()) {
//                    if (entry.getValue() == RawSlot) {
//                        int room = entry.getKey();
//                        PacketPlayOutPlayerInfo add = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) player).getHandle());
//                        for (UUID id : MLGRush2x1.Players.get(room)) {
//                            Player p = Bukkit.getPlayer(id);
//                            p.hidePlayer(player);
//                            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(add);
//                        }
//                        MLGRush2x1.joinSpec(player, room);
//                        player.closeInventory();
//                        return;
//                    }
//                }
//                for (Map.Entry<Integer, Integer> entry : Core.specMenustick.entrySet()) {
//                    if (entry.getValue() == RawSlot) {
//                        int room = entry.getKey();
//                        PacketPlayOutPlayerInfo add = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) player).getHandle());
//                        for (UUID id : StickDuel.Players.get(room)) {
//                            Player p = Bukkit.getPlayer(id);
//                            p.hidePlayer(player);
//                            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(add);
//                        }
//                        StickDuel.JoinSpec(player, room);
//                        player.closeInventory();
//                        return;
//                    }
//                }
                if (Core.specMenu4x1.containsValue(RawSlot))
                    player.closeInventory();
            } else if (holder == Core.ChallengerHolder) { // Challenge
                e.setCancelled(true);
                Member member = Member.getMember(player);
                Member lastdamager = member.LastDamage;
                if (lastdamager.user.player == null) {
                    player.closeInventory();
                    return;
                }
                switch (RawSlot) {
                    case 11:
                        player.closeInventory();
                        if (lastdamager.Challenge.contains(member)) {
                            if (lastdamager.LastDamage != null && lastdamager.LastDamage.equals(member)) {
                                lastdamager.Challenge.add(member);
                                lastdamager.stick = null;
                                member.user.sendLangMessage(StringList.ChallengingAction, lastdamager.user.getFormatName(false), "§bMLGRush 2x1");
                                lastdamager.user.sendLangMessage(StringList.ChallengeFrom, member.user.getFormatName(false), "§bMLGRush 2x1");
                            } else
                                member.user.sendLangMessage(StringList.AlreadyChallenged, lastdamager.user.getFormatName(false), "§bMLGRush 2x1");
                        } else {
                            lastdamager.Challenge.add(member);
                            member.user.sendLangMessage(StringList.ChallengingAction, lastdamager.user.getFormatName(false), "§bMLGRush 2x1");
                            lastdamager.user.sendLangMessage(StringList.ChallengeFrom, member.user.getFormatName(false), "§bMLGRush 2x1");
                        }
                        break;
                    case 15:
                        player.closeInventory();
                        if (lastdamager.Challenge.contains(member)) {
                            if (lastdamager.LastDamage.equals(member)) {
                                lastdamager.Challenge.add(member);
                                lastdamager.stick = member;
                                member.user.sendLangMessage(StringList.ChallengingAction, lastdamager.user.getFormatName(false), "§bStickDuel");
                                lastdamager.user.sendLangMessage(StringList.ChallengeFrom, member.user.getFormatName(false), "§bStickDuel");
                            } else
                                member.user.sendLangMessage(StringList.AlreadyChallenged, lastdamager.user.getFormatName(false), "§bStickDuel");
                        } else {
                            lastdamager.Challenge.add(member);
                            lastdamager.stick = member;
                            member.user.sendLangMessage(StringList.ChallengingAction, lastdamager.user.getFormatName(false), "§bStickDuel");
                            lastdamager.user.sendLangMessage(StringList.ChallengeFrom, member.user.getFormatName(false), "§bStickDuel");
                        }
                        break;
                }
            } else BlockEditor.select(holder, e, RawSlot, player);
        }
    }
}
