package dk.minepay.server.bukkit.hooks.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import dk.minepay.server.bukkit.classes.StoreRequest;
import dk.minepay.server.bukkit.events.StoreRequestCancelOnlineEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EvtStoreRequestCancelOnline extends SkriptEvent {
    static {
        Skript.registerEvent(
                "Store Request Cancel Online",
                EvtStoreRequestCancelOnline.class,
                StoreRequestCancelOnlineEvent.class,
                "store request cancel online");
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
