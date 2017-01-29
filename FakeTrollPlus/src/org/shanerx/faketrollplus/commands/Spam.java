package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Spam implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Spam(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("spam")) {
			if (!this.plugin.getConfig().getBoolean("enable-spam")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.spam")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length <= 2) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /spam <target> <times> <message>");
				return true;
			}
			String msg = args[2];
			for (int i = 3; i < args.length; i++) {
				msg = msg + " " + args[i];
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			String target_name = target.getName();
			try {
				int msgCount = Integer.parseInt(args[1]);
				int msgCounter = 1;
				while (msgCounter <= msgCount) {
					target.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
					msgCounter++;
				}
				sender.sendMessage(ChatColor.GOLD + "You've successfully annoyed " + target_name + "!");
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.GOLD + "The 2nd parameter has to be a whole number!");
			}
		}
		
		return true;
	}

}
