package top.speedcubing.mlgrush.BukkitEventListener;


import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import top.speedcubing.mlgrush.Core.Game.MLGRush2x1;
import top.speedcubing.mlgrush.Core.Game.StickDuel;
import top.speedcubing.mlgrush.List.ItemStackList;
import top.speedcubing.mlgrush.Member;
import top.speedcubing.server.player.User;

public class EntityDamageByEntity implements Listener {

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
//        System.out.println("C");
        double time = System.currentTimeMillis();
        if (!(e.getDamager() instanceof Player && e.getEntity() instanceof Player))
            return;
        Player damager = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();
        if ((damager.getGameMode() == GameMode.SURVIVAL || damager.getGameMode() == GameMode.ADVENTURE) && damager.getAllowFlight()) {
            e.setCancelled(true);
            return;
        }
        switch (victim.getWorld().getName()) {
            case "world":
                if (damager.getItemInHand().getType() == Material.GOLD_SWORD) {
                    Member victimUniqueId = Member.getMember(victim);
                    Member damagerUniqueId = Member.getMember(damager);
                    damagerUniqueId.LastDamage = victimUniqueId;
                    if (damagerUniqueId.Challenge.contains(victimUniqueId)) {
                        if (damagerUniqueId.stick != null && damagerUniqueId.stick.equals(victimUniqueId))
                            new StickDuel(damagerUniqueId, victimUniqueId, 0);
                        else
                            new MLGRush2x1(damagerUniqueId, victimUniqueId, 0);
                        damagerUniqueId.DeleteChallenge();
                        victimUniqueId.DeleteChallenge();
                    } else damager.openInventory(ItemStackList.Challenger[User.getUser(damager).lang]);
                }
                break;
            case "MLGRush4x1":
                if (victim.getLocation().getX() > 500)
                    e.setCancelled(true);
                break;
            case "MLGRush2x1": {
                if (!damager.getAllowFlight()) {
                    Member member = Member.getMember(victim);
                    if (member.game.oldKb && victim.getMaximumNoDamageTicks() != 0 && victim.getNoDamageTicks() <= victim.getMaximumNoDamageTicks() / 2) {
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
                        velocity.setX(velocity.getX() * horizontalMultiplier);
                        velocity.setY(velocity.getY() * verticalMultiplier);
                        velocity.setZ(velocity.getZ() * horizontalMultiplier);
                        member.user.knockback(member.user.applyKnockback(velocity));
                    }
                    (member.game).Kill[(member.game).Players[0].equals(member) ? 0 : 1] = time;
                }
            }
            break;
            case "StickDuel":
                Member member = Member.getMember(victim);
                if (!damager.getAllowFlight())
                    ((StickDuel) member.game).Kill[((StickDuel) member.game).Players[0].equals(member) ? 0 : 1] = time;
                break;
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void dwdw(PlayerVelocityEvent e) {
        Player player = e.getPlayer();
        EntityDamageEvent lastDamageCause = player.getLastDamageCause();
        if (lastDamageCause instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent) lastDamageCause).getDamager() instanceof Player && player.getMaximumNoDamageTicks() != 0 && Member.getMember(player).game.oldKb)
            e.setCancelled(true);
    }
}