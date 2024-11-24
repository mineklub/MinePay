package dk.minepay.server.bukkit.classes;

import com.google.gson.JsonObject;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

/** Class representing a product in the store. */
@Getter
@Setter
public class StoreProduct {
    private String name;
    private String id;
    private double price;
    private double quantity;
    private HashMap<String, String> metadata = null;

    /** Default constructor for StoreProduct. */
    public StoreProduct() {}

    /**
     * Constructor for StoreProduct with metadata.
     *
     * @param name the name of the product
     * @param id the unique identifier of the product
     * @param price the price of the product
     * @param quantity the quantity of the product
     * @param metadata additional metadata for the product
     */
    public StoreProduct(
            String name,
            String id,
            double price,
            double quantity,
            HashMap<String, String> metadata) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.metadata = metadata;
    }

    /**
     * Constructor for StoreProduct without metadata.
     *
     * @param name the name of the product
     * @param id the unique identifier of the product
     * @param price the price of the product
     * @param quantity the quantity of the product
     */
    public StoreProduct(String name, String id, double price, double quantity) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Converts the StoreProduct to a JSON object.
     *
     * @return a JsonObject representing the StoreProduct
     */
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("price", price);
        jsonObject.addProperty("quantity", quantity);
        if (metadata != null) {
            JsonObject metadataObject = new JsonObject();
            for (String key : metadata.keySet()) {
                metadataObject.addProperty(key, metadata.get(key));
            }
            jsonObject.add("metadata", metadataObject);
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return "StoreProduct{"
                + "name='"
                + name
                + '\''
                + ", id='"
                + id
                + '\''
                + ", price="
                + price
                + ", quantity="
                + quantity
                + ", metadata="
                + metadata
                + '}';
    }
}
