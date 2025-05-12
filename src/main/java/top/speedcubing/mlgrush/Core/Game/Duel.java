package top.speedcubing.mlgrush.Core.Game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import net.minecraft.server.v1_8_R3.BlockPosition;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import top.speedcubing.lib.bukkit.PlayerUtils;
import top.speedcubing.lib.bukkit.WorldUtils;
import top.speedcubing.lib.utils.TimeFormatter;
import top.speedcubing.mlgrush.Main;
import top.speedcubing.mlgrush.Member;
import top.speedcubing.server.player.User;

public class Duel extends Game {
    public Member[] Players;
    public final int[] Score = {0, 0};
    public final long[] Kill = {0,0};
    public java.util.Timer timer = new Timer();
    public int Time;
    public int map;
    public int room;
    public int coord;
    public boolean oldKb;
    public final Set<BlockPosition> Blocks = new HashSet<>();
    public final Set<Member> Spectators = new HashSet<>();
    public String actionbar;
    public boolean[] Preference;
    public World world;
    public HashMap<BlockPosition, Integer> Diff = new HashMap<>();

    public Duel(int map, String world) {
        this.world = Bukkit.getWorld(world);
        Set<Integer> Rooms = world.equals("MLGRush2x1") ? MLGRush2x1.inst.Rooms[map] : StickDuel.inst.Rooms[map];
        int room = 1;
        while (Rooms.contains(room))
            room++;
        Rooms.add(room);
        this.coord = 250 * (room * (int) Math.pow(-1, room + 1) + room % 2);
        this.map = map;
        this.room = room;
        Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () ->
                WorldUtils.cloneArea(-30 + 500 * map, 80, -30, 30 + 500 * map, 120, 30, -30 + 500 * map, 80, -30 + coord, this.world));
    }

    public void initStage2(int[] settings, String[][] displayName, Member[] members) {
        Preference = new boolean[]{settings[0] > 1, settings[1] > 1, settings[2] > 1, settings[3] > 1, settings[4] > 1};
        for (Member m : members) {
            m.game = this;
            m.DeleteChallenge();
            m.QuitAllQueue();
            m.PlacedBlocks = 0;
        }
        for (Member p : members) {
            p.user.bGetInventory().setHeldItemSlot(0);
            p.user.bSetGameMode(GameMode.SURVIVAL);
        }
        if (settings[1] > 1)
            for (Member p : members)
                p.user.bSetMaximumNoDamageTicks(0);
        oldKb = settings[4] > 1;
        timer.schedule(new TimerTask() {
            int x = 0;

            @Override
            public void run() {
                Time = x;
                Set<Member> targets = new HashSet<>();
                targets.addAll(Arrays.asList(Players));
                targets.addAll(Spectators);
                for (Member m : targets) {
                    if (m != null) {
                        PlayerUtils.sendActionBar(m.user.player, actionbar);
                        m.user.bGetScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(displayName[m.user.lang][0].replace("%t%", new TimeFormatter(x, TimeUnit.SECONDS).format("%H%:", true).format("%m%:%s%", false).toString()));
                    }
                }
                x++;
            }
        }, 0, 1000);
    }

    public void resetBlocks() {
        for (BlockPosition pos : Blocks) {
            Diff.remove(pos);
            world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()).setType(Material.AIR);
        }
        Blocks.clear();
    }

    public void gameEnd(Member member) {

    }

    public void fell(Player player) {
    }
}
