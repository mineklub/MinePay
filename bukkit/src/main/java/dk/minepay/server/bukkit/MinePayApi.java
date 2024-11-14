package dk.minepay.server.bukkit;

import dk.minepay.server.bukkit.managers.SocketManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class MinePayApi {
    @Getter private static final MinePayApi INSTANCE = new MinePayApi();
    @Getter private JavaPlugin plugin;
    private final SocketManager socketManager = new SocketManager();
    @Getter private String token;

    public static void initApi(JavaPlugin javaPlugin) {
        INSTANCE.init(javaPlugin);
    }

    public void init(JavaPlugin javaPlugin) {
        this.plugin = javaPlugin;
    }
}
