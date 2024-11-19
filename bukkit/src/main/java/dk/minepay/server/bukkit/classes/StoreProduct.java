package dk.minepay.server.bukkit.classes;

import com.google.gson.JsonObject;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreProduct {
    private String name;
    private String id;
    private double price;
    private double quantity;
    private HashMap<String, String> metadata = null;

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

    public StoreProduct(String name, String id, double price, double quantity) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

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
}
