package dk.minepay.server.bukkit.classes;

import java.util.Arrays;
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

    /**
     * Constructor for the StoreRequest class. Used by Skript to deserialize the store request.
     *
     * @param _id the unique identifier of the store request
     * @param uuid the unique identifier of the store request
     * @param products the products of the store request
     * @param status the status of the store request
     * @param serverStatus the server status of the store request
     * @param price the price of the store request
     */
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

    @Override
    public String toString() {
        return "StoreRequest{"
                + "_id='"
                + _id
                + '\''
                + ", uuid='"
                + uuid
                + '\''
                + ", products="
                + Arrays.toString(products)
                + ", status="
                + status
                + ", serverStatus="
                + serverStatus
                + ", price="
                + price
                + '}';
    }
}
