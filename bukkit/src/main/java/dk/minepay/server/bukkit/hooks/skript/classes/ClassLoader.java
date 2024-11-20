package dk.minepay.server.bukkit.hooks.skript.classes;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import dk.minepay.server.bukkit.classes.RequestStatus;
import dk.minepay.server.bukkit.classes.StoreProduct;
import dk.minepay.server.bukkit.classes.StoreRequest;
import java.io.StreamCorruptedException;
import java.util.Arrays;
import java.util.HashMap;

public class ClassLoader {
    static {
        Classes.registerClass(
                new ClassInfo<>(StoreProduct.class, "product")
                        .user("products?")
                        .name("Product")
                        .description("Products are the items that are being sold in the store.")
                        .defaultExpression(new EventValueExpression<>(StoreProduct.class))
                        .parser(
                                new Parser<StoreProduct>() {

                                    @Override
                                    public StoreProduct parse(String input, ParseContext context) {
                                        return null;
                                    }

                                    @Override
                                    public boolean canParse(ParseContext context) {
                                        return false;
                                    }

                                    @Override
                                    public String toVariableNameString(StoreProduct product) {
                                        return "product:"
                                                + product.getId()
                                                + ","
                                                + product.getName()
                                                + ","
                                                + product.getPrice();
                                    }

                                    @Override
                                    public String toString(StoreProduct product, int flags) {
                                        return toVariableNameString(product);
                                    }
                                })
                        .serializer(
                                new Serializer<StoreProduct>() {
                                    @Override
                                    public Fields serialize(StoreProduct o) {
                                        Fields fields = new Fields();
                                        fields.putObject("_id", o.getId());
                                        fields.putObject("name", o.getName());
                                        fields.putObject("price", o.getPrice());
                                        fields.putObject("quantity", o.getQuantity());
                                        fields.putObject("metadata", o.getMetadata());
                                        return fields;
                                    }

                                    @SuppressWarnings("unchecked, ConstantConditions")
                                    @Override
                                    protected StoreProduct deserialize(Fields fields)
                                            throws StreamCorruptedException {
                                        return new StoreProduct(
                                                (String) fields.getObject("_id"),
                                                (String) fields.getObject("name"),
                                                (double) fields.getObject("price"),
                                                (double) fields.getObject("quantity"),
                                                (HashMap<String, String>)
                                                        fields.getObject("metadata"));
                                    }

                                    @Override
                                    public void deserialize(StoreProduct o, Fields f) {}

                                    @Override
                                    public boolean mustSyncDeserialization() {
                                        return true;
                                    }

                                    @Override
                                    protected boolean canBeInstantiated() {
                                        return false;
                                    }
                                }));
        Classes.registerClass(
                new ClassInfo<>(StoreRequest.class, "request")
                        .user("request?")
                        .name("Request")
                        .description("Requests are the requests that are being made in the store.")
                        .defaultExpression(new EventValueExpression<>(StoreRequest.class))
                        .parser(
                                new Parser<StoreRequest>() {

                                    @Override
                                    public StoreRequest parse(String input, ParseContext context) {
                                        return null;
                                    }

                                    @Override
                                    public boolean canParse(ParseContext context) {
                                        return false;
                                    }

                                    @Override
                                    public String toVariableNameString(StoreRequest request) {
                                        return "request:"
                                                + request.get_id()
                                                + ","
                                                + request.getUuid()
                                                + ","
                                                + Arrays.toString(request.getProducts())
                                                + ","
                                                + request.getPrice();
                                    }

                                    @Override
                                    public String toString(StoreRequest request, int flags) {
                                        return toVariableNameString(request);
                                    }
                                })
                        .serializer(
                                new Serializer<StoreRequest>() {
                                    @Override
                                    public Fields serialize(StoreRequest o) {
                                        Fields fields = new Fields();
                                        fields.putObject("_id", o.get_id());
                                        fields.putObject("uuid", o.getUuid());
                                        fields.putObject("products", o.getProducts());
                                        fields.putObject("status", o.getStatus());
                                        fields.putObject("serverStatus", o.getServerStatus());
                                        fields.putObject("price", o.getPrice());
                                        return fields;
                                    }

                                    @SuppressWarnings("ConstantConditions")
                                    @Override
                                    protected StoreRequest deserialize(Fields fields)
                                            throws StreamCorruptedException {
                                        return new StoreRequest(
                                                (String) fields.getObject("_id"),
                                                (String) fields.getObject("uuid"),
                                                (StoreProduct[]) fields.getObject("products"),
                                                (RequestStatus) fields.getObject("status"),
                                                (RequestStatus) fields.getObject("serverStatus"),
                                                (double) fields.getObject("price"));
                                    }

                                    @Override
                                    public void deserialize(StoreRequest o, Fields f) {}

                                    @Override
                                    public boolean mustSyncDeserialization() {
                                        return true;
                                    }

                                    @Override
                                    protected boolean canBeInstantiated() {
                                        return false;
                                    }
                                }));
    }
}
