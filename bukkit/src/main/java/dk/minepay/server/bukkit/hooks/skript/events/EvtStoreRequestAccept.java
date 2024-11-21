package dk.minepay.server.bukkit.hooks.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import dk.minepay.server.bukkit.classes.StoreRequest;
import dk.minepay.server.bukkit.events.StoreRequestAcceptEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

/** The type Evt store request accept. */
public class EvtStoreRequestAccept extends SkriptEvent {
    static {
        Skript.registerEvent(
                "Store Request Accept",
                EvtStoreRequestAccept.class,
                StoreRequestAcceptEvent.class,
                "store request accept");
        EventValues.registerEventValue(
                StoreRequestAcceptEvent.class,
                OfflinePlayer.class,
                new Getter<OfflinePlayer, StoreRequestAcceptEvent>() {
                    @Override
                    public OfflinePlayer get(StoreRequestAcceptEvent event) {
                        return event.getPlayer();
                    }
                },
                0);
        EventValues.registerEventValue(
                StoreRequestAcceptEvent.class,
                StoreRequest.class,
                new Getter<StoreRequest, StoreRequestAcceptEvent>() {
                    @Override
                    public StoreRequest get(StoreRequestAcceptEvent event) {
                        return event.getRequest();
                    }
                },
                0);
    }

    /** Instantiates a new Evt store request accept. */
    public EvtStoreRequestAccept() {}

    @Override
    public boolean init(
            Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return event instanceof StoreRequestAcceptEvent;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "";
    }
}
