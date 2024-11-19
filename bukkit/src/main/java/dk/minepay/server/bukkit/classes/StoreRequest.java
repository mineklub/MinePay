package dk.minepay.server.bukkit.classes;

import java.util.UUID;
import lombok.Getter;

@Getter
public class StoreRequest {
    private String _id;
    private String uuid;
    private StoreProduct[] products;
    private RequestStatus status;
    private RequestStatus serverStatus;
    private double price;

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }
}
