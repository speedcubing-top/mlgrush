package speedcubing.BukkitEventListener;

import cubingserver.speedcubingServer;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import speedcubing.All.Lobby;
import speedcubing.Core.Core;

import java.util.UUID;

public class PlayerJoin implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.isOnline()) {
            UUID uuid = player.getUniqueId();
            player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0));
            PlayerQuit.bruh.add(uuid);
            Lobby.Join(player, true);
            new Thread(() -> Core.integerCache.put(uuid, ArrayUtils.toObject(speedcubingServer.connection.selectInts("playersdata", "mlgrush_stick,mlgrush_block,mlgrush_pickaxe,mlgrush_stickskin,mlgrush_blockskin,mlgrush_autoreset,mlgrush_hitdelay,mlgrush_falldamage,mlgrush_autoremove,mlgrush_oldkb", "uuid='" + uuid + "'")))).start();
        }
    }
}






