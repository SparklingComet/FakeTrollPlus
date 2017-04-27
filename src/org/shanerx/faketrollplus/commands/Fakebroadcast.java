package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Fakebroadcast implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Fakebroadcast(FakeTrollPlus instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("fake-broadcast.enable")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.fakebroadcast")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length < 2) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakebroadcast <target> <message>");
				return false;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			int msgLength = args.length;
			String message = args[1];
			for (int i = 2; i < msgLength; i++) {
				message = message + " " + args[i];
			}
			target.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("fake-broadcast.format") + message));

		return true;
	}

}
