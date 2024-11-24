package dk.minepay.server.bukkit;

import dk.minepay.server.bukkit.classes.*;
import dk.minepay.server.bukkit.listeners.JoinListener;
import dk.minepay.server.bukkit.managers.EventManager;
import dk.minepay.server.bukkit.managers.RequestManager;
import dk.minepay.server.bukkit.managers.SocketManager;
import java.io.File;
import java.io.IOException;
import java.util.*;
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
        loadOnline();
    }

    /** Checks if the required folders exist and creates them if they do not. */
    public void checkFolders() {
        File MinepayAPI = new File(plugin.getDataFolder().getParent(), "MinepayAPI");
        if (!MinepayAPI.exists()) {
            MinepayAPI.mkdir();
        }
        File saves = new File(MinepayAPI, "saves.yml");
        if (!saves.exists()) {
            try {
                saves.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /** Saves the online requests and votes to a file. */
    public void saveOnline() {
        File MinepayAPI = new File(plugin.getDataFolder().getParent(), "MinepayAPI");
        File saves = new File(MinepayAPI, "saves.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(saves);
        List<String> requests = new ArrayList<>();
        List<String> votes = new ArrayList<>();
        for (StoreRequest joinRequest : EventManager.requestsOnline) {
            requests.add(joinRequest.get_id());
        }
        for (Vote vote : EventManager.votesOnline) {
            votes.add(vote.get_id());
        }
        yamlConfiguration.set("requests", requests);
        yamlConfiguration.set("votes", votes);
        try {
            yamlConfiguration.save(saves);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Loads the online votes/requests from the saves.yml file. */
    public void loadOnline() {
        File MinepayAPI = new File(plugin.getDataFolder().getParent(), "MinepayAPI");
        File saves = new File(MinepayAPI, "saves.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(saves);
        List<String> requests = yamlConfiguration.getStringList("requests");
        List<String> votes = yamlConfiguration.getStringList("votes");
        for (String request : requests) {
            StoreRequest joinRequest = requestManager.getRequest(request);
            EventManager.requestsOnline.add(joinRequest);
        }
        for (String vote : votes) {
            Vote vote1 = requestManager.getVote(vote);
            EventManager.votesOnline.add(vote1);
        }
        saves.delete();
    }

    /** Disables the MinePayApi instance. */
    public void disable() {
        if (this.socketManager != null) {
            this.socketManager.getSocket().close();
        }
        checkFolders();
        saveOnline();
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
                                            RequestBody.builder()
                                                    .statuses(
                                                            Arrays.asList(
                                                                    RequestStatus.accepted,
                                                                    RequestStatus.cancelled))
                                                    .serverStatuses(
                                                            Collections.singletonList(
                                                                    RequestStatus.pending))
                                                    .build());

                            if (requests.length == 0) {
                                return;
                            }

                            for (StoreRequest request : requests) {
                                EventManager.callRequestEvent(request);
                            }

                            EventManager.calledIds.clear();
                        },
                        20L,
                        600L);
        plugin.getServer()
                .getScheduler()
                .runTaskTimerAsynchronously(
                        plugin,
                        () -> {
                            Vote[] votes =
                                    requestManager.getVotes(
                                            VoteBody.builder()
                                                    .statuses(
                                                            Collections.singletonList(
                                                                    VoteStatus.pending))
                                                    .build());

                            if (votes.length == 0) {
                                return;
                            }

                            for (Vote vote : votes) {
                                EventManager.callVoteEvent(vote);
                            }

                            EventManager.calledVoteIds.clear();
                        },
                        20L,
                        600L);
    }

    /**
     * Runs the given Runnable asynchronously.
     *
     * @param runnable the Runnable to run
     */
    public static void runAsync(Runnable runnable) {
        MinePayApi.getINSTANCE()
                .getPlugin()
                .getServer()
                .getScheduler()
                .runTaskAsynchronously(MinePayApi.getINSTANCE().getPlugin(), runnable);
    }
}
