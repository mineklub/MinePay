package dk.minepay.server.bukkit.managers;

import dk.minepay.server.bukkit.MinePayApi;
import dk.minepay.server.bukkit.classes.RequestStatus;
import dk.minepay.server.bukkit.classes.StoreRequest;
import dk.minepay.server.bukkit.classes.Vote;
import dk.minepay.server.bukkit.events.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/** Manager for handling events. */
@Getter
public class EventManager {
    /** The set of called store request IDs. */
    public static HashSet<String> calledIds = new HashSet<>();

    /** The set of called vote IDs. */
    public static HashSet<String> calledVoteIds = new HashSet<>();

    /** The list of store requests that are online. */
    public static List<StoreRequest> requestsOnline = new ArrayList<>();

    /** The list of votes that are online. */
    public static List<Vote> votesOnline = new ArrayList<>();

    /** Constructor for EventManager. */
    public EventManager() {}

    /**
     * Calls the appropriate event for the given store request.
     *
     * @param storeRequest the store request for which to call the event
     */
    public static void callRequestEvent(StoreRequest storeRequest) {
        if (calledIds.contains(storeRequest.get_id())) {
            return;
        }

        OfflinePlayer player =
                MinePayApi.getINSTANCE()
                        .getPlugin()
                        .getServer()
                        .getOfflinePlayer(storeRequest.getUuid());
        calledIds.add(storeRequest.get_id());
        if (player.isOnline()) {
            callOnlineRequestEvent(storeRequest);
        } else {
            requestsOnline.add(storeRequest);
        }
        if (storeRequest.getStatus().equals(RequestStatus.accepted)) {
            StoreRequestAcceptEvent event = new StoreRequestAcceptEvent(storeRequest, player);
            MinePayApi.runAsync(
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
            MinePayApi.runAsync(
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

    /**
     * Calls the appropriate vote event for the given vote.
     *
     * @param vote the vote for which to call the event
     */
    public static void callVoteEvent(Vote vote) {
        if (calledVoteIds.contains(vote.get_id())) {
            return;
        }

        OfflinePlayer player =
                MinePayApi.getINSTANCE().getPlugin().getServer().getOfflinePlayer(vote.getUuid());
        calledVoteIds.add(vote.get_id());
        if (player.isOnline()) {
            callOnlineVoteEvent(vote);
        } else {
            votesOnline.add(vote);
        }

        VoteEvent event = new VoteEvent(vote, player);
        MinePayApi.runAsync(
                () -> {
                    MinePayApi.getINSTANCE()
                            .getPlugin()
                            .getServer()
                            .getPluginManager()
                            .callEvent(event);
                    calledVoteIds.remove(vote.get_id());
                });
    }

    /**
     * Calls the appropriate online vote event for the given vote.
     *
     * @param vote the vote for which to call the online event
     */
    public static void callOnlineVoteEvent(Vote vote) {
        Player player = MinePayApi.getINSTANCE().getPlugin().getServer().getPlayer(vote.getUuid());

        VoteOnlineEvent event = new VoteOnlineEvent(vote, player);
        MinePayApi.runAsync(
                () ->
                        MinePayApi.getINSTANCE()
                                .getPlugin()
                                .getServer()
                                .getPluginManager()
                                .callEvent(event));

        votesOnline.remove(vote);
    }

    /**
     * Calls the appropriate join event for the given store request.
     *
     * @param storeRequest the store request for which to call the join event
     */
    public static void callOnlineRequestEvent(StoreRequest storeRequest) {
        Player player =
                MinePayApi.getINSTANCE().getPlugin().getServer().getPlayer(storeRequest.getUuid());
        if (storeRequest.getStatus().equals(RequestStatus.accepted)) {
            StoreRequestAcceptOnlineEvent event =
                    new StoreRequestAcceptOnlineEvent(storeRequest, player);
            MinePayApi.runAsync(
                    () ->
                            MinePayApi.getINSTANCE()
                                    .getPlugin()
                                    .getServer()
                                    .getPluginManager()
                                    .callEvent(event));
        } else if (storeRequest.getStatus().equals(RequestStatus.cancelled)) {
            StoreRequestCancelOnlineEvent event =
                    new StoreRequestCancelOnlineEvent(storeRequest, player);
            MinePayApi.runAsync(
                    () ->
                            MinePayApi.getINSTANCE()
                                    .getPlugin()
                                    .getServer()
                                    .getPluginManager()
                                    .callEvent(event));
        }

        requestsOnline.remove(storeRequest);
    }

    /**
     * Gets the store requests that are online for the given player.
     *
     * @param player the player for which to get the store requests
     * @return the store requests that are online for the given player
     */
    public static List<StoreRequest> getRequestsOnlineByUUID(UUID player) {
        List<StoreRequest> requests = new ArrayList<>();
        for (StoreRequest request : requestsOnline) {
            if (request.getUuid().equals(player)) {
                requests.add(request);
            }
        }
        return requests;
    }

    /**
     * Gets the votes that are online for the given player.
     *
     * @param player the player for which to get the votes
     * @return the votes that are online for the given player
     */
    public static List<Vote> getVotesOnlineByUUID(UUID player) {
        List<Vote> votes = new ArrayList<>();
        for (Vote vote : votesOnline) {
            if (vote.getUuid().equals(player)) {
                votes.add(vote);
            }
        }
        return votes;
    }
}
