package dk.minepay.server.bukkit.hooks.skript.expressions.product;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import dk.minepay.server.bukkit.classes.StoreProduct;

public class ExprQuantityOfProduct extends SimplePropertyExpression<StoreProduct, Double> {
    static {
        register(ExprQuantityOfProduct.class, Double.class, "quantity", "product");
    }

    @Override
    public Double convert(StoreProduct request) {
        return request.getQuantity();
    }

    @Override
    protected String getPropertyName() {
        return "quantity of product";
    }

    @Override
    public Class<? extends Double> getReturnType() {
        return Double.class;
    }
}
