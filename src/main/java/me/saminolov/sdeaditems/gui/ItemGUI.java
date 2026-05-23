package me.saminolov.sdeaditems.gui;

import me.saminolov.sdeaditems.SDeadItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemGUI implements Listener {

    private static final String GUI_TITLE = ChatColor.DARK_PURPLE + "sDeadItems Editor";
    private static final int GUI_SIZE = 54;
    private static final int ADD_SLOT = 49;

    private final SDeadItems plugin;

    public ItemGUI(SDeadItems plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void open(Player player) {
        List<ItemStack> items = plugin.getConfigManager().getItems();
        Inventory inv = Bukkit.createInventory(null, GUI_SIZE, GUI_TITLE);

        for (int i = 0; i < items.size() && i < 45; i++) {
            inv.setItem(i, items.get(i));
        }

        for (int i = 45; i < 54; i++) {
            if (i == ADD_SLOT) {
                ItemStack addItem = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                ItemMeta meta = addItem.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Add item");
                meta.setLore(Arrays.asList(
                        ChatColor.GRAY + "Hold item in your hand",
                        ChatColor.GRAY + "and click here to add it"
                ));
                addItem.setItemMeta(meta);
                inv.setItem(i, addItem);
            } else {
                ItemStack border = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                ItemMeta meta = border.getItemMeta();
                meta.setDisplayName(" ");
                border.setItemMeta(meta);
                inv.setItem(i, border);
            }
        }

        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(GUI_TITLE)) return;
        if (!(event.getWhoClicked() instanceof Player)) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        if (slot < 0 || slot >= 45) {
            if (slot == ADD_SLOT) {
                ItemStack hand = player.getInventory().getItemInMainHand();
                if (hand == null || hand.getType() == Material.AIR) {
                    player.sendMessage(ChatColor.RED + "Hold an item in your hand to add it.");
                    return;
                }
                ItemStack toAdd = hand.clone();
                toAdd.setAmount(1);
                plugin.getConfigManager().addItem(toAdd);
                player.sendMessage(ChatColor.GREEN + "Item added!");
                player.closeInventory();
                open(player);
            }
            return;
        }

        List<ItemStack> items = plugin.getConfigManager().getItems();
        if (slot >= items.size()) return;

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;

        if (event.isShiftClick()) {
            plugin.getConfigManager().removeItem(slot);
            player.sendMessage(ChatColor.RED + "Item removed!");
        } else {
            plugin.getConfigManager().removeItem(slot);
            player.getInventory().addItem(clicked.clone());
            player.sendMessage(ChatColor.GREEN + "Item retrieved!");
        }

        player.closeInventory();
        open(player);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getView().getTitle().equals(GUI_TITLE)) {
            event.setCancelled(true);
        }
    }
}
