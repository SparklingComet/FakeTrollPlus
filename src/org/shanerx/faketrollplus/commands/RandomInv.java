package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class RandomInv implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public RandomInv(FakeTrollPlus instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!plugin.getConfig().getBoolean("random-inv.enable")) {
				sender.sendMessage(FakeTrollPlus.col(plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.randominv")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /randominv <target>");
				return false;
			}
			Player target = plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(plugin.getConfig().getString("invalid-target")));
				return false;
			}
			Inventory playerInventory = target.getInventory();
			target.openInventory(playerInventory);
			boolean targetMsg = plugin.getConfig().getBoolean("random-inv.do-target-msg");
			String targetMessage = plugin.getConfig().getString("random-inv.target-msg");
			if (targetMsg) {
				target.sendMessage(FakeTrollPlus.col(targetMessage));
			}

		return true;
	}

}
