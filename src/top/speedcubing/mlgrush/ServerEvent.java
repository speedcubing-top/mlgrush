package top.speedcubing.mlgrush;

import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import top.speedcubing.common.database.DatabaseData;
import top.speedcubing.common.events.CubingTickEvent;
import top.speedcubing.lib.bukkit.SideBar;
import top.speedcubing.lib.bukkit.events.packet.PlayInEvent;
import top.speedcubing.lib.eventbus.CubingEventHandler;
import top.speedcubing.mlgrush.List.StringList;
import top.speedcubing.server.events.player.HubEvent;
import top.speedcubing.server.events.player.NickEvent;
import top.speedcubing.server.events.player.SkinEvent;
import top.speedcubing.server.events.player.ToggleFlyEvent;
import top.speedcubing.server.lang.GlobalString;
import top.speedcubing.server.player.User;
import top.speedcubing.server.share.Chat;

public class ServerEvent {

    @CubingEventHandler
    public void cubingTickEvent(CubingTickEvent e) {
        for (SideBar s : SideBar.getSidebars()) {
            if (s.player.getWorld().getName().equals("lobby"))
                s.setLine(StringList.LobbySideBar[User.getUser(s.player).lang][1] + DatabaseData.onlineCount, 4);
        }
    }

    @CubingEventHandler
    public void playInEvent(PlayInEvent e) {
        if (e.getPacket() instanceof PacketPlayInChat) {
            String message = ((PacketPlayInChat) e.getPacket()).a();
            if (message.charAt(0) != '/') {
                e.setCancelled(true);
                Chat.globalChat(Bukkit.getOnlinePlayers(), e.getPlayer(), GlobalString.MessageFormat, message, Member.getMember(e.getPlayer()).user.getPrefixName(false), Member.getMember(e.getPlayer()).user.getChatColor(false));
            }
        }
    }


    @CubingEventHandler
    public void hubEvent(HubEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("lobby")) {
            e.getPlayer().teleport(new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0));
            e.setCancelled(true);
        }
    }

    @CubingEventHandler
    public void nickEvent(NickEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("lobby")) {
            Member.getMember(e.getPlayer()).user.sendLangMessage(GlobalString.OnlyInHub);
            e.setCancelled(true);
        }
    }

    @CubingEventHandler
    public void skinEvent(SkinEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("lobby")) {
            Member.getMember(e.getPlayer()).user.sendLangMessage(GlobalString.OnlyInHub);
            e.setCancelled(true);
        }
    }

    @CubingEventHandler
    public void toggleFlyEvent(ToggleFlyEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("lobby")) {
            Member.getMember(e.getPlayer()).user.sendLangMessage(GlobalString.OnlyInHub);
            e.setCancelled(true);
        }
    }

}
