package dk.minepay.server.bukkit.hooks.skript.expressions.requests;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import dk.minepay.server.bukkit.classes.StoreRequest;

public class ExprServerStatusOfRequest extends SimplePropertyExpression<StoreRequest, String> {
    static {
        register(ExprServerStatusOfRequest.class, String.class, "serverstatus", "request");
    }

    @Override
    public String convert(StoreRequest request) {
        return request.getServerStatus().name();
    }

    @Override
    protected String getPropertyName() {
        return "serverstatus of request";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
