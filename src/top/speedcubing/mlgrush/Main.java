package top.speedcubing.mlgrush;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import top.speedcubing.lib.eventbus.CubingEventManager;
import top.speedcubing.mlgrush.BukkitCommands.reset;
import top.speedcubing.mlgrush.BukkitEventListener.BlockBreak;
import top.speedcubing.mlgrush.BukkitEventListener.BlockPlace;
import top.speedcubing.mlgrush.BukkitEventListener.EntityDamage;
import top.speedcubing.mlgrush.BukkitEventListener.EntityDamageByEntity;
import top.speedcubing.mlgrush.BukkitEventListener.FoodLevelChange;
import top.speedcubing.mlgrush.BukkitEventListener.InventoryClick;
import top.speedcubing.mlgrush.BukkitEventListener.InventoryClose;
import top.speedcubing.mlgrush.BukkitEventListener.PlayerChangedWorld;
import top.speedcubing.mlgrush.BukkitEventListener.PlayerDropItem;
import top.speedcubing.mlgrush.BukkitEventListener.PlayerInteract;
import top.speedcubing.mlgrush.BukkitEventListener.PlayerJoin;
import top.speedcubing.mlgrush.BukkitEventListener.PlayerMove;
import top.speedcubing.mlgrush.BukkitEventListener.PlayerQuit;
import top.speedcubing.mlgrush.BukkitEventListener.PlayerSpawnLocation;
import top.speedcubing.mlgrush.Core.BlockEditor;
import top.speedcubing.mlgrush.List.EntityList;
import top.speedcubing.mlgrush.List.ItemStackList;
import top.speedcubing.server.speedcubingServer;

public class Main extends JavaPlugin implements CommandExecutor {

    public void onEnable() {
        MinecraftServer.getServer().getPropertyManager().setProperty("server-name", "mlgrush");
        CubingEventManager.registerListeners(new ServerEvent());
        registerCommands();
        registerListeners();

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
        Bukkit.getWorld("MLGRush2x1").setAutoSave(false);
        Bukkit.getWorld("MLGRush4x1").setAutoSave(false);
        Bukkit.getWorld("StickDuel").setAutoSave(false);
        Bukkit.getWorld("world").setAutoSave(false);
        new ItemStackList().Load();
        new EntityList().Load();
        new BlockEditor().Load();
    }

    private void registerCommands() {
        Bukkit.getPluginCommand("reset").setExecutor(new reset());
        Bukkit.getPluginCommand("reset").setTabCompleter(new reset());
    }

    private void registerListeners() {
        speedcubingServer.registerListeners(
                new EntityDamage(),
                new PlayerJoin(),
                new PlayerChangedWorld(),
                new EntityDamageByEntity(),
                new InventoryClick(),
                new BlockBreak(),
                new BlockPlace(),
                new PlayerDropItem(),
                new PlayerInteract(),
                new PlayerMove(),
                new PlayerQuit(),
                new FoodLevelChange(),
                new PlayerSpawnLocation(),
                new InventoryClose()
        );
    }
}
