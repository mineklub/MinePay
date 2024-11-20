package dk.minepay.server.bukkit.hooks.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import dk.minepay.server.bukkit.classes.StoreRequest;
import dk.minepay.server.bukkit.events.StoreRequestAcceptOnlineEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EvtStoreRequestAcceptOnline extends SkriptEvent {
    static {
        Skript.registerEvent(
                "Store Request Accept Online",
                EvtStoreRequestAcceptOnline.class,
                StoreRequestAcceptOnlineEvent.class,
                "store request accept online");
        EventValues.registerEventValue(
                StoreRequestAcceptOnlineEvent.class,
                Player.class,
                new Getter<Player, StoreRequestAcceptOnlineEvent>() {
                    @Override
                    public Player get(StoreRequestAcceptOnlineEvent event) {
                        return event.getPlayer();
                    }
                },
                0);
        EventValues.registerEventValue(
                StoreRequestAcceptOnlineEvent.class,
                StoreRequest.class,
                new Getter<StoreRequest, StoreRequestAcceptOnlineEvent>() {
                    @Override
                    public StoreRequest get(StoreRequestAcceptOnlineEvent event) {
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
        return event instanceof StoreRequestAcceptOnlineEvent;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "";
    }
}
