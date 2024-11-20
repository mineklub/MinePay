package dk.minepay.server.bukkit.hooks.skript.expressions.product;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import dk.minepay.server.bukkit.classes.StoreProduct;

public class ExprIdOfProduct extends SimplePropertyExpression<StoreProduct, String> {
    static {
        register(ExprIdOfProduct.class, String.class, "id", "product");
    }

    @Override
    public String convert(StoreProduct request) {
        return request.getId();
    }

    @Override
    protected String getPropertyName() {
        return "id of product";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
