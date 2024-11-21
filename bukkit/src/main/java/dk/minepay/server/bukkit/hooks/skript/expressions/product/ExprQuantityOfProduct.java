package dk.minepay.server.bukkit.hooks.skript.expressions.product;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import dk.minepay.server.bukkit.classes.StoreProduct;
import org.bukkit.event.Event;

/** Quantity of product expression. */
public class ExprQuantityOfProduct extends SimplePropertyExpression<StoreProduct, Double> {
    static {
        register(ExprQuantityOfProduct.class, Double.class, "quantity", "product");
    }

    /** Creates a new quantity of product expression. */
    public ExprQuantityOfProduct() {}

    @Override
    public Double convert(StoreProduct request) {
        return request.getQuantity();
    }

    @Override
    protected String getPropertyName() {
        return "quantity of product";
    }

    @Override
    public Class<? extends Double> getReturnType() {
        return Double.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET
                || mode == Changer.ChangeMode.ADD
                || mode == Changer.ChangeMode.REMOVE) {
            return CollectionUtils.array(Double.class);
        }

        return null;
    }

    @Override
    public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
        for (StoreProduct product : this.getExpr().getArray(event)) {
            if (product == null || delta == null || mode == null) {
                return;
            }

            if (mode == Changer.ChangeMode.SET) {
                product.setQuantity((double) delta[0]);
            } else if (mode == Changer.ChangeMode.ADD) {
                product.setQuantity(product.getQuantity() + (double) delta[0]);
            } else if (mode == Changer.ChangeMode.REMOVE) {
                product.setQuantity(product.getQuantity() - (double) delta[0]);
            }
        }
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "quantity of product";
    }
}
