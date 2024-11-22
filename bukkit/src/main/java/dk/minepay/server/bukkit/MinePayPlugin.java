package dk.minepay.server.bukkit;

import dk.minepay.server.bukkit.hooks.skript.SkriptHook;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the MinePay plugin. This class initializes the MinePay API and the Skript hook if
 * Skript is available.
 */
@Getter
public class MinePayPlugin extends JavaPlugin {
    private MinePayApi minePayApi;

    /** Constructor for the MinePayPlugin class. */
    public MinePayPlugin() {}

    /**
     * Called when the plugin is enabled. Initializes the MinePay API and Skript hook if Skript is
     * available.
     */
    @Override
    public void onEnable() {
        minePayApi = MinePayApi.initApi(this);
        initSkriptHook();
    }

    /** Initializes the Skript hook if the Skript plugin is available. */
    public void initSkriptHook() {
        if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
            new SkriptHook().init();
        }
    }

    @Override
    public void onDisable() {
        minePayApi.disable();
    }
}
