package speedcubing.BukkitEventListener;

import com.mysql.jdbc.Buffer;
import speedcubing.server.speedcubingServer;
import speedcubing.server.things.events.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import speedcubing.All.Lobby;
import speedcubing.Core.Core;
import speedcubing.Core.MLGRush2x1;

import java.util.UUID;

public class PlayerQuit implements Listener {

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (PlayerJoin.JoinPackets.containsKey(uuid)) {
            if (player.getWorld().getName().equals("MLGRush2x1"))
                MLGRush2x1.gameEnd(uuid);
            else {
                Core.QuitAllQueue(uuid);
                Lobby.DeleteChallenge(uuid);
                Core.iseditingkit.remove(uuid);
            }
            new Thread(() -> {
                Integer[] x = Core.integerCache.get(uuid);
                speedcubingServer.connection.update("playersdata",
                        "mlgrush_stick=" + x[0] +
                                ",mlgrush_block=" + x[1] +
                                ",mlgrush_pickaxe=" + x[2] +
                                ",mlgrush_stickskin=" + x[3] +
                                ",mlgrush_blockskin=" + x[4] +
                                ",mlgrush_autoreset=" + x[5] +
                                ",mlgrush_hitdelay=" + x[6] +
                                ",mlgrush_falldamage=" + x[7] +
                                ",mlgrush_autoremove=" + x[8] +
                                ",mlgrush_oldkb=" + x[9]
                        , "uuid='" + uuid + "'");
                Core.integerCache.remove(uuid);
            }).start();

            EntityDamageByEntity.LastDamage.remove(uuid);
        }
    }
}