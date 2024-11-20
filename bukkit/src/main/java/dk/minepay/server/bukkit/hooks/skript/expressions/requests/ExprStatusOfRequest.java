package dk.minepay.server.bukkit.hooks.skript.expressions.requests;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import dk.minepay.server.bukkit.classes.StoreRequest;

public class ExprStatusOfRequest extends SimplePropertyExpression<StoreRequest, String> {
    static {
        register(ExprStatusOfRequest.class, String.class, "status", "request");
    }

    @Override
    public String convert(StoreRequest request) {
        return request.getStatus().name();
    }

    @Override
    protected String getPropertyName() {
        return "status of request";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
