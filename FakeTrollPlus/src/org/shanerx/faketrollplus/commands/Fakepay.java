package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Fakepay implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Fakepay(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("fakepay")) {
			if (!this.plugin.getConfig().getBoolean("fake-pay.enable")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.fakepay")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length != 2) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakepay <target> <amount>");
				return true;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			Player troll = (Player)sender;
			String trollName = troll.getDisplayName();
			double amount = Double.parseDouble(args[1]);
			String targetMsg = this.plugin.getConfig().getString("fake-pay.format");
			targetMsg = targetMsg.replace("{SENDER}", trollName);
			targetMsg = targetMsg.replace("{AMOUNT}", String.valueOf(amount));
			target.sendMessage(ChatColor.translateAlternateColorCodes('&', targetMsg));
			sender.sendMessage(ChatColor.GOLD + "Faked transaction of $" + amount + " to " + target.getName());
		}
		
		return true;
	}

}
