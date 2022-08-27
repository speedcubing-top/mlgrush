package speedcubing;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import speedcubing.All.Lobby;
import speedcubing.Core.MLGRush2x1;
import speedcubing.Core.MLGRushCategory.MLGRushGame;
import speedcubing.List.EntityList;
import speedcubing.lib.utils.SQL.SQLUtils;
import speedcubing.server.libs.User;
import speedcubing.server.speedcubingServer;

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
        new Thread(() -> integerCache = ArrayUtils.toPrimitive(SQLUtils.getIntArray(speedcubingServer.connection.select("playersdata", "mlgrush_stick,mlgrush_block,mlgrush_pickaxe,mlgrush_stickskin,mlgrush_blockskin,mlgrush_autoreset,mlgrush_hitdelay,mlgrush_falldamage,mlgrush_autoremove,mlgrush_oldkb", "uuid='" + uuid + "'")))).start();
    }


    public void QuitAllQueue() {
        if (MLGRush2x1.Queue != null && MLGRush2x1.Queue.equals(this)) {
            MLGRush2x1.Queue = null;
            EntityList.Lobbys[4].setName("§a0/2");
        }
        if (speedcubing.Core.StickDuel.Queue != null && speedcubing.Core.StickDuel.Queue.equals(this)) {
            speedcubing.Core.StickDuel.Queue = null;
            EntityList.Lobbys[3].setName("§a0/2");
        }
    }
}
