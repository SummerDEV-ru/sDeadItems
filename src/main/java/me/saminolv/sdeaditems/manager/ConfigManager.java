package me.saminolv.sdeaditems.manager;

import me.saminolv.sdeaditems.SDeadItems;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private final SDeadItems plugin;
    private boolean enabled;
    private List<ItemStack> items;

    public ConfigManager(SDeadItems plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        this.enabled = config.getBoolean("enabled", true);
        this.items = loadItems(config);
    }

    private List<ItemStack> loadItems(FileConfiguration config) {
        List<ItemStack> result = new ArrayList<>();
        List<String> raw = config.getStringList("items");
        for (String entry : raw) {
            String[] parts = entry.split(":");
            if (parts.length >= 2) {
                Material mat = Material.getMaterial(parts[0].toUpperCase());
                if (mat != null) {
                    try {
                        int amount = Integer.parseInt(parts[1]);
                        result.add(new ItemStack(mat, Math.min(amount, 64)));
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }
        return result;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        plugin.getConfig().set("enabled", enabled);
        plugin.saveConfig();
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
        List<String> raw = new ArrayList<>();
        for (ItemStack item : items) {
            raw.add(item.getType().name() + ":" + item.getAmount());
        }
        plugin.getConfig().set("items", raw);
        plugin.saveConfig();
    }

    public void addItem(ItemStack item) {
        items.add(item);
        setItems(items);
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            setItems(items);
        }
    }
}
