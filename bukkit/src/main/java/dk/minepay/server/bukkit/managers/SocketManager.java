package dk.minepay.server.bukkit.managers;

import dk.minepay.server.bukkit.MinePayApi;
import io.socket.client.IO;
import java.net.Socket;
import java.util.Collections;

public class SocketManager extends Socket {
    public void init() {
        IO.Options options =
                IO.Options.builder()
                        .setAuth(
                                Collections.singletonMap(
                                        "token", MinePayApi.getINSTANCE().getToken()))
                        .build();
    }
}
