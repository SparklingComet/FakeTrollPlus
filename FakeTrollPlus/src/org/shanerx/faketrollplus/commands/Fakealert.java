package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Fakealert implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Fakealert(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("fakealert")) {
			if (!this.plugin.getConfig().getBoolean("enable-fake-alert")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.fakealert")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length < 2) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakealert <target> <message>");
				return true;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			int msgLength = args.length;
			String alert = args[1];
			for (int i = 2; i < msgLength; i++) {
				alert = alert + " " + args[i];
			}
			target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4Alert&8]&f " + alert));
		}
		
		
		return true;
	}

}
