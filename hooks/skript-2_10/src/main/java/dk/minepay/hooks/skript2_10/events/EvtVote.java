package dk.minepay.hooks.skript2_10.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import dk.minepay.api.bukkit.events.VoteEvent;
import dk.minepay.common.classes.Vote;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

/** The type Evt vote. */
public class EvtVote extends SkriptEvent {
    static {
        Skript.registerEvent("vote", EvtVote.class, VoteEvent.class, "[mineclub] vote");
        EventValues.registerEventValue(VoteEvent.class, OfflinePlayer.class, VoteEvent::getPlayer);
        EventValues.registerEventValue(VoteEvent.class, Vote.class, VoteEvent::getVote);
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
