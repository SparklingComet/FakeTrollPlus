package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Fakegod implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Fakegod(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("fakegod")) {
			if (!this.plugin.getConfig().getBoolean("enable-fake-god")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.fakegod")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length != 2) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakegod <target> <on|off>");
				return true;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			if ((!args[1].equalsIgnoreCase("on")) && (!args[1].equalsIgnoreCase("off"))) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakegod <target> <on|off>");
				return true;
			}
			if (args[1].equalsIgnoreCase("on")) {
				target.sendMessage(ChatColor.GOLD + "God mode " + ChatColor.RED + "enabled" + ChatColor.GOLD + ".");
			}
			else {
				target.sendMessage(ChatColor.GOLD + "God mode " + ChatColor.RED + "disabled" + ChatColor.GOLD + ".");
			}
		}
		
		return true;
	}

}
