package top.speedcubing.mlgrush;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
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
import top.speedcubing.server.utils.MapManager;

public class Main extends JavaPlugin implements CommandExecutor {

    public void onEnable() {
        MinecraftServer.getServer().getPropertyManager().setProperty("server-name", "mlgrush");
        registerCommands();
        registerListeners();
        registerWorlds();

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

    private void registerWorlds() {
        try {
            MapManager.install("map_mlgrush_2x1", "MLGRush2x1", Difficulty.PEACEFUL, 6000, false);
            MapManager.install("map_mlgrush_stickduel", "StickDuel", Difficulty.PEACEFUL, 6000, false);
            MapManager.install("mlgrushLobby", "lobby", Difficulty.PEACEFUL, 6000, false);
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
    }
}
