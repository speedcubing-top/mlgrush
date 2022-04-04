package speedcubing;

import cubing.lib.bukkit.Event.ServerEventManager;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import speedcubing.BukkitCommands.reset;
import speedcubing.BukkitEventListener.*;
import speedcubing.Core.BlockEditor;
import speedcubing.Core.MLGRush2x1;
import speedcubing.Core.MLGRush4x1;
import speedcubing.Core.StickDuel;
import speedcubing.List.EntityList;
import speedcubing.List.ItemStackList;

public class Main extends JavaPlugin implements CommandExecutor {

    public void onEnable() {
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
        Bukkit.getPluginManager().registerEvents(new PlayerSpawnLocation(),this);
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
        new MLGRush2x1().Load();
        new MLGRush4x1().Load();
        new StickDuel().Load();
        new BlockEditor().Load();
        ServerEventManager.registerListeners(new ServerEvent());
    }
}
