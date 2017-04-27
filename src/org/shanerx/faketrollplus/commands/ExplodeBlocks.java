package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.core.TrollPlayer;

public class ExplodeBlocks implements CommandExecutor {

	FakeTrollPlus plugin;

	public ExplodeBlocks(FakeTrollPlus instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!this.plugin.getConfig().getBoolean("explode-blocks.enable")) {
			sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
			return false;
		}
		if (!sender.hasPermission("faketroll.explodeblocks")) {
			sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
			return false;
		}
		if (args.length != 1) {
			sender.sendMessage(ChatColor.GOLD + "Usage: /explodeblocks <target>");
			return false;
		}
		Player target = this.plugin.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
			return false;
		}
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(target.getUniqueId());
		if (tp.hasExplodeMinedBlocksEffect()) {
			tp.setExplodeMinedBlocksEffect(false);
			sender.sendMessage(ChatColor.GOLD + "Removed effect from " + target.getName() + "!");
			return true;
		}
		tp.setExplodeMinedBlocksEffect(true);
		sender.sendMessage(ChatColor.GOLD + "Applied effect on " + target.getName() + "!");

		return true;
	}

}