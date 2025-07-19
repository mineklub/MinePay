package dk.minepay.hooks.skript2_10.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.minepay.api.bukkit.MinePayApi;
import dk.minepay.common.classes.StoreProduct;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/** The type Eff send request. */
public class EffSendRequest extends Effect {
    static {
        Skript.registerEffect(
                EffSendRequest.class, "[mineclub] send request %products% to %players%");
    }

    /** Instantiates a new Eff send request. */
    public EffSendRequest() {}

    private Expression<StoreProduct> product;
    private Expression<Player> player;

    @Override
    protected void execute(Event event) {
        StoreProduct[] storeProducts = product.getAll(event);
        Player[] players = player.getAll(event);

        for (Player player1 : players) {
            MinePayApi.runAsync(
                    () -> {
                        MinePayApi.getINSTANCE()
                                .getRequestManager()
                                .createRequest(player1.getUniqueId(), storeProducts);
                    });
        }
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "send request %products% to %player%";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(
            Expression<?>[] expressions,
            int matchedPattern,
            Kleenean isDelayed,
            SkriptParser.ParseResult parseResult) {
        product = (Expression<StoreProduct>) expressions[0];
        player = (Expression<Player>) expressions[1];
        return true;
    }
}
