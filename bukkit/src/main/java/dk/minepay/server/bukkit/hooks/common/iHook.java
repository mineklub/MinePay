package dk.minepay.server.bukkit.hooks.common;

/**
 * This interface defines the contract for a hook in the Bukkit server. Implementations of this
 * interface should provide initialization logic and a method to check if the hook is enabled.
 */
public interface iHook {
    /**
     * Initializes the hook. This method should contain any setup logic required for the hook to
     * function properly.
     */
    void init();

    /**
     * Checks if the hook is enabled.
     *
     * @return true if the hook is enabled, false otherwise.
     */
    boolean isEnabled();
}
