package top.speedcubing.mlgrush.Core.MLGRushCategory;

import top.speedcubing.mlgrush.Member;
import net.minecraft.server.v1_8_R3.BlockPosition;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class MLGRushGame {
    public final Set<BlockPosition> Blocks = new HashSet<>();
    public final Set<Member> Spectators = new HashSet<>();
    public String actionbar;

    public void gameEnd(Member member) {

    }

    public void resetBlocks() {
    }

    public void fell(Player player) {
    }
}
