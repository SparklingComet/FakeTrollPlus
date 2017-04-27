package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.core.TrollPlayer;

public class Badfood implements CommandExecutor {

	FakeTrollPlus plugin;

	public Badfood(FakeTrollPlus instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!this.plugin.getConfig().getBoolean("badfood.enable")) {
			sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
			return false;
		}
		if (!sender.hasPermission("faketroll.badfood")) {
			sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
			return false;
		}
		if (args.length != 1) {
			sender.sendMessage(ChatColor.GOLD + "Usage: /badfood <target>");
			return false;
		}
		Player target = this.plugin.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
			return false;
		}
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(target.getUniqueId());
		if (tp.hasBadfoodEffect()) {
			tp.setBadfoodEffect(false);
			sender.sendMessage(ChatColor.GOLD + "Removed effect from " + target.getName() + "!");
			return true;
		}
		tp.setBadfoodEffect(true);
		sender.sendMessage(ChatColor.GOLD + "Applied effect on " + target.getName() + "!");

		return true;
	}

}