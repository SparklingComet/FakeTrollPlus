package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.core.TrollPlayer;

public class Nopickup implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Nopickup(FakeTrollPlus instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//TODO FIX:
//			if (!this.plugin.getConfig().getBoolean("no-pickup.enable")) {
//				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
//				return false;
//			}
			if (!sender.hasPermission("faketroll.nopickup")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /nopickup <target>");
				return false;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			TrollPlayer tp = plugin.getUserCache().getTrollPlayer(target.getUniqueId());
			if (!tp.canPickup()) {
				tp.setPickup(true);
				sender.sendMessage(ChatColor.GOLD + "Player " + ChatColor.RED + target.getName() + ChatColor.GOLD + " can now pickup items!");
				if (plugin.getConfig().getBoolean("alert-victim")) {
					target.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("no-pickup.disable")));
				}
				return true;
			}
			tp.setPickup(false);
			sender.sendMessage(ChatColor.GOLD + "Player " + ChatColor.RED + target.getName() + ChatColor.GOLD + " can no longer pickup items!");
			if (plugin.getConfig().getBoolean("alert-victim")) {
				target.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("no-pickup.enable")));
			}
		
		return true;
	}
	
}
