package dk.minepay.hooks.skript2_10.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import dk.minepay.api.bukkit.events.StoreRequestCancelOnlineEvent;
import dk.minepay.common.classes.StoreRequest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/** The type Evt store request cancel online. */
public class EvtStoreRequestCancelOnline extends SkriptEvent {
    static {
        Skript.registerEvent(
                "Store Request Cancel Online",
                EvtStoreRequestCancelOnline.class,
                StoreRequestCancelOnlineEvent.class,
                "[mineclub] store request cancel online");
        EventValues.registerEventValue(
                StoreRequestCancelOnlineEvent.class,
                Player.class,
                StoreRequestCancelOnlineEvent::getPlayer);
        EventValues.registerEventValue(
                StoreRequestCancelOnlineEvent.class,
                StoreRequest.class,
                StoreRequestCancelOnlineEvent::getRequest);
    }

    /** Instantiates a new Evt store request cancel online. */
    public EvtStoreRequestCancelOnline() {}

    @Override
    public boolean init(
            Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return event instanceof StoreRequestCancelOnlineEvent;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "";
    }
}
