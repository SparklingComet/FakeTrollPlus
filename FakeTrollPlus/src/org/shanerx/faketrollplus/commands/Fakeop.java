package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Fakeop implements CommandExecutor {

	FakeTrollPlus plugin;
	
	public Fakeop(FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("enable-fake-op")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.fakeop")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakeop <target>");
				return false;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);

			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			String target_name = target.getName();
			if (!(sender instanceof Player)) {
				target.sendMessage(FakeTrollPlus.col("&7&o[Server: Opped " + target_name + "]"));
				return false;
			}
			target.sendMessage(FakeTrollPlus.col("&7&o[" + sender.getName() + ": Opped " + target_name + "]"));
			sender.sendMessage(ChatColor.GOLD + "Fake-opped " + target_name + "!");

		return true;
	}

}
