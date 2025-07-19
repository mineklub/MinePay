package dk.minepay.hooks.skript2_9.expressions.requests;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import dk.minepay.common.classes.StoreRequest;

/** Status of request expression. */
public class ExprStatusOfRequest extends SimplePropertyExpression<StoreRequest, String> {
    static {
        register(ExprStatusOfRequest.class, String.class, "status", "request");
    }

    /** Creates a new status of request expression. */
    public ExprStatusOfRequest() {}

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
