package top.speedcubing.mlgrush;

import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.event.PlayInEvent;
import top.speedcubing.lib.eventbus.LibEventHandler;
import top.speedcubing.mlgrush.List.StringList;
import top.speedcubing.server.database.DataCenter;
import top.speedcubing.server.events.CubingTickEvent;
import top.speedcubing.server.events.player.HubEvent;
import top.speedcubing.server.events.player.NickEvent;
import top.speedcubing.server.events.player.SkinEvent;
import top.speedcubing.server.events.player.ToggleFlyEvent;
import top.speedcubing.server.libs.GlobalString;
import top.speedcubing.server.libs.User;
import top.speedcubing.server.share.Chat;

public class ServerEvent {

    @LibEventHandler
    public void CubingTickEvent(CubingTickEvent e) {
        for (SideBar s : SideBar.getSidebars()) {
            if (s.player.getWorld().getName().equals("world"))
                s.setLine(StringList.LobbySideBar[User.getUser(s.player).lang][1] + DataCenter.onlineCount, 4);
        }
    }

    @LibEventHandler
    public void PlayInEvent(PlayInEvent e) {
        if (e.packet instanceof PacketPlayInChat) {
            String message = ((PacketPlayInChat) e.packet).a();
            if (message.charAt(0) != '/') {
                e.isCancelled = true;
                String[] formats = Member.getMember(e.player).user.getFormat();
                Chat.globalChat(Bukkit.getOnlinePlayers(), e.player, GlobalString.MessageFormat, message, formats[0] + e.player.getName(), formats[2]);
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
