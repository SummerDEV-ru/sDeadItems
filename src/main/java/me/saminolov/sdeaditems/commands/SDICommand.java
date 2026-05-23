package me.saminolov.sdeaditems.commands;

import me.saminolov.sdeaditems.SDeadItems;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SDICommand implements CommandExecutor {

    private final SDeadItems plugin;

    public SDICommand(SDeadItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /sdi <gui|on|off>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "gui":
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Only players can use GUI.");
                    return true;
                }
                if (!sender.hasPermission("sdeaditems.gui") && !sender.isOp()) {
                    sender.sendMessage(ChatColor.RED + "You don't have permission.");
                    return true;
                }
                plugin.getItemGUI().open((Player) sender);
                sender.sendMessage(ChatColor.GREEN + "GUI opened.");
                break;

            case "on":
                if (!sender.hasPermission("sdeaditems.toggle") && !sender.isOp()) {
                    sender.sendMessage(ChatColor.RED + "You don't have permission.");
                    return true;
                }
                plugin.getConfigManager().setEnabled(true);
                sender.sendMessage(ChatColor.GREEN + "sDeadItems enabled.");
                break;

            case "off":
                if (!sender.hasPermission("sdeaditems.toggle") && !sender.isOp()) {
                    sender.sendMessage(ChatColor.RED + "You don't have permission.");
                    return true;
                }
                plugin.getConfigManager().setEnabled(false);
                sender.sendMessage(ChatColor.RED + "sDeadItems disabled.");
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Usage: /sdi <gui|on|off>");
                break;
        }

        return true;
    }
}
