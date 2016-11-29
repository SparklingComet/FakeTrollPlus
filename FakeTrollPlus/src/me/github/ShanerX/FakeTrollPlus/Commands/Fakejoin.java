package me.github.ShanerX.FakeTrollPlus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.github.ShanerX.FakeTrollPlus.FakeTrollPlus;

public class Fakejoin implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Fakejoin(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("fakejoin")) {
			if (!this.plugin.getConfig().getBoolean("fake-join.enable")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.fakejoin")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakejoin <target>");
				return true;
			}
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("fake-join.message").replaceAll("%Player%", args[0])));
		}
		
		return true;
	}

}
