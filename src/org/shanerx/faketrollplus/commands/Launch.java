package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Launch implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Launch(FakeTrollPlus plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("launch")) {
			if (!this.plugin.getConfig().getBoolean("enable-launch")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
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
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			double y = Double.parseDouble(args[1]);
			target.setVelocity(new Vector(0, y, 0));
			sender.sendMessage(FakeTrollPlus.col("&c" + target.getName() + " &6has been launched in the air!"));
			target.sendMessage(ChatColor.BLUE + "Whooshh!");
		}
		return true;
	}

}
