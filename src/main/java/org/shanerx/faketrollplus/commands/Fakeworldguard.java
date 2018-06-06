package org.shanerx.faketrollplus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.core.data.TrollPlayer;
import org.shanerx.faketrollplus.utils.Message;

public class Fakeworldguard implements CommandExecutor {

	private FakeTrollPlus plugin;

	public Fakeworldguard(final FakeTrollPlus instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.fakeworldguard", Message.getBool("fakeworldguard.enable"), () -> args.length != 1)) {
			return false;
		}

		final Player target = plugin.getTarget(args[0]);
		if (target == null) {
			sender.sendMessage(Message.PREFIX + Message.getString("invalid-target"));
			return false;
		}

		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(target);
		tp.setFakeWorldGuard(!tp.isFakeWorldGuard());
		sender.sendMessage(Message.getString("fakeworldguard.msg")
				.replace("%status%", tp.isFakeWorldGuard() ? "on" : "off")
				.replace("%target%", tp.getName())
		);
		return false;
	}
}