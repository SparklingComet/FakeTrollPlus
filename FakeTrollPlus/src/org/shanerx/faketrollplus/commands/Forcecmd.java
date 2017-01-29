package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Forcecmd implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Forcecmd(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("forcecmd")) {
			if (!this.plugin.getConfig().getBoolean("enable-force-cmd")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.forcecmd")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length < 2) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /forcecmd <target> <command> [args]");
				return true;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			String target_name = target.getName();
			String forceCmd = args[1];
			if (args.length > 2) {
				for (int i = 2; i < args.length; i++) {
					forceCmd = forceCmd + " " + args[i];
				}
			}
			sender.sendMessage(ChatColor.GOLD + "Forced " + target_name + " to run /" + forceCmd);
			this.plugin.getServer().dispatchCommand(target, forceCmd);
		}
		
		return true;
	}

}
