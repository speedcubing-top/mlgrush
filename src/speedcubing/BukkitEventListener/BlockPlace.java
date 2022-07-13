package speedcubing.BukkitEventListener;

import net.minecraft.server.v1_8_R3.BlockPosition;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import speedcubing.Core.Core;
import speedcubing.Core.MLGRush2x1;
import speedcubing.List.ItemStackList;
import speedcubing.Member;

import java.util.UUID;

public class BlockPlace implements Listener {
    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent e) {
        Block block = e.getBlock();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (player.getGameMode() == GameMode.SURVIVAL) {
            if (player.getAllowFlight()) {
                e.setCancelled(true);
                return;
            }
            int x = block.getX();
            int y = block.getY();
            int z = block.getZ();
            switch (player.getWorld().getName()) {
                case "MLGRush2x1":
                    if (y > 112 || y < 85 || Math.abs(z - Core.randomToBed(z)) > 30 || Math.abs(x) > 50)
                        e.setCancelled(true);
                    else {
                        Member member = Member.getMember(player);
                        member.game.Blocks.add(new BlockPosition(x, y, z));
                        member.PlacedBlocks += 1;
                        if (member.PlacedBlocks > 32) {
                            member.PlacedBlocks = 0;
                            int[] i = member.integerCache;
                            player.getInventory().setItem(i[1], ItemStackList.Blocks[i[4]][member.lang]);
                        }
                        if (((MLGRush2x1) member.game).Preference[3]) {
                            MLGRush2x1.Diff.put(new BlockPosition(x, y, z), 0);
                            MLGRush2x1.id++;
                        }
                    }
                    break;
                case "StickDuel":
                    if (y > 112 || y < 85 || Math.abs(z - Core.randomToBed(z)) > 30 || Math.abs(x) > 50)
                        e.setCancelled(true);
                    else {
                        Member member = Member.getMember(player);
                        member.game.Blocks.add(new BlockPosition(x, y, z));
                        member.PlacedBlocks += 1;
                        if (member.PlacedBlocks > 32) {
                            member.PlacedBlocks = 0;
                            int[] i = member.integerCache;
                            player.getInventory().setItem(i[1], ItemStackList.Blocks[i[4]][member.lang]);
                        }
                    }
                    break;
                case "MLGRush4x1":
                    if (y > 112 || y < 85 || Math.abs(z - Core.randomToBed(z)) > 50 || Math.abs(x) > 50)
                        e.setCancelled(true);
                    else {
//                        Core.PlacedBlocks.put(uuid, Core.PlacedBlocks.get(uuid) + 1);
//                        MLGRush4x1.Blocks.get(Core.PlayerRoom.get(uuid)).add(new BlockPosition(block.getX(), y, z));
//                        if (Core.PlacedBlocks.get(uuid) > 48) {
//                            Core.PlacedBlocks.put(uuid, 0);
//                            player.getInventory().setItem(Core.integerCache.get(uuid)[1], ItemStackList.Blocks[Core.integerCache.get(uuid)[4]][User.getUser(player).lang]);
//                        }
                    }
                    break;
            }
        }
    }
}
