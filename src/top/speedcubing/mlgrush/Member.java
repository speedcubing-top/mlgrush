package top.speedcubing.mlgrush;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.speedcubing.mlgrush.All.Lobby;
import top.speedcubing.mlgrush.Core.MLGRush2x1;
import top.speedcubing.mlgrush.Core.MLGRushCategory.MLGRushGame;
import top.speedcubing.mlgrush.List.EntityList;
import top.speedcubing.server.libs.User;
import top.speedcubing.server.speedcubingServer;

import java.util.*;

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
    public Member StickDuel;
    //spec
    public int Spectate;
    public int PlacedBlocks;
    public boolean iseditingkit;

    public MLGRushGame game;
    public int[] integerCache;

    public Member LastDamage;
    public boolean oldKb;
    public boolean inGame;

    public Member(Player player) {
        this.user = User.getUser(player);
        users.put(user.id, this);
        Lobby.Join(player, true);
        UUID uuid = player.getUniqueId();
        new Thread(() -> integerCache = ArrayUtils.toPrimitive(speedcubingServer.connection.select("mlgrush_stick,mlgrush_block,mlgrush_pickaxe,mlgrush_stickskin,mlgrush_blockskin,mlgrush_autoreset,mlgrush_hitdelay,mlgrush_falldamage,mlgrush_autoremove,mlgrush_oldkb").from("playersdata").where("uuid='" + uuid + "'").getIntArray())).start();
    }


    public void QuitAllQueue() {
        if (MLGRush2x1.Queue != null && MLGRush2x1.Queue.equals(this)) {
            MLGRush2x1.Queue = null;
            EntityList.Lobbys[4].setName("§a0/2");
        }
        if (top.speedcubing.mlgrush.Core.StickDuel.Queue != null && top.speedcubing.mlgrush.Core.StickDuel.Queue.equals(this)) {
            top.speedcubing.mlgrush.Core.StickDuel.Queue = null;
            EntityList.Lobbys[3].setName("§a0/2");
        }
    }
}
