package speedcubing.BukkitEventListener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import speedcubing.All.Lobby;

public class PlayerChangedWorld implements Listener {

    @EventHandler
    public void PlayerChangeWorldEvent(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (player.getWorld().getName().equals("world"))
            Lobby.Join(player, false);
    }
}