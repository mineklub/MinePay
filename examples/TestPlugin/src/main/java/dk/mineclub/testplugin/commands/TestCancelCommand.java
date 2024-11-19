package dk.mineclub.testplugin.commands;

import dk.mineclub.testplugin.Main;
import dk.minepay.server.bukkit.classes.StoreProduct;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCancelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(
            CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return true;
        }

        StoreProduct storeProduct = new StoreProduct("Magnus416 hoved", "MAN417", 100.50, 1);
        HashMap<String, String> metadata = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            metadata.put("key" + i, "value" + i);
        }
        StoreProduct storeProduct2 =
                new StoreProduct("Magnus416 hoved", "MAN417", 100.59, 2, metadata);
        Bukkit.getScheduler()
                .runTaskAsynchronously(
                        Main.getMinePayApi().getPlugin(),
                        () -> {
                            Main.getMinePayApi()
                                    .getRequestManager()
                                    .createRequest(
                                            player.getUniqueId(),
                                            List.of(storeProduct, storeProduct2)
                                                    .toArray(new StoreProduct[0]));
                        });
        return true;
    }
}
