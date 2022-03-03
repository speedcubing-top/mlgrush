package speedcubing.All;


import cubing.utils.Reflections;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import speedcubing.Core.MLGRush2x1;
import speedcubing.Core.StickDuel;
import speedcubing.List.EntityList;

import java.util.List;

public class NPCClick {

    public void PacketInject(Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;

        if (channel.pipeline().get("PacketPlayOutInUseEntity") != null)
            return;
        channel.pipeline().addAfter("decoder", "PacketPlayInUseEntity", new MessageToMessageDecoder<PacketPlayInUseEntity>() {
            @Override
            protected void decode(ChannelHandlerContext channel, PacketPlayInUseEntity packet, List<Object> arg) {
                arg.add(packet);
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
        });
    }
}
