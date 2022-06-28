package speedcubing.BukkitEventListener;


import speedcubing.lib.bukkit.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import speedcubing.Core.Core;
import speedcubing.Core.MLGRush2x1;
import speedcubing.Core.MLGRush4x1;
import speedcubing.Core.StickDuel;

public class PlayerMove implements Listener {

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        double time = System.currentTimeMillis();
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
            Location to = e.getTo();
            switch (world.getName()) {
                case "MLGRush4x1": {
                    double z = to.getZ();
                    double x = to.getX();
                    if (Math.abs(z - Core.randomToBed(z)) > 51 || (Math.abs(x) > 51 && x < 500))
                        player.teleport(e.getFrom());
                    if (player.getLocation().getX() < 500)
                        if (!player.getAllowFlight() && to.getY() <= 85)
                            MLGRush4x1.fell(player);
                }
                break;
                case "MLGRush2x1":
                    if (player.getAllowFlight()) {
                        double z = to.getZ();
                        if (Math.abs(z - Core.randomToBed(z)) > 31 || Math.abs(to.getX()) > 51)
                            player.teleport(e.getFrom());
                        else if (to.getY() <= 85)
                            player.teleport(new Location(Bukkit.getWorld("MLGRush2x1"), 0.5, 100, Core.randomToBed(z), 0, 0));
                        else if (to.getY() >= 150)
                            player.teleport(e.getFrom());
                    } else if (to.getY() <= 85) {
                        MLGRush2x1.fell(player);
                    }
                    break;
                case "StickDuel":
                    if (player.getAllowFlight()) {
                        double z = to.getZ();
                        if (Math.abs(z - Core.randomToBed(z)) > 31 || Math.abs(to.getX()) > 51)
                            player.teleport(e.getFrom());
                        else if (to.getY() <= 85)
                            player.teleport(new Location(Bukkit.getWorld("StickDuel"), 0.5, 100, Core.randomToBed(z), 0, 0));
                        else if (to.getY() >= 150)
                            player.teleport(e.getFrom());
                    } else if (!player.getAllowFlight() && to.getY() <= 85)
                        StickDuel.Fell(player, time);
                    break;
                case "world":
                    if (Math.abs(to.getX()) > 101 || Math.abs(to.getZ()) > 101)
                        player.teleport(e.getFrom());
                    if (to.getY() <= 80) {
                        player.closeInventory();
                        player.teleport(new Location(player.getWorld(), 0.5, 100, 0.5, 0, 0));
                    }
                    break;
            }
        }
    }
}


