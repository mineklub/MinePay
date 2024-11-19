package dk.mineclub.testplugin;

import dk.mineclub.testplugin.commands.TestCancelCommand;
import dk.mineclub.testplugin.commands.TestCommand;
import dk.mineclub.testplugin.listeners.StoreRequestListener;
import dk.minepay.server.bukkit.MinePayApi;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static MinePayApi minePayApi;

    @Override
    public void onEnable() {
        minePayApi = MinePayApi.initApi(this);
        getCommand("test").setExecutor(new TestCommand());
        getCommand("testcancel").setExecutor(new TestCancelCommand());
        getServer().getPluginManager().registerEvents(new StoreRequestListener(), this);
    }

    public static MinePayApi getMinePayApi() {
        return minePayApi;
    }
}
