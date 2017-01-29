package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Poison implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Poison(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("poison")) {
			if (!this.plugin.getConfig().getBoolean("enable-poison")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.poison")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length != 2) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /poison <target> <time>");
				return true;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			String target_name = target.getName();
			try {
				int time = Integer.parseInt(args[1]);
				sender.sendMessage(ChatColor.GOLD + "Poisoned " + target_name + " for " + (time / 20)  + " seconds");
				target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time, 2));
				
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.GOLD + "Time must be in seconds.");
				return false;
			}
		}
		
		return true;
	}

}
