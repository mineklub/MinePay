package dk.minepay.server.bukkit.listeners;

import dk.minepay.server.bukkit.MinePayApi;
import dk.minepay.server.bukkit.managers.RequestManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/** Listener class that handles player join events. */
public class JoinListener implements Listener {

    /** Constructor for JoinListener. Registers this listener with the plugin's event manager. */
    public JoinListener() {
        MinePayApi.getINSTANCE()
                .getPlugin()
                .getServer()
                .getPluginManager()
                .registerEvents(this, MinePayApi.getINSTANCE().getPlugin());
    }

    /**
     * Handles the PlayerJoinEvent.
     *
     * @param event the PlayerJoinEvent that is triggered when a player joins the server
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        RequestManager requestManager = MinePayApi.getINSTANCE().getRequestManager();
        if (MinePayApi.getINSTANCE()
                .getJoinRequests()
                .containsKey(event.getPlayer().getUniqueId())) {
            requestManager.callOnlineEvent(
                    MinePayApi.getINSTANCE()
                            .getJoinRequests()
                            .get(event.getPlayer().getUniqueId()));
            MinePayApi.getINSTANCE().getJoinRequests().remove(event.getPlayer().getUniqueId());
        }
    }
}
