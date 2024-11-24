package dk.minepay.server.bukkit.events;

import dk.minepay.server.bukkit.classes.Vote;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/** Event that is triggered when a vote is made. */
@Getter
public class VoteOnlineEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Vote vote;
    private final Player player;

    /**
     * Constructor for VoteEvent.
     *
     * @param vote the vote that was made
     * @param player the player that made the vote
     */
    public VoteOnlineEvent(Vote vote, Player player) {
        super(true);
        this.vote = vote;
        this.player = player;
    }

    /**
     * Gets the handler list for the event.
     *
     * @return the handler list for the event
     */
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Gets the handler list for the event.
     *
     * @return the handler list for the event
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
