package speedcubing.BukkitEventListener;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import speedcubing.All.Lobby;
import speedcubing.Core.Core;
import speedcubing.server.libs.GlobalString;
import speedcubing.server.libs.User;

import java.util.UUID;

public class InventoryClose implements Listener {
    @EventHandler
    public void InventoryCloseEvent(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() == Core.KitEditorHolder) {
            Player player = (Player) e.getPlayer();
            UUID uuid = player.getUniqueId();
            if (Core.iseditingkit.contains(uuid)) {
                Core.iseditingkit.remove(uuid);
                player.sendMessage(GlobalString.UnSavedSettings[User.getUser(uuid).lang]);
                Lobby.Items(player);
            }
        }
    }
}
