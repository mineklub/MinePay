package dk.minepay.server.bukkit.hooks.skript.expressions.requests;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import dk.minepay.server.bukkit.classes.StoreRequest;

/** ID of request expression. */
public class ExprIdOfRequest extends SimplePropertyExpression<StoreRequest, String> {
    static {
        register(ExprIdOfRequest.class, String.class, "id", "request");
    }

    /** Creates a new id of request expression. */
    public ExprIdOfRequest() {}

    @Override
    public String convert(StoreRequest request) {
        return request.get_id();
    }

    @Override
    protected String getPropertyName() {
        return "id of request";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
