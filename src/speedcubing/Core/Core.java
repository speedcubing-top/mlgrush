package speedcubing.Core;

import cubing.lib.bukkit.InventoryUtils;
import cubingserver.StringList.GlobalString;
import cubingserver.libs.User;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import speedcubing.All.Lobby;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;

import java.util.*;

public class Core {
    public static Set<UUID> iseditingkit = new HashSet<>();

    public static Map<UUID, Integer[]> integerCache = new HashMap<>();

    public static final InventoryHolder specHolder = () -> null;
    public static final InventoryHolder SettingsHolder = () -> null;
    public static final InventoryHolder GadgetsHolder = () -> null;
    public static final InventoryHolder PrefHolder = () -> null;
    public static final InventoryHolder KitEditorHolder = () -> null;
    public static final InventoryHolder ChallengerHolder = () -> null;
    public static final InventoryHolder StickSkinHolder = () -> null;
    //Challenge
    public static final Map<UUID, Set<UUID>> Challenge = new HashMap<>();
    public static final Map<UUID, UUID> StickDuel = new HashMap<>();
    //spec
    public static final Map<UUID, Integer> Spectate = new HashMap<>();
    public static final Map<Integer, Integer> specMenu2x1 = new HashMap<>();
    public static final Map<Integer, Integer> specMenustick = new HashMap<>();
    public static final Map<Integer, Integer> specMenu4x1 = new HashMap<>();

    public static Map<UUID, Integer> PlacedBlocks = new HashMap<>();
    public static Map<UUID, Integer> PlayerRoom = new HashMap<>();
    public static Map<UUID, Integer> PlayerMap = new HashMap<>();

    private static final String[] stickedit = {"§bStick Skin", "§bStick Skin", "§bStick Skin", "§bStick Skin"};
    private static final String[] prefedit = {"§bPreferences", "§bPreferences", "§bPreferences", "§bPreferences"};

    public static void resetItems(Player player) {
        UUID uuid = player.getUniqueId();
        Inventory inventory = player.getInventory();
        Integer[] ints = integerCache.get(uuid);
        inventory.setItem(ints[1], ItemStackList.Blocks[ints[4]][User.getLang(uuid)]);
    }

    public static void saveStickEditor(Player player, int RawSlot) {
        if (RawSlot >= 10 && RawSlot <= 15) {
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
            UUID uuid = player.getUniqueId();
            integerCache.get(uuid)[3] = RawSlot - 10;
            player.closeInventory();
            player.sendMessage(GlobalString.SavedSettings[User.getLang(uuid)]);
        }
    }

    public static void saveKitEditor(Player player, ItemStack cursor, int lang) {
        Inventory inventory = player.getInventory();
        if (cursor.getType() == Material.AIR) {
            iseditingkit.remove(player.getUniqueId());
            player.sendMessage(GlobalString.SavedSettings[lang]);
            Integer[] x = integerCache.get(player.getUniqueId());
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
        UUID uuid = player.getUniqueId();
        int lang = User.getLang(uuid);
        player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
        Inventory inventory = player.getInventory();
        inventory.clear();
        player.openInventory(ItemStackList.InventoryKitEditor[lang]);
        InventoryUtils.fillItem(inventory, 9, 36, ItemStackList.Invalid[lang]);
        Integer[] ints = integerCache.get(uuid);
        inventory.setItem(ints[0], ItemStackList.Sticks[0][lang]);
        inventory.setItem(ints[1], ItemStackList.Blocks[0][lang]);
        inventory.setItem(ints[2], ItemStackList.Pickaxe[lang]);
        iseditingkit.add(player.getUniqueId());
    }

    public static void openPreference(Player player) {
        UUID uuid = player.getUniqueId();
        int lang = User.getLang(uuid);
        Inventory inventory = Bukkit.createInventory(PrefHolder, 36, prefedit[lang]);
        for (int i = 0; i < 5; i++) {
            inventory.setItem(i + 11, ItemStackList.prefereditor[i][lang]);
        }
        ItemStack stack = ItemStackList.preferButton[0][lang];
        ItemStack stack1 = ItemStackList.preferButton[1][lang];
        Integer[] bs = integerCache.get(uuid);
        inventory.setItem(20, bs[9] == 1 ? stack : stack1);
        inventory.setItem(21, bs[5] == 1 ? stack : stack1);
        inventory.setItem(22, bs[6] == 1 ? stack : stack1);
        inventory.setItem(23, bs[7] == 1 ? stack : stack1);
        inventory.setItem(24, bs[8] == 1 ? stack : stack1);
        player.openInventory(inventory);
    }

    public static void openStickEditor(Player player) {
        UUID uuid = player.getUniqueId();
        int lang = User.getLang(uuid);
        Inventory inventory = Bukkit.createInventory(StickSkinHolder, 27, stickedit[lang]);
        for (int i = 0; i < 6; i++) {
            inventory.setItem(i + 10, ItemStackList.stickeditor[0][i][lang]);
        }
        int x = integerCache.get(uuid)[3];
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


    public static void QuitAllQueue(UUID uuid) {
        if (MLGRush2x1.Queue != null && MLGRush2x1.Queue.equals(uuid)) {
            MLGRush2x1.Queue = null;
            EntityArmorStand stand = EntityList.Lobbys[4];
            stand.setCustomName("§a0/2");
            PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true);
            for (Player p : Bukkit.getWorld("world").getPlayers()) {
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
        }
        if (speedcubing.Core.StickDuel.Queue != null && speedcubing.Core.StickDuel.Queue.equals(uuid)) {
            speedcubing.Core.StickDuel.Queue = null;
            EntityArmorStand stand = EntityList.Lobbys[3];
            stand.setCustomName("§a0/2");
            PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true);
            for (Player p : Bukkit.getWorld("world").getPlayers()) {
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public static void QuitAllGame(UUID uuid) {
        for (int i = 0; i < MLGRush4x1.roomSize; i++) {
            for (int j = 0; j < 4; j++) {
                UUID[] ids = MLGRush4x1.Players.get(i);
                if (ids != null && ids[j].equals(uuid))
                    MLGRush4x1.Players.get(i)[j] = null;
            }
        }
    }
}
