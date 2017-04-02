package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Freeze implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Freeze(FakeTrollPlus instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("freeze.enable")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.freeze")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /freeze <target>");
				return false;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			String target_name = target.getName();
			if (FakeTrollPlus.frozenPlayers.contains(target_name)) {
				FakeTrollPlus.frozenPlayers.remove(target_name);
				sender.sendMessage(ChatColor.GOLD + "Player " + target_name + " has been unfrozen!");
				target.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("freeze.unfreeze-msg")));
				return true;
			}
			FakeTrollPlus.frozenPlayers.add(target_name);
			sender.sendMessage(ChatColor.GOLD + "Player " + target_name + " has been frozen!");
			target.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("freeze.freeze-msg")));
		
		return true;
	}
	
}
