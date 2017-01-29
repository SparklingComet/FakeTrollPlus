package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Fakedeop implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Fakedeop(FakeTrollPlus instance) {
		
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("fakedeop")) {
			if (!this.plugin.getConfig().getBoolean("enable-fake-deop")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.fakedeop")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakedeop <target>");
				return true;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			String target_name = target.getName();
			if (!(sender instanceof Player)) {
				target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&o[Server: De-opped " + target_name + "]"));
				return true;
			}
			target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&o[" + sender.getName() + ": De-opped " + target_name + "]"));
			sender.sendMessage(ChatColor.GOLD + "Fake-opped " + target_name + "!");
		}
		
		
		return true;
	}

}
