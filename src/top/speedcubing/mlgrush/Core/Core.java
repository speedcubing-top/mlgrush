package top.speedcubing.mlgrush.Core;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import top.speedcubing.lib.bukkit.inventory.*;
import top.speedcubing.mlgrush.All.Lobby;
import top.speedcubing.mlgrush.List.ItemStackList;
import top.speedcubing.mlgrush.Member;
import top.speedcubing.server.lang.GlobalString;

import java.util.*;

public class Core {

    public static final InventoryHolder specHolder = () -> null;
    public static final InventoryHolder ChallengerHolder = () -> null;
    public static final Map<Integer, Integer> specMenu2x1 = new HashMap<>();
    public static final Map<Integer, Integer> specMenustick = new HashMap<>();
    public static final Map<Integer, Integer> specMenu4x1 = new HashMap<>();

    private static final String[] stickedit = {"§bStick Skin", "§bStick Skin", "§bStick Skin", "§bStick Skin"};
    private static final String[] prefedit = {"§bPreferences", "§bPreferences", "§bPreferences", "§bPreferences"};

    public static void resetItems(Player player) {
        int[] ints = Member.getMember(player).integerCache;
        player.getInventory().setItem(ints[1], ItemStackList.Blocks[ints[4]][Member.getMember(player).user.lang]);
    }

    public static void saveStickEditor(Player player, int RawSlot) {
        if (RawSlot >= 10 && RawSlot <= 15) {
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
            Member member = Member.getMember(player);
            member.integerCache[3] = RawSlot - 10;
            player.closeInventory();
            member.user.sendLangMessage(GlobalString.SavedSettings);
        }
    }

    public static void saveKitEditor(Player player, ItemStack cursor) {
        Inventory inventory = player.getInventory();
        if (cursor.getType() == Material.AIR) {
            Member member = Member.getMember(player);
            member.iseditingkit = false;
            member.user.sendLangMessage(GlobalString.SavedSettings);
            int[] x = member.integerCache;
            for (int i = 0; i < 9; i++) {
                if (inventory.getItem(i) != null) {
                    switch (inventory.getItem(i).getType()) {
                        case STICK:
                            x[0] = i;
                            break;
                        case SANDSTONE:
                            x[1] = i;
                            break;
                        case WOOD_PICKAXE:
                            x[2] = i;
                            break;
                    }
                }
            }
            player.closeInventory();
            player.setItemOnCursor(new ItemStack(Material.AIR));
            Lobby.Items(player);
        } else {
            for (int i = 0; i < 9; i++) {
                if (inventory.getItem(i) == null) {
                    inventory.setItem(i, cursor);
                    break;
                }
            }
            player.setItemOnCursor(new ItemStack(Material.AIR));
        }
    }

    public static void openKitEditor(Player player) {
        int lang = Member.getMember(player).user.lang;
        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
        Inventory inventory = player.getInventory();
        inventory.clear();
        player.openInventory(ItemStackList.InventoryKitEditor[lang]);
        InventoryUtils.fillItem(inventory, 9, 36, ItemStackList.Invalid[lang]);
        int[] ints = Member.getMember(player).integerCache;
        inventory.setItem(ints[0], ItemStackList.Sticks[0][lang]);
        inventory.setItem(ints[1], ItemStackList.Blocks[0][lang]);
        inventory.setItem(ints[2], ItemStackList.Pickaxe[lang]);
        Member.getMember(player).iseditingkit = true;
    }

    public static void openPreference(Player player) {
        Member member = Member.getMember(player);
        InventoryBuilder builder = new InventoryBuilder(36, prefedit[member.user.lang]).deleteOnClose(true);
        for (int i = 0; i < 5; i++)
            builder.setItem(ItemStackList.prefereditor[i][member.user.lang], i + 11);
        ItemStack stack = ItemStackList.preferButton[0][member.user.lang];
        ItemStack stack1 = ItemStackList.preferButton[1][member.user.lang];
        int[] bs = member.integerCache;
        builder.setItem(bs[9] == 1 ? stack : stack1, (p, e) -> {
            p.playSound(p.getLocation(), Sound.CLICK, 1F, 1F);
            int b = member.integerCache[9];
            e.getClickedInventory().setItem(20, ItemStackList.preferButton[b][member.user.lang]);
            member.integerCache[9] = 1 - b;
        }, 20);
        builder.setItem(bs[5] == 1 ? stack : stack1, (p, e) -> {
            player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
            int b = member.integerCache[5];
            e.getClickedInventory().setItem(21, ItemStackList.preferButton[b][member.user.lang]);
            member.integerCache[5] = 1 - b;
        }, 21);
        builder.setItem(bs[6] == 1 ? stack : stack1, (p, e) -> {
            player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
            int b = member.integerCache[6];
            e.getClickedInventory().setItem(22, ItemStackList.preferButton[b][member.user.lang]);
            member.integerCache[6] = 1 - b;
        }, 22);
        builder.setItem(bs[7] == 1 ? stack : stack1, (p, e) -> {
            player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
            int b = member.integerCache[7];
            e.getClickedInventory().setItem(23, ItemStackList.preferButton[b][member.user.lang]);
            member.integerCache[7] = 1 - b;
        }, 23);
        builder.setItem(bs[8] == 1 ? stack : stack1, (p, e) -> {
            player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
            int b = member.integerCache[8];
            e.getClickedInventory().setItem(24, ItemStackList.preferButton[b][member.user.lang]);
            member.integerCache[8] = 1 - b;
        }, 24);
        player.openInventory(builder.getInventory());
    }

    public static void openStickEditor(Player player) {
        int lang = Member.getMember(player).user.lang;
        InventoryBuilder inventory = new InventoryBuilder(27, stickedit[lang]).deleteOnClose(true).setAllClickEvent((p, e) -> saveStickEditor(p, e.getRawSlot()));
        for (int i = 0; i < 6; i++)
            inventory.setItem(ItemStackList.stickeditor[0][i][lang], i + 10);
        int x = Member.getMember(player).integerCache[3];
        inventory.setItem(ItemStackList.stickeditor[1][x][lang], x + 10);
        player.openInventory(inventory.getInventory());
    }

    public static void QuitAllGame(UUID uuid) {
//        for (int i = 0; i < MLGRush4x1.roomSize; i++) {
//            for (int j = 0; j < 4; j++) {
//                UUID[] ids = MLGRush4x1.Players.get(i);
//                if (ids != null && ids[j].equals(uuid))
//                    MLGRush4x1.Players.get(i)[j] = null;
//            }
//        }
    }
}
