package dk.minepay.server.bukkit;

import dk.minepay.server.bukkit.classes.RequestStatus;
import dk.minepay.server.bukkit.classes.StoreRequest;
import dk.minepay.server.bukkit.listeners.JoinListener;
import dk.minepay.server.bukkit.managers.RequestManager;
import dk.minepay.server.bukkit.managers.SocketManager;
import java.util.Arrays;
import java.util.Collections;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main API class for the MinePay plugin. This class handles the initialization and management of
 * the plugin's components.
 */
public class MinePayApi {
    @Getter private static final MinePayApi INSTANCE = new MinePayApi();
    @Getter private JavaPlugin plugin;
    private SocketManager socketManager;
    @Getter private RequestManager requestManager;
    @Getter @Setter private String token;

    /** Constructor for the MinePayApi class. */
    public MinePayApi() {}

    /**
     * Initializes the MinePay API with the given JavaPlugin instance.
     *
     * @param javaPlugin the JavaPlugin instance
     * @return the initialized MinePayApi instance
     */
    public static MinePayApi initApi(JavaPlugin javaPlugin) {
        MinePayApi.getINSTANCE().init(javaPlugin);
        return MinePayApi.getINSTANCE();
    }

    public boolean isInitialized() {
        return this.plugin != null;
    }

    /**
     * Initializes the MinePayApi instance with the given JavaPlugin.
     *
     * @param javaPlugin the JavaPlugin instance
     */
    public void init(JavaPlugin javaPlugin) {
        if (isInitialized()) {
            return;
        }

        javaPlugin.getLogger().info("Initializing MinePayApi");
        this.token = System.getenv("TOKEN");
        this.plugin = javaPlugin;
        this.socketManager = new SocketManager();
        this.socketManager.init();
        initRequestManager();
        new JoinListener();
    }

    /** Initializes the RequestManager and schedules a task to process requests periodically. */
    public void initRequestManager() {
        this.requestManager = new RequestManager();
        plugin.getServer()
                .getScheduler()
                .runTaskTimerAsynchronously(
                        plugin,
                        () -> {
                            StoreRequest[] requests =
                                    requestManager.getRequests(
                                            Arrays.asList(
                                                    RequestStatus.accepted,
                                                    RequestStatus.cancelled),
                                            Collections.singletonList(RequestStatus.pending));

                            if (requests.length == 0) {
                                return;
                            }

                            for (StoreRequest request : requests) {
                                requestManager.callEvent(request);
                            }

                            requestManager.getCalledIds().clear();
                        },
                        20L,
                        600L);
    }
}
