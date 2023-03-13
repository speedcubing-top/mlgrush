package top.speedcubing.mlgrush.BukkitEventListener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import top.speedcubing.mlgrush.Member;

public class PlayerQuit implements Listener {

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Member member = Member.getMember(player);
        if (player.getWorld().getName().equals("MLGRush2x1"))
            member.game.gameEnd(member);
        else {
            member.QuitAllQueue();
            member.DeleteChallenge();
            member.iseditingkit = false;
        }
        int[] x = member.integerCache;
        member.user.dbUpdate(
                "mlgrush_stick=" + x[0] +
                        ",mlgrush_block=" + x[1] +
                        ",mlgrush_pickaxe=" + x[2] +
                        ",mlgrush_stickskin=" + x[3] +
                        ",mlgrush_blockskin=" + x[4] +
                        ",mlgrush_autoreset=" + x[5] +
                        ",mlgrush_hitdelay=" + x[6] +
                        ",mlgrush_falldamage=" + x[7] +
                        ",mlgrush_autoremove=" + x[8] +
                        ",mlgrush_oldkb=" + x[9]);
    }
}