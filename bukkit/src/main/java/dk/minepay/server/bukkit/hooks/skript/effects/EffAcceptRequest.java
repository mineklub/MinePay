package dk.minepay.server.bukkit.hooks.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.minepay.server.bukkit.MinePayApi;
import dk.minepay.server.bukkit.classes.StoreRequest;
import org.bukkit.event.Event;

/** The type Eff accept request. */
public class EffAcceptRequest extends Effect {
    static {
        Skript.registerEffect(EffAcceptRequest.class, "accept request %requests%");
    }

    /** Instantiates a new Eff accept request. */
    public EffAcceptRequest() {}

    private Expression<StoreRequest> request;

    @Override
    protected void execute(Event event) {
        StoreRequest[] requests = request.getAll(event);
        for (StoreRequest request : requests) {
            MinePayApi.runAsync(() -> {
                MinePayApi.getINSTANCE().getRequestManager().acceptRequest(request.get_id());
            });
        }
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "accept request %requests%";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(
            Expression<?>[] expressions,
            int matchedPattern,
            Kleenean isDelayed,
            SkriptParser.ParseResult parseResult) {
        request = (Expression<StoreRequest>) expressions[0];
        return true;
    }
}
