package dk.minepay.server.bukkit;

import dk.minepay.server.bukkit.classes.RequestStatus;
import dk.minepay.server.bukkit.classes.StoreRequest;
import dk.minepay.server.bukkit.listeners.JoinListener;
import dk.minepay.server.bukkit.managers.RequestManager;
import dk.minepay.server.bukkit.managers.SocketManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class MinePayApi {
    @Getter private static MinePayApi INSTANCE;
    @Getter private JavaPlugin plugin;
    private SocketManager socketManager;
    @Getter private RequestManager requestManager;
    @Getter private String token;

    public static MinePayApi initApi(JavaPlugin javaPlugin) {
        MinePayApi minePayApi = new MinePayApi();
        minePayApi.init(javaPlugin);
        return minePayApi;
    }

    public void init(JavaPlugin javaPlugin) {
        INSTANCE = this;
        this.token = System.getenv("TOKEN");
        this.plugin = javaPlugin;
        this.socketManager = new SocketManager();
        this.socketManager.init();
        initRequestManager();
        plugin.getServer().getPluginManager().registerEvents(new JoinListener(), plugin);
    }

    public void initRequestManager() {
        this.requestManager = new RequestManager();
        plugin.getServer()
                .getScheduler()
                .runTaskTimerAsynchronously(
                        plugin,
                        () -> {
                            StoreRequest[] requests =
                                    requestManager.getRequests(
                                            RequestStatus.accepted, RequestStatus.pending);
                            StoreRequest[] requests2 =
                                    requestManager.getRequests(
                                            RequestStatus.cancelled, RequestStatus.pending);
                            List<StoreRequest> requestList = new ArrayList<>();
                            if (requests != null && requests.length > 0) {
                                requestList.addAll(Arrays.asList(requests));
                            }
                            if (requests2 != null && requests2.length > 0) {
                                requestList.addAll(Arrays.asList(requests2));
                            }

                            if (requestList.isEmpty()) {
                                return;
                            }

                            for (StoreRequest request : requestList) {
                                requestManager.callEvent(request);
                            }

                            requestManager.getCalledIds().clear();
                        },
                        20L,
                        600L);
    }
}
