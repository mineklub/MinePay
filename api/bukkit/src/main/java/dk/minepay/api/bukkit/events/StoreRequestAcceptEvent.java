package dk.minepay.api.bukkit.events;

import dk.minepay.common.classes.StoreRequest;
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
