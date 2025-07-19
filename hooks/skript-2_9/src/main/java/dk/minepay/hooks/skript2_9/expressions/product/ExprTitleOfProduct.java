package dk.minepay.hooks.skript2_9.expressions.product;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import dk.minepay.common.classes.StoreProduct;
import org.bukkit.event.Event;

/** Title of product expression. */
public class ExprTitleOfProduct extends SimplePropertyExpression<StoreProduct, String> {
    static {
        register(ExprTitleOfProduct.class, String.class, "[mineclub] title", "product");
    }

    /** Creates a new title of product expression. */
    public ExprTitleOfProduct() {}

    @Override
    public String convert(StoreProduct request) {
        return request.getName();
    }

    @Override
    protected String getPropertyName() {
        return "title of product";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
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
        for (StoreProduct product : this.getExpr().getArray(event)) {
            if (product == null || delta == null) {
                return;
            }

            switch (mode) {
                case SET:
                    product.setName((String) delta[0]);
                    break;
                case DELETE:
                    product.setName(null);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "title of product";
    }
}
