package org.shanerx.faketrollplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.core.TrollPlayer;

public class Gibberish implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Gibberish(FakeTrollPlus instance) {
		plugin = instance;
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("enable-gibberish")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.gibberish")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}			
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /gibberish <target>");
				return false;
			}
			Player p = Bukkit.getPlayer(args[0]);
			if (p == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());
			if (tp.chatIsGibberish()) {
				tp.setGibberishChat(false);
				sender.sendMessage(ChatColor.RED + args[0] + ChatColor.GOLD + " will no longer talk gibberish!");
				return true;
			}
			tp.setGibberishChat(true);
			sender.sendMessage(ChatColor.RED + args[0] + ChatColor.GOLD + " will now talk gibberish!");

		return true;
	}

}