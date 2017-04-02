package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Cobweb implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Cobweb(FakeTrollPlus instance) {		
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("enable-cobweb")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.cobweb")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /cobweb <target>");
				return false;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			String target_name = target.getName();
			sender.sendMessage(ChatColor.GOLD + "You have trapped " + ChatColor.RED + target_name);
			target.getLocation().getBlock().setType(Material.WEB);
		
		return true;
	}

}