package top.speedcubing.mlgrush.List;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import top.speedcubing.lib.bukkit.inventory.*;
import top.speedcubing.mlgrush.All.Lobby;
import top.speedcubing.mlgrush.Core.*;
import top.speedcubing.mlgrush.Core.Game.MLGRush2x1;
import top.speedcubing.mlgrush.Member;
import top.speedcubing.server.lang.GlobalString;

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
    public static ItemStack[] unQueue = new ItemStack[4];

    public static final String[] blockedit = {"§bBlock Skin", "§bBlock Skin", "§bBlock Skin", "§bBlock Skin"};
    public static final String[] sandstoneedit = {"§bCategory: Sandstone", "§bCategory: Sandstone", "§bCategory: Sandstone", "§bCategory: Sandstone"};
    public static final String[] quartzedit = {"§bCategory: Quartz Blocks", "§bCategory: Quartz Blocks", "§bCategory: Quartz Blocks", "§bCategory: Quartz Blocks"};
    public static final String[] clayedit = {"§bCategory: Clay", "§bCategory: Clay", "§bCategory: Clay", "§bCategory: Clay"};

    public static Inventory[] duelMenu = new Inventory[4];

    public void Load() {
        Invalid();
        HubSlime();
        Lobby();
        MLGRush();
    }

    private void HubSlime() {
        String[] display = {"§6hub", "§6hub", "§6返回大廳", "§6返回大厅"};
        for (int i = 0; i < 4; i++) {
            HubSlime[i] = new ItemBuilder(Material.SLIME_BALL).name(display[i]).build();
        }
    }

    private void Lobby() {
        String[] title = {"§bJoin Queue", "§bJoin Queue", "§bJoin Queue", "§bJoin Queue"};
        for (int i = 0; i < 4; i++) {
            duelMenu[i] = new InventoryBuilder(18, title[i])
                    .setItem(new ItemBuilder(Material.SANDSTONE).name("Normal").lore(StringList.Lores[i][0], StringList.Lores[i][1].replace("%int%", "0"), StringList.Lores[i][2].replace("%int%", "0")).build(), 0)
                    .setItem(new ItemBuilder(Material.SANDSTONE).name("Inclined").lore(StringList.Lores[i][0], StringList.Lores[i][1].replace("%int%", "0"), StringList.Lores[i][2].replace("%int%", "0")).build(), 1)
                    .setAllClickEvent((p, e) -> {
                        if (e.getRawSlot() < MLGRush2x1.inst.maps) {
                            int map = e.getRawSlot();
                            Member member = Member.getMember(p);
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                            Member waiter = MLGRush2x1.inst.Queue[map];
                            if (waiter != null) {
                                if (waiter != member) {
                                    new MLGRush2x1(member, MLGRush2x1.inst.Queue[map], map);
                                    member.user.sendLangMessage(StringList.MLGRushOpponentFound);
                                    member.QuitAllQueue();
                                    MLGRush2x1.inst.Queue[map] = member;
                                } else {
                                    member.QuitAllQueue();
                                    Lobby.Items(p);
                                }
                                MLGRush2x1.inst.lore(map, 0, false);
                            } else {
                                member.user.sendLangMessage(StringList.MLGRushQueue);
                                member.QuitAllQueue();
                                MLGRush2x1.inst.Queue[map] = member;
                                MLGRush2x1.inst.lore(map, 1, false);
                            }
                            p.closeInventory();
                        }
                    }).getInventory();
        }
        String[] Challenge = {"§6Challenger", "§6Challenger", "§6Challenger", "§6Challenger"};
        String[] spec = {"§bSpectate", "§bSpectate", "§bSpectate", "§bSpectate"};
        String[] gad = {"§bGadgets", "§bGadgets", "§bGadgets", "§bGadgets"};
        String[] settings = {"§cSettings", "§cSettings", "§cSettings", "§cSettings"};
        for (int i = 0; i < 4; i++) {
            LobbyItems[0][i] = new ItemBuilder(Material.GOLD_SWORD).name(Challenge[i]).hideAttr().hideUnBreak().build();
            LobbyItems[1][i] = new ItemBuilder(Material.CHEST).name(gad[i]).build();
            LobbyItems[2][i] = new ItemBuilder(Material.REDSTONE_COMPARATOR).name(settings[i]).build();
            LobbyItems[3][i] = new ItemBuilder(Material.COMPASS).name(spec[i]).build();
        }
    }


    private void Invalid() {
        String[] display = {"§4Sie können die Elemente hier nicht einstellen!", "§4You can't set the items here!", "§4你不能將物品設定於此處!", "§4你不能将物品设定于此处!"};
        for (int i = 0; i < 4; i++) {
            Invalid[i] = new ItemBuilder(Material.STAINED_GLASS_PANE).durability(8).name(display[i]).build();
        }
    }

    private void MLGRush() {
        {
            String[] unqueue = {"§4Click to unqueue", "§4Click to unqueue", "§4Click to unqueue", "§4Click to unqueue"};
            for (int i = 0; i < 4; i++) {
                unQueue[i] = new ItemBuilder(Material.REDSTONE).name(unqueue[i]).build();
            }
        }
        {
            String[] title = {"§cMLGRush karten", "§cMLGRush maps", "§cMLGRush 地圖", "§cMLGRush 地图"};
            String[] MAP0 = {"§bBedwars", "§bBedwars", "§bBedwars", "§bBedwars"};
            String[] MAP1 = {"§b5cpsboi", "§b5cpsboi", "§b5cpsboi", "§b5cpsboi"};
            for (int i = 0; i < 4; i++) { // ?
                MLGRush2x1.InventoryMap2x1[i] = new InventoryBuilder(27, title[i])
                        .setItem(new ItemBuilder(Material.BED).name(MAP0[i]).build(), 10)
                        .setItem(new ItemBuilder(Material.BRICK).name(MAP1[i]).build(), 11).getInventory();
            }
        }
        {
            String[] title = {"§cMLGRush Einstellungen", "§cMLGRush Settings", "§cMLGRush 設定", "§cMLGRush 设置"};
            String[] inv = {"§bInventar", "§bInventory", "§b物品欄", "§b物品栏"};
            String[] prefedit = {"§bPreferences", "§bPreferences", "§bPreferences", "§bPreferences"};
            for (int i = 0; i < 4; i++) {
                Settings[i] = new InventoryBuilder(27, title[i])
                        .setItem(new ItemBuilder(Material.CHEST).durability(3).name(inv[i]).build(), (p, e) -> {
                            p.playSound(p.getLocation(), Sound.CLICK, 1F, 1F);
                            Core.openKitEditor(p);
                        }, 11)
                        .setItem(new ItemBuilder(Material.DIODE).name(prefedit[i]).build(), (p, e) -> {
                            p.playSound(p.getLocation(), Sound.CLICK, 1F, 1F);
                            Core.openPreference(p);
                        }, 15).getInventory();
            }
        }
        {
            String[] title = {"§cMLGRush Inventar", "§cMLGRush Inventory", "§cMLGRush 物品欄", "§cMLGRush 物品栏"};
            String[] save = {"§aÄnderungen speichern", "§aSave Changes", "§a儲存設定", "§a储存设置"};
            String[] unsave = {"§cnicht gespeicherte Änderungen", "§cUnSave Changes", "§c不儲存設定", "§c不储存设置"};
            for (int i = 0; i < 4; i++) {
                InventoryKitEditor[i] = new InventoryBuilder(9, title[i])
                        .setItem(new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name("§a").build(), 0, 8)
                        .setItem(new ItemBuilder(Material.INK_SACK).durability(10).name(save[i]).build(), 3)
                        .setItem(new ItemBuilder(Material.INK_SACK).durability(1).name(unsave[i]).build(), 5)
                        .setClickEvent((p, e) -> {
                            p.playSound(p.getLocation(), Sound.CLICK, 1F, 1F);
                            Core.saveKitEditor(p, e.getCursor());
                        }, 3)
                        .setClickEvent((p, e) -> {
                            p.playSound(p.getLocation(), Sound.CLICK, 1F, 1F);
                            p.closeInventory();
                        }, 5)
                        .setClickable(true, 36, 44)
                        .setCloseInventory((p, e) -> {
                            Member member = Member.getMember(p);
                            if (member.iseditingkit) {
                                member.iseditingkit = false;
                                member.user.sendLangMessage(GlobalString.UnSavedSettings);
                                Lobby.Items(p);
                            }
                        }).getInventory();
            }
        }
        {
            String[] title = {"§eMode Select", "§eMode Select", "§eMode Select", "§eMode Select"};
            String[] rush = {"§bMLGRush", "§bMLGRush", "§bMLGRush", "§bMLGRush"};
            String[] stick = {"§bStickDuel", "§bStickDuel", "§bStickDuel", "§bStickDuel"};
            for (int i = 0; i < 4; i++) {
                Challenger[i] = Bukkit.createInventory(Core.ChallengerHolder, 27, title[i]);
                Challenger[i].setItem(11, new ItemBuilder(Material.BLAZE_ROD).name(rush[i]).glow().build());
                Challenger[i].setItem(15, new ItemBuilder(Material.STICK).name(stick[i]).glow().build());
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
                        new ItemBuilder(Material.STICK).build(),
                        new ItemBuilder(Material.DIAMOND_SWORD).hideAttr().build(),
                        new ItemBuilder(Material.DIAMOND_HOE).hideAttr().build(),
                        new ItemBuilder(Material.BLAZE_ROD).build(),
                        new ItemBuilder(Material.BONE).build(),
                        new ItemBuilder(Material.FEATHER).build()
                };
                ItemStack[] sticks2 = {
                        new ItemBuilder(Material.STICK).glow().lore(GlobalString.selected.get(i)).build(),
                        new ItemBuilder(Material.DIAMOND_SWORD).glow().hideAttr().lore(GlobalString.selected.get(i)).build(),
                        new ItemBuilder(Material.DIAMOND_HOE).glow().hideAttr().lore(GlobalString.selected.get(i)).build(),
                        new ItemBuilder(Material.BLAZE_ROD).glow().lore(GlobalString.selected.get(i)).build(),
                        new ItemBuilder(Material.BONE).glow().lore(GlobalString.selected.get(i)).build(),
                        new ItemBuilder(Material.FEATHER).glow().lore(GlobalString.selected.get(i)).build()
                };

                ItemStack[] categoryblockeditor = {
                        new ItemBuilder(Material.SANDSTONE).name(block[0][i]).lore(GlobalString.category.get(i)).build(),
                        new ItemBuilder(Material.QUARTZ_BLOCK).name(block[1][i]).lore(GlobalString.category.get(i)).build(),
                        new ItemBuilder(Material.STAINED_CLAY).name(block[2][i]).lore(GlobalString.category.get(i)).build()
                };

                ItemStack[] sandstoneeditor = {
                        new ItemBuilder(Material.SANDSTONE).build(),
                        new ItemBuilder(Material.SANDSTONE).durability(1).build(),
                        new ItemBuilder(Material.SANDSTONE).durability(2).build(),
                        new ItemBuilder(Material.RED_SANDSTONE).build(),
                        new ItemBuilder(Material.RED_SANDSTONE).durability(1).build(),
                        new ItemBuilder(Material.RED_SANDSTONE).durability(2).build(),
                };
                ItemStack[] quartzeditor = {
                        new ItemBuilder(Material.QUARTZ_BLOCK).build(),
                        new ItemBuilder(Material.QUARTZ_BLOCK).durability(1).build(),
                        new ItemBuilder(Material.QUARTZ_BLOCK).durability(2).build(),
                };
                ItemStack[] clayeditor = {
                        new ItemBuilder(Material.STAINED_CLAY).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(1).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(2).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(3).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(4).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(5).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(6).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(7).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(8).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(9).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(10).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(11).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(12).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(13).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(14).build(),
                        new ItemBuilder(Material.STAINED_CLAY).durability(15).build(),
                        new ItemBuilder(Material.HARD_CLAY).build()
                };

                ItemStack[] prefers = {
                        new ItemBuilder(Material.STICK).name(prefs[0][i]).build(),
                        new ItemBuilder(Material.SANDSTONE).durability(2).name(prefs[1][i]).build(),
                        new ItemBuilder(Material.RAW_FISH).durability(3).name(prefs[2][i]).build(),
                        new ItemBuilder(Material.DIAMOND_BOOTS).name(prefs[3][i]).build(),
                        new ItemBuilder(Material.WOOD_PICKAXE).name(prefs[4][i]).hideAttr().build()
                };
                ItemStack[] pref = {
                        new ItemBuilder(Material.INK_SACK).durability(10).name(active[i]).build(),
                        new ItemBuilder(Material.INK_SACK).durability(1).name(deactive[i]).build()
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
                    Pickaxe[i] = new ItemBuilder(Material.WOOD_PICKAXE).unBreak().hideAttr().ench(new Enchantment[]{Enchantment.DIG_SPEED}, new int[]{2}).build();
                    ItemStack[] sticks = {
                            new ItemBuilder(Material.STICK).ench(new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}).build(),
                            new ItemBuilder(Material.DIAMOND_SWORD).unBreak().hideAttr().ench(new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}).build(),
                            new ItemBuilder(Material.DIAMOND_HOE).unBreak().hideAttr().ench(new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}).build(),
                            new ItemBuilder(Material.BLAZE_ROD).ench(new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}).build(),
                            new ItemBuilder(Material.BONE).ench(new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}).build(),
                            new ItemBuilder(Material.FEATHER).ench(new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}).build(),
                    };
                    for (int j = 0; j < sticks.length; j++) {
                        Sticks[j][i] = sticks[j];
                    }
                    ItemStack[] blocks = {
                            new ItemBuilder(Material.SANDSTONE).amount(64).build(),
                            new ItemBuilder(Material.SANDSTONE).amount(64).durability(1).build(),
                            new ItemBuilder(Material.SANDSTONE).amount(64).durability(2).build(),
                            new ItemBuilder(Material.RED_SANDSTONE).amount(64).build(),
                            new ItemBuilder(Material.RED_SANDSTONE).amount(64).durability(1).build(),
                            new ItemBuilder(Material.RED_SANDSTONE).amount(64).durability(2).build(),
                            new ItemBuilder(Material.QUARTZ_BLOCK).amount(64).build(),
                            new ItemBuilder(Material.QUARTZ_BLOCK).amount(64).durability(1).build(),
                            new ItemBuilder(Material.QUARTZ_BLOCK).amount(64).durability(2).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(1).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(2).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(3).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(4).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(5).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(6).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(7).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(8).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(9).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(10).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(11).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(12).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(13).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(14).build(),
                            new ItemBuilder(Material.STAINED_CLAY).amount(64).durability(15).build(),
                            new ItemBuilder(Material.HARD_CLAY).amount(64).build(),
                    };
                    for (int j = 0; j < blocks.length; j++) {
                        Blocks[j][i] = blocks[j];
                    }
                }
                Gadgets[i] = new InventoryBuilder(27, title[i])
                        .setItem(new ItemBuilder(Material.BLAZE_ROD).name(stick[i]).build(), (p, e) -> {
                            p.playSound(p.getLocation(), Sound.CLICK, 1F, 1F);
                            Core.openStickEditor(p);
                        }, 11)
                        .setItem(new ItemBuilder(Material.SANDSTONE).name(block[i]).build(), (p, e) -> {
                            p.playSound(p.getLocation(), Sound.CLICK, 1F, 1F);
                            BlockEditor.createMainPage(p);
                        }, 15).getInventory();
                specinventory[i] = Bukkit.createInventory(Core.specHolder, 54, spec[i]);
            }
        }
    }
}
