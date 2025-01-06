package dk.mineclub.gradleExample;

import dk.mineclub.gradleExample.commands.CommandBuyvip;
import dk.mineclub.gradleExample.listeners.BuyListener;
import dk.mineclub.gradleExample.listeners.VoteListener;
import dk.minepay.api.bukkit.MinePayApi;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	private static Main instance;
	private MinePayApi minePayApi;

	@Override
	public void onEnable() {
		instance = this;
		minePayApi = MinePayApi.initApi(this);
		getCommand("buyvip").setExecutor(new CommandBuyvip());
		getServer().getPluginManager().registerEvents(new BuyListener(), this);
		getServer().getPluginManager().registerEvents(new VoteListener(), this);
	}

	public MinePayApi getMinePayApi() {
		return minePayApi;
	}

	public static Main getInstance() {
		return instance;
	}
}
