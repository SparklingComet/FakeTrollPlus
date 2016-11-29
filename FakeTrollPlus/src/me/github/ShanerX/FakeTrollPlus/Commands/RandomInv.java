package me.github.ShanerX.FakeTrollPlus.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.github.ShanerX.FakeTrollPlus.FakeTrollPlus;

public class RandomInv implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public RandomInv(FakeTrollPlus instance) {
		
		plugin = instance;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("randominv")) {
			  /*	  if (!plugin.getConfig().getBoolean("randominv.enable")) {
			     	 sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message-for-disabled-cmds")));
			     	return true;
			      }*/
			if (!sender.hasPermission("faketroll.randominv")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /randominv <target>");
				return true;
			}
			Player target = plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("invalid-target")));
				return true;
			}
			Inventory playerInventory = target.getInventory();
			target.openInventory(playerInventory);
			boolean targetMsg = plugin.getConfig().getBoolean("do-target-msg");
			String targetMessage = plugin.getConfig().getString("target-msg");
			if (targetMsg) {
				target.sendMessage(ChatColor.translateAlternateColorCodes('&', targetMessage));
			}
		}
		
		return true;
	}

}
