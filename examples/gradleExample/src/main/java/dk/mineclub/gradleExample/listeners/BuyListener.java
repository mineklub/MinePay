package dk.mineclub.gradleExample.listeners;

import dk.mineclub.gradleExample.Main;
import dk.minepay.api.bukkit.events.StoreRequestAcceptEvent;
import dk.minepay.api.bukkit.events.StoreRequestCancelEvent;
import dk.minepay.common.classes.StoreProduct;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BuyListener implements Listener {
	@EventHandler
	public void onAccept(StoreRequestAcceptEvent event) {
		Main.getInstance().getMinePayApi().getRequestManager().acceptRequest(event.getRequest().get_id());
		for (StoreProduct product : event.getRequest().getProducts()) {
			Bukkit.broadcastMessage("§6[Butik] §f" + event.getPlayer().getName() + " §7har lige købt ranket §b" + product.getName() + " §7for §e⛃" + product.getPrice());
		}
	}

	@EventHandler
	public void onCancel(StoreRequestCancelEvent event) {
		Main.getInstance().getMinePayApi().getRequestManager().acceptRequest(event.getRequest().get_id());
	}
}
