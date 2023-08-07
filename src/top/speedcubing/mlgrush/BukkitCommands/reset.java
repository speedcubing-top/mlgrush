package top.speedcubing.mlgrush.BukkitCommands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import top.speedcubing.mlgrush.List.StringList;
import top.speedcubing.mlgrush.Member;

import java.util.*;

public class reset implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            if (player.getAllowFlight())
                Member.getMember(player).user.sendLangMessage(StringList.MLGRushInvalidWorld);
            else
                switch (player.getWorld().getName()) {
                    case "MLGRush2x1":
                        Member.getMember(player).game.resetBlocks();
                        break;
                    case "MLGRush4x1":
//                        MLGRush4x1.resetBlocks(Core.randomToRoom(player.getLocation().getZ()));
                        break;
                    default:
                        Member.getMember(player).user.sendLangMessage(StringList.MLGRushInvalidWorld);
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

