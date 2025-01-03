package dk.minepay.server.bukkit.hooks.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import dk.minepay.api.bukkit.events.StoreRequestCancelEvent;
import dk.minepay.common.classes.StoreRequest;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

/** The type Evt store request cancel. */
public class EvtStoreRequestCancel extends SkriptEvent {
    static {
        Skript.registerEvent(
                "Store Request Cancel",
                EvtStoreRequestCancel.class,
                StoreRequestCancelEvent.class,
                "[mineclub] store request cancel");
        EventValues.registerEventValue(
                StoreRequestCancelEvent.class,
                OfflinePlayer.class,
                new Getter<OfflinePlayer, StoreRequestCancelEvent>() {
                    @Override
                    public OfflinePlayer get(StoreRequestCancelEvent event) {
                        return event.getPlayer();
                    }
                },
                0);
        EventValues.registerEventValue(
                StoreRequestCancelEvent.class,
                StoreRequest.class,
                new Getter<StoreRequest, StoreRequestCancelEvent>() {
                    @Override
                    public StoreRequest get(StoreRequestCancelEvent event) {
                        return event.getRequest();
                    }
                },
                0);
    }

    /** Instantiates a new Evt store request cancel. */
    public EvtStoreRequestCancel() {}

    @Override
    public boolean init(
            Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return event instanceof StoreRequestCancelEvent;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "";
    }
}
