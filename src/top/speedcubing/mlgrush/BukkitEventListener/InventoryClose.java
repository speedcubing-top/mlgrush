package top.speedcubing.mlgrush.BukkitEventListener;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import top.speedcubing.mlgrush.All.Lobby;
import top.speedcubing.mlgrush.Member;

public class InventoryClose implements Listener {
    @EventHandler
    public void InventoryCloseEvent(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        Member member = Member.getMember(player);
        if (member.iseditingkit) {
            member.iseditingkit = false;
            member.user.sendMessage("%lang_inventory_discard%");
            Lobby.Items(player);
        }
    }
}