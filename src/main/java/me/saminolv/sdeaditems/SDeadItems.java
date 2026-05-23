package me.saminolv.sdeaditems;

import me.saminolv.sdeaditems.commands.SDICommand;
import me.saminolv.sdeaditems.gui.ItemGUI;
import me.saminolv.sdeaditems.listener.DeathListener;
import me.saminolv.sdeaditems.manager.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SDeadItems extends JavaPlugin {

    private static SDeadItems instance;
    private ConfigManager configManager;
    private ItemGUI itemGUI;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        configManager = new ConfigManager(this);
        itemGUI = new ItemGUI(this);

        getCommand("sdi").setExecutor(new SDICommand(this));
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);

        getLogger().info("sDeadItems enabled | Author: Saminolov | t.me/SummerDEV");
    }

    @Override
    public void onDisable() {
        getLogger().info("sDeadItems disabled");
    }

    public static SDeadItems getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ItemGUI getItemGUI() {
        return itemGUI;
    }
}
