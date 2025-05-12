package top.speedcubing.mlgrush.BukkitEventListener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import top.speedcubing.mlgrush.List.ItemStackList;
import top.speedcubing.mlgrush.List.StringList;
import top.speedcubing.mlgrush.Member;

public class PlayerInteract implements Listener {

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        switch (player.getWorld().getName()) {
            case "lobby":
                switch (player.getItemInHand().getType()) {
                    case COMPASS:
                        player.openInventory(ItemStackList.specinventory[Member.getMember(player).user.lang]);
                        break;
                    case CHEST:
                        player.openInventory(ItemStackList.Gadgets[Member.getMember(player).user.lang]);
                        break;
                    case REDSTONE_COMPARATOR:
                        player.openInventory(ItemStackList.Settings[Member.getMember(player).user.lang]);
                        break;
                }
                if (e.getClickedBlock() != null && (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE))
                    e.setCancelled(true);
                break;
            case "StickDuel":
            case "MLGRush4x1":
            case "MLGRush2x1":
                Action action = e.getAction();
                Block block = e.getClickedBlock();
                if (block != null && (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) && block.getType().equals(Material.BED_BLOCK) && !player.isSneaking() && action == Action.RIGHT_CLICK_BLOCK) {
                    e.setCancelled(true);
                    return;
                }
                Material hand = player.getItemInHand().getType();
                if (hand.equals(Material.SLIME_BALL)) {
                    switch (player.getWorld().getName()) {
                        case "MLGRush2x1":
                        case "StickDuel": {
                            player.teleport(new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0));
                            Member member = Member.getMember(player);
                            player.setAllowFlight(false);
                            member.game.Spectators.remove(member);
                            member.Spectate = -1;
                            String str = member.user.getColorName(false);
                            for (Member m : member.game.Spectators) {
                                m.user.sendMessage("§8[§3Spectate§8] %p% §3is no longer spectating.", str);
                            }
                            for (Member m : member.game.Players) {
                                m.user.sendMessage("§8[§3Spectate§8] %p% §3is no longer spectating.", str);
                                m.user.bShowPlayer(player);
                            }
                        }
                        break;
                    }
                } else if (hand.equals(Material.DIAMOND_SWORD) && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
                    e.setCancelled(true);
                    player.updateInventory();
                }
                break;
        }
    }
}