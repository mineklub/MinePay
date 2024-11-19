package dk.minepay.server.bukkit.events;

import dk.minepay.server.bukkit.classes.StoreRequest;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/** Event that is triggered when a store request join is cancelled. */
@Getter
public class StoreRequestCancelJoinEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final StoreRequest request;
    private final Player player;

    /**
     * Constructor for StoreRequestCancelJoinEvent.
     *
     * @param request the store request that was cancelled
     * @param player the player that cancelled the store request
     */
    public StoreRequestCancelJoinEvent(StoreRequest request, Player player) {
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
