package dk.minepay.server.bukkit.hooks.skript.expressions.product;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.minepay.server.bukkit.classes.StoreProduct;
import org.bukkit.event.Event;

public class ExprMetadataValueOfProduct extends SimpleExpression<String> {
    static {
        Skript.registerExpression(
                ExprMetadataValueOfProduct.class,
                String.class,
                ExpressionType.COMBINED,
                "metadata-value %string% of %product%");
    }

    private Expression<StoreProduct> product;
    private Expression<String> key;

    @Override
    protected String[] get(Event e) {
        StoreProduct product = this.product.getSingle(e);
        if (product == null) {
            return new String[0];
        }

        return new String[] {product.getMetadata().get(key.getSingle(e))};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "metadata-key of product";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(
            Expression<?>[] expressions,
            int matchedPattern,
            Kleenean isDelayed,
            SkriptParser.ParseResult parseResult) {
        key = (Expression<String>) expressions[0];
        product = (Expression<StoreProduct>) expressions[1];
        return true;
    }
}
