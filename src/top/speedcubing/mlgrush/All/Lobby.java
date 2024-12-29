package top.speedcubing.mlgrush.All;


import java.util.Timer;
import java.util.TimerTask;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import top.speedcubing.common.database.DatabaseData;
import top.speedcubing.lib.bukkit.PlayerUtils;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.entity.NPC;
import top.speedcubing.mlgrush.List.EntityList;
import top.speedcubing.mlgrush.List.ItemStackList;
import top.speedcubing.mlgrush.List.StringList;
import top.speedcubing.mlgrush.Member;

public class Lobby {
    public static void Join(Player player, boolean externalMessage) {
        Member member = Member.getMember(player);
        if (externalMessage)
            member.user.sendMessage("%lang_lobby_msg_join_lobby%", member.user.getPrefixNameChatColor(false));
        for (Player p : player.getWorld().getPlayers()) {
            Member.getMember(p).user.sendMessage("%lang_lobby_msg_join_lobby%", member.user.getPrefixNameChatColor(false));
        }

        player.setMaximumNoDamageTicks(20);
        player.getInventory().setHeldItemSlot(0);
        Lobby.Items(player);
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (NPC npc : EntityList.npcs) {
                    connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc.entityPlayer));
                }
            }
        }, 4000);
        String[] sidebar = StringList.LobbySideBar[Member.getMember(player).user.lang];
        new SideBar(player, sidebar[5])
                .staticLine(sidebar[4], 4)
                .staticLine(sidebar[3], 3)
                .staticLine(sidebar[2], 2)
                .changeableLine(sidebar[1] + DatabaseData.onlineCount, 1)
                .staticLine(sidebar[0], 0).create();
        PlayerUtils.sendActionBar(player, "");
        player.setGameMode(GameMode.ADVENTURE);
    }

    public static void Items(Player player) {
        int lang = Member.getMember(player).user.lang;
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        inventory.setItem(0, ItemStackList.LobbyItems[0][lang]);
        inventory.setItem(7, ItemStackList.LobbyItems[1][lang]);
        inventory.setItem(8, ItemStackList.LobbyItems[2][lang]);
        inventory.setItem(4, ItemStackList.LobbyItems[3][lang]);
        player.updateInventory();
    }
}
