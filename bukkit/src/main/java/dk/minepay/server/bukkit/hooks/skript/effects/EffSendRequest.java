package dk.minepay.server.bukkit.hooks.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.minepay.server.bukkit.MinePayApi;
import dk.minepay.server.bukkit.classes.StoreProduct;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EffSendRequest extends Effect {
    static {
        Skript.registerEffect(EffSendRequest.class, "send request %products% to %players%");
    }

    private Expression<StoreProduct> product;
    private Expression<Player> player;

    @Override
    protected void execute(Event event) {
        StoreProduct[] storeProducts = product.getAll(event);
        Player[] players = player.getAll(event);

        for (Player player1 : players) {
            MinePayApi.getINSTANCE()
                    .getRequestManager()
                    .createRequest(player1.getUniqueId(), storeProducts);
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