package dk.minepay.hooks.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.minepay.api.bukkit.MinePayApi;
import dk.minepay.common.classes.Vote;
import org.bukkit.event.Event;

/** The type Eff accept vote. */
public class EffAcceptVote extends Effect {
    static {
        Skript.registerEffect(EffAcceptVote.class, "[mineclub] accept vote %votes%");
    }

    /** Instantiates a new Eff accept request. */
    public EffAcceptVote() {}

    private Expression<Vote> vote;

    @Override
    protected void execute(Event event) {
        Vote[] votes = vote.getAll(event);
        for (Vote vote1 : votes) {
            MinePayApi.runAsync(
                    () -> {
                        MinePayApi.getINSTANCE().getRequestManager().acceptVote(vote1.get_id());
                    });
        }
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "accept vote %votes%";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(
            Expression<?>[] expressions,
            int matchedPattern,
            Kleenean isDelayed,
            SkriptParser.ParseResult parseResult) {
        vote = (Expression<Vote>) expressions[0];
        return true;
    }
}
