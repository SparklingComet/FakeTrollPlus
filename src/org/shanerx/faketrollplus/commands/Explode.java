package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Explode implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Explode(FakeTrollPlus instance) {
		plugin = instance;		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("explode.enable")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.explode")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /explode <target>");
				return false;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			float power = (float) plugin.getConfig().getDouble("explode.power");
			boolean setFire = plugin.getConfig().getBoolean("explode.set-fire");
			if (!target.getWorld().createExplosion(target.getLocation(), power, setFire)) {
				sender.sendMessage(FakeTrollPlus.col("&6Generated explosion at &c" + target.getName() + "&6's location."));
			}

		return true;
	}

}