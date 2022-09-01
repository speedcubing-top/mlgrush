package top.speedcubing.mlgrush.BukkitEventListener;

import top.speedcubing.mlgrush.Member;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        new Member(e.getPlayer());
     }
}






