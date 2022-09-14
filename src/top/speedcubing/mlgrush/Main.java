package top.speedcubing.mlgrush;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import top.speedcubing.lib.eventbus.LibEventManager;
import top.speedcubing.mlgrush.BukkitCommands.reset;
import top.speedcubing.mlgrush.BukkitEventListener.*;
import top.speedcubing.mlgrush.Core.BlockEditor;
import top.speedcubing.mlgrush.Core.MLGRush2x1;
import top.speedcubing.mlgrush.Core.StickDuel;
import top.speedcubing.mlgrush.List.EntityList;
import top.speedcubing.mlgrush.List.ItemStackList;
import top.speedcubing.server.speedcubingServer;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends JavaPlugin implements CommandExecutor {

    public void onEnable() {
        MinecraftServer.getServer().getPropertyManager().setProperty("server-name", "mlgrush");
        Bukkit.getPluginManager().registerEvents(new EntityDamage(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChangedWorld(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClose(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlace(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItem(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
        Bukkit.getPluginManager().registerEvents(new FoodLevelChange(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSpawnLocation(), this);
        getServer().createWorld(new WorldCreator("MLGRush2x1"));
        getServer().createWorld(new WorldCreator("StickDuel"));
        getServer().createWorld(new WorldCreator("MLGRush4x1"));
        Bukkit.getWorld("MLGRush2x1").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("MLGRush4x1").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("StickDuel").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("MLGRush2x1").setTime(6000);
        Bukkit.getWorld("MLGRush4x1").setTime(6000);
        Bukkit.getWorld("StickDuel").setTime(6000);
        Bukkit.getWorld("world").setTime(6000);
        Bukkit.getPluginCommand("reset").setExecutor(new reset());
        Bukkit.getPluginCommand("reset").setTabCompleter(new reset());
        new ItemStackList().Load();
        new EntityList().Load();
        MLGRush2x1.Load();
        StickDuel.Load();
        new BlockEditor().Load();
        LibEventManager.registerListeners(new ServerEvent());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                speedcubingServer.tcp.sendClean(25576 - Bukkit.getPort() % 2, "online|3|" + Bukkit.getOnlinePlayers().size());
            }
        }, 0, 1000);
    }
}
