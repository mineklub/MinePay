package dk.minepay.server.bukkit;

import dk.minepay.server.bukkit.classes.RequestStatus;
import dk.minepay.server.bukkit.classes.StoreRequest;
import dk.minepay.server.bukkit.listeners.JoinListener;
import dk.minepay.server.bukkit.managers.RequestManager;
import dk.minepay.server.bukkit.managers.SocketManager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;
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
    @Getter private HashMap<UUID, StoreRequest> joinRequests = new HashMap<>();

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

    /**
     * Checks if the MinePayApi instance is initialized.
     *
     * @return true if the instance is initialized, false otherwise
     */
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
        new JoinListener();
        initRequestManager();
        checkFolders();
        loadJoinRequests();
    }

    public void checkFolders() {
        File MinepayAPI = new File(plugin.getDataFolder().getParent(), "MinepayAPI");
        if (!MinepayAPI.exists()) {
            MinepayAPI.mkdir();
        }
        File joinRequests = new File(MinepayAPI, "joinRequests.yml");
        if (!joinRequests.exists()) {
            try {
                joinRequests.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveJoinRequests() {
        File MinepayAPI = new File(plugin.getDataFolder().getParent(), "MinepayAPI");
        File joinRequests = new File(MinepayAPI, "joinRequests.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(joinRequests);
        for (UUID uuid : this.joinRequests.keySet()) {
            yamlConfiguration.set(uuid.toString(), this.joinRequests.get(uuid).get_id());
        }
        try {
            yamlConfiguration.save(joinRequests);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadJoinRequests() {
        File MinepayAPI = new File(plugin.getDataFolder().getParent(), "MinepayAPI");
        File joinRequests = new File(MinepayAPI, "joinRequests.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(joinRequests);
        for (String key : yamlConfiguration.getKeys(false)) {
            this.joinRequests.put(UUID.fromString(key), requestManager.getRequest(yamlConfiguration.getString(key)));
        }
    }

    public void disable() {
        if (this.socketManager != null) {
            this.socketManager.getSocket().close();
        }
        if (this.joinRequests != null && !this.joinRequests.isEmpty()) {
            checkFolders();
            saveJoinRequests();
        }
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

    public static void runAsync(Runnable runnable) {
        MinePayApi.getINSTANCE().getPlugin().getServer().getScheduler().runTaskAsynchronously(MinePayApi.getINSTANCE().getPlugin(), runnable);
    }
}
