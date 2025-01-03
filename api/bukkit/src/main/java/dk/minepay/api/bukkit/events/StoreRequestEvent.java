package dk.minepay.api.bukkit.events;

import dk.minepay.common.classes.StoreRequest;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/** Event that is triggered when a store request is made. */
@Getter
public class StoreRequestEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final StoreRequest request;
    private final OfflinePlayer player;

    /**
     * Constructor for StoreRequestEvent.
     *
     * @param request the store request that was made
     * @param player the player that made the store request
     */
    public StoreRequestEvent(StoreRequest request, OfflinePlayer player) {
        super(true);
        this.request = request;
        this.player = player;
    }

    /**
     * Gets the handler list for the event.
     *
     * @return the handler list for the event
     */
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Gets the handler list for the event.
     *
     * @return the handler list for the event
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
