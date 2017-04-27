package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Anvil implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Anvil(FakeTrollPlus instance) {
		plugin = instance;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("enable-anvil")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.anvil")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /anvil <target>");
				return false;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			String target_name = target.getName();
			int x = target.getLocation().getBlockX();
			int y = target.getLocation().getBlockY();
			int z = target.getLocation().getBlockZ();
			World world = target.getLocation().getWorld();
			Block block = world.getBlockAt(x, y + 2, z);
			block.setTypeId(145);
			sender.sendMessage(ChatColor.GOLD + "Ouch!");
			if (!target.isOnGround()) {
				sender.sendMessage(ChatColor.GOLD + target_name + " is flying right now.");
			}

		return true;
	}

}
