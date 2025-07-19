package dk.minepay.hooks.skript2_9.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import dk.minepay.common.classes.StoreItem;
import dk.minepay.common.classes.StoreProduct;
import dk.minepay.common.classes.StoreRequest;
import org.bukkit.event.Event;

/** Price of product/request expression. */
public class ExprPrice extends SimplePropertyExpression<StoreItem, Double> {
    static {
        Skript.registerExpression(
                ExprPrice.class,
                Double.class,
                ExpressionType.PROPERTY,
                "[mineclub] price of %products%",
                "[mineclub] %products%'s price",
                "[mineclub] price of %requests%",
                "[mineclub] %requests%'s price");
    }

    /** Creates a new price of request expression. */
    public ExprPrice() {}

    private int pattern;
    private Expression<StoreRequest> request;
    private Expression<StoreProduct> product;

    @Override
    public Double convert(StoreItem request) {
        if (request instanceof StoreProduct) {
            return ((StoreProduct) request).getPrice();
        } else if (request instanceof StoreRequest) {
            return ((StoreRequest) request).getPrice();
        }

        return null;
    }

    @Override
    protected String getPropertyName() {
        return "price of product/request";
    }

    @Override
    public Class<? extends Double> getReturnType() {
        return Double.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if ((pattern == 0 || pattern == 1)
                && (mode == Changer.ChangeMode.SET
                        || mode == Changer.ChangeMode.REMOVE
                        || mode == Changer.ChangeMode.ADD)) {
            return CollectionUtils.array(Double.class);
        }

        return null;
    }

    @Override
    public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
        if (pattern == 0 || pattern == 1) {
            for (StoreProduct product : product.getAll(event)) {
                if (product == null || delta == null || mode == null) {
                    return;
                }

                if (mode == Changer.ChangeMode.SET) {
                    product.setPrice((double) delta[0]);
                } else if (mode == Changer.ChangeMode.ADD) {
                    product.setPrice(product.getPrice() + (double) delta[0]);
                } else if (mode == Changer.ChangeMode.REMOVE) {
                    product.setPrice(product.getPrice() - (double) delta[0]);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(
            Expression<?>[] expressions,
            int matchedPattern,
            Kleenean isDelayed,
            SkriptParser.ParseResult parseResult) {
        pattern = matchedPattern;
        if (pattern == 0 || pattern == 1) {
            product = (Expression<StoreProduct>) expressions[0];
        } else {
            request = (Expression<StoreRequest>) expressions[0];
        }
        setExpr((Expression<? extends StoreItem>) expressions[0]);
        return true;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "price of product/request";
    }
}
