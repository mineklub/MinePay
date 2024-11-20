package dk.minepay.server.bukkit.hooks.skript.expressions.product;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import dk.minepay.server.bukkit.classes.StoreProduct;

public class ExprNameOfProduct extends SimplePropertyExpression<StoreProduct, String> {
    static {
        register(ExprNameOfProduct.class, String.class, "name", "product");
    }

    @Override
    public String convert(StoreProduct request) {
        return request.getName();
    }

    @Override
    protected String getPropertyName() {
        return "name of product";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
