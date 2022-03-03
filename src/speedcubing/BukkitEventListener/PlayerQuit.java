package speedcubing.BukkitEventListener;

import cubingserver.speedcubingServer;
import cubingserver.things.events.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import speedcubing.All.Lobby;
import speedcubing.Core.Core;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerQuit implements Listener {
    public static Set<UUID> bruh = new HashSet<>();

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (PlayerJoin.JoinPackets.containsKey(uuid)) {
            bruh.remove(uuid);
            player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0));
            Core.QuitAllQueue(uuid);
            Lobby.DeleteChallenge(uuid);
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
            Core.iseditingkit.remove(uuid);
        }
    }
}