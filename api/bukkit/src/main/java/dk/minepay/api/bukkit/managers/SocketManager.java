package dk.minepay.api.bukkit.managers;

import com.google.gson.Gson;
import dk.minepay.api.bukkit.MinePayApi;
import dk.minepay.common.classes.StoreRequest;
import dk.minepay.common.classes.Vote;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import lombok.Getter;

/** Manager class for the socket connection. */
public class SocketManager {
    @Getter private Socket socket;

    /** Constructor for the SocketManager class. */
    public SocketManager() {}

    /**
     * Initializes the socket connection and sets up event listeners. Connects to the socket server
     * and handles various socket events.
     */
    public void init() {
        HashMap<String, String> auth = new HashMap<>();
        auth.put("token", MinePayApi.getINSTANCE().getToken());
        auth.put("type", "plugins");
        IO.Options options = IO.Options.builder().setAuth(auth).build();

        try {
            socket = IO.socket("https://api.mineclub.dk", options);
            socket.on(
                    Socket.EVENT_CONNECT,
                    args ->
                            MinePayApi.getINSTANCE()
                                    .getPlugin()
                                    .getLogger()
                                    .info("Socket.io connected."));
            socket.on(
                    Socket.EVENT_DISCONNECT,
                    args ->
                            MinePayApi.getINSTANCE()
                                    .getPlugin()
                                    .getLogger()
                                    .info("Socket.io disconnected."));
            socket.on(
                    Socket.EVENT_CONNECT_ERROR,
                    args ->
                            MinePayApi.getINSTANCE()
                                    .getPlugin()
                                    .getLogger()
                                    .severe(
                                            "Socket.io connection error: "
                                                    + Arrays.toString(args)));
            socket.on(
                    "storeRequest",
                    args -> {
                        StoreRequest storeRequest =
                                new Gson().fromJson(args[0].toString(), StoreRequest.class);
                        EventManager.callRequestEvent(storeRequest);
                    });
            socket.on(
                    "vote",
                    args -> {
                        Vote vote = new Gson().fromJson(args[0].toString(), Vote.class);
                        EventManager.callVoteEvent(vote);
                    });

            socket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
