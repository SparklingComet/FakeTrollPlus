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

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.Message;

public class Fakeop implements CommandExecutor {

	FakeTrollPlus plugin;

	public Fakeop(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.fakeop", Message.getBool("enable-fake-op"), () -> args.length != 1)) {
			return false;
		}
		final Player target = plugin.getServer().getPlayer(args[0]);

		if (target == null) {
			sender.sendMessage(Message.getString("invalid-target"));
			return false;
		}
		final String target_name = target.getName();
		if (!(sender instanceof Player)) {
			target.sendMessage(Message.col("&7&o[Server: Opped " + target_name + "]"));
			return false;
		}
		target.sendMessage(Message.col("&7&o[" + sender.getName() + ": Opped " + target_name + "]"));
		sender.sendMessage(ChatColor.GOLD + "Fake-opped " + target_name + "!");
		return true;
	}

}