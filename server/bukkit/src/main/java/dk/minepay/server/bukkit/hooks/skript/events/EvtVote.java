package dk.minepay.server.bukkit.hooks.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import dk.minepay.api.bukkit.events.VoteEvent;
import dk.minepay.common.classes.Vote;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

/** The type Evt vote. */
public class EvtVote extends SkriptEvent {
    static {
        Skript.registerEvent("vote", EvtVote.class, VoteEvent.class, "[mineclub] vote");
        EventValues.registerEventValue(
                VoteEvent.class,
                OfflinePlayer.class,
                new Getter<OfflinePlayer, VoteEvent>() {
                    @Override
                    public OfflinePlayer get(VoteEvent event) {
                        return event.getPlayer();
                    }
                },
                0);
        EventValues.registerEventValue(
                VoteEvent.class,
                Vote.class,
                new Getter<Vote, VoteEvent>() {
                    @Override
                    public Vote get(VoteEvent event) {
                        return event.getVote();
                    }
                },
                0);
    }

    /** Instantiates a new Evt store request accept. */
    public EvtVote() {}

    @Override
    public boolean init(
            Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return event instanceof VoteEvent;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "";
    }
}
