package me.github.ShanerX.FakeTrollPlus.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.github.ShanerX.FakeTrollPlus.FakeTrollPlus;

public class Gibberish implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Gibberish(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("gibberish")) {
			
			if (!this.plugin.getConfig().getBoolean("enable-gibberish")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			
			if (!sender.hasPermission("faketroll.gibberish")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /gibberish <target>");
				return true;
			}
			
			if (FakeTrollPlus.gibberish.contains(args[0])) {
				FakeTrollPlus.gibberish.remove(args[0]);
				sender.sendMessage(ChatColor.RED + args[0] + ChatColor.GOLD + " will no longer talk gibberish!");
				return true;
			}
			FakeTrollPlus.gibberish.add(args[0]);
			sender.sendMessage(ChatColor.RED + args[0] + ChatColor.GOLD + " will now talk gibberish!");
			return true;			
		}
		
		
		return true;
	}

}
