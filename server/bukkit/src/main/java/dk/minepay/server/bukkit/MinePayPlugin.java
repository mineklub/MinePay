package dk.minepay.server.bukkit;

import ch.njol.skript.Skript;
import dk.minepay.api.bukkit.MinePayApi;
import dk.minepay.hooks.skript.SkriptHook;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
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
        Plugin skriptPlugin = Bukkit.getPluginManager().getPlugin("Skript");
        if (skriptPlugin != null) {
            Skript skript = (Skript) skriptPlugin;
            // Check skript version
            new SkriptHook().init();
        }
    }

    @Override
    public void onDisable() {
        minePayApi.disable();
    }
}
