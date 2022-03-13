package speedcubing.BukkitCommands;

import cubingserver.libs.User;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import speedcubing.Core.Core;
import speedcubing.Core.MLGRush2x1;
import speedcubing.Core.MLGRush4x1;
import speedcubing.Core.StickDuel;
import speedcubing.List.StringList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class reset implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            if (player.getAllowFlight())
                player.sendMessage(StringList.MLGRushInvalidWorld[User.getLang(player.getUniqueId())]);
            else
                switch (player.getWorld().getName()) {
                    case "MLGRush2x1":
                        MLGRush2x1.resetBlocks(Core.PlayerRoom.get(player.getUniqueId()));
                        break;
                    case "MLGRush4x1":
//                        MLGRush4x1.resetBlocks(Core.randomToRoom(player.getLocation().getZ()));
                        break;
                    default:
                        player.sendMessage(StringList.MLGRushInvalidWorld[User.getLang(player.getUniqueId())]);
                        break;
                }
        } else
            player.sendMessage("/reset");
        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}

