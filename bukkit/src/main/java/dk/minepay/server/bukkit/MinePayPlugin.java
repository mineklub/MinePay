package dk.minepay.server.bukkit;

import dk.minepay.server.bukkit.hooks.SkriptHook;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class MinePayPlugin extends JavaPlugin {
    private MinePayApi minePayApi;

    @Override
    public void onEnable() {
        minePayApi = MinePayApi.initApi(this);
        initSkriptHook();
    }

    public void initSkriptHook() {
        if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
            new SkriptHook().init();
        }
    }
}
