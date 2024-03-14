package top.speedcubing.mlgrush;

import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import top.speedcubing.common.database.DatabaseData;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.eventbus.CubingEventHandler;
import top.speedcubing.lib.events.packet.PlayInEvent;
import top.speedcubing.mlgrush.List.StringList;
import top.speedcubing.server.events.CubingTickEvent;
import top.speedcubing.server.events.player.HubEvent;
import top.speedcubing.server.events.player.NickEvent;
import top.speedcubing.server.events.player.SkinEvent;
import top.speedcubing.server.events.player.ToggleFlyEvent;
import top.speedcubing.server.lang.GlobalString;
import top.speedcubing.server.player.User;
import top.speedcubing.server.share.Chat;

public class ServerEvent {

    @CubingEventHandler
    public void CubingTickEvent(CubingTickEvent e) {
        for (SideBar s : SideBar.getSidebars()) {
            if (s.player.getWorld().getName().equals("world"))
                s.setLine(StringList.LobbySideBar[User.getUser(s.player).lang][1] + DatabaseData.onlineCount, 4);
        }
    }

    @CubingEventHandler
    public void PlayInEvent(PlayInEvent e) {
        if (e.packet instanceof PacketPlayInChat) {
            String message = ((PacketPlayInChat) e.packet).a();
            if (message.charAt(0) != '/') {
                e.isCancelled = true;
                Chat.globalChat(Bukkit.getOnlinePlayers(), e.player, GlobalString.MessageFormat, message, Member.getMember(e.player).user.getPrefixName(false), Member.getMember(e.player).user.getChatColor(false));
            }
        }
    }

    @CubingEventHandler
    public void ToggleFlyEvent(ToggleFlyEvent e) {
        if (!e.player.getWorld().getName().equals("world")) {
            Member.getMember(e.player).user.sendLangMessage(GlobalString.OnlyInHub);
            e.isCancelled = true;
        }
    }

    @CubingEventHandler
    public void NickEvent(NickEvent e) {
        if (!e.player.getWorld().getName().equals("world")) {
            Member.getMember(e.player).user.sendLangMessage(GlobalString.OnlyInHub);
            e.isCancelled = true;
        }
    }

    @CubingEventHandler
    public void SkinEvent(SkinEvent e) {
        if (!e.player.getWorld().getName().equals("world")) {
            Member.getMember(e.player).user.sendLangMessage(GlobalString.OnlyInHub);
            e.isCancelled = true;
        }
    }

    @CubingEventHandler
    public void HubEvent(HubEvent e) {
        if (!e.player.getWorld().getName().equals("world")) {
            e.player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 0, 0));
            e.isCancelled = true;
        }
    }
}
