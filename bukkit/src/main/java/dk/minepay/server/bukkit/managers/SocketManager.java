package dk.minepay.server.bukkit.managers;

import com.google.gson.Gson;
import dk.minepay.server.bukkit.MinePayApi;
import dk.minepay.server.bukkit.classes.StoreRequest;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;

public class SocketManager {
    private Socket socket;

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
                        MinePayApi.getINSTANCE().getRequestManager().callEvent(storeRequest);
                    });
            socket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
