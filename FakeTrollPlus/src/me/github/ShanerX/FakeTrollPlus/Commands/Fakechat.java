package me.github.ShanerX.FakeTrollPlus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.github.ShanerX.FakeTrollPlus.FakeTrollPlus;

public class Fakechat implements CommandExecutor {

	FakeTrollPlus plugin;
	
	public Fakechat(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("fakechat")) {
			if (!this.plugin.getConfig().getBoolean("fake-chat.enable")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.fakechat")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length < 2) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakechat <target> <message>");
				return true;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			boolean targetIsReal = false;
			if (target != null) targetIsReal = true;
			if ((target == null) && (!this.plugin.getConfig().getBoolean("fake-chat.allow-unexisting-nicks"))) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			String msg = args[1];
			for (int i = 2; i < args.length; i++) {
				msg = msg + " " + args[i];
			}
			String fakeMsg = this.plugin.getConfig().getString("fake-chat.format");
			String displayName = null;
			if (targetIsReal) {
				displayName = target.getDisplayName();
			}
			else {
				displayName = args[0];
			}
			fakeMsg = fakeMsg.replace("{PLAYER}", displayName);
			fakeMsg = fakeMsg.replace("{MESSAGE}", msg);
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', fakeMsg));
		}
		
		return true;
	}
	
	

}
