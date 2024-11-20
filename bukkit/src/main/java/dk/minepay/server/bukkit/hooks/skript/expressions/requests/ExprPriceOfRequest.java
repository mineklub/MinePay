package dk.minepay.server.bukkit.hooks.skript.expressions.requests;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import dk.minepay.server.bukkit.classes.StoreRequest;

public class ExprPriceOfRequest extends SimplePropertyExpression<StoreRequest, Double> {
    static {
        register(ExprPriceOfRequest.class, Double.class, "price", "request");
    }

    @Override
    public Double convert(StoreRequest request) {
        return request.getPrice();
    }

    @Override
    protected String getPropertyName() {
        return "price of request";
    }

    @Override
    public Class<? extends Double> getReturnType() {
        return Double.class;
    }
}
