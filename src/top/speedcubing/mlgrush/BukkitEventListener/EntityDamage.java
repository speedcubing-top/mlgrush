package top.speedcubing.mlgrush.BukkitEventListener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import top.speedcubing.mlgrush.Member;

public class EntityDamage implements Listener {
    @EventHandler
    public void EntityDamageEvent(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof Player) {
            e.setDamage(0);
            switch (entity.getWorld().getName()) {
                case "MLGRush2x1":
                    EntityDamageEvent.DamageCause cause = e.getCause();
                    if (cause == EntityDamageEvent.DamageCause.FALL && !( Member.getMember((Player) entity).game).Preference[2])
                        e.setCancelled(true);
                    else if (cause == EntityDamageEvent.DamageCause.SUFFOCATION)
                        e.setCancelled(true);
                    break;
                case "StickDuel":
                case "MLGRush4x1":
                    if (e.getCause() == EntityDamageEvent.DamageCause.FALL)
                        e.setCancelled(true);
                    break;
                case "world":
                    e.setCancelled(true);
                    break;
            }
        }
    }
}

