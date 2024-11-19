package dk.mineclub.testplugin.listeners;

import com.google.gson.JsonObject;
import dk.minepay.server.bukkit.MinePayApi;
import dk.minepay.server.bukkit.classes.StoreProduct;
import dk.minepay.server.bukkit.events.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StoreRequestListener implements Listener {
    @EventHandler
    public void onStoreRequestAccept(StoreRequestAcceptEvent event) {
        System.out.println("StoreRequestAcceptEvent");
        StringBuilder message =
                new StringBuilder("§2[MinePay] " + event.getPlayer().getName() + " har købt: ");
        boolean refund = false;
        for (StoreProduct product : event.getRequest().getProducts()) {
            if (product.getId().equals("MAN417")) {
                refund = true;
            }
            message.append(product.getName()).append(", ");
        }

        message.append("for ").append(event.getRequest().getPrice()).append(" mønter.");
        if (refund) {
            Bukkit.getScheduler()
                    .runTaskAsynchronously(
                            MinePayApi.getINSTANCE().getPlugin(),
                            () ->
                                    MinePayApi.getINSTANCE()
                                            .getRequestManager()
                                            .cancelRequest(event.getRequest().get_id()));
        } else {
            Bukkit.broadcastMessage(message.toString());
            Bukkit.getScheduler()
                    .runTaskAsynchronously(
                            MinePayApi.getINSTANCE().getPlugin(),
                            () -> {
                                MinePayApi.getINSTANCE()
                                        .getRequestManager()
                                        .acceptRequest(event.getRequest().get_id());
                            });
        }
    }

    @EventHandler
    public void onStoreRequestCancel(StoreRequestCancelEvent event) {
        JsonObject jsonObject =
                MinePayApi.getINSTANCE()
                        .getRequestManager()
                        .acceptRequest(event.getRequest().get_id());
        System.out.println(jsonObject);
    }

    @EventHandler
    public void onStoreRequest(StoreRequestEvent event) {
        // Do something with the event
    }

    @EventHandler
    public void onStoreRequestAcceptJoin(StoreRequestAcceptJoinEvent event) {
        StringBuilder message = new StringBuilder("§2Du har købt: ");

        for (StoreProduct product : event.getRequest().getProducts()) {
            message.append(product.getName()).append(", ");
        }

        message.append("for ").append(event.getRequest().getPrice()).append(" mønter.");
        event.getPlayer().getPlayer().sendMessage(message.toString());
    }

    @EventHandler
    public void onStoreRequestCancelJoin(StoreRequestCancelJoinEvent event) {
        StringBuilder message = new StringBuilder("§cDu har annulleret købet af: ");

        for (StoreProduct product : event.getRequest().getProducts()) {
            message.append(product.getName()).append(", ");
        }

        message.append("for ").append(event.getRequest().getPrice()).append(" mønter.");
        event.getPlayer().getPlayer().sendMessage(message.toString());
    }
}
