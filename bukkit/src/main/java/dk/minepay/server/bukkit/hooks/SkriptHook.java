package dk.minepay.server.bukkit.hooks;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import dk.minepay.server.bukkit.MinePayApi;
import dk.minepay.server.bukkit.hooks.common.iHook;
import java.io.IOException;

public class SkriptHook implements iHook {
    private static SkriptAddon addon = null;

    @Override
    public void init() {
        if (!isEnabled()) {
            return;
        }

        addon = Skript.registerAddon(MinePayApi.getINSTANCE().getPlugin());
        try {
            addon.loadClasses("dk.minepay.server.bukkit.hooks", "skript");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
