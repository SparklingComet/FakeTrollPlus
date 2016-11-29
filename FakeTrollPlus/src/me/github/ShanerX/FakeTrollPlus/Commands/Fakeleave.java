package me.github.ShanerX.FakeTrollPlus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.github.ShanerX.FakeTrollPlus.FakeTrollPlus;

public class Fakeleave implements CommandExecutor {

	FakeTrollPlus plugin;
	
	public Fakeleave(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("fakeleave")) {
			if (!this.plugin.getConfig().getBoolean("fake-leave.enable")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.fakeleave")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakeleave <target>");
				return true;
			}
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("fake-leave.message").replaceAll("%Player%", args[0])));
		}
		
		return true;
	}
}
