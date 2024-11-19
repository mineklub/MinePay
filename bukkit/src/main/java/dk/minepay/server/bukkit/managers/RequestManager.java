package dk.minepay.server.bukkit.managers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dk.minepay.server.bukkit.MinePayApi;
import dk.minepay.server.bukkit.classes.RequestStatus;
import dk.minepay.server.bukkit.classes.StoreProduct;
import dk.minepay.server.bukkit.classes.StoreRequest;
import dk.minepay.server.bukkit.events.StoreRequestAcceptEvent;
import dk.minepay.server.bukkit.events.StoreRequestAcceptJoinEvent;
import dk.minepay.server.bukkit.events.StoreRequestCancelEvent;
import dk.minepay.server.bukkit.events.StoreRequestCancelJoinEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.*;
import lombok.Getter;
import okhttp3.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@Getter
public class RequestManager {
    private final OkHttpClient client =
            new OkHttpClient()
                    .newBuilder()
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build();

    @Getter private HashSet<String> calledIds = new HashSet<>();
    @Getter private HashMap<UUID, StoreRequest> joinRequests = new HashMap<>();

    public JsonObject createRequest(UUID uuid, StoreProduct... storeProducts) {
        RequestBody requestBody;
        MediaType mediaType = MediaType.parse("application/json");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uuid", uuid.toString());
        JsonArray jsonArray = new JsonArray();
        for (StoreProduct storeProduct : storeProducts) {
            jsonArray.add(storeProduct.toJson());
        }
        jsonObject.add("products", jsonArray);
        requestBody = RequestBody.create(mediaType, jsonObject.toString());

        HttpUrl.Builder urlBuilder =
                Objects.requireNonNull(HttpUrl.parse("https://api.mineclub.dk/v1/store/"))
                        .newBuilder();

        Request request =
                new Request.Builder()
                        .url(urlBuilder.build())
                        .method("POST", requestBody)
                        .header("Authorization", "Bearer " + MinePayApi.getINSTANCE().getToken())
                        .build();

        Response response;
        // Execute the request and retrieve the response in thread-safe manner
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Callable<Response> callable = () -> client.newCall(request).execute();

            Future<Response> future = executor.submit(callable);
            response = future.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }

        try {
            assert response.body() != null;
            return new Gson().fromJson(response.body().string(), JsonObject.class);
        } catch (IOException e) {
            return null;
        }
    }

    public StoreRequest[] getRequests(RequestStatus status, RequestStatus serverStatus) {
        HttpUrl.Builder urlBuilder =
                Objects.requireNonNull(HttpUrl.parse("https://api.mineclub.dk/v1/store/"))
                        .newBuilder()
                        .addQueryParameter("status", status.toString())
                        .addQueryParameter("serverStatus", serverStatus.toString());

        Request request =
                new Request.Builder()
                        .url(urlBuilder.build())
                        .method("GET", null)
                        .header("Authorization", "Bearer " + MinePayApi.getINSTANCE().getToken())
                        .build();

        Response response;
        // Execute the request and retrieve the response in thread-safe manner
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Callable<Response> callable = () -> client.newCall(request).execute();

            Future<Response> future = executor.submit(callable);
            response = future.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }

        try {
            assert response.body() != null;
            JsonObject jsonObject = new Gson().fromJson(response.body().string(), JsonObject.class);
            return new Gson().fromJson(jsonObject.get("data"), StoreRequest[].class);
        } catch (IOException e) {
            return null;
        }
    }

    public JsonObject acceptRequest(String requestId) {
        RequestBody requestBody;
        MediaType mediaType = MediaType.parse("application/json");
        JsonObject jsonObject = new JsonObject();
        requestBody = RequestBody.create(mediaType, jsonObject.toString());

        HttpUrl.Builder urlBuilder =
                Objects.requireNonNull(
                                HttpUrl.parse(
                                        "https://api.mineclub.dk/v1/store/"
                                                + requestId
                                                + "/accept"))
                        .newBuilder();

        Request request =
                new Request.Builder()
                        .url(urlBuilder.build())
                        .method("POST", requestBody)
                        .header("Authorization", "Bearer " + MinePayApi.getINSTANCE().getToken())
                        .build();

        Response response;
        // Execute the request and retrieve the response in thread-safe manner
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Callable<Response> callable = () -> client.newCall(request).execute();

            Future<Response> future = executor.submit(callable);
            response = future.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }

        try {
            assert response.body() != null;
            return new Gson().fromJson(response.body().string(), JsonObject.class);
        } catch (IOException e) {
            return null;
        }
    }

    public JsonObject cancelRequest(String requestId) {
        RequestBody requestBody;
        MediaType mediaType = MediaType.parse("application/json");
        JsonObject jsonObject = new JsonObject();
        requestBody = RequestBody.create(mediaType, jsonObject.toString());

        HttpUrl.Builder urlBuilder =
                Objects.requireNonNull(
                                HttpUrl.parse(
                                        "https://api.mineclub.dk/v1/store/"
                                                + requestId
                                                + "/cancel"))
                        .newBuilder();

        Request request =
                new Request.Builder()
                        .url(urlBuilder.build())
                        .method("POST", requestBody)
                        .header("Authorization", "Bearer " + MinePayApi.getINSTANCE().getToken())
                        .build();

        Response response;
        // Execute the request and retrieve the response in thread-safe manner
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Callable<Response> callable = () -> client.newCall(request).execute();

            Future<Response> future = executor.submit(callable);
            response = future.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }

        try {
            assert response.body() != null;
            return new Gson().fromJson(response.body().string(), JsonObject.class);
        } catch (IOException e) {
            return null;
        }
    }

    public void callEvent(StoreRequest storeRequest) {
        if (calledIds.contains(storeRequest.get_id())) {
            return;
        }

        OfflinePlayer player =
                MinePayApi.getINSTANCE()
                        .getPlugin()
                        .getServer()
                        .getOfflinePlayer(storeRequest.getUuid());
        calledIds.add(storeRequest.get_id());
        System.out.println("StoreRequest: " + storeRequest.getStatus());
        joinRequests.put(player.getUniqueId(), storeRequest);
        if (storeRequest.getStatus().equals(RequestStatus.accepted)) {
            StoreRequestAcceptEvent event = new StoreRequestAcceptEvent(storeRequest, player);
            MinePayApi.getINSTANCE()
                    .getPlugin()
                    .getServer()
                    .getScheduler()
                    .runTaskAsynchronously(
                            MinePayApi.getINSTANCE().getPlugin(),
                            () -> {
                                MinePayApi.getINSTANCE()
                                        .getPlugin()
                                        .getServer()
                                        .getPluginManager()
                                        .callEvent(event);
                                calledIds.remove(storeRequest.get_id());
                            });
        } else if (storeRequest.getStatus().equals(RequestStatus.cancelled)) {
            StoreRequestCancelEvent event = new StoreRequestCancelEvent(storeRequest, player);
            MinePayApi.getINSTANCE()
                    .getPlugin()
                    .getServer()
                    .getScheduler()
                    .runTaskAsynchronously(
                            MinePayApi.getINSTANCE().getPlugin(),
                            () -> {
                                MinePayApi.getINSTANCE()
                                        .getPlugin()
                                        .getServer()
                                        .getPluginManager()
                                        .callEvent(event);
                                calledIds.remove(storeRequest.get_id());
                            });
        }
    }

    public void callJoinEvent(StoreRequest storeRequest) {
        Player player =
                MinePayApi.getINSTANCE().getPlugin().getServer().getPlayer(storeRequest.getUuid());
        if (storeRequest.getStatus().equals(RequestStatus.accepted)) {
            StoreRequestAcceptJoinEvent event =
                    new StoreRequestAcceptJoinEvent(storeRequest, player);
            MinePayApi.getINSTANCE()
                    .getPlugin()
                    .getServer()
                    .getScheduler()
                    .runTaskAsynchronously(
                            MinePayApi.getINSTANCE().getPlugin(),
                            () ->
                                    MinePayApi.getINSTANCE()
                                            .getPlugin()
                                            .getServer()
                                            .getPluginManager()
                                            .callEvent(event));
        } else if (storeRequest.getStatus().equals(RequestStatus.cancelled)) {
            StoreRequestCancelJoinEvent event =
                    new StoreRequestCancelJoinEvent(storeRequest, player);
            MinePayApi.getINSTANCE()
                    .getPlugin()
                    .getServer()
                    .getScheduler()
                    .runTaskAsynchronously(
                            MinePayApi.getINSTANCE().getPlugin(),
                            () ->
                                    MinePayApi.getINSTANCE()
                                            .getPlugin()
                                            .getServer()
                                            .getPluginManager()
                                            .callEvent(event));
        }

        joinRequests.remove(player.getUniqueId());
    }
}
