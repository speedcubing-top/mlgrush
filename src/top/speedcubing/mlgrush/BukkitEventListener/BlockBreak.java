package top.speedcubing.mlgrush.BukkitEventListener;

import net.minecraft.server.v1_8_R3.BlockPosition;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import top.speedcubing.mlgrush.Core.Game.MLGRush2x1;
import top.speedcubing.mlgrush.Member;

public class BlockBreak implements Listener {

    @EventHandler
    public void BlocKBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        switch (player.getWorld().getName()) {
            case "lobby":
                if (player.getGameMode() == GameMode.SURVIVAL)
                    e.setCancelled(true);
            case "MLGRush2x1":
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    e.setCancelled(true);
                    if (!player.getAllowFlight()) {
                        Block block = e.getBlock();
                        Member member = Member.getMember(player);
                        BlockPosition position = new BlockPosition(block.getX(), block.getY(), block.getZ());
                        if (member.game.Blocks.contains(position)) {
                            MLGRush2x1.inst.Diff.remove(position);
                            member.game.Blocks.remove(position);
                            block.setType(Material.AIR);
                        } else if (block.getType().equals(Material.BED_BLOCK))
                            ((MLGRush2x1) member.game).breakBed(player, block.getX());
                    }
                }
                break;
            case "MLGRush4x1":
//                if (gamemode != GameMode.CREATIVE) {
//                    e.setCancelled(true);
//                    if (!player.getAllowFlight()) {
//                        Block block = e.getBlock();
//                        int z = block.getZ();
//                        BlockPosition pos = new BlockPosition(block.getX(), block.getY(), z);
//                        if (MLGRush4x1.Blocks.get(room).contains(pos)) {
//                            MLGRush4x1.Blocks.get(room).remove(pos);
//                            block.setType(Material.AIR);
//                        } else if (block.getType().equals(Material.BED_BLOCK))
//                            MLGRush4x1.breakBed(player, block.getX(), z, room);
//                    }
//                }
                break;
            case "StickDuel":
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    e.setCancelled(true);
                    Block block = e.getBlock();
                    Member member = Member.getMember(player);
                    BlockPosition pos = new BlockPosition(block.getX(), block.getY(), block.getZ());
                    if (member.game.Blocks.contains(pos)) {
                        member.game.Blocks.remove(pos);
                        block.setType(Material.AIR);
                    }
                }
                break;
        }
    }
}

