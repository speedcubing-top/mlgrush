package speedcubing;

import speedcubing.spigot.Event.ServerEventHandler;
import speedcubing.spigot.Event.events.PlayInChatEvent;
import speedcubing.spigot.Event.events.PlayInUseEntityEvent;
import speedcubing.lib.bukkit.packetwrapper.OutPlayerListHeaderFooter;
import speedcubing.lib.utils.Console;
import speedcubing.lib.utils.Reflections;
import speedcubing.server.libs.GlobalString;
import speedcubing.server.events.UDPEvent;
import speedcubing.server.libs.User;
import speedcubing.server.speedcubingServer;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import speedcubing.Core.MLGRush2x1;
import speedcubing.Core.StickDuel;
import speedcubing.List.EntityList;

import java.util.UUID;

public class ServerEvent {
    @ServerEventHandler
    public void UDPEvent(UDPEvent e) {
        String[] rs = e.rs;
        if (rs[0].equals("o") && rs[1].equals("1")) {
            speedcubingServer.AllPlayers = Integer.parseInt(rs[2]);
            for (Player p : Bukkit.getOnlinePlayers()) {
                User user = User.getUser(p.getUniqueId());
                if (user != null) {
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(OutPlayerListHeaderFooter.a(GlobalString.LobbyTabList[0][user.lang], GlobalString.LobbyTabList[1][user.lang].replace("%int%", rs[2])));
                    Team team = p.getScoreboard().getTeam(speedcubing.List.StringList.LobbySideBar[user.lang][1]);
                    if (team != null)
                        team.setSuffix(rs[2]);
                }
            }
        }
    }

    @ServerEventHandler
    public void PlayInChatEvent(PlayInChatEvent e) {
        String message = e.packet.a();
        if (message.charAt(0) != '/') {
            e.setCancelled(true);
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

    @ServerEventHandler
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
