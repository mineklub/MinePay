package dk.minepay.api.bukkit;

import dk.minepay.api.bukkit.listeners.JoinListener;
import dk.minepay.api.bukkit.managers.EventManager;
import dk.minepay.api.bukkit.managers.SocketManager;
import dk.minepay.common.IMinePayApi;
import dk.minepay.common.classes.RequestBody;
import dk.minepay.common.classes.RequestStatus;
import dk.minepay.common.classes.StoreRequest;
import dk.minepay.common.classes.Vote;
import dk.minepay.common.classes.VoteBody;
import dk.minepay.common.classes.VoteStatus;
import dk.minepay.common.managers.RequestManager;
import java.io.File;
import java.io.IOException;
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

/**
 * Main API class for the MinePay plugin. This class handles the initialization and management of
 * the plugin's components.
 */
public class MinePayApi implements IMinePayApi {
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
        YamlConfigurationLoader loader = YamlConfigurationLoader.builder().file(saves).build();
        List<String> requests = new ArrayList<>();
        List<String> votes = new ArrayList<>();
        for (StoreRequest joinRequest : EventManager.requestsOnline) {
            requests.add(joinRequest.get_id());
        }
        for (Vote vote : EventManager.votesOnline) {
            votes.add(vote.get_id());
        }
        ConfigurationNode node;
        try {
            node = loader.load();
            node.node("requests").setList(String.class, requests);
            node.node("votes").setList(String.class, votes);
            loader.save(node);
        } catch (ConfigurateException e) {
            getPlugin().getLogger().severe("Failed to save online requests and votes.");
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
    }

    /** Loads the online votes/requests from the saves.yml file. */
    public void loadOnline() {
        File MinepayAPI = new File(plugin.getDataFolder().getParent(), "MinepayAPI");
        File saves = new File(MinepayAPI, "saves.yml");
        if (!saves.exists()) {
            return;
        }

        YamlConfigurationLoader loader = YamlConfigurationLoader.builder().file(saves).build();
        try {
            ConfigurationNode node = loader.load();
            List<String> requests = node.node("requests").getList(String.class);
            List<String> votes = node.node("votes").getList(String.class);
            if (requests == null || votes == null) {
                return;
            }
            for (String request : requests) {
                StoreRequest joinRequest = requestManager.getRequest(request);
                EventManager.requestsOnline.add(joinRequest);
            }
            for (String vote : votes) {
                Vote vote1 = requestManager.getVote(vote);
                EventManager.votesOnline.add(vote1);
            }
            saves.delete();
        } catch (ConfigurateException e) {
            getPlugin().getLogger().severe("Failed to load online requests and votes.");
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
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
        this.requestManager = new RequestManager(this);
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
