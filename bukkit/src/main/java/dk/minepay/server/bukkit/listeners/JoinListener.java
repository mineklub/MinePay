package dk.minepay.server.bukkit.listeners;

import dk.minepay.server.bukkit.MinePayApi;
import dk.minepay.server.bukkit.managers.RequestManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        RequestManager requestManager = MinePayApi.getINSTANCE().getRequestManager();
        if (requestManager.getJoinRequests().containsKey(event.getPlayer().getUniqueId())) {
            requestManager.callJoinEvent(
                    requestManager.getJoinRequests().get(event.getPlayer().getUniqueId()));
            requestManager.getJoinRequests().remove(event.getPlayer().getUniqueId());
        }
    }
}
