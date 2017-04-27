package org.shanerx.faketrollplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Fakeafk implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Fakeafk(FakeTrollPlus instance) {
		plugin = instance;		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			
			if (!this.plugin.getConfig().getBoolean("fake-afk.enable")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			
			if (!sender.hasPermission("faketroll.fakeafk")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			
			if (args.length != 2) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakeafk <target> <on|off>");
				return false;
			}
			
			Player target = this.plugin.getServer().getPlayer(args[0]);
			String target_name;
			
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			
			target_name = target.getDisplayName();
			
			if (args[1].equalsIgnoreCase("on")) {
				Bukkit.broadcastMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("fake-afk.format-on")).replace("{PLAYER}", target_name));
			} else if (args[1].equalsIgnoreCase("off")) {
				Bukkit.broadcastMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("fake-afk.format-off")).replace("{PLAYER}", target_name));
			} else {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakeafk <target> <on|off>");
			}
		
		return true;
	}
	
}
