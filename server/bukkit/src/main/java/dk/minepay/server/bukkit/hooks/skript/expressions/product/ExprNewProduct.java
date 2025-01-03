package dk.minepay.server.bukkit.hooks.skript.expressions.product;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.avaje.ebean.validation.NotNull;
import dk.minepay.common.classes.StoreProduct;
import org.bukkit.event.Event;

/** The type Expr new product. */
public class ExprNewProduct extends SimpleExpression<StoreProduct> {
    static {
        Skript.registerExpression(
                ExprNewProduct.class, StoreProduct.class, ExpressionType.COMBINED, "new product");
    }

    /** Instantiates a new Expr new product. */
    public ExprNewProduct() {}

    @Override
    public @NotNull Class<? extends StoreProduct> getReturnType() {
        return StoreProduct.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public boolean init(
            Expression<?>[] expressions,
            int matchedPattern,
            Kleenean isDelayed,
            SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public @NotNull String toString(Event e, boolean arg1) {
        return "new product";
    }

    @Override
    protected StoreProduct[] get(Event event) {
        return new StoreProduct[] {new StoreProduct()};
    }
}
