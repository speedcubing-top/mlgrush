package speedcubing.Core;

import speedcubing.lib.bukkit.inventory.ItemUtils;
import speedcubing.server.libs.GlobalString;
import speedcubing.server.libs.User;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import speedcubing.List.ItemStackList;

import java.util.UUID;

public class BlockEditor {

    public static final InventoryHolder BlockHolder = () -> null;
    public static final InventoryHolder sandstoneholder = () -> null;
    public static final InventoryHolder quartzeditor = () -> null;
    public static final InventoryHolder clayeditor = () -> null;


    static int length;

    public void Load() {
        length = ItemStackList.sandstoneeditor.length + ItemStackList.quartzeditor.length + ItemStackList.clayeditor.length;
    }

    public static void createMainPage(Player player) {
        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
        int lang = User.getUser(player).lang;
        Inventory inv = Bukkit.createInventory(BlockHolder, 27, ItemStackList.blockedit[lang]);
        for (int i = 0; i < ItemStackList.categoryblockeditor.length; i++) {
            inv.setItem(i + 12, ItemStackList.categoryblockeditor[i][lang]);
        }
        player.openInventory(inv);
    }

    public static void select(InventoryHolder holder, InventoryClickEvent e, int RawSlot, Player player) {
        if (holder == BlockHolder) {
            e.setCancelled(true);
            if (RawSlot >= 12 && RawSlot <=12+ ItemStackList.categoryblockeditor.length) {
                e.setCancelled(true);
                switch (RawSlot) {
                    case 12: {
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                        UUID uuid = player.getUniqueId();
                        int lang = User.getUser(player).lang;
                        Inventory inv = Bukkit.createInventory(sandstoneholder, 27, ItemStackList.sandstoneedit[lang]);
                        for (int i = 0; i < 3; i++) {
                            inv.setItem(i + 10, ItemStackList.sandstoneeditor[i][lang]);
                        }
                        for (int i = 3; i < 6; i++) {
                            inv.setItem(i + 11, ItemStackList.sandstoneeditor[i][lang]);
                        }
                        int set = Core.integerCache.get(uuid)[4];
                        if (set < 6)
                            inv.setItem((set < 3) ? set + 10 : set + 11, ItemUtils.glow(ItemUtils.setLore(ItemStackList.sandstoneeditor[set][lang].clone(),GlobalString.selected[lang])));
                        player.openInventory(inv);
                    }
                    break;
                    case 13: {
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                        UUID uuid = player.getUniqueId();
                        int lang = User.getUser(player).lang;
                        Inventory inv = Bukkit.createInventory(quartzeditor, 27, ItemStackList.quartzedit[lang]);
                        for (int i = 0; i < ItemStackList.quartzeditor.length; i++) {
                            inv.setItem(i + 12, ItemStackList.quartzeditor[i][lang]);
                        }
                        int index = Core.integerCache.get(uuid)[4] - ItemStackList.sandstoneeditor.length;
                        if (index < ItemStackList.quartzeditor.length && index >= 0)
                            inv.setItem(index + 12, ItemUtils.glow(ItemUtils.setLore(ItemStackList.quartzeditor[index][lang].clone(), GlobalString.selected[lang])));
                        player.openInventory(inv);
                    }
                    break;
                    case 14: {
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                        UUID uuid = player.getUniqueId();
                        int lang = User.getUser(player).lang;
                        Inventory inv = Bukkit.createInventory(clayeditor, 27, ItemStackList.clayedit[lang]);
                        for (int i = 0; i < ItemStackList.clayeditor.length; i++) {
                            inv.setItem(i, ItemStackList.clayeditor[i][lang]);
                        }
                        int index = Core.integerCache.get(uuid)[4] - (ItemStackList.sandstoneeditor.length + ItemStackList.quartzeditor.length);
                        if (index < ItemStackList.clayeditor.length && index >= 0)
                            inv.setItem(index, ItemUtils.glow(ItemUtils.setLore(ItemStackList.clayeditor[index][lang].clone(), GlobalString.selected[lang])));
                        player.openInventory(inv);
                    }
                    break;
                }
            }
        } else if (holder == sandstoneholder) {
            e.setCancelled(true);
            if (RawSlot > 9 && RawSlot < 17 && RawSlot != 13) {
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                int x = RawSlot > 12 ? RawSlot - 11 : RawSlot - 10;
                UUID uuid = player.getUniqueId();
                int lang = User.getUser(player).lang;
                Core.integerCache.get(uuid)[4] = x;
                player.closeInventory();
                player.sendMessage(GlobalString.SavedSettings[lang]);
            }
        } else if (holder == quartzeditor) {
            e.setCancelled(true);
            if (RawSlot <= 14 && RawSlot >= 12) {
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                int x = RawSlot + ItemStackList.sandstoneeditor.length - 12;
                UUID uuid = player.getUniqueId();
                int lang = User.getUser(player).lang;
                Core.integerCache.get(uuid)[4] = x;
                player.closeInventory();


                player.sendMessage(GlobalString.SavedSettings[lang]);
            }
        } else if (holder == clayeditor) {
            e.setCancelled(true);
            if (RawSlot < 17) {
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                int x = RawSlot + ItemStackList.sandstoneeditor.length + ItemStackList.quartzeditor.length;
                UUID uuid = player.getUniqueId();
                int lang = User.getUser(player).lang;
                Core.integerCache.get(uuid)[4] = x;
                player.closeInventory();
                player.sendMessage(GlobalString.SavedSettings[lang]);
            }
        }
    }
}
