package top.speedcubing.mlgrush.Core;

import top.speedcubing.mlgrush.List.ItemStackList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import top.speedcubing.mlgrush.All.Lobby;
import top.speedcubing.mlgrush.Member;
import top.speedcubing.lib.bukkit.inventory.InventoryUtils;
import top.speedcubing.server.libs.GlobalString;
import top.speedcubing.server.libs.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Core {

    public static final InventoryHolder specHolder = () -> null;
    public static final InventoryHolder SettingsHolder = () -> null;
    public static final InventoryHolder GadgetsHolder = () -> null;
    public static final InventoryHolder PrefHolder = () -> null;
    public static final InventoryHolder KitEditorHolder = () -> null;
    public static final InventoryHolder ChallengerHolder = () -> null;
    public static final InventoryHolder StickSkinHolder = () -> null;
    public static final Map<Integer, Integer> specMenu2x1 = new HashMap<>();
    public static final Map<Integer, Integer> specMenustick = new HashMap<>();
    public static final Map<Integer, Integer> specMenu4x1 = new HashMap<>();

    private static final String[] stickedit = {"§bStick Skin", "§bStick Skin", "§bStick Skin", "§bStick Skin"};
    private static final String[] prefedit = {"§bPreferences", "§bPreferences", "§bPreferences", "§bPreferences"};

    public static void resetItems(Player player) {
        Inventory inventory = player.getInventory();
        int[] ints = Member.getMember(player).integerCache;
        inventory.setItem(ints[1], ItemStackList.Blocks[ints[4]][User.getUser(player).lang]);
    }

    public static void saveStickEditor(Player player, int RawSlot) {
        if (RawSlot >= 10 && RawSlot <= 15) {
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
            Member.getMember(player).integerCache[3] = RawSlot - 10;
            player.closeInventory();
            player.sendMessage(GlobalString.SavedSettings[User.getUser(player).lang]);
        }
    }

    public static void saveKitEditor(Player player, ItemStack cursor, int lang) {
        Inventory inventory = player.getInventory();
        if (cursor.getType() == Material.AIR) {
            Member member = Member.getMember(player);
            member.iseditingkit = false;
            player.sendMessage(GlobalString.SavedSettings[lang]);
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
        int lang = User.getUser(player).lang;
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
        int lang = User.getUser(player).lang;
        Inventory inventory = Bukkit.createInventory(PrefHolder, 36, prefedit[lang]);
        for (int i = 0; i < 5; i++) {
            inventory.setItem(i + 11, ItemStackList.prefereditor[i][lang]);
        }
        ItemStack stack = ItemStackList.preferButton[0][lang];
        ItemStack stack1 = ItemStackList.preferButton[1][lang];
        int[] bs = Member.getMember(player).integerCache;
        inventory.setItem(20, bs[9] == 1 ? stack : stack1);
        inventory.setItem(21, bs[5] == 1 ? stack : stack1);
        inventory.setItem(22, bs[6] == 1 ? stack : stack1);
        inventory.setItem(23, bs[7] == 1 ? stack : stack1);
        inventory.setItem(24, bs[8] == 1 ? stack : stack1);
        player.openInventory(inventory);
    }

    public static void openStickEditor(Player player) {
        int lang = User.getUser(player).lang;
        Inventory inventory = Bukkit.createInventory(StickSkinHolder, 27, stickedit[lang]);
        for (int i = 0; i < 6; i++) {
            inventory.setItem(i + 10, ItemStackList.stickeditor[0][i][lang]);
        }
        int x = Member.getMember(player).integerCache[3];
        ItemStack selected = ItemStackList.stickeditor[1][x][lang];
        inventory.setItem(x + 10, selected);
        player.openInventory(inventory);
    }

    public static int randomToBed(double random) {
        return (int) Math.round(random / 500D) * 500;
    }

    public static int roomToBed(int room) {
        return 250 * (room * (int) Math.pow(-1, room + 1) + room % 2);
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
