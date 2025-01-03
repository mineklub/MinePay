package dk.minepay.server.bukkit.hooks.skript.expressions;

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

/** ID of product expression. */
public class ExprId extends SimplePropertyExpression<StoreItem, String> {
    static {
        Skript.registerExpression(
                ExprId.class,
                String.class,
                ExpressionType.PROPERTY,
                "[mineclub] id of %product%",
                "[mineclub] %product%'s id",
                "[mineclub] id of %request%",
                "[mineclub] %request%'s id");
    }

    /** Creates a new id of product expression. */
    public ExprId() {}

    private int pattern;
    private Expression<StoreRequest> request;
    private Expression<StoreProduct> product;

    @Override
    public String convert(StoreItem item) {
        if (item instanceof StoreProduct) {
            return ((StoreProduct) item).getId();
        } else if (item instanceof StoreRequest) {
            return ((StoreRequest) item).get_id();
        }

        return null;
    }

    @Override
    protected String getPropertyName() {
        return "id of product/request";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if ((pattern == 0 || pattern == 1)
                && (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE)) {
            return CollectionUtils.array(String.class);
        }

        return null;
    }

    @Override
    public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
        if (pattern == 0 || pattern == 1) {
            for (StoreProduct product : product.getAll(event)) {
                if (product == null || delta == null) {
                    return;
                }

                switch (mode) {
                    case SET:
                        product.setId((String) delta[0]);
                        break;
                    case DELETE:
                        product.setId(null);
                        break;
                    default:
                        break;
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
        return "id of product/request";
    }
}
