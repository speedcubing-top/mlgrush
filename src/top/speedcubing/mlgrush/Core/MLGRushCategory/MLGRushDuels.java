package top.speedcubing.mlgrush.Core.MLGRushCategory;

import top.speedcubing.mlgrush.Member;

public class MLGRushDuels extends MLGRushGame {
    public Member[] Players;
    public final int[] Score = {0, 0};
    public final double[] Kill = {0D, 0D};
    public java.util.TimerTask TimerTask;
    public int Time;
    public int map;
    public int room;
}
