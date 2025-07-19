package dk.minepay.hooks.skript2_10.expressions.product;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import dk.minepay.common.classes.StoreProduct;
import java.util.HashMap;
import org.bukkit.event.Event;

/** Metadata value of product expression. */
public class ExprMetadataValueOfProduct extends SimpleExpression<String> {
    static {
        Skript.registerExpression(
                ExprMetadataValueOfProduct.class,
                String.class,
                ExpressionType.COMBINED,
                "metadata-value %string% of %product%");
    }

    /** Creates a new metadata value of product expression. */
    public ExprMetadataValueOfProduct() {}

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
        return "metadata-value of product";
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

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE) {
            return CollectionUtils.array(String.class);
        }
        return null;
    }

    @Override
    public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
        StoreProduct product = this.product.getSingle(event);
        if (product == null || delta == null) {
            return;
        }

        switch (mode) {
            case SET:
                if (product.getMetadata() == null) {
                    product.setMetadata(new HashMap<>());
                }
                product.getMetadata().put(key.getSingle(event), (String) delta[0]);
                break;
            case DELETE:
                if (product.getMetadata() == null) {
                    product.setMetadata(new HashMap<>());
                }
                product.getMetadata().remove(key.getSingle(event));
                break;
            default:
                break;
        }
    }
}
