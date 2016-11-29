package me.github.ShanerX.FakeTrollPlus.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.github.ShanerX.FakeTrollPlus.FakeTrollPlus;

public class Hurt implements CommandExecutor{
	
	FakeTrollPlus plugin;
	
	public Hurt(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("hurt")) {
			if (!this.plugin.getConfig().getBoolean("enable-hurt")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.hurt")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length < 2) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /hurt <target> <HP>");
				return true;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			String target_name = target.getName();
			int hp_dmg = Integer.parseInt(args[1]);
			int dmg = hp_dmg / 2;
			if (target.getHealth() - hp_dmg <= 0.0D) target.setHealth(0.0D); else
				target.setHealth(target.getHealth() - hp_dmg);
			sender.sendMessage(ChatColor.GOLD + "Applied a " + dmg + " heart(s) damage to " + target_name + "!");
		}
		
		return true;
	}
	


}
