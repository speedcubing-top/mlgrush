package speedcubing;

import cubing.bukkit.Event.ServerEventHandler;
import cubing.bukkit.Event.events.PlayInChatEvent;
import cubing.bukkit.Event.events.PlayInUseEntityEvent;
import cubing.bukkit.PlayerUtils;
import cubing.utils.Console;
import cubing.utils.Reflections;
import cubingserver.StringList.GlobalString;
import cubingserver.customEvents.UDPEvent;
import cubingserver.libs.PlayerData;
import cubingserver.libs.Rank;
import cubingserver.speedcubingServer;
import cubingserver.things.events.PlayerJoin;
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
                int lang = PlayerData.getLang(p.getUniqueId());
                PlayerUtils.sendTabHeaderFooter(((CraftPlayer) p).getHandle().playerConnection, GlobalString.LobbyTabList[0][lang], GlobalString.LobbyTabList[1][lang].replace("%int%", rs[2]));
                Team team = p.getScoreboard().getTeam(speedcubing.List.StringList.LobbySideBar[lang][1]);
                if (team != null)
                    team.setSuffix(rs[2]);
            }
        }
    }

    @ServerEventHandler
    public void PlayInChatEvent(PlayInChatEvent e) {
        Player player = e.player;
        String message = Reflections.getField(e.packet, "a").toString();
        long time = System.currentTimeMillis();
        UUID uuid = player.getUniqueId();
        boolean spam = PlayerJoin.Canspam.contains(uuid);
        if (!spam && time - speedcubingServer.spam.get(uuid) < 500)
            player.sendMessage(GlobalString.spam[PlayerData.getLang(uuid)]);
        else if (message.charAt(0) != '/') {
            if (!spam && speedcubingServer.lastmsg.get(uuid).equals(message))
                player.sendMessage(GlobalString.samemsg[PlayerData.getLang(uuid)]);
            else {
                String[] formats = Rank.format(uuid);
                String text = formats[0] + formats[1] + player.getName();
                String[] str = GlobalString.MessageFormat[0];
                String[] out = new String[]{
                        str[0].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[1].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[2].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[3].replace("%text%", text).replace("%text2%", formats[2] + message)
                };
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(out[PlayerData.getLang(p.getUniqueId())]);
                }
                Console.sendColoredConsole("§7[§aChatLog§7] [§b" + player.getWorld().getName() + "§7] " + out[1]);
                speedcubingServer.lastmsg.put(uuid, message);
            }
            e.setCancelled(true);
        }
        speedcubingServer.spam.put(uuid, time);
    }

    @ServerEventHandler
    public void PlayInUseEntityEvent(PlayInUseEntityEvent e) {
        Player player = e.player;
        PacketPlayInUseEntity packet = e.packet;
        if (player.getItemInHand().getType() == org.bukkit.Material.AIR && Reflections.getField(packet, "action").toString().equals("INTERACT")) {
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
