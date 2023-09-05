package top.speedcubing.mlgrush;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import top.speedcubing.lib.eventbus.CubingEventManager;
import top.speedcubing.mlgrush.BukkitCommands.reset;
import top.speedcubing.mlgrush.BukkitEventListener.*;
import top.speedcubing.mlgrush.Core.BlockEditor;
import top.speedcubing.mlgrush.List.*;
import top.speedcubing.server.speedcubingServer;

public class Main extends JavaPlugin implements CommandExecutor {

    public void onEnable() {
        MinecraftServer.getServer().getPropertyManager().setProperty("server-name", "mlgrush");
        Bukkit.getPluginManager().registerEvents(new EntityDamage(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChangedWorld(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlace(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItem(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
        Bukkit.getPluginManager().registerEvents(new FoodLevelChange(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSpawnLocation(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClose(), this);
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
        new BlockEditor().Load();
        CubingEventManager.registerListeners(new ServerEvent());
        speedcubingServer.canRestart = false;
    }
}
