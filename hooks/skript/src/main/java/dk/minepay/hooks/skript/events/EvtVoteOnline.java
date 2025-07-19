package dk.minepay.hooks.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import dk.minepay.api.bukkit.events.VoteOnlineEvent;
import dk.minepay.common.classes.Vote;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/** The type Evt vote online. */
public class EvtVoteOnline extends SkriptEvent {
    static {
        Skript.registerEvent(
                "vote", EvtVoteOnline.class, VoteOnlineEvent.class, "[mineclub] vote online");
        EventValues.registerEventValue(
                VoteOnlineEvent.class,
                Player.class,
                new Getter<Player, VoteOnlineEvent>() {
                    @Override
                    public Player get(VoteOnlineEvent event) {
                        return event.getPlayer();
                    }
                },
                0);
        EventValues.registerEventValue(
                VoteOnlineEvent.class,
                Vote.class,
                new Getter<Vote, VoteOnlineEvent>() {
                    @Override
                    public Vote get(VoteOnlineEvent event) {
                        return event.getVote();
                    }
                },
                0);
    }

    /** Instantiates a new Evt store request accept. */
    public EvtVoteOnline() {}

    @Override
    public boolean init(
            Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return event instanceof VoteOnlineEvent;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "";
    }
}
