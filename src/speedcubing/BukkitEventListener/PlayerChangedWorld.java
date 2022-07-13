package speedcubing.BukkitEventListener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import speedcubing.All.Lobby;
import speedcubing.Member;

public class PlayerChangedWorld implements Listener {
    @EventHandler
    public void PlayerChangedWorldEvent(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        Member member = Member.getMember(player);
        switch (e.getFrom().getName()) {
            case "MLGRush2x1":
            case "StickDuel":
                if (member.inGame)
                    member.game.gameEnd(member);
                Lobby.Join(player, false);
                break;
            case "MLGRush4x1":
//                    {
//                    int room = Core.randomToRoom(from.getZ());
//                    if (from.getBlockX() > 500) {
//                        String[] format = speedcubingServer.getFormat(User.getUser((uuid);
//                        MLGRush4x1.Queue[room].remove(uuid);
//                        List<UUID> queue = MLGRush4x1.Queue[room];
//                        queue.forEach(p -> Bukkit.getPlayer(p).sendMessage(StringList.MLGRushQuitQueue[User.getLang(p)]
//                                .replace("%format0%", format[0])
//                                .replace("%name%", player.getName())
//                                .replace("%format2%", format[2])
//                                .replace("%int0%", Integer.toString(queue.size()))));
//                        if (queue.size() == 2) {
//                            queue.forEach(id -> {
//                                Player p = Bukkit.getPlayer(id);
//                                p.sendMessage(StringList.MLGRushNotenoughplayers[User.getUser(id).lang]);
//                                p.setLevel(0);
//                            });
//                            MLGRush4x1.QueueCounter.get(room).cancel();
//                            MLGRush4x1.QueueCounter.remove(room);
//                        }
//                        player.setLevel(0);
//                    } else {
//                        UUID[] uuids = MLGRush4x1.Players.get(room);
//                        if (uuids != null) {
//                            if (Arrays.asList(uuids).contains(player.getUniqueId())) {
//                                int k = 0;
//                                for (int i = 0; i < 4; i++) {
//                                    if (uuids[i] != null && Bukkit.getPlayer(uuids[i]).getWorld().getName().equals("MLGRush4x1"))
//                                        k++;
//                                }
//                                if (k == 3)
//                                    MLGRush2x1.gameEnd(room, Arrays.asList(uuids).indexOf(player.getUniqueId()));
//                            } else if (MLGRush4x1.Spectators.get(room).contains(player.getUniqueId()))
//                                MLGRush2x1.quitSpec(player, room);
//                        }
//                    }
//                }
                break;
        }
    }
}
