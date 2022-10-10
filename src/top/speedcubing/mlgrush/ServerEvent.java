package top.speedcubing.mlgrush;

import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.event.PlayInEvent;
import top.speedcubing.lib.eventbus.LibEventHandler;
import top.speedcubing.lib.utils.Console;
import top.speedcubing.mlgrush.List.StringList;
import top.speedcubing.server.events.CubingTickEvent;
import top.speedcubing.server.events.player.HubEvent;
import top.speedcubing.server.events.player.NickEvent;
import top.speedcubing.server.events.player.SkinEvent;
import top.speedcubing.server.events.player.ToggleFlyEvent;
import top.speedcubing.server.libs.GlobalString;
import top.speedcubing.server.libs.User;
import top.speedcubing.server.speedcubingServer;

public class ServerEvent {

    int a;

    @LibEventHandler
    public void CubingTickEvent(CubingTickEvent e) {
        a = speedcubingServer.getOnlineCount();
        Member.users.values().forEach(m -> {
            if (m.user.player.getWorld().getName().equals("world"))
                SideBar.getSidebar(m.user.player).setLineByScore(StringList.LobbySideBar[m.user.lang][1] + a, 4);
        });
    }

    @LibEventHandler
    public void PlayInChatEvent(PlayInEvent e) {
        if (e.packet instanceof PacketPlayInChat) {
            String message = ((PacketPlayInChat) e.packet).a();
            if (message.charAt(0) != '/') {
                e.isCancelled = true;
                Player player = e.player;
                String[] formats = speedcubingServer.getFormat(User.getUser(player).rank);
                String text = formats[0] + player.getName();
                String[] str = GlobalString.MessageFormat[0];
                String[] out = new String[]{
                        str[0].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[1].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[2].replace("%text%", text).replace("%text2%", formats[2] + message),
                        str[3].replace("%text%", text).replace("%text2%", formats[2] + message)
                };
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(out[User.getUser(p).lang]);
                }
                Console.sendColoredConsole("§7[§aChatLog§7] [§b" + player.getWorld().getName() + "§7] " + out[1]);
            }
        }
    }

    @LibEventHandler
    public void ToggleFlyEvent(ToggleFlyEvent e) {
        if (!e.player.getWorld().getName().equals("world")) {
            e.player.sendMessage(GlobalString.OnlyInHub[User.getUser(e.player).lang]);
            e.isCancelled = true;
        }
    }

    @LibEventHandler
    public void NickEvent(NickEvent e) {
        if (!e.player.getWorld().getName().equals("world")) {
            e.player.sendMessage(GlobalString.OnlyInHub[User.getUser(e.player).lang]);
            e.isCancelled = true;
        }
    }

    @LibEventHandler
    public void SkinEvent(SkinEvent e) {
        if (!e.player.getWorld().getName().equals("world")) {
            e.player.sendMessage(GlobalString.OnlyInHub[User.getUser(e.player).lang]);
            e.isCancelled = true;
        }
    }

    @LibEventHandler
    public void HubEvent(HubEvent e) {
        if (!e.player.getWorld().getName().equals("world")) {
            e.player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0));
            e.isCancelled = true;
        }
    }
}
