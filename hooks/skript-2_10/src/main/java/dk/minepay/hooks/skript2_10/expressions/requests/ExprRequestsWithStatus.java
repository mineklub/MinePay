package dk.minepay.hooks.skript2_10.expressions.requests;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.minepay.api.bukkit.MinePayApi;
import dk.minepay.common.classes.RequestBody;
import dk.minepay.common.classes.RequestStatus;
import dk.minepay.common.classes.StoreRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.event.Event;

/** Request with status expression. */
public class ExprRequestsWithStatus extends SimpleExpression<StoreRequest> {
    /** Creates a new request with Status expression. */
    public ExprRequestsWithStatus() {}

    static {
        Skript.registerExpression(
                ExprRequestsWithStatus.class,
                StoreRequest.class,
                ExpressionType.SIMPLE,
                "request[s] with status %strings%[ and serverstatus %strings%]");
    }

    private Expression<String> status;
    private Expression<String> serverStatus;

    @Override
    protected StoreRequest[] get(Event event) {
        List<RequestStatus> statuses = new ArrayList<>();
        List<RequestStatus> serverStatuses = new ArrayList<>();

        for (String status : this.status.getAll(event)) {
            RequestStatus requestStatus = RequestStatus.fromString(status);
            if (requestStatus != null) {
                statuses.add(requestStatus);
            }
        }

        for (String serverStatus : this.serverStatus.getAll(event)) {
            RequestStatus requestStatus = RequestStatus.fromString(serverStatus);
            if (requestStatus != null) {
                serverStatuses.add(requestStatus);
            }
        }

        RequestBody requestBody =
                RequestBody.builder().statuses(statuses).serverStatuses(serverStatuses).build();

        if (serverStatuses.isEmpty() && statuses.isEmpty()) {
            return new StoreRequest[0];
        } else {
            return Arrays.stream(
                            MinePayApi.getINSTANCE().getRequestManager().getRequests(requestBody))
                    .toArray(StoreRequest[]::new);
        }
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
        return "requests with status";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(
            Expression<?>[] expressions,
            int matchedPattern,
            Kleenean isDelayed,
            SkriptParser.ParseResult parseResult) {
        status = (Expression<String>) expressions[0];
        serverStatus = (Expression<String>) expressions[1];
        return true;
    }
}
