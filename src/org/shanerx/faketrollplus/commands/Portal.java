package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Portal implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Portal(FakeTrollPlus instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("enable-portal")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.portal")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /portal <target>");
				return false;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			String target_name = target.getName();
			String world = target.getLocation().getWorld().getName();
			if (world.contains("_the_end")) {
				sender.sendMessage(ChatColor.GOLD + "Player " + target_name + " is already there!");
				return false;
			}
			if (world.contains("_nether")) {
				world = world.replace("_nether", "");
			}
			sender.sendMessage(ChatColor.GOLD + target_name + " has been warped far away!");
			Location spawn = this.plugin.getServer().getWorld(target.getWorld().getName() + "_the_end").getSpawnLocation();
			target.teleport(spawn);

		return true;
	}

}