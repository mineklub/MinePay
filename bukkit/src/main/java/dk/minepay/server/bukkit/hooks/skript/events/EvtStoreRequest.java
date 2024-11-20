package dk.minepay.server.bukkit.hooks.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import dk.minepay.server.bukkit.classes.StoreRequest;
import dk.minepay.server.bukkit.events.StoreRequestEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

public class EvtStoreRequest extends SkriptEvent {
    static {
        Skript.registerEvent(
                "Store Request", EvtStoreRequest.class, StoreRequestEvent.class, "store request");
        EventValues.registerEventValue(
                StoreRequestEvent.class,
                OfflinePlayer.class,
                new Getter<OfflinePlayer, StoreRequestEvent>() {
                    @Override
                    public OfflinePlayer get(StoreRequestEvent event) {
                        return event.getPlayer();
                    }
                },
                0);
        EventValues.registerEventValue(
                StoreRequestEvent.class,
                StoreRequest.class,
                new Getter<StoreRequest, StoreRequestEvent>() {
                    @Override
                    public StoreRequest get(StoreRequestEvent event) {
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
        return event instanceof StoreRequestEvent;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "";
    }
}
