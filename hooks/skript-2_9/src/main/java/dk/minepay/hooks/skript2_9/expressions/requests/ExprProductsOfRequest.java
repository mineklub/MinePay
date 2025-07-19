package dk.minepay.hooks.skript2_9.expressions.requests;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.minepay.api.bukkit.events.StoreRequestAcceptOnlineEvent;
import dk.minepay.api.bukkit.events.StoreRequestCancelOnlineEvent;
import dk.minepay.api.bukkit.events.StoreRequestEvent;
import dk.minepay.common.classes.StoreProduct;
import dk.minepay.common.classes.StoreRequest;
import org.bukkit.event.Event;

/** The type Expr products of request. */
public class ExprProductsOfRequest extends SimpleExpression<StoreProduct> {
    static {
        Skript.registerExpression(
                ExprProductsOfRequest.class,
                StoreProduct.class,
                ExpressionType.SIMPLE,
                "products of %request%");
    }

    /** Instantiates a new Expr products of request. */
    public ExprProductsOfRequest() {}

    @Override
    protected StoreProduct[] get(Event e) {
        if (!(e instanceof StoreRequestEvent)
                && !(e instanceof StoreRequestAcceptOnlineEvent)
                && !(e instanceof StoreRequestCancelOnlineEvent)) {
            return new StoreProduct[0];
        }

        StoreRequest request = null;
        if (e instanceof StoreRequestEvent) {
            request = ((StoreRequestEvent) e).getRequest();
        } else if (e instanceof StoreRequestAcceptOnlineEvent) {
            request = ((StoreRequestAcceptOnlineEvent) e).getRequest();
        } else {
            request = ((StoreRequestCancelOnlineEvent) e).getRequest();
        }

        return request.getProducts();
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends StoreProduct> getReturnType() {
        return StoreProduct.class;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "products of request";
    }

    @Override
    public boolean init(
            Expression<?>[] expressions,
            int matchedPattern,
            Kleenean isDelayed,
            SkriptParser.ParseResult parseResult) {
        return true;
    }
}
