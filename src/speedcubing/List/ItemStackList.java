package speedcubing.List;

import cubing.bukkit.InventoryUtils;
import cubing.bukkit.ItemUtils;
import cubingserver.StringList.GlobalString;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import speedcubing.Core.Core;
import speedcubing.Core.MLGRush2x1;

import java.util.Collections;

public class ItemStackList {

    public static ItemStack[] Invalid = new ItemStack[4];
    public static ItemStack[] HubSlime = new ItemStack[4];
    public static ItemStack[][] LobbyItems = new ItemStack[5][4];

    public static Inventory[] Challenger = new Inventory[4];

    public static final Inventory[] specinventory = new Inventory[4];

    public static ItemStack[] Pickaxe = new ItemStack[4];
    public static ItemStack[][] Sticks = new ItemStack[6][4];
    public static Inventory[] Settings = new Inventory[4];
    public static Inventory[] Gadgets = new Inventory[4];
    public static ItemStack[][][] stickeditor = new ItemStack[2][6][4];


    public static ItemStack[][] prefereditor = new ItemStack[5][4];
    public static ItemStack[][] preferButton = new ItemStack[2][4];
    public static Inventory[] InventoryKitEditor = new Inventory[4];


    public static ItemStack[][] Blocks = new ItemStack[26][4];
    public static ItemStack[][] categoryblockeditor = new ItemStack[3][4];
    public static ItemStack[][] sandstoneeditor = new ItemStack[6][4];
    public static ItemStack[][] quartzeditor = new ItemStack[3][4];
    public static ItemStack[][] clayeditor = new ItemStack[17][4];


    public static final String[] blockedit = {"§bBlock Skin", "§bBlock Skin", "§bBlock Skin", "§bBlock Skin"};
    public static final String[] sandstoneedit = {"§bCategory: Sandstone", "§bCategory: Sandstone", "§bCategory: Sandstone", "§bCategory: Sandstone"};
    public static final String[] quartzedit = {"§bCategory: Quartz Blocks", "§bCategory: Quartz Blocks", "§bCategory: Quartz Blocks", "§bCategory: Quartz Blocks"};
    public static final String[] clayedit = {"§bCategory: Clay", "§bCategory: Clay", "§bCategory: Clay", "§bCategory: Clay"};

    public void Load() {
        Invalid();
        HubSlime();
        Lobby();
        MLGRush();
    }

    private void HubSlime() {
        String[] display = {"§6hub", "§6hub", "§6返回大廳", "§6返回大厅"};
        for (int i = 0; i < 4; i++) {
            HubSlime[i] = ItemUtils.item(Material.SLIME_BALL, display[i], 1, -1, false, false, null, null, null, null);
        }
    }

    private void Lobby() {
        String[] Challenge = {"§6Challenger", "§6Challenger", "§6Challenger", "§6Challenger"};
        String[] spec = {"§bSpectate", "§bSpectate", "§bSpectate", "§bSpectate"};
        String[] gad = {"§bGadgets", "§bGadgets", "§bGadgets", "§bGadgets"};
        String[] settings = {"§cSettings", "§cSettings", "§cSettings", "§cSettings"};
        for (int i = 0; i < 4; i++) {
            LobbyItems[0][i] = ItemUtils.item(Material.GOLD_SWORD, Challenge[i], 1, -1, false, true, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE}, null, null, null);
            LobbyItems[1][i] = ItemUtils.item(Material.CHEST, gad[i], 1, -1, false, false, null, null, null, null);
            LobbyItems[2][i] = ItemUtils.item(Material.REDSTONE_COMPARATOR, settings[i], 1, -1, false, false, null, null, null, null);
            LobbyItems[3][i] = ItemUtils.item(Material.COMPASS, spec[i], 1, -1, false, false, null, null, null, null);
        }
    }

    private void Invalid() {
        String[] display = {"§4Sie können die Elemente hier nicht einstellen!", "§4You can't set the items here!", "§4你不能將物品設定於此處!", "§4你不能将物品设定于此处!"};
        for (int i = 0; i < 4; i++) {
            Invalid[i] = ItemUtils.item(Material.STAINED_GLASS_PANE, display[i], 1, 8, false, false, null, null, null, null);
        }
    }

    private void MLGRush() {
        {
            String[] title = {"§cMLGRush karten", "§cMLGRush maps", "§cMLGRush 地圖", "§cMLGRush 地图"};
            String[] MAP0 = {"§bBedwars", "§bBedwars", "§bBedwars", "§bBedwars"};
            String[] MAP1 = {"§b5cpsboi", "§b5cpsboi", "§b5cpsboi", "§b5cpsboi"};
            for (int i = 0; i < 4; i++) {
                MLGRush2x1.InventoryMap2x1[i] = Bukkit.createInventory(MLGRush2x1.Map2x1Holder, 27, title[i]);
                MLGRush2x1.InventoryMap2x1[i].setItem(10, ItemUtils.item(Material.BED, MAP0[i], 1, -1, false, false, null, null, null, null));
                MLGRush2x1.InventoryMap2x1[i].setItem(11, ItemUtils.item(Material.BRICK, MAP1[i], 1, -1, false, false, null, null, null, null));
            }
        }
        {
            String[] title = {"§cMLGRush Einstellungen", "§cMLGRush Settings", "§cMLGRush 設定", "§cMLGRush 设置"};
            String[] inv = {"§bInventar", "§bInventory", "§b物品欄", "§b物品栏"};
            String[] prefedit = {"§bPreferences", "§bPreferences", "§bPreferences", "§bPreferences"};
            for (int i = 0; i < 4; i++) {
                Settings[i] = Bukkit.createInventory(Core.SettingsHolder, 27, title[i]);
                Settings[i].setItem(11, ItemUtils.item(Material.CHEST, inv[i], 1, 3, false, false, null, null, null, null));
                Settings[i].setItem(15, ItemUtils.item(Material.DIODE, prefedit[i], 1, -1, false, false, null, null, null, null));
            }
        }
        {
            String[] title = {"§cMLGRush Inventar", "§cMLGRush Inventory", "§cMLGRush 物品欄", "§cMLGRush 物品栏"};
            String[] save = {"§aÄnderungen speichern", "§aSave Changes", "§a儲存設定", "§a储存设置"};
            String[] unsave = {"§cnicht gespeicherte Änderungen", "§cUnSave Changes", "§c不儲存設定", "§c不储存设置"};
            for (int i = 0; i < 4; i++) {
                InventoryKitEditor[i] = Bukkit.createInventory(Core.KitEditorHolder, 9, title[i]);
                InventoryUtils.fillItem(InventoryKitEditor[i], 0, 9, ItemUtils.item(Material.STAINED_GLASS_PANE, "§a", 1, 7, false, false, null, null, null, null));
                InventoryKitEditor[i].setItem(3, ItemUtils.item(Material.INK_SACK, save[i], 1, 10, false, false, null, null, null, null));
                InventoryKitEditor[i].setItem(5, ItemUtils.item(Material.INK_SACK, unsave[i], 1, 1, false, false, null, null, null, null));
            }
        }
        {
            String[] title = {"§eMode Select", "§eMode Select", "§eMode Select", "§eMode Select"};
            String[] rush = {"§bMLGRush", "§bMLGRush", "§bMLGRush", "§bMLGRush"};
            String[] stick = {"§bStickDuel", "§bStickDuel", "§bStickDuel", "§bStickDuel"};
            for (int i = 0; i < 4; i++) {
                Challenger[i] = Bukkit.createInventory(Core.ChallengerHolder, 27, title[i]);
                Challenger[i].setItem(11, ItemUtils.item(Material.BLAZE_ROD, rush[i], 1, -1, true, false, null, null, null, null));
                Challenger[i].setItem(15, ItemUtils.item(Material.STICK, stick[i], 1, -1, true, false, null, null, null, null));
            }
        }

        {
            String[][] block = {
                    {"§bSandstone Blocks", "§bSandstone Blocks", "§bSandstone Blocks", "§bSandstone Blocks"},
                    {"§bQuartz Blocks", "§bQuartz Blocks", "§bQuartz Blocks", "§bQuartz Blocks"},
                    {"§bOre (Blocks)", "§bOre (Blocks)", "§bOre (Blocks)", "§bOre (Blocks)"},
                    {"§bOres", "§bOres", "§bOres", "§bOres"},
                    {"§bQuartz Blocks", "§bQuartz Blocks", "§bQuartz Blocks", "§bQuartz Blocks"},
                    {"§bWoods", "§bWoods", "§bWoods", "§bWoods"}
            };
            String[][] prefs = {
                    {"§6Another Kb for flats", "§6Another Kb for flats", "§6Another Kb for flats", "§6Another Kb for flats"},
                    {"§6Auto Block Reset", "§6Auto Block Reset", "§6Auto Block Reset", "§6Auto Block Reset"},
                    {"§6No Hit Delay", "§6No Hit Delay", "§6No Hit Delay", "§6No Hit Delay"},
                    {"§6Fall Damage", "§6Fall Damage", "§6Fall Damage", "§6Fall Damage"},
                    {"§6Auto Block Remove", "§6Auto Block Remove", "§6Auto Block Remove", "§6Auto Block Remove"}
            };
            String[] active = {"§aActivated", "§aActivated", "§aActivated", "§aActivated"};
            String[] deactive = {"§cDeactivated", "§cDeactivated", "§cDeactivated", "§cDeactivated"};
            for (int i = 0; i < 4; i++) {
                ItemStack[] sticks = {
                        ItemUtils.item(Material.STICK, null, 1, -1, false, false, null, null, null, null),
                        ItemUtils.item(Material.DIAMOND_SWORD, null, 1, -1, false, false, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}, null, null, null),
                        ItemUtils.item(Material.DIAMOND_HOE, null, 1, -1, false, false, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}, null, null, null),
                        ItemUtils.item(Material.BLAZE_ROD, null, 1, -1, false, false, null, null, null, null),
                        ItemUtils.item(Material.BONE, null, 1, -1, false, false, null, null, null, null),
                        ItemUtils.item(Material.FEATHER, null, 1, -1, false, false, null, null, null, null)
                };
                ItemStack[] sticks2 = {
                        ItemUtils.item(Material.STICK, null, 1, -1, true, false, null, null, null, Collections.singletonList(GlobalString.selected[i])),
                        ItemUtils.item(Material.DIAMOND_SWORD, null, 1, -1, true, false, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}, null, null, Collections.singletonList(GlobalString.selected[i])),
                        ItemUtils.item(Material.DIAMOND_HOE, null, 1, -1, true, false, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}, null, null, Collections.singletonList(GlobalString.selected[i])),
                        ItemUtils.item(Material.BLAZE_ROD, null, 1, -1, true, false, null, null, null, Collections.singletonList(GlobalString.selected[i])),
                        ItemUtils.item(Material.BONE, null, 1, -1, true, false, null, null, null, Collections.singletonList(GlobalString.selected[i])),
                        ItemUtils.item(Material.FEATHER, null, 1, -1, true, false, null, null, null, Collections.singletonList(GlobalString.selected[i]))
                };

                ItemStack[] categoryblockeditor = {
                        ItemUtils.item(Material.SANDSTONE, block[0][i], 1, -1, false, false, null, null, null, Collections.singletonList(GlobalString.category[i])),
                        ItemUtils.item(Material.QUARTZ_BLOCK, block[1][i], 1, -1, false, false, null, null, null, Collections.singletonList(GlobalString.category[i])),
                        ItemUtils.item(Material.STAINED_CLAY, block[2][i], 1, -1, false, false, null, null, null, Collections.singletonList(GlobalString.category[i]))
                };

                ItemStack[] sandstoneeditor = {
                        ItemUtils.item(Material.SANDSTONE, null, 1, 0, false, false, null, null, null, null),
                        ItemUtils.item(Material.SANDSTONE, null, 1, 1, false, false, null, null, null, null),
                        ItemUtils.item(Material.SANDSTONE, null, 1, 2, false, false, null, null, null, null),
                        ItemUtils.item(Material.RED_SANDSTONE, null, 1, 0, false, false, null, null, null, null),
                        ItemUtils.item(Material.RED_SANDSTONE, null, 1, 1, false, false, null, null, null, null),
                        ItemUtils.item(Material.RED_SANDSTONE, null, 1, 2, false, false, null, null, null, null),
                };
                ItemStack[] quartzeditor = {
                        ItemUtils.item(Material.QUARTZ_BLOCK, null, 1, 0, false, false, null, null, null, null),
                        ItemUtils.item(Material.QUARTZ_BLOCK, null, 1, 1, false, false, null, null, null, null),
                        ItemUtils.item(Material.QUARTZ_BLOCK, null, 1, 2, false, false, null, null, null, null),
                };
                ItemStack[] clayeditor = {
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 0, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 1, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 2, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 3, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 4, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 5, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 6, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 7, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 8, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 9, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 10, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 11, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 12, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 13, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 14, false, false, null, null, null, null),
                        ItemUtils.item(Material.STAINED_CLAY, null, 1, 15, false, false, null, null, null, null),
                        ItemUtils.item(Material.HARD_CLAY, null, 1, -1, false, false, null, null, null, null)
                };

                ItemStack[] prefers = {
                        ItemUtils.item(Material.STICK, prefs[0][i], 1, -1, false, false, null, null, null, null),
                        ItemUtils.item(Material.SANDSTONE, prefs[1][i], 1, 2, false, false, null, null, null, null),
                        ItemUtils.item(Material.RAW_FISH, prefs[2][i], 1, 3, false, false, null, null, null, null),
                        ItemUtils.item(Material.DIAMOND_BOOTS, prefs[3][i], 1, -1, false, false, null, null, null, null),
                        ItemUtils.item(Material.WOOD_PICKAXE, prefs[4][i], 1, -1, false, false, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}, null, null, null)
                };
                ItemStack[] pref = {ItemUtils.item(Material.INK_SACK, active[i], 1, 10, false, false, null, null, null, null),
                        ItemUtils.item(Material.INK_SACK, deactive[i], 1, 1, false, false, null, null, null, null)
                };
                for (int j = 0; j < sticks.length; j++) {
                    stickeditor[0][j][i] = sticks[j];
                    stickeditor[1][j][i] = sticks2[j];
                }
                for (int j = 0; j < categoryblockeditor.length; j++) {
                    ItemStackList.categoryblockeditor[j][i] = categoryblockeditor[j];
                }
                for (int j = 0; j < sandstoneeditor.length; j++) {
                    ItemStackList.sandstoneeditor[j][i] = sandstoneeditor[j];
                }
                for (int j = 0; j < quartzeditor.length; j++) {
                    ItemStackList.quartzeditor[j][i] = quartzeditor[j];
                }
                for (int j = 0; j < clayeditor.length; j++) {
                    ItemStackList.clayeditor[j][i] = clayeditor[j];
                }
                for (int j = 0; j < prefers.length; j++) {
                    prefereditor[j][i] = prefers[j];
                }
                for (int j = 0; j < pref.length; j++) {
                    preferButton[j][i] = pref[j];
                }
            }
        }

        {
            String[] title = {"§bGadgets", "§bGadgets", "§bGadgets", "§bGadgets"};
            String[] stick = {"§bStick Skin", "§bStick Skin", "§bStick Skin", "§bStick Skin"};
            String[] block = {"§bBlock Skin", "§bBlock Skin", "§bBlock Skin", "§bBlock Skin"};
            String[] spec = {"§bSpectate Menu (Testing)", "§bSpectate Menu (Testing)", "§bSpectate Menu (Testing)", "§bSpectate Menu (Testing)"};
            for (int i = 0; i < 4; i++) {
                {
                    Pickaxe[i] = ItemUtils.item(Material.WOOD_PICKAXE, null, 1, -1, false, true, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}, new Enchantment[]{Enchantment.DIG_SPEED}, new int[]{2}, null);
                    ItemStack[] sticks = {
                            ItemUtils.item(Material.STICK, null, 1, -1, false, false, null, new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}, null),
                            ItemUtils.item(Material.DIAMOND_SWORD, null, 1, -1, false, true, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}, new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}, null),
                            ItemUtils.item(Material.DIAMOND_HOE, null, 1, -1, false, true, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}, new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}, null),
                            ItemUtils.item(Material.BLAZE_ROD, null, 1, -1, false, false, null, new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}, null),
                            ItemUtils.item(Material.BONE, null, 1, -1, false, false, null, new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}, null),
                            ItemUtils.item(Material.FEATHER, null, 1, -1, false, false, null, new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}, null)
                    };
                    for (int j = 0; j < sticks.length; j++) {
                        Sticks[j][i] = sticks[j];
                    }
                    ItemStack[] blocks = {
                            ItemUtils.item(Material.SANDSTONE, null, 64, 0, false, false, null, null, null, null),
                            ItemUtils.item(Material.SANDSTONE, null, 64, 1, false, false, null, null, null, null),
                            ItemUtils.item(Material.SANDSTONE, null, 64, 2, false, false, null, null, null, null),
                            ItemUtils.item(Material.RED_SANDSTONE, null, 64, 0, false, false, null, null, null, null),
                            ItemUtils.item(Material.RED_SANDSTONE, null, 64, 1, false, false, null, null, null, null),
                            ItemUtils.item(Material.RED_SANDSTONE, null, 64, 2, false, false, null, null, null, null),
                            ItemUtils.item(Material.QUARTZ_BLOCK, null, 64, 0, false, false, null, null, null, null),
                            ItemUtils.item(Material.QUARTZ_BLOCK, null, 64, 1, false, false, null, null, null, null),
                            ItemUtils.item(Material.QUARTZ_BLOCK, null, 64, 2, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 0, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 1, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 2, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 3, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 4, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 5, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 6, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 7, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 8, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 9, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 10, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 11, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 12, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 13, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 14, false, false, null, null, null, null),
                            ItemUtils.item(Material.STAINED_CLAY, null, 64, 15, false, false, null, null, null, null),
                            ItemUtils.item(Material.HARD_CLAY, null, 64, -1, false, false, null, null, null, null),
                    };
                    for (int j = 0; j < blocks.length; j++) {
                        Blocks[j][i] = blocks[j];
                    }
                }
                Gadgets[i] = Bukkit.createInventory(Core.GadgetsHolder, 27, title[i]);
                Gadgets[i].setItem(11, ItemUtils.item(Material.BLAZE_ROD, stick[i], 1, -1, false, false, null, null, null, null));
                Gadgets[i].setItem(15, ItemUtils.item(Material.SANDSTONE, block[i], 1, -1, false, false, null, null, null, null));
                specinventory[i] = Bukkit.createInventory(Core.specHolder, 54, spec[i]);
            }
        }
    }
}
