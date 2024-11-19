package dk.minepay.server.bukkit.events;

import dk.minepay.server.bukkit.classes.StoreRequest;
import org.bukkit.OfflinePlayer;

/** Event that is triggered when a store request is cancelled. */
public class StoreRequestCancelEvent extends StoreRequestEvent {
    /**
     * Constructor for StoreRequestCancelEvent.
     *
     * @param request the store request that was cancelled
     * @param player the player that cancelled the store request
     */
    public StoreRequestCancelEvent(StoreRequest request, OfflinePlayer player) {
        super(request, player);
    }
}
