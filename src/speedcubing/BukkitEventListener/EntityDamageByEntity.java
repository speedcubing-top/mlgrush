package speedcubing.BukkitEventListener;


import cubingserver.libs.User;
import cubingserver.speedcubingServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import speedcubing.All.Lobby;
import speedcubing.Core.Core;
import speedcubing.Core.MLGRush2x1;
import speedcubing.Core.StickDuel;
import speedcubing.List.ItemStackList;

import java.util.*;

public class EntityDamageByEntity implements Listener {

    public static Map<UUID, UUID> LastDamage = new HashMap<>();
    public static Set<UUID> oldKb = new HashSet<>();

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
//        System.out.println("C");
        double time = System.currentTimeMillis();
        if (!(e.getDamager() instanceof Player && e.getEntity() instanceof Player))
            return;
        Player damager = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();
        if ((damager.getGameMode()== GameMode.SURVIVAL || damager.getGameMode() == GameMode.ADVENTURE) && damager.getAllowFlight()) {
            e.setCancelled(true);
            return;
        }
        switch (victim.getWorld().getName()) {
            case "world":
                if (damager.getItemInHand().getType() == Material.GOLD_SWORD) {
                    UUID victimUniqueId = victim.getUniqueId();
                    UUID damagerUniqueId = damager.getUniqueId();
                    LastDamage.put(damagerUniqueId, victimUniqueId);
                    if (Core.Challenge.containsKey(damagerUniqueId) && Core.Challenge.get(damagerUniqueId).contains(victimUniqueId)) {
                        if (Core.StickDuel.containsKey(damagerUniqueId) && Core.StickDuel.get(damagerUniqueId).equals(victimUniqueId))
                            StickDuel.gameStart(damagerUniqueId, victimUniqueId, 0);
                        else
                            MLGRush2x1.gameStart(damagerUniqueId, victimUniqueId, 0);
                        Lobby.DeleteChallenge(damagerUniqueId);
                        Lobby.DeleteChallenge(victimUniqueId);
                    } else damager.openInventory(ItemStackList.Challenger[User.getLang(damagerUniqueId)]);
                }
                break;
            case "MLGRush4x1":
                if (victim.getLocation().getX() > 500)
                    e.setCancelled(true);
                break;
            case "MLGRush2x1": {
                if (!damager.getAllowFlight()) {
                    UUID uuid = victim.getUniqueId();
                    if (victim.getMaximumNoDamageTicks() != 0 && oldKb.contains(uuid)) {
                        if (victim.getNoDamageTicks() > victim.getMaximumNoDamageTicks() / 2)
                            return;
                        double xDiff = damager.getLocation().getX() - victim.getLocation().getX();
                        double zDiff = damager.getLocation().getZ() - victim.getLocation().getZ();
                        float horizontalDistance = (float) Math.sqrt(xDiff * xDiff + zDiff * zDiff);
                        Vector velocity = victim.getVelocity();
                        velocity.setX(velocity.getX() / 2.0D);
                        velocity.setY(velocity.getY() / 2.0D);
                        velocity.setZ(velocity.getZ() / 2.0D);
                        velocity.setX(velocity.getX() - xDiff / horizontalDistance * 0.4F);
                        velocity.setY(velocity.getY() + 0.4F);
                        velocity.setZ(velocity.getZ() - zDiff / horizontalDistance * 0.4F);
                        if (velocity.getY() > 0.4000000059604645D)
                            velocity.setY(0.4000000059604645D);
                        double horizontalMultiplier = victim.isOnGround() ? 0.9 : 1.1;
                        double verticalMultiplier = victim.isOnGround() ? 0.8 : 1;
                        if (((damager.getItemInHand() == null) ? 0 : damager.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK)) == 1) {
                            velocity.setX(velocity.getX() - Math.sin(damager.getLocation().getYaw() * Math.PI / 180.0D) / 2);
                            velocity.setY(velocity.getY() + 0.1D);
                            velocity.setZ(velocity.getZ() + Math.cos(damager.getLocation().getYaw() * Math.PI / 180.0D) / 2);
                        }
                        if (damager.isSprinting()) {
                            horizontalMultiplier *= 1.1;
                        }
                        victim.setVelocity(velocity);
                        Vector vector = victim.getVelocity();
                        double x = vector.getX() * horizontalMultiplier;
                        double y = vector.getY() * verticalMultiplier;
                        double z = vector.getZ() * horizontalMultiplier;
                        if (speedcubingServer.velocities.containsKey(uuid)) {
                            Double[] d = speedcubingServer.velocities.get(uuid);
                            x = x * d[0];
                            y = y * d[1];
                            z = z * d[0];
                        }
                        (((CraftPlayer) victim).getHandle()).playerConnection.sendPacket(new PacketPlayOutEntityVelocity(victim.getEntityId(), x, y, z));
                    }
                    int room = Core.PlayerRoom.get(uuid);
                    MLGRush2x1.Kill.get(room)[MLGRush2x1.Players.get(room)[0].equals(uuid) ? 0 : 1] = time;
                }
            }
            break;
            case "StickDuel":
                if (!damager.getAllowFlight()) {
                    int room = Core.PlayerRoom.get(victim.getUniqueId());
                    StickDuel.Kill.get(room)[StickDuel.Players.get(room)[0].equals(victim.getUniqueId()) ? 0 : 1] = time;
                }
                break;
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void dwdw(PlayerVelocityEvent e) {
        Player player = e.getPlayer();
        EntityDamageEvent lastDamageCause = player.getLastDamageCause();
        if (lastDamageCause instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent) lastDamageCause).getDamager() instanceof Player && player.getMaximumNoDamageTicks() != 0 && oldKb.contains(player.getUniqueId()))
            e.setCancelled(true);
    }
}