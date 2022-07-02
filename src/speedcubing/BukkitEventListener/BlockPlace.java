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
import speedcubing.Core.MLGRush4x1;
import speedcubing.Core.StickDuel;
import speedcubing.List.ItemStackList;
import speedcubing.server.libs.User;

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
                        int room = Core.PlayerRoom.get(uuid);
                        BlockPosition position = new BlockPosition(x, y, z);
                        MLGRush2x1.Blocks.get(room).add(position);
                        Core.PlacedBlocks.put(uuid, Core.PlacedBlocks.get(uuid) + 1);
                        if (Core.PlacedBlocks.get(uuid) > 32) {
                            Core.PlacedBlocks.put(uuid, 0);
                            Integer[] i = Core.integerCache.get(uuid);
                            player.getInventory().setItem(i[1], ItemStackList.Blocks[i[4]][User.getUser(player).lang]);
                        }
                        if (MLGRush2x1.Preference.get(room)[3]) {
                            MLGRush2x1.Diff.put(position, 0);
                            MLGRush2x1.id++;
                        }
                    }
                    break;
                case "StickDuel":
                    if (y > 112 || y < 85 || Math.abs(z - Core.randomToBed(z)) > 30 || Math.abs(x) > 50)
                        e.setCancelled(true);
                    else {
                        StickDuel.Blocks.get(Core.PlayerRoom.get(uuid)).add(new BlockPosition(block.getX(), block.getY(), z));
                        Core.PlacedBlocks.put(uuid, Core.PlacedBlocks.get(uuid) + 1);
                        if (Core.PlacedBlocks.get(uuid) > 32) {
                            Core.PlacedBlocks.put(uuid, 0);
                            Integer[] i = Core.integerCache.get(uuid);
                            player.getInventory().setItem(i[1], ItemStackList.Blocks[i[4]][User.getUser(player).lang]);
                        }
                    }
                    break;
                case "MLGRush4x1":
                    if (y > 112 || y < 85 || Math.abs(z - Core.randomToBed(z)) > 50 || Math.abs(x) > 50)
                        e.setCancelled(true);
                    else {
                        Core.PlacedBlocks.put(uuid, Core.PlacedBlocks.get(uuid) + 1);
                        MLGRush4x1.Blocks.get(Core.PlayerRoom.get(uuid)).add(new BlockPosition(block.getX(), y, z));
                        if (Core.PlacedBlocks.get(uuid) > 48) {
                            Core.PlacedBlocks.put(uuid, 0);
                            player.getInventory().setItem(Core.integerCache.get(uuid)[1], ItemStackList.Blocks[Core.integerCache.get(uuid)[4]][User.getUser(player).lang]);
                        }
                    }
                    break;
            }
        }
    }
}
