package top.speedcubing.mlgrush;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.speedcubing.mlgrush.All.Lobby;
import top.speedcubing.mlgrush.Core.Game.Duel;
import top.speedcubing.mlgrush.Core.Game.MLGRush2x1;
import top.speedcubing.mlgrush.Core.Game.StickDuel;
import top.speedcubing.server.player.User;

public class Member {
    public static Map<Integer, Member> users = new HashMap<>();

    public static Member getMember(CommandSender sender) {
        return getMember(User.getUser(sender).id);
    }

    public static Member getMember(int id) {
        return users.get(id);
    }

    public final User user;

    //Challenge
    public final Set<Member> Challenge = new HashSet<>();
    public Member stick;
    //spec
    public int Spectate;
    public int PlacedBlocks;
    public boolean iseditingkit;

    public Duel game;
    public int[] integerCache;
    public Member LastDamage;

    public Member(Player player) {
        this.user = User.getUser(player);
        users.put(user.id, this);
        Lobby.Join(player, true);
        new Thread(() -> integerCache = ArrayUtils.toPrimitive(user.dbSelect("mlgrush_stick,mlgrush_block,mlgrush_pickaxe,mlgrush_stickskin,mlgrush_blockskin,mlgrush_autoreset,mlgrush_hitdelay,mlgrush_falldamage,mlgrush_autoremove,mlgrush_oldkb").getIntArray())).start();
    }


    public void QuitAllQueue() {
        for (int i = 0; i < MLGRush2x1.inst.maps; i++) {
            if (MLGRush2x1.inst.Queue[i] == this) {
                MLGRush2x1.inst.Queue[i] = null;
                MLGRush2x1.inst.lore(i, 0, false);
            }
        }
        for (int i = 0; i < StickDuel.inst.maps; i++) {
            if (StickDuel.inst.Queue[i] == this) {
                StickDuel.inst.Queue[i] = null;
                StickDuel.inst.lore(i, 0, false);
            }
        }
    }

    public void DeleteChallenge() {
        Challenge.clear();
        stick = null;
        for (Member m : Member.users.values()) {
            m.Challenge.remove(this);
            if (m.stick != null && m.stick.equals(this))
                m.stick = null;
        }
    }
}
