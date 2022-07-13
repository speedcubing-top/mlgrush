package speedcubing.BukkitEventListener;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import speedcubing.All.Lobby;
import speedcubing.Core.Core;
import speedcubing.Member;
import speedcubing.server.libs.GlobalString;

public class InventoryClose implements Listener {
    @EventHandler
    public void InventoryCloseEvent(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() == Core.KitEditorHolder) {
            Player player = (Player) e.getPlayer();
            Member member = Member.getMember(player);
            if (member.iseditingkit) {
                member.iseditingkit = false;
                player.sendMessage(GlobalString.UnSavedSettings[member.lang]);
                Lobby.Items(player);
            }
        }
    }
}
