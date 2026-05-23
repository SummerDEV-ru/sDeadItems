package me.saminolv.sdeaditems.listener;

import me.saminolv.sdeaditems.SDeadItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class DeathListener implements Listener {

    private final SDeadItems plugin;
    private final Random random = new Random();

    public DeathListener(SDeadItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!plugin.getConfigManager().isEnabled()) return;

        Player player = event.getEntity();
        List<ItemStack> items = plugin.getConfigManager().getItems();

        if (items.isEmpty()) return;

        ItemStack randomItem = items.get(random.nextInt(items.size())).clone();
        player.getWorld().dropItemNaturally(player.getLocation(), randomItem);
    }
}
