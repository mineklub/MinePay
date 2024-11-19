package dk.minepay.server.bukkit.events;

import dk.minepay.server.bukkit.classes.StoreRequest;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class StoreRequestCancelJoinEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final StoreRequest request;
    private final Player player;

    public StoreRequestCancelJoinEvent(StoreRequest request, Player player) {
        super(true);
        this.request = request;
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
