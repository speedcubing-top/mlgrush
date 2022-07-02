package speedcubing.BukkitCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import speedcubing.Core.Core;
import speedcubing.Core.MLGRush2x1;
import speedcubing.List.StringList;
import speedcubing.server.libs.User;

import java.util.ArrayList;
import java.util.List;

public class reset implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            if (player.getAllowFlight())
                player.sendMessage(StringList.MLGRushInvalidWorld[User.getUser(player).lang]);
            else
                switch (player.getWorld().getName()) {
                    case "MLGRush2x1":
                        MLGRush2x1.resetBlocks(Core.PlayerRoom.get(player));
                        break;
                    case "MLGRush4x1":
//                        MLGRush4x1.resetBlocks(Core.randomToRoom(player.getLocation().getZ()));
                        break;
                    default:
                        player.sendMessage(StringList.MLGRushInvalidWorld[User.getUser(player).lang]);
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

