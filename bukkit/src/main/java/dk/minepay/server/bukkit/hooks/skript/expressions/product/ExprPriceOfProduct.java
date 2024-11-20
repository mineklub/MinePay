package dk.minepay.server.bukkit.hooks.skript.expressions.product;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import dk.minepay.server.bukkit.classes.StoreProduct;

public class ExprPriceOfProduct extends SimplePropertyExpression<StoreProduct, Double> {
    static {
        register(ExprPriceOfProduct.class, Double.class, "price", "product");
    }

    @Override
    public Double convert(StoreProduct request) {
        return request.getPrice();
    }

    @Override
    protected String getPropertyName() {
        return "price of product";
    }

    @Override
    public Class<? extends Double> getReturnType() {
        return Double.class;
    }
}
