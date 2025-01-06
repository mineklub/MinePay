package dk.mineclub.gradleExample.listeners;

import dk.mineclub.gradleExample.Main;
import dk.minepay.api.bukkit.events.VoteOnlineEvent;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

public class VoteListener implements Listener {
	@EventHandler
	public void onVote(VoteOnlineEvent event) {
		Bukkit.broadcastMessage("§d[Vote] §f" + event.getPlayer().getName() + " §7har givet en vote til serveren! §c❤");
		ItemStack item = new ItemStack(Material.INK_SACK);
		Dye dye = new Dye();
		dye.setColor(DyeColor.PURPLE);
		item.setData(dye);
		event.getPlayer().getInventory().addItem(item);
		Main.getInstance().getMinePayApi().getRequestManager().acceptVote(event.getVote().get_id());
	}
}
