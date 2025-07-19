package dk.minepay.hooks.skript2_10.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import dk.minepay.api.bukkit.events.StoreRequestAcceptOnlineEvent;
import dk.minepay.common.classes.StoreRequest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/** The type Evt store request accept online. */
public class EvtStoreRequestAcceptOnline extends SkriptEvent {
    static {
        Skript.registerEvent(
                "Store Request Accept Online",
                EvtStoreRequestAcceptOnline.class,
                StoreRequestAcceptOnlineEvent.class,
                "[mineclub] store request accept online");
        EventValues.registerEventValue(
                StoreRequestAcceptOnlineEvent.class,
                Player.class,
                StoreRequestAcceptOnlineEvent::getPlayer);
        EventValues.registerEventValue(
                StoreRequestAcceptOnlineEvent.class,
                StoreRequest.class,
                StoreRequestAcceptOnlineEvent::getRequest);
    }

    /** Instantiates a new Evt store request accept online. */
    public EvtStoreRequestAcceptOnline() {}

    @Override
    public boolean init(
            Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return event instanceof StoreRequestAcceptOnlineEvent;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "";
    }
}
