package dk.minepay.api.bukkit.events;

import dk.minepay.common.classes.StoreRequest;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/** Event that is triggered when a store request is accepted. */
@Getter
public class StoreRequestAcceptOnlineEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final StoreRequest request;
    private final Player player;

    /**
     * Constructor for StoreRequestAcceptJoinEvent.
     *
     * @param request the store request that was accepted
     * @param player the player that accepted the store request
     */
    public StoreRequestAcceptOnlineEvent(StoreRequest request, Player player) {
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
