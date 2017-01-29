package me.github.ShanerX.FakeTrollPlus.Commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.github.ShanerX.FakeTrollPlus.FakeTrollPlus;

public class Freeze implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Freeze(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("freeze")) {
			if (!this.plugin.getConfig().getBoolean("freeze.enable")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.freeze")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /freeze <target>");
				return true;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			String target_name = target.getName();
			if (FakeTrollPlus.frozenPlayers.contains(target_name)) {
				FakeTrollPlus.frozenPlayers.remove(target_name);
				sender.sendMessage(ChatColor.GOLD + "Player " + target_name + " has been unfrozen!");
				target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("freeze.unfreeze-msg")));
				return true;
			}
			FakeTrollPlus.frozenPlayers.add(target_name);
			sender.sendMessage(ChatColor.GOLD + "Player " + target_name + " has been frozen!");
			target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("freeze.freeze-msg")));
		}
		
		return true;
	}
	
}