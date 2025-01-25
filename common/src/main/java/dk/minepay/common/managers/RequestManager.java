package dk.minepay.common.managers;

import com.google.gson.*;
import dk.minepay.common.IMinePayApi;
import dk.minepay.common.classes.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import lombok.Getter;
import okhttp3.*;
import okhttp3.RequestBody;
import org.jspecify.annotations.Nullable;

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
    private final IMinePayApi MinePayApi;

    /** Constructor for the RequestManager class. */
    public RequestManager(IMinePayApi MinePayApi) {
        this.MinePayApi = MinePayApi;
    }

    /**
     * Creates a new store request for the given player UUID and store products.
     *
     * @param uuid the UUID of the player
     * @param storeProducts the store products to include in the request
     * @return a JsonObject representing the response from the store API
     */
    public @Nullable JsonObject createRequest(UUID uuid, StoreProduct[] storeProducts) {
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
                        .header("Authorization", "Bearer " + MinePayApi.getToken())
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
            if (response.body() == null) {
                return null;
            }
            return new Gson().fromJson(response.body().string(), JsonObject.class);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Retrieves store requests with the given request statuses.
     *
     * @param body the request body containing the request statuses
     * @return an array of StoreRequest objects representing the retrieved requests
     */
    public StoreRequest[] getRequests(dk.minepay.common.classes.RequestBody body) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(requestUrl)).newBuilder();

        for (RequestStatus requestStatus : body.getStatuses()) {
            urlBuilder.addQueryParameter("status", requestStatus.toString());
        }

        for (RequestStatus requestStatus : body.getServerStatuses()) {
            urlBuilder.addQueryParameter("serverStatus", requestStatus.toString());
        }

        Request request =
                new Request.Builder()
                        .url(urlBuilder.build())
                        .method("GET", null)
                        .header("Authorization", "Bearer " + MinePayApi.getToken())
                        .build();

        Response response;
        // Execute the request and retrieve the response in thread-safe manner
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Callable<Response> callable = () -> client.newCall(request).execute();

            Future<Response> future = executor.submit(callable);
            response = future.get();
        } catch (InterruptedException | ExecutionException e) {
            return new StoreRequest[0];
        }

        try {
            if (response.body() == null) {
                return new StoreRequest[0];
            }
            JsonObject jsonObject = new Gson().fromJson(response.body().string(), JsonObject.class);
            StoreRequest[] requests = new Gson().fromJson(jsonObject.get("data"), StoreRequest[].class);
            if (requests == null) {
                return new StoreRequest[0];
            }
            return requests;
        } catch (IOException | JsonSyntaxException e) {
            return new StoreRequest[0];
        }
    }

    /**
     * Retrieves a store request with the given request ID.
     *
     * @param requestId the ID of the request to retrieve
     * @return a StoreRequest object representing the retrieved request
     */
    public @Nullable StoreRequest getRequest(String requestId) {
        HttpUrl.Builder urlBuilder =
                Objects.requireNonNull(HttpUrl.parse(requestUrl + requestId)).newBuilder();

        Request request =
                new Request.Builder()
                        .url(urlBuilder.build())
                        .method("GET", null)
                        .header("Authorization", "Bearer " + MinePayApi.getToken())
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
            if (response.body() == null) {
                return null;
            }
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
    public @Nullable JsonObject acceptRequest(String requestId) {
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
                        .header("Authorization", "Bearer " + MinePayApi.getToken())
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
            if (response.body() == null) {
                return null;
            }
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
    public @Nullable JsonObject cancelRequest(String requestId) {
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
                        .header("Authorization", "Bearer " + MinePayApi.getToken())
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
            if (response.body() == null) {
                return null;
            }
            return new Gson().fromJson(response.body().string(), JsonObject.class);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Retrieves votes with the given vote statuses.
     *
     * @param body the vote body containing the vote statuses
     * @return an array of Vote objects representing the retrieved votes
     */
    public Vote[] getVotes(VoteBody body) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(voteUrl)).newBuilder();

        for (VoteStatus voteStatus : body.getStatuses()) {
            urlBuilder.addQueryParameter("status", voteStatus.toString());
        }

        if (body.getMcaccount() != null) {
            urlBuilder.addQueryParameter("mcaccount", body.getMcaccount().toString());
        }

        Request request =
                new Request.Builder()
                        .url(urlBuilder.build())
                        .method("GET", null)
                        .header("Authorization", "Bearer " + MinePayApi.getToken())
                        .build();

        Response response;
        // Execute the request and retrieve the response in thread-safe manner
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Callable<Response> callable = () -> client.newCall(request).execute();

            Future<Response> future = executor.submit(callable);
            response = future.get();
        } catch (InterruptedException | ExecutionException e) {
            return new Vote[0];
        }

        try {
            if (response.body() == null) {
                return new Vote[0];
            }
            JsonObject jsonObject = new Gson().fromJson(response.body().string(), JsonObject.class);
            Vote[] votes = new Gson().fromJson(jsonObject.get("data"), Vote[].class);
            if (votes == null) {
                return new Vote[0];
            }
            return votes;
        } catch (IOException | JsonSyntaxException e) {
            return new Vote[0];
        }
    }

    /**
     * Retrieves a vote with the given vote ID.
     *
     * @param voteId the ID of the vote to retrieve
     * @return a Vote object representing the retrieved vote
     */
    public @Nullable Vote getVote(String voteId) {
        HttpUrl.Builder urlBuilder =
                Objects.requireNonNull(HttpUrl.parse(voteUrl + voteId)).newBuilder();

        Request request =
                new Request.Builder()
                        .url(urlBuilder.build())
                        .method("GET", null)
                        .header("Authorization", "Bearer " + MinePayApi.getToken())
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
            if (response.body() == null) {
                return null;
            }
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
    public @Nullable JsonObject acceptVote(String voteId) {
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
                        .header("Authorization", "Bearer " + MinePayApi.getToken())
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
            if (response.body() == null) {
                return null;
            }
            return new Gson().fromJson(response.body().string(), JsonObject.class);
        } catch (IOException e) {
            return null;
        }
    }
}
