package speedcubing.BukkitEventListener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerSpawnLocation implements Listener {
    @EventHandler
    public void PlayerSpawnLocationEvent(PlayerSpawnLocationEvent e){
        e.setSpawnLocation(new Location(Bukkit.getWorld("world"), 129.5, 111, -21.5, 90, 0));
    }
}
