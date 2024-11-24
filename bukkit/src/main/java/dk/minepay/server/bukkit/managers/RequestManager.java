package dk.minepay.server.bukkit.managers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dk.minepay.server.bukkit.MinePayApi;
import dk.minepay.server.bukkit.classes.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/** Manager class for handling store requests. */
@Getter
public class RequestManager {
    private final OkHttpClient client =
            new OkHttpClient()
                    .newBuilder()
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build();
    private final String requestUrl = "https://api.mineclub.dk/v1/plugin/request/";
    private final String voteUrl = "https://api.mineclub.dk/v1/plugin/vote/";

    /** Constructor for the RequestManager class. */
    public RequestManager() {}

    /**
     * Creates a new store request for the given player UUID and store products.
     *
     * @param uuid the UUID of the player
     * @param storeProducts the store products to include in the request
     * @return a JsonObject representing the response from the store API
     */
    public JsonObject createRequest(UUID uuid, StoreProduct[] storeProducts) {
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

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(requestUrl)).newBuilder();

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

    /**
     * Retrieves store requests with the given status and server status.
     *
     * @param status the status of the requests to retrieve
     * @param serverStatus the server status of the requests to retrieve
     * @return an array of StoreRequest objects representing the retrieved requests
     */
    public StoreRequest[] getRequests(
            List<RequestStatus> status, List<RequestStatus> serverStatus) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(requestUrl)).newBuilder();

        for (RequestStatus requestStatus : status) {
            urlBuilder.addQueryParameter("status", requestStatus.toString());
        }

        for (RequestStatus requestStatus : serverStatus) {
            urlBuilder.addQueryParameter("serverStatus", requestStatus.toString());
        }

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

    /**
     * Retrieves store requests with the given status.
     *
     * @param status the status of the requests to retrieve
     * @return an array of StoreRequest objects representing the retrieved requests
     */
    public StoreRequest[] getRequests(List<RequestStatus> status) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(requestUrl)).newBuilder();

        for (RequestStatus requestStatus : status) {
            urlBuilder.addQueryParameter("status", requestStatus.toString());
        }

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

    /**
     * Retrieves all store requests.
     *
     * @return an array of StoreRequest objects representing the retrieved requests
     */
    public StoreRequest[] getRequests() {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(requestUrl)).newBuilder();

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

    /**
     * Retrieves store requests with the given server status.
     *
     * @param requestStatuses the server status of the requests to retrieve
     * @return an array of StoreRequest objects representing the retrieved requests
     */
    public StoreRequest[] getRequestsWithServerStatus(List<RequestStatus> requestStatuses) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(requestUrl)).newBuilder();

        for (RequestStatus requestStatus : requestStatuses) {
            urlBuilder.addQueryParameter("serverStatus", requestStatus.toString());
        }

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

    /**
     * Retrieves a store request with the given request ID.
     *
     * @param requestId the ID of the request to retrieve
     * @return a StoreRequest object representing the retrieved request
     */
    public StoreRequest getRequest(String requestId) {
        HttpUrl.Builder urlBuilder =
                Objects.requireNonNull(HttpUrl.parse(requestUrl + requestId)).newBuilder();

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
            return new Gson().fromJson(jsonObject.get("data"), StoreRequest.class);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Accepts a store request with the given request ID.
     *
     * @param requestId the ID of the request to accept
     * @return a JsonObject representing the response from the store API
     */
    public JsonObject acceptRequest(String requestId) {
        RequestBody requestBody;
        MediaType mediaType = MediaType.parse("application/json");
        JsonObject jsonObject = new JsonObject();
        requestBody = RequestBody.create(mediaType, jsonObject.toString());

        HttpUrl.Builder urlBuilder =
                Objects.requireNonNull(HttpUrl.parse(requestUrl + requestId + "/accept"))
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

    /**
     * Cancels a store request with the given request ID.
     *
     * @param requestId the ID of the request to cancel
     * @return a JsonObject representing the response from the store API
     */
    public JsonObject cancelRequest(String requestId) {
        RequestBody requestBody;
        MediaType mediaType = MediaType.parse("application/json");
        JsonObject jsonObject = new JsonObject();
        requestBody = RequestBody.create(mediaType, jsonObject.toString());

        HttpUrl.Builder urlBuilder =
                Objects.requireNonNull(HttpUrl.parse(requestUrl + requestId + "/cancel"))
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

    /**
     * Retrieves votes with the given vote statuses.
     *
     * @param voteStatuses the vote statuses of the votes to retrieve
     * @return an array of Vote objects representing the retrieved votes
     */
    public Vote[] getVotes(List<VoteStatus> voteStatuses) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(voteUrl)).newBuilder();

        for (VoteStatus voteStatus : voteStatuses) {
            urlBuilder.addQueryParameter("status", voteStatus.toString());
        }

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
            return new Gson().fromJson(jsonObject.get("data"), Vote[].class);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Retrieves votes
     *
     * @return an array of Vote objects representing the retrieved votes
     */
    public Vote[] getVotes() {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(voteUrl)).newBuilder();

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
            return new Gson().fromJson(jsonObject.get("data"), Vote[].class);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Retrieves a vote with the given vote ID.
     *
     * @param voteId the ID of the vote to retrieve
     * @return a Vote object representing the retrieved vote
     */
    public Vote getVote(String voteId) {
        HttpUrl.Builder urlBuilder =
                Objects.requireNonNull(HttpUrl.parse(voteUrl + voteId)).newBuilder();

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
            return new Gson().fromJson(jsonObject.get("data"), Vote.class);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Accepts a vote with the given vote ID.
     *
     * @param voteId the ID of the vote to accept
     * @return a JsonObject representing the response from the vote API
     */
    public JsonObject acceptVote(String voteId) {
        RequestBody requestBody;
        MediaType mediaType = MediaType.parse("application/json");
        JsonObject jsonObject = new JsonObject();
        requestBody = RequestBody.create(mediaType, jsonObject.toString());

        HttpUrl.Builder urlBuilder =
                Objects.requireNonNull(HttpUrl.parse(voteUrl + voteId + "/accept")).newBuilder();

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
}
