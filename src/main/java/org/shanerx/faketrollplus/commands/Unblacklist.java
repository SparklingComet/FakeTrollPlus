/*
 *     Copyright 2016-2017 ShanerX @ http://shanerx.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.shanerx.faketrollplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.Message;
import org.shanerx.faketrollplus.core.TrollPlayer;

public class Unblacklist implements CommandExecutor {

	private FakeTrollPlus ftp;

	public Unblacklist(final FakeTrollPlus ftp) {
		this.ftp = ftp;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!sender.hasPermission("faketroll.unblacklist")) {
			sender.sendMessage(Message.col(Message.ACCESS_DENIED));
			return false;
		}
		if (args.length != 1) {
			sender.sendMessage(ChatColor.GOLD + "Usage: /unblacklist <target>");
			return false;
		}
		@SuppressWarnings("deprecation")
		final OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
		if (op == null) {
			sender.sendMessage(Message.getString("invalid-target"));
			return false;
		}
		final TrollPlayer target = ftp.getUserCache().getTrollPlayer(op.getUniqueId());
		if (!target.isBlacklisted()) {
			sender.sendMessage(Message.col("not-blacklisted"));
			return false;
		}
		target.setBlacklisted(false);
		sender.sendMessage(Message.getString("on-unblacklist").replace("%player%", target.getName()));
		return true;
	}

}