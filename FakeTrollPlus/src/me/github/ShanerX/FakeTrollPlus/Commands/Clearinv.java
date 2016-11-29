package me.github.ShanerX.FakeTrollPlus.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.github.ShanerX.FakeTrollPlus.FakeTrollPlus;

public class Clearinv implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Clearinv(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("clearinv")) {
			if (!this.plugin.getConfig().getBoolean("enable-clearinv")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.clearinv")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /clearinv <target>");
				return true;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			String target_name = target.getName();
			target.getInventory().clear();
			sender.sendMessage(ChatColor.GOLD + "Cleared inventory of " + target_name);
		}
		
		return true;
	}

}
