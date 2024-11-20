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

    public StoreRequest(
            String _id,
            String uuid,
            StoreProduct[] products,
            RequestStatus status,
            RequestStatus serverStatus,
            double price) {
        this._id = _id;
        this.uuid = uuid;
        this.products = products;
        this.status = status;
        this.serverStatus = serverStatus;
        this.price = price;
    }

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
