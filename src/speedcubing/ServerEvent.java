package speedcubing;

import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import speedcubing.Core.MLGRush2x1;
import speedcubing.Core.StickDuel;
import speedcubing.List.EntityList;
import speedcubing.lib.bukkit.event.PlayInChatEvent;
import speedcubing.lib.bukkit.event.PlayInUseEntityEvent;
import speedcubing.lib.eventbus.LibEventHandler;
import speedcubing.lib.utils.Console;
import speedcubing.lib.utils.Reflections;
import speedcubing.server.events.SocketEvent;
import speedcubing.server.libs.GlobalString;
import speedcubing.server.libs.User;
import speedcubing.server.speedcubingServer;

public class ServerEvent {
    @LibEventHandler
    public void SocketEvent(SocketEvent e) {
        String[] rs = e.receive.split("\\|");
        if (rs[0].equals("online") && rs[1].equals("1")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                User user = User.getUser(p.getUniqueId());
                if (user != null) {
                    Team team = p.getScoreboard().getTeam(speedcubing.List.StringList.LobbySideBar[user.lang][1]);
                    if (team != null)
                        team.setSuffix(rs[2]);
                }
            }
        }
    }

    @LibEventHandler
    public void PlayInChatEvent(PlayInChatEvent e) {
        String message = e.packet.a();
        if (message.charAt(0) != '/') {
            e.isCancelled = true;
            Player player = e.player;
            String[] formats = speedcubingServer.getFormat(User.getUser(player.getUniqueId()).rank);
            String text = formats[0] + player.getName();
            String[] str = GlobalString.MessageFormat[0];
            String[] out = new String[]{
                    str[0].replace("%text%", text).replace("%text2%", formats[2] + message),
                    str[1].replace("%text%", text).replace("%text2%", formats[2] + message),
                    str[2].replace("%text%", text).replace("%text2%", formats[2] + message),
                    str[3].replace("%text%", text).replace("%text2%", formats[2] + message)
            };
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(out[User.getUser(p.getUniqueId()).lang]);
            }
            Console.sendColoredConsole("§7[§aChatLog§7] [§b" + player.getWorld().getName() + "§7] " + out[1]);
        }
    }

    @LibEventHandler
    public void PlayInUseEntityEvent(PlayInUseEntityEvent e) {
        Player player = e.player;
        PacketPlayInUseEntity packet = e.packet;
        if (player.getItemInHand().getType() == org.bukkit.Material.AIR && packet.a().equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT)) {
            switch (EntityList.NPCS.indexOf(Reflections.getField(packet, "a"))) {
                case 0:
                    StickDuel.Queue(player);
                    break;
                case 1:
                    MLGRush2x1.queue(player);
                    break;
                case 2:
                    player.sendMessage("§cThis gamemode is under maintenance.");
                    break;
            }
        }
    }
}
