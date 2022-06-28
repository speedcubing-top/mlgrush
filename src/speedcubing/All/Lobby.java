package speedcubing.All;


import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import speedcubing.Core.Core;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;
import speedcubing.lib.bukkit.PlayerUtils;
import speedcubing.lib.bukkit.SideBar;
import speedcubing.lib.bukkit.entity.Hologram;
import speedcubing.lib.bukkit.entity.NPC;
import speedcubing.server.libs.User;
import speedcubing.server.speedcubingServer;

import java.util.*;

public class Lobby {
    public static void Join(Player player, boolean externalMessage) {
        UUID uuid = player.getUniqueId();
        String name = player.getName();
        String[] format = speedcubingServer.getFormat(User.getUser(uuid).rank);
        int lang = User.getUser(uuid).lang;
        if (externalMessage)
            player.sendMessage(StringList.LobbyJoinMessage[lang]
                    .replace("%f0%", format[0])
                    .replace("%f1%", format[2])
                    .replace("%name%", name));
        for (Player p : player.getWorld().getPlayers()) {
            p.sendMessage(StringList.LobbyJoinMessage[User.getUser(p.getUniqueId()).lang]
                    .replace("%f0%", format[0])
                    .replace("%f1%", format[2])
                    .replace("%name%", name));
        }

        player.setMaximumNoDamageTicks(20);
        player.getInventory().setHeldItemSlot(0);
        Lobby.Items(player);
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        for (NPC npc : EntityList.npcs) {
            npc.setListenerValues(connection).spawn().rollBackListenerValues();
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (NPC npc : EntityList.npcs) {
                    connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc.entityPlayer));
                }
            }
        }, 4000);
        for (Hologram hologram : EntityList.Lobbys) {
            hologram.listener.add(connection);
            hologram.setListenerValues(connection).spawn().rollBackListenerValues();
        }
        String[] sidebar = StringList.LobbySideBar[User.getUser(player.getUniqueId()).lang];
        new SideBar(player, sidebar[5])
                .staticLine(sidebar[4], 4)
                .staticLine(sidebar[3], 3)
                .staticLine(sidebar[2], 2)
                .changeableLine(sidebar[1] + "loading...", 1)
                .staticLine(sidebar[0], 0).create();
        PlayerUtils.sendActionBar(player, "");
        player.setGameMode(GameMode.ADVENTURE);
    }

    public static void Items(Player player) {
        int lang = User.getUser(player.getUniqueId()).lang;
        player.setItemOnCursor(new ItemStack(Material.AIR));
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        inventory.setItem(0, ItemStackList.LobbyItems[0][lang]);
        inventory.setItem(7, ItemStackList.LobbyItems[1][lang]);
        inventory.setItem(8, ItemStackList.LobbyItems[2][lang]);
        inventory.setItem(4, ItemStackList.LobbyItems[3][lang]);
        player.updateInventory();
    }

    public static void DeleteChallenge(UUID id) {
        Core.Challenge.remove(id);
        Core.StickDuel.remove(id);
        for (Map.Entry<UUID, Set<UUID>> entry : Core.Challenge.entrySet()) {
            Core.Challenge.get(entry.getKey()).remove(id);
        }
        for (Map.Entry<UUID, UUID> entry : Core.StickDuel.entrySet()) {
            UUID key = entry.getKey();
            if (Core.StickDuel.get(key).equals(id))
                Core.StickDuel.remove(key);
        }
    }
}
