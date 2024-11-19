package dk.minepay.server.bukkit.classes;

import java.util.UUID;
import lombok.Getter;

/** Class representing a store request. */
@Getter
public class StoreRequest {
    private String _id;
    private String uuid;
    private StoreProduct[] products;
    private RequestStatus status;
    private RequestStatus serverStatus;
    private double price;

    /** Constructor for the StoreRequest class. */
    public StoreRequest() {}

    /**
     * Gets the unique identifier of the store request.
     *
     * @return the unique identifier of the store request
     */
    public UUID getUuid() {
        return UUID.fromString(uuid);
    }
}
