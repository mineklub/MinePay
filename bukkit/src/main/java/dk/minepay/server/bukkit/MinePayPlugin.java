package dk.minepay.server.bukkit;

import dk.minepay.server.bukkit.hooks.SkriptHook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MinePayPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        MinePayApi.initApi(this);
        initSkriptHook();
    }

    public void initSkriptHook() {
        if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
            new SkriptHook().init();
        }
    }
}
