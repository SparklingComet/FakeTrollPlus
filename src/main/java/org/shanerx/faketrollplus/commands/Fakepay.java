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

public class Fakepay implements CommandExecutor {

	FakeTrollPlus plugin;

	public Fakepay(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.fakepay", Message.getBool("fake-pay.enable"), () -> {
			if (args.length != 2) {
				return true;
			} else {
				try {
					Double.parseDouble(args[1]);
					return false;
				} catch (NumberFormatException e) {
					return true;
				}
			}
		})) {
			return false;
		}
		final Player target = plugin.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Message.getString("invalid-target"));
			return false;
		}
		final Player troll = (Player) sender;
		final String trollName = troll.getDisplayName();
		final double amount = Double.parseDouble(args[1]);
		String targetMsg = plugin.getConfig().getString("fake-pay.format");
		targetMsg = targetMsg.replace("{SENDER}", trollName);
		targetMsg = targetMsg.replace("{AMOUNT}", String.valueOf(amount));
		target.sendMessage(Message.col(targetMsg));
		sender.sendMessage(ChatColor.GOLD + "Faked transaction of $" + amount + " to " + target.getName());
		return true;
	}

}