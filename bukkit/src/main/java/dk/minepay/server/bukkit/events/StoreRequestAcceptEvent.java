package dk.minepay.server.bukkit.events;

import dk.minepay.server.bukkit.classes.StoreRequest;
import org.bukkit.OfflinePlayer;

/** Event that is triggered when a store request is accepted. */
public class StoreRequestAcceptEvent extends StoreRequestEvent {
    /**
     * Constructor for StoreRequestAcceptEvent.
     *
     * @param request the store request that was accepted
     * @param player the player that accepted the store request
     */
    public StoreRequestAcceptEvent(StoreRequest request, OfflinePlayer player) {
        super(request, player);
    }
}
