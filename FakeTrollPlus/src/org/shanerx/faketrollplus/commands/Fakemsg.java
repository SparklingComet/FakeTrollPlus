package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Fakemsg implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Fakemsg(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("fakemsg")) {
			if (!this.plugin.getConfig().getBoolean("fake-msg.enable")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.fakemsg")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length < 3) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakemsg <target> <fake-name> <message>");
				return true;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			String fakeName;
			try {
				Player fakePlayer = this.plugin.getServer().getPlayer(args[1]);
				fakeName = fakePlayer.getDisplayName();
			}
			catch (NullPointerException e) {
				fakeName = args[1];
			}
			String msg = args[2];
			for (int i = 3; i < args.length; i++) {
				msg = msg + " " + args[i];
			}
			String fakeMsg = this.plugin.getConfig().getString("fake-msg.format");
			fakeMsg = fakeMsg.replace("{PLAYER}", fakeName);
			fakeMsg = fakeMsg.replace("{MESSAGE}", msg);
			target.sendMessage(ChatColor.translateAlternateColorCodes('&', fakeMsg));
		}
		
		return true;
	}

}