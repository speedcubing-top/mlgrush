package speedcubing.BukkitEventListener;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import speedcubing.Core.BlockEditor;
import speedcubing.Core.Core;
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;
import speedcubing.Member;
import speedcubing.server.libs.User;
import speedcubing.server.speedcubingServer;

import java.util.UUID;

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
            UUID uuid = player.getUniqueId();
            Inventory inventory = e.getInventory();
            InventoryHolder holder = inventory.getHolder();
            if (inventory.getName().equals("container.crafting")) {
                if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
                    e.setCancelled(true);
            } else if (holder == Core.KitEditorHolder) { // KitEditor
                if (RawSlot < 36)
                    e.setCancelled(true);
                switch (RawSlot) {
                    case 3:
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        Core.saveKitEditor(player, e.getCursor(), User.getUser(player).lang);
                        break;
                    case 5:
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        player.closeInventory();
                        break;
                }
            } else if (holder == Core.StickSkinHolder) {
                Core.saveStickEditor(player, RawSlot);
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
            } else if (holder == Core.GadgetsHolder) { // Gadgets
                e.setCancelled(true);
                switch (RawSlot) {
                    case 11:
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        Core.openStickEditor(player);
                        break;
                    case 15:
                        BlockEditor.createMainPage(player);
                        break;
                }
                e.setCancelled(true);
            } else if (holder == Core.PrefHolder) { //Preferences
                e.setCancelled(true);
                switch (RawSlot) {
                    case 20: {
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        int b = Member.getMember(player).integerCache[9];
                        e.getClickedInventory().setItem(20, ItemStackList.preferButton[b][User.getUser(player).lang]);
                        Member.getMember(player).integerCache[9] = 1 - b;
                    }
                    break;
                    case 21: {
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        int b = Member.getMember(player).integerCache[5];
                        e.getClickedInventory().setItem(21, ItemStackList.preferButton[b][User.getUser(player).lang]);
                        Member.getMember(player).integerCache[5] = 1 - b;
                    }
                    break;
                    case 22: {
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        int b = Member.getMember(player).integerCache[6];
                        e.getClickedInventory().setItem(22, ItemStackList.preferButton[b][User.getUser(player).lang]);
                        Member.getMember(player).integerCache[6] = 1 - b;
                    }
                    break;
                    case 23: {
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        int b = Member.getMember(player).integerCache[7];
                        e.getClickedInventory().setItem(23, ItemStackList.preferButton[b][User.getUser(player).lang]);
                        Member.getMember(player).integerCache[7] = 1 - b;
                    }
                    break;
                    case 24: {
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        int b = Member.getMember(player).integerCache[8];
                        e.getClickedInventory().setItem(24, ItemStackList.preferButton[b][User.getUser(player).lang]);
                        Member.getMember(player).integerCache[8] = 1 - b;
                    }
                    break;
                }
            } else if (holder == Core.SettingsHolder) { // Settings Page
                e.setCancelled(true);
                switch (RawSlot) {
                    case 11:
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        Core.openKitEditor(player);
                        break;
                    case 15:
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        Core.openPreference(player);
                        break;
                }
            } else if (holder == Core.ChallengerHolder) { // Challenge
                e.setCancelled(true);
                Member member = Member.getMember(player);
                Member lastdamager = member.LastDamage;
                if (lastdamager.user.player == null) {
                    player.closeInventory();
                    return;
                }
                int lang = User.getUser(player).lang;
                switch (RawSlot) {
                    case 11:
                        player.closeInventory();
                        if (lastdamager.Challenge.contains(member)) {
                            if (lastdamager.LastDamage != null && lastdamager.LastDamage.equals(member)) {
                                lastdamager.Challenge.add(member);
                                lastdamager.StickDuel = null;
                                player.sendMessage(StringList.ChallengingAction[lang].replace("%player%", speedcubingServer.getFormat(lastdamager.user.rank)[1] + lastdamager.user.player.getName()).replace("%gametype%", "§bMLGRush 2x1"));
                                lastdamager.user.player.sendMessage(StringList.ChallengeFrom[lastdamager.user.lang].replace("%player%", speedcubingServer.getFormat(User.getUser(player).rank)[1] + player.getName()).replace("%gametype%", "§bMLGRush 2x1"));
                            } else
                                player.sendMessage(StringList.AlreadyChallenged[lang].replace("%player%", speedcubingServer.getFormat(lastdamager.user.rank)[1] + lastdamager.user.player.getName()).replace("%gametype%", "§bMLGRush 2x1"));
                        } else {
                            lastdamager.Challenge.add(member);
                            player.sendMessage(StringList.ChallengingAction[lang].replace("%player%", speedcubingServer.getFormat(lastdamager.user.rank)[1] + lastdamager.user.player.getName()).replace("%gametype%", "§bMLGRush 2x1"));
                            lastdamager.user.player.sendMessage(StringList.ChallengeFrom[lastdamager.user.lang].replace("%player%", speedcubingServer.getFormat(User.getUser(player).rank)[1] + player.getName()).replace("%gametype%", "§bMLGRush 2x1"));
                        }
                        break;
                    case 15:
                        player.closeInventory();
                        if (lastdamager.Challenge.contains(member)) {
                            if (lastdamager.LastDamage.equals(member)) {
                                lastdamager.Challenge.add(member);
                                lastdamager.StickDuel = member;
                                player.sendMessage(StringList.ChallengingAction[lang].replace("%player%", speedcubingServer.getFormat(lastdamager.user.rank)[1] + lastdamager.user.player.getName()).replace("%gametype%", "§bStickDuel"));
                                lastdamager.user.player.sendMessage(StringList.ChallengeFrom[lastdamager.user.lang].replace("%player%", speedcubingServer.getFormat(User.getUser(player).rank)[1] + player.getName()).replace("%gametype%", "§bStickDuel"));
                            } else
                                player.sendMessage(StringList.AlreadyChallenged[lang].replace("%player%", speedcubingServer.getFormat(lastdamager.user.rank)[1] + lastdamager.user.player.getName()).replace("%gametype%", "§bStickDuel"));
                        } else {
                            lastdamager.Challenge.add(member);
                            lastdamager.StickDuel = member;
                            player.sendMessage(StringList.ChallengingAction[lang].replace("%player%", speedcubingServer.getFormat(lastdamager.user.rank)[1] + lastdamager.user.player.getName()).replace("%gametype%", "§bStickDuel"));
                            lastdamager.user.player.sendMessage(StringList.ChallengeFrom[lastdamager.user.lang].replace("%player%", speedcubingServer.getFormat(User.getUser(player).rank)[1] + player.getName()).replace("%gametype%", "§bStickDuel"));
                        }
                        break;
                }
            } else BlockEditor.select(holder, e, RawSlot, player);
        }
    }
}
