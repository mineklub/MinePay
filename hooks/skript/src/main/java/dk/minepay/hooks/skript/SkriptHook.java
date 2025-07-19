package dk.minepay.hooks.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.util.Version;
import dk.minepay.api.bukkit.MinePayApi;
import dk.minepay.common.iHook;
import java.io.IOException;

/** Class that handles the integration with the Skript plugin. */
public class SkriptHook implements iHook {
    private static SkriptAddon addon = null;

    /** Constructor for the SkriptHook class. */
    public SkriptHook() {}

    /**
     * Initializes the Skript hook. Registers the addon with Skript and loads the necessary classes.
     */
    @Override
    public void init() {
        if (!isEnabled()) {
            return;
        }

        addon = Skript.registerAddon(MinePayApi.getINSTANCE().getPlugin());
        try {
            addon.loadClasses("dk.minepay.server.bukkit.hooks", "skript");
            if (addon.version.isLargerThan(new Version(2, 6))
                    || addon.version.equals(new Version(2, 6))) {
                addon.loadClasses("dk.minepay.server.bukkit.hooks", "skript.new");
            } else {
                addon.loadClasses("dk.minepay.server.bukkit.hooks", "skript.old");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the Skript hook is enabled.
     *
     * @return true if the Skript hook is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
