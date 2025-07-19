package dk.minepay.hooks.skript2_9.expressions.requests;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.minepay.api.bukkit.MinePayApi;
import dk.minepay.common.classes.StoreRequest;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.event.Event;

/** Request with ID expression. */
public class ExprRequestWithId extends SimpleExpression<StoreRequest> {
    /** Creates a new request with ID expression. */
    public ExprRequestWithId() {}

    static {
        Skript.registerExpression(
                ExprRequestWithId.class,
                StoreRequest.class,
                ExpressionType.SIMPLE,
                "request[s] with ID %strings%");
    }

    private Expression<String> requestId;

    @Override
    protected StoreRequest[] get(Event event) {
        List<StoreRequest> requests = new ArrayList<>();
        for (String s : requestId.getAll(event)) {
            MinePayApi.runAsync(
                    () -> {
                        StoreRequest request =
                                MinePayApi.getINSTANCE().getRequestManager().getRequest(s);
                        if (request != null) {
                            requests.add(request);
                        }
                    });
        }

        return requests.toArray(new StoreRequest[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends StoreRequest> getReturnType() {
        return StoreRequest.class;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "request with ID";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(
            Expression<?>[] expressions,
            int matchedPattern,
            Kleenean isDelayed,
            SkriptParser.ParseResult parseResult) {
        requestId = (Expression<String>) expressions[0];
        return true;
    }
}
