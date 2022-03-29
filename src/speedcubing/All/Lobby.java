package speedcubing.All;


import cubing.spigot.lib.bukkit.PlayerUtils;
import cubing.spigot.lib.bukkit.api.SideBarAPI;
import cubingserver.libs.User;
import cubingserver.speedcubingServer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import speedcubing.Core.Core;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;
import speedcubing.List.StringList;

import java.util.*;

public class Lobby {
    public static void Join(Player player, boolean externalMessage) {
        UUID uuid = player.getUniqueId();
        String name = player.getName();
        String[] format = User.getFormat(User.getRank(uuid));
        int lang = User.getLang(uuid);
        if (externalMessage)
            player.sendMessage(StringList.LobbyJoinMessage[lang]
                    .replace("%f0%", format[0])
                    .replace("%f1%", format[2])
                    .replace("%name%", name));
        for (Player p : player.getWorld().getPlayers()) {
            p.sendMessage(StringList.LobbyJoinMessage[User.getLang(p.getUniqueId())]
                    .replace("%format0%", format[0])
                    .replace("%format1%", format[1])
                    .replace("%format2%", format[2])
                    .replace("%name%", name));
        }

        player.setMaximumNoDamageTicks(20);
        player.getInventory().setHeldItemSlot(0);
        Lobby.Items(player);
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        for (Packet p : EntityList.LobbyNPCPackets) {
            connection.sendPacket(p);
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (Packet p : EntityList.LobbyNPCDelayedPackets) {
                    connection.sendPacket(p);
                }
            }
        }, 4000);
        for (PacketPlayOutSpawnEntityLiving packet : EntityList.spawnpackets) {
            connection.sendPacket(packet);
        }

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        String[] sidebar = StringList.LobbySideBar[User.getLang(player.getUniqueId())];
        Objective objective = scoreboard.registerNewObjective("sidebar", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(sidebar[5]);
        SideBarAPI.sidebarTeams(scoreboard, new String[][]{
                {sidebar[4]},
                {sidebar[3]},
                {sidebar[2]},
                {"", sidebar[1], Integer.toString(speedcubingServer.AllPlayers)},
                {sidebar[0]},
        }, new int[]{4, 3, 2, 1, 0});
        player.setScoreboard(scoreboard);

        PlayerUtils.sendActionBar(connection, "");
        player.setGameMode(GameMode.ADVENTURE);
    }

    public static void Items(Player player) {
        int lang = User.getLang(player.getUniqueId());
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
