package top.speedcubing.mlgrush.BukkitEventListener;


import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import top.speedcubing.mlgrush.Member;

public class PlayerMove implements Listener {

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
            Location to = e.getTo();
            switch (world.getName()) {
                case "MLGRush4x1": {
                    double z = to.getZ();
                    double x = to.getX();
                    Member member = Member.getMember(player);
                    if (Math.abs(z - member.game.coord - 0.5) > 51 || (Math.abs(x - member.game.map * 500 - 0.5) > 51))
                        player.teleport(e.getFrom());
                    if (player.getLocation().getX() < 500)
                        if (!player.getAllowFlight() && to.getY() <= 85)
                            Member.getMember(player).game.fell(player);
                }
                break;
                case "MLGRush2x1":
                case "StickDuel":
                    Member member = Member.getMember(player);
                    boolean flag = Math.abs(to.getZ() - member.game.coord - 0.5) > 51 || (Math.abs(to.getX() - member.game.map * 500 - 0.5) > 51);
                    if (player.getAllowFlight()) {
                        if (flag)
                            player.teleport(e.getFrom());
                        else if (to.getY() <= 85)
                            player.teleport(new Location(player.getWorld(), 0.5, 100, member.game.coord, 0, 0));
                        else if (to.getY() >= 150)
                            player.teleport(e.getFrom());
                    } else if (flag || to.getY() <= 85)
                        Member.getMember(player).game.fell(player);
                    break;
                case "lobby":
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


