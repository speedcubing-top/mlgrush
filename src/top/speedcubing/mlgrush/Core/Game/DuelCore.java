package top.speedcubing.mlgrush.Core.Game;

import net.minecraft.server.v1_8_R3.BlockPosition;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import top.speedcubing.lib.bukkit.inventory.ItemUtils;
import top.speedcubing.mlgrush.List.ItemStackList;
import top.speedcubing.mlgrush.List.StringList;
import top.speedcubing.mlgrush.Main;
import top.speedcubing.mlgrush.Member;

import java.util.*;

public class DuelCore {

    public int maps = 2;
    public Member[] Queue = new Member[maps];
    public Set<Integer>[] Rooms = new HashSet[maps];
    public final Set<Member> QueueCoolDown = new HashSet<>();
    public HashMap<BlockPosition, Integer> Diff = new HashMap<>();

    public DuelCore(String type) {
        for (int i = 0; i < maps; i++)
            Rooms[i] = new HashSet<>();
        org.bukkit.World world = Bukkit.getWorld(type);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), () -> {
            Set<BlockPosition> ba = new HashSet<>();
            for (Map.Entry<BlockPosition, Integer> x : Diff.entrySet()) {
                int age = x.getValue();
                if (age == 100) {
                    BlockPosition b = x.getKey();
                    world.getBlockAt(b.getX(), b.getY(), b.getZ()).setType(Material.REDSTONE_BLOCK);
                } else if (age == 120) {
                    BlockPosition b = x.getKey();
                    world.getBlockAt(b.getX(), b.getY(), b.getZ()).setType(Material.AIR);
                    ba.add(b);
                }
            }
            for (BlockPosition b : ba)
                Diff.remove(b);
            Diff.replaceAll((x, v) -> v + 1);
        }, 0, 1);
    }

    public void lore(int type, int replacement, boolean game) {
        for (int i = 0; i < 4; i++) {
            ItemStack stack = ItemStackList.duelMenu[i].getItem(type);
            List<String> meta = stack.getItemMeta().getLore();
            if (game) {
                ItemUtils.setLore(stack, meta.get(0), StringList.Lores[i][1].replace("%int%", String.valueOf(replacement)), meta.get(2));
                if (replacement > 0)
                    stack.setAmount(replacement);
            } else {
                ItemUtils.setLore(stack, meta.get(0), meta.get(1), StringList.Lores[i][2].replace("%int%", Integer.toString(replacement)));
            }
        }
        for (Player p : Bukkit.getWorld("world").getPlayers()) {
            for (Inventory i : ItemStackList.duelMenu) {
                if (p.getOpenInventory().getTopInventory().getHolder() == i.getHolder())
                    p.updateInventory();
            }
        }
    }
}
