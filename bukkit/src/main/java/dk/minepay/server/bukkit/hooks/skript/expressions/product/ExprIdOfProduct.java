package dk.minepay.server.bukkit.hooks.skript.expressions.product;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import dk.minepay.server.bukkit.classes.StoreProduct;
import org.bukkit.event.Event;

/** ID of product expression. */
public class ExprIdOfProduct extends SimplePropertyExpression<StoreProduct, String> {
    static {
        register(ExprIdOfProduct.class, String.class, "id", "product");
    }

    /** Creates a new id of product expression. */
    public ExprIdOfProduct() {}

    @Override
    public String convert(StoreProduct request) {
        return request.getId();
    }

    @Override
    protected String getPropertyName() {
        return "id of product";
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
        for (StoreProduct product : getExpr().getArray(event)) {
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

    @Override
    public String toString(Event event, boolean debug) {
        return "id of product";
    }
}
