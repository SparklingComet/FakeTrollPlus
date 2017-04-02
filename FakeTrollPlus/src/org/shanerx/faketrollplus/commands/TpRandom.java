package org.shanerx.faketrollplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class TpRandom implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public TpRandom(FakeTrollPlus instance) {	
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("tprandom.enable")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.tprandom")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /tprandom <target>");
				return false;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			String target_name = target.getName();
			double bounds = this.plugin.getConfig().getDouble("tprandom.tp-bounds");
			double x = Math.random() * 2.0D * bounds - bounds;
			double y = target.getLocation().getY();
			double z = Math.random() * 2.0D * bounds - bounds;
			World w = Bukkit.getServer().getWorld("world");
			target.teleport(new Location(w, x, y, z));
			sender.sendMessage(ChatColor.GOLD + target_name + " has been teleported to a random location!");
			if (!this.plugin.getConfig().getBoolean("tprandom.do-msg-to-target")) {
				return true;
			}
			target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("tprandom.msg-to-target")));
		
		return true;
	}

}
