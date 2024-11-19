package dk.minepay.server.bukkit.events;

import dk.minepay.server.bukkit.classes.StoreRequest;
import org.bukkit.OfflinePlayer;

public class StoreRequestAcceptEvent extends StoreRequestEvent {
    public StoreRequestAcceptEvent(StoreRequest request, OfflinePlayer player) {
        super(request, player);
    }
}
