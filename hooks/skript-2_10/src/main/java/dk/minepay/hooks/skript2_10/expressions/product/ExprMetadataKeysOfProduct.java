package dk.minepay.hooks.skript2_10.expressions.product;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.minepay.common.classes.StoreProduct;
import org.bukkit.event.Event;

/** The type Expr metadata keys of product. */
public class ExprMetadataKeysOfProduct extends SimpleExpression<String> {
    static {
        Skript.registerExpression(
                ExprMetadataKeysOfProduct.class,
                String.class,
                ExpressionType.SIMPLE,
                "metadata-keys of %product%");
    }

    /** Instantiates a new Expr metadata keys of product. */
    public ExprMetadataKeysOfProduct() {}

    private Expression<StoreProduct> product;

    @Override
    protected String[] get(Event e) {
        StoreProduct product = this.product.getSingle(e);
        if (product == null || product.getMetadata() == null) {
            return new String[0];
        }

        return product.getMetadata().keySet().toArray(new String[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "products of request";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(
            Expression<?>[] expressions,
            int matchedPattern,
            Kleenean isDelayed,
            SkriptParser.ParseResult parseResult) {
        product = (Expression<StoreProduct>) expressions[0];
        return true;
    }
}
