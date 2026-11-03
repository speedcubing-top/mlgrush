package top.speedcubing.mlgrush.List;

import top.speedcubing.common.text.Msg;
import top.speedcubing.server.lang.Lang;

/**
 *
 **/
public class StringList {

    public static final String PREFIX = Msg.prefix("MLGRush", 'b');

    /**
     * Resolves a language key into one string per language id (0-3), for per-language item/inventory caches.
     */
    public static String[] lang(String key, String... param) {
        return text("%lang_" + key + "%", param);
    }

    public static String[] text(String unformatted, String... param) {
        String[] out = new String[4];
        for (int i = 0; i < 4; i++)
            out[i] = Lang.of(unformatted, param).getString(i);
        return out;
    }

    public static String[][] LobbySideBar = {
            {"§5", "§8» §b", "§aOnline-Spieler§a:", "§8", "§6speedcubing.top", "§3MLGRush"},
            {"§5", "§8» §b", "§aOnline players§a:", "§8", "§6speedcubing.top", "§3MLGRush"},
            {"§5", "§8» §b", "§a線上玩家:", "§8", "§6speedcubing.top", "§3MLGRush"},
            {"§5", "§8» §b", "§a线上玩家", "§8", "§6speedcubing.top", "§3MLGRush"}
    };
    public static String[][] MLGRushSideBar = {
            {"§3MLGRush 2x1 §7▶§b %t%", "§6speedcubing.top", "§3", " §7▶ ", " §7▶ ", "§0"},
            {"§3MLGRush 2x1 §7▶§b %t%", "§6speedcubing.top", "§3", " §7▶ ", " §7▶ ", "§0"},
            {"§3MLGRush 2x1 §7▶§b %t%", "§6speedcubing.top", "§3", " §7▶ ", " §7▶ ", "§0"},
            {"§3MLGRush 2x1 §7▶§b %t%", "§6speedcubing.top", "§3", " §7▶ ", " §7▶ ", "§0"}
    };
    public static String[][] StickDuelSideBar = {
            {"§3StickDuel §7▶§b %t%", "§6speedcubing.top", "§3", " §7▶ ", " §7▶ ", "§0"},
            {"§3StickDuel §7▶§b %t%", "§6speedcubing.top", "§3", " §7▶ ", " §7▶ ", "§0"},
            {"§3StickDuel §7▶§b %t%", "§6speedcubing.top", "§3", " §7▶ ", " §7▶ ", "§0"},
            {"§3StickDuel §7▶§b %t%", "§6speedcubing.top", "§3", " §7▶ ", " §7▶ ", "§0"}
    };
    public static String[][] Lores = new String[4][];

    static {
        String[] inFights = lang("mlgrush_lore_in_fights", "%int%");
        String[] queued = lang("mlgrush_lore_queued", "%int%");
        for (int i = 0; i < 4; i++)
            Lores[i] = new String[]{" ", inFights[i], queued[i]};
    }

}
