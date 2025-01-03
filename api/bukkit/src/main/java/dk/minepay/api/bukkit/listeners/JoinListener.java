package dk.minepay.api.bukkit.listeners;

import dk.minepay.api.bukkit.MinePayApi;
import dk.minepay.api.bukkit.managers.EventManager;
import dk.minepay.common.classes.StoreRequest;
import dk.minepay.common.classes.Vote;
import java.util.List;
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
        List<StoreRequest> requests =
                EventManager.getRequestsOnlineByUUID(event.getPlayer().getUniqueId());
        if (!requests.isEmpty()) {
            for (StoreRequest request : requests) {
                EventManager.callOnlineRequestEvent(request);
            }
        }
        List<Vote> votes = EventManager.getVotesOnlineByUUID(event.getPlayer().getUniqueId());
        if (!votes.isEmpty()) {
            for (Vote vote : votes) {
                EventManager.callOnlineVoteEvent(vote);
            }
        }
    }
}
