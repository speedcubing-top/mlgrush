//package speedcubing.Core;
//
//import top.speedcubing.server.libs.GlobalString;
//import top.speedcubing.server.libs.User;
//import net.minecraft.server.v1_8_R3.BlockPosition;
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.Sound;
//import org.bukkit.entity.Player;
//import top.speedcubing.List.ItemStackList;
//import top.speedcubing.List.StringList;
//import top.speedcubing.Main;
//import top.speedcubing.server.speedcubingServer;
//
//import java.util.*;
//
//public class MLGRush4x1 {
//    public static final int roomSize = 1;
//
//    public static final Set<UUID> QueueCoolDown = new HashSet<>();
//    public static final Map<Integer, TimerTask> QueueCounter = new HashMap<>();
//    public static final List<UUID>[] Queue = new ArrayList[roomSize];
//    public static final boolean[] Rooms = new boolean[roomSize];
//    public static final int[] MapStorage = {0};
//    public static Map<Integer, Set<UUID>> Spectators = new HashMap<>();
//    public static Map<Integer, UUID[]> Players = new HashMap<>();
//    public static Map<Integer, Set<BlockPosition>> Blocks = new HashMap<>();
//
//    public void Load() {
//        for (int i = 0; i < roomSize; i++) {
//            Queue[i] = new ArrayList<>();
//            Rooms[i] = true;
//        }
//    }
//
//
//    public static void fell(Player player) {
////        player.setHealth(20);
////        int room = Core.randomToRoom(player.getLocation().getZ());
////        int t = Arrays.asList(Players.get(room)).indexOf(player.getUniqueId());
////        int[] Map = Core.MapLocations[1][MapStorage[room]];
////        Core.resetItems(player);
////        player.closeInventory();
////        int[] xy = {t % 2 == 0 ? (1 - t) * Map[0] : (t - 2) * Map[3], t % 2 == 0 ? (1 - t) * Map[3] : (2 - t) * Map[0]};
////        WorldServer mlgrush4x1 = ((CraftWorld) Bukkit.getWorld("MLGRush4x1")).getHandle();
////        for (int y = Map[2]; y >= Map[1]; y--) {
////            if (mlgrush4x1.getType(new BlockPosition(xy[0], y, xy[1])) != net.minecraft.server.v1_8_R3.Blocks.AIR.getBlockData()) {
////                PlayerUtils.teleport(((CraftPlayer) player).getHandle().playerConnection, xy[0] + 0.5, y + 1, xy[1] + 0.5, t == 3 ? 0 : 90 * (t + 1), 0);
////                break;
////            }
////        }
//    }
//
//    public static void gameEnd(int room, int quitter) {
//        resetBlocks(room);
//        UUID[] players = Players.get(room);
//        Set<UUID> spec = Spectators.get(room);
//        Players.remove(room);
//        Spectators.remove(room);
//        Location lb = new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0);
//        for (UUID id : players) {
//            if (id != null) {
//                if (Arrays.asList(players).indexOf(id) != quitter) {
//                    Bukkit.getPlayer(id).teleport(lb);
//                }
//            }
//        }
//        for (UUID id : spec) {
//            Player p = Bukkit.getPlayer(id);
//            p.setAllowFlight(false);
//            p.sendMessage(StringList.MLGRushGameEnd[User.getUser(p).lang]);
//            p.teleport(lb);
//        }
//        Blocks.remove(room);
//        Rooms[room] = true;
//    }
//
//    public static void gameStart(UUID[] uuids, int room) {
////        int[] Map = Core.MapLocations[1][MapStorage[room]];
////        double z = Core.roomToBed(room);
////        List<Integer> rand = Arrays.asList(0, 1, 2, 3);
////        Collections.shuffle(rand);
////        Players.put(room, new UUID[4]);
////        for (int i = 0; i < uuids.length; i++) {
////            Players.get(room)[rand.get(i)] = Queue[room].get(i);
////            PlayerUtils.teleport(((CraftPlayer) Bukkit.getPlayer(Players.get(room)[rand.get(i)])).getHandle().playerConnection, rand.get(i) % 2 == 0 ? (1 - rand.get(i)) * Map[0] + 0.5 : (rand.get(i) - 2) * Map[3] + 0.5, Map[1] + 1, rand.get(i) % 2 == 0 ? (1 - rand.get(i)) * Map[3] + 0.5 + z : (2 - rand.get(i)) * Map[0] + 0.5 + z, rand.get(i) == 3 ? 0 : 90 * (rand.get(i) + 1), 0);
////        }
////        Queue[room].clear();
////        for (UUID id : Players.get(room)) {
////            if (id != null) {
////                Player p = Bukkit.getPlayer(id);
////                Core.PlacedBlocks.put(id, 0);
////                Inventory inventory = p.getInventory();
////                inventory.clear();
////                int lang = User.getUser(id).lang;
////                Integer[] ints = Core.integerCache.get(id);
////                inventory.setItem(ints[0], ItemStackList.Sticks[ints[3]][lang]);
////                inventory.setItem(ints[1], ItemStackList.Blocks[ints[4]][lang]);
////                inventory.setItem(ints[2], ItemStackList.Pickaxe[lang]);
////                Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () -> p.setGameMode(GameMode.SURVIVAL));
////            }
////        }
////        Blocks.put(room, new HashSet<>());
////        Spectators.put(room, new HashSet<>());
////        Rooms[room] = false;
//    }
//
//    public static void joinSpec(Player player, int room) {
////                for (UUID id : Spectators4x1.get(room)) {
////                    Bukkit.getPlayer(id).sendMessage(StringList.JoinSpectate[User.getUser(id).lang].replace("%player%", name));
////                }
////                for (UUID id : Players4x1.get(room)) {
////                    Player p = Bukkit.getPlayer(id);
////                    if (p.getWorld().getName().equals("MLGRush4x1"))
////                        p.sendMessage(StringList.JoinSpectate[User.getUser(id).lang].replace("%player%", name));
////                }
////                Spectators4x1.get(room).add(uuid);
//    }
//
//    public static void breakBed(Player breaker, int bedX, int bedZ, int room) {
////        UUID uuid = breaker.getUniqueId();
////        int lose = bedX == 0 ? 2 - (bedZ + room * 500) / Math.abs(bedZ + room * 500) : 1 - bedX / Math.abs(bedX);
////        if (Players.get(room)[lose] == null)
////            return;
////        if (Arrays.asList(Players.get(room)).indexOf(uuid) == lose)
////            breaker.sendMessage(StringList.MLGRushBreakOwnBed[User.getUser(uuid).lang]);
////        else {
////            resetBlocks(room);
////            int[] Map = Core.MapLocations[1][MapStorage[room]];
////            for (int i = 0; i < Players.get(room).length; i++) {
////                UUID id = Players.get(room)[i];
////                Player player = Bukkit.getPlayer(id);
////                if (id != null && player.getWorld().getName().equals("MLGRush4x1")) {
////                    Core.resetItems(player);
////                    PlayerUtils.teleport(((CraftPlayer) player).getHandle().playerConnection, i % 2 == 0 ? (1 - i) * Map[0] + 0.5 : (i - 2) * Map[3] + 0.5, Map[1] + 1, (i % 2 == 0 ? (1 - i) * Map[3] : (2 - i) * Map[0]) + 0.5 + Math.round(bedZ / 500D) * 500, i == 3 ? 0 : 90 * (i + 1), 0);
////                }
////            }
////        }
//    }
//
//    public static void queue(Player player, int lang) {
//        int room = -1;
//        UUID uuid = player.getUniqueId();
//        outer:
//        for (int i = 3; i > -1; i--) {
//            for (int j = 0; j < roomSize; j++) {
//                if (Queue[j].size() == i && Rooms[j]) {
//                    Queue[j].add(uuid);
//                    room = j;
//                    break outer;
//                }
//            }
//        }
//        if (room == -1) {
//            player.sendMessage(GlobalString.NoEnoughServers[lang]);
//            return;
//        }
//        Core.QuitAllQueue(uuid);
//        Core.QuitAllGame(uuid);
//        if (!QueueCoolDown.contains(uuid)) {
//            QueueCoolDown.add(uuid);
//            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
//            player.getInventory().clear();
//            player.getInventory().setItem(8, ItemStackList.HubSlime[lang]);
//            int R = room;
//            String[] format = speedcubingServer.getFormat(User.getUser(player).rank);
//            List<UUID> queue = Queue[room];
//            String size = Integer.toString(queue.size());
//            Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () -> player.teleport(new Location(Bukkit.getWorld("MLGRush4x1"), 1000, 100, Core.roomToBed(R), 0, 0)));
//            queue.forEach(id -> Bukkit.getPlayer(id).sendMessage(StringList.MLGRushJoinQueue[User.getUser(Bukkit.getPlayer(id)).lang]
//                    .replace("%format0%", format[1])
//                    .replace("%name%", player.getName())
//                    .replace("%format2%", format[2])
//                    .replace("%int0%", size)));
//            switch (queue.size()) {
//                case 3:
//                    queue.forEach(id -> Bukkit.getPlayer(id).sendMessage(StringList.MLGRushGameStartCountDown[User.getUser(Bukkit.getPlayer(id)).lang]));
//                    QueueCounter.put(room, new TimerTask() {
//                        int c = 20;
//
//                        @Override
//                        public void run() {
//                            queue.forEach(id -> Bukkit.getPlayer(id).setLevel(c));
//                            c--;
//                            if (c == -1) {
//                                gameStart(new UUID[queue.size()], R);
//                                QueueCounter.get(R).cancel();
//                                QueueCounter.remove(R);
//                            }
//                        }
//                    });
//                    new Timer().schedule(QueueCounter.get(room), 0, 1000);
//                    break;
//                case 4:
//                    //#ForceStart
//                    break;
//            }
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    QueueCoolDown.remove(uuid);
//                }
//            }, 2000);
//        }
//    }
//
//    public static void quitSpec(Player player, int room) {
//        player.setAllowFlight(false);
////                MLGRush.PvpDisable.remove(player.getUniqueId());
////                Spectators4x1.get(room).remove(player);
////                Set<Player> Targets = new HashSet<>();
////                Targets.addAll(Spectators4x1.get(room));
////                Targets.addAll(Arrays.asList(Players4x1.get(room)));
////                Targets.forEach(p -> {
////                    if (p.getWorld().getName().equals("MLGRush4x1"))
////                        p.sendMessage(str);
////                });
//    }
//
//    public static void resetBlocks(int room) {
////        IBlockData AIR = net.minecraft.server.v1_8_R3.Blocks.AIR.getBlockData();
////        World world = ((CraftWorld) Bukkit.getWorld("MLGRush4x1")).getHandle();
////        for (BlockPosition pos : Blocks.get(room)) {
////            world.setTypeAndData(pos, AIR, 3);
////        }
////        Blocks.get(room).clear();
//    }
//}
