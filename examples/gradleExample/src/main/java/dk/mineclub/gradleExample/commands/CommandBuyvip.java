package dk.mineclub.gradleExample.commands;

import dk.mineclub.gradleExample.Main;
import dk.minepay.common.classes.StoreProduct;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommandBuyvip implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			HashMap<String, String> data = new HashMap<>();
			data.put("DATA", "WHATEVER");
			StoreProduct storeProduct = new StoreProduct("VIP", "VIP", 100.0, 1, data);
			StoreProduct[] storeProducts = new StoreProduct[] {storeProduct};
			Main.getInstance().getMinePayApi().getRequestManager().createRequest(player.getUniqueId(), storeProducts);
		}
		return true;
	}
}
