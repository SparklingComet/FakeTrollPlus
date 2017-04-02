package org.shanerx.faketrollplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Fakejoin implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Fakejoin(FakeTrollPlus instance) {
		plugin = instance;
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("fake-join.enable")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.fakejoin")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakejoin <target>");
				return false;
			}
			Bukkit.broadcastMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("fake-join.message").replaceAll("%Player%", args[0])));
		
		return true;
	}

}