package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Launch implements CommandExecutor {
	
	org.shanerx.faketrollplus.FakeTrollPlus plugin;
	
	public Launch(org.shanerx.faketrollplus.FakeTrollPlus plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("launch")) {
			if (!this.plugin.getConfig().getBoolean("enable-launch")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.launch")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length != 2) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /launch <target> <height>");
				return true;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			double y = Double.parseDouble(args[1]);
			target.setVelocity(new Vector(0, y, 0));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + target.getName() + " &6has been launched in the air!"));
			target.sendMessage(ChatColor.BLUE + "Whooshh!");
		}
		return true;
	}

}
