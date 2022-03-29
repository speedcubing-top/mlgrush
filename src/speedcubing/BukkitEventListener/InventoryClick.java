package speedcubing.BukkitEventListener;

import cubingserver.libs.User;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import speedcubing.Core.BlockEditor;
import speedcubing.Core.Core;
import speedcubing.Core.MLGRush2x1;
import speedcubing.Core.StickDuel;
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;

import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class InventoryClick implements Listener {

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        int RawSlot = e.getRawSlot();
        if (RawSlot != -999) {
            Player player = (Player) e.getWhoClicked();
            UUID uuid = player.getUniqueId();
            Inventory inventory = e.getInventory();
            InventoryHolder holder = inventory.getHolder();
            if (inventory.getName().equals("container.crafting")) {
                if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
                    e.setCancelled(true);
            } else if (holder == Core.KitEditorHolder) { // KitEditor
                if (RawSlot <= 35 || (e.getAction() == InventoryAction.PICKUP_HALF && e.getCurrentItem().getAmount() != 1)) {
                    e.setCancelled(true);
                    player.updateInventory();
                }
                switch (RawSlot) {
                    case 3:
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        Core.saveKitEditor(player, e.getCursor(), User.getLang(uuid));
                        break;
                    case 5:
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        player.closeInventory();
                        break;
                }
            } else if (holder == Core.StickSkinHolder) {
                Core.saveStickEditor(player, RawSlot);
            } else if (holder == Core.specHolder) { // Spectate
                e.setCancelled(true);
                for (Map.Entry<Integer, Integer> entry : Core.specMenu2x1.entrySet()) {
                    if (entry.getValue() == RawSlot) {
                        int room = entry.getKey();
                        PacketPlayOutPlayerInfo add = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) player).getHandle());
                        for (UUID id : MLGRush2x1.Players.get(room)) {
                            Player p = Bukkit.getPlayer(id);
                            p.hidePlayer(player);
                            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(add);
                        }
                        MLGRush2x1.joinSpec(player, room);
                        player.closeInventory();
                        return;
                    }
                }
                for (Map.Entry<Integer, Integer> entry : Core.specMenustick.entrySet()) {
                    if (entry.getValue() == RawSlot) {
                        int room = entry.getKey();
                        PacketPlayOutPlayerInfo add = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) player).getHandle());
                        for (UUID id : StickDuel.Players.get(room)) {
                            Player p = Bukkit.getPlayer(id);
                            p.hidePlayer(player);
                            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(add);
                        }
                        StickDuel.JoinSpec(player, room);
                        player.closeInventory();
                        return;
                    }
                }
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
                        int b = Core.integerCache.get(uuid)[9];
                        e.getClickedInventory().setItem(20, ItemStackList.preferButton[b][User.getLang(uuid)]);
                        Core.integerCache.get(uuid)[9] = 1 - b;
                    }
                    break;
                    case 21: {
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        int b = Core.integerCache.get(uuid)[5];
                        e.getClickedInventory().setItem(21, ItemStackList.preferButton[b][User.getLang(uuid)]);
                        Core.integerCache.get(uuid)[5] = 1 - b;
                    }
                    break;
                    case 22: {
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        int b = Core.integerCache.get(uuid)[6];
                        e.getClickedInventory().setItem(22, ItemStackList.preferButton[b][User.getLang(uuid)]);
                        Core.integerCache.get(uuid)[6] = 1 - b;
                    }
                    break;
                    case 23: {
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        int b = Core.integerCache.get(uuid)[7];
                        e.getClickedInventory().setItem(23, ItemStackList.preferButton[b][User.getLang(uuid)]);
                        Core.integerCache.get(uuid)[7] = 1 - b;
                    }
                    break;
                    case 24: {
                        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                        int b = Core.integerCache.get(uuid)[8];
                        e.getClickedInventory().setItem(24, ItemStackList.preferButton[b][User.getLang(uuid)]);
                        Core.integerCache.get(uuid)[8] = 1 - b;
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
                UUID lastdamager = EntityDamageByEntity.LastDamage.get(player.getUniqueId());
                Player lastdamage = Bukkit.getPlayer(lastdamager);
                if (lastdamage == null) {
                    player.closeInventory();
                    return;
                }
                int lang = User.getLang(uuid);
                switch (RawSlot) {
                    case 11:
                        player.closeInventory();
                        if (!Core.Challenge.containsKey(lastdamager))
                            Core.Challenge.put(lastdamager, new HashSet<>());
                        if (Core.Challenge.containsKey(lastdamager) && Core.Challenge.get(lastdamager).contains(uuid)) {
                            if (Core.StickDuel.containsKey(lastdamager) && Core.StickDuel.get(lastdamager).equals(uuid)) {
                                Core.Challenge.get(lastdamager).add(uuid);
                                Core.StickDuel.remove(lastdamager);
                                player.sendMessage(StringList.ChallengingAction[lang].replace("%player%", User.getFormat(User.getRank(lastdamager))[1] + lastdamage.getName()).replace("%gametype%", "§bMLGRush 2x1"));
                                lastdamage.sendMessage(StringList.ChallengeFrom[User.getLang(lastdamager)].replace("%player%", User.getFormat(User.getRank(uuid))[1] + player.getName()).replace("%gametype%", "§bMLGRush 2x1"));
                            } else
                                player.sendMessage(StringList.AlreadyChallenged[lang].replace("%player%", User.getFormat(User.getRank(lastdamager))[1] + lastdamage.getName()).replace("%gametype%", "§bMLGRush 2x1"));
                        } else {
                            Core.Challenge.get(lastdamager).add(uuid);
                            player.sendMessage(StringList.ChallengingAction[lang].replace("%player%", User.getFormat(User.getRank(lastdamager))[1] + lastdamage.getName()).replace("%gametype%", "§bMLGRush 2x1"));
                            lastdamage.sendMessage(StringList.ChallengeFrom[User.getLang(lastdamager)].replace("%player%", User.getFormat(User.getRank(uuid))[1] + player.getName()).replace("%gametype%", "§bMLGRush 2x1"));
                        }
                        break;
                    case 15:
                        player.closeInventory();
                        if (!Core.Challenge.containsKey(lastdamager))
                            Core.Challenge.put(lastdamager, new HashSet<>());
                        if (Core.Challenge.containsKey(lastdamager) && Core.Challenge.get(lastdamager).contains(uuid)) {
                            if (!(Core.StickDuel.containsKey(lastdamager) && Core.StickDuel.get(lastdamager).equals(uuid))) {
                                Core.Challenge.get(lastdamager).add(uuid);
                                Core.StickDuel.put(lastdamager, uuid);
                                player.sendMessage(StringList.ChallengingAction[lang].replace("%player%", User.getFormat(User.getRank(lastdamager))[1] + lastdamage.getName()).replace("%gametype%", "§bStickDuel"));
                                lastdamage.sendMessage(StringList.ChallengeFrom[User.getLang(lastdamager)].replace("%player%", User.getFormat(User.getRank(uuid))[1] + player.getName()).replace("%gametype%", "§bStickDuel"));
                            } else
                                player.sendMessage(StringList.AlreadyChallenged[lang].replace("%player%", User.getFormat(User.getRank(lastdamager))[1] + lastdamage.getName()).replace("%gametype%", "§bStickDuel"));
                        } else {
                            Core.Challenge.get(lastdamager).add(uuid);
                            Core.StickDuel.put(lastdamager, uuid);
                            player.sendMessage(StringList.ChallengingAction[lang].replace("%player%", User.getFormat(User.getRank(lastdamager))[1] + lastdamage.getName()).replace("%gametype%", "§bStickDuel"));
                            lastdamage.sendMessage(StringList.ChallengeFrom[User.getLang(lastdamager)].replace("%player%", User.getFormat(User.getRank(uuid))[1] + player.getName()).replace("%gametype%", "§bStickDuel"));
                        }
                        break;
                }
            } else BlockEditor.select(holder, e, RawSlot, player);
        }
    }
}
