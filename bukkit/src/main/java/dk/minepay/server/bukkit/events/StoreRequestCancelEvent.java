package dk.minepay.server.bukkit.events;

import dk.minepay.server.bukkit.classes.StoreRequest;
import org.bukkit.OfflinePlayer;

public class StoreRequestCancelEvent extends StoreRequestEvent {
    public StoreRequestCancelEvent(StoreRequest request, OfflinePlayer player) {
        super(request, player);
    }
}
