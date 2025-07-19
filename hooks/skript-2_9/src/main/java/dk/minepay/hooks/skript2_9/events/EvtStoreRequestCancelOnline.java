package dk.minepay.hooks.skript2_9.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
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
                new Getter<Player, StoreRequestCancelOnlineEvent>() {
                    @Override
                    public Player get(StoreRequestCancelOnlineEvent event) {
                        return event.getPlayer();
                    }
                },
                0);
        EventValues.registerEventValue(
                StoreRequestCancelOnlineEvent.class,
                StoreRequest.class,
                new Getter<StoreRequest, StoreRequestCancelOnlineEvent>() {
                    @Override
                    public StoreRequest get(StoreRequestCancelOnlineEvent event) {
                        return event.getRequest();
                    }
                },
                0);
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
