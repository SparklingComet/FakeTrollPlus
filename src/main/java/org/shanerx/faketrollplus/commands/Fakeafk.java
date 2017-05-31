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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.Message;

public class Fakeafk implements CommandExecutor {

	FakeTrollPlus plugin;

	public Fakeafk(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.fakeafk", Message.getBool("fake-afk.enable"), () -> checkArgs(args))) {
			return false;
		}
		final Player target = plugin.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Message.getString("invalid-target"));
			return false;
		}
		String target_name = target.getDisplayName();
		if (args[1].equalsIgnoreCase("on")) {
			Bukkit.broadcastMessage(Message.getString("fake-afk.format-on").replace("{PLAYER}", target_name));
		} else if (args[1].equalsIgnoreCase("off")) {
			Bukkit.broadcastMessage(Message.getString("fake-afk.format-off").replace("{PLAYER}", target_name));
		}
		return true;
	}
	
	private boolean checkArgs(String[] args) {
		if (args.length != 2) {
			return true;	
		} else if (!args[1].equalsIgnoreCase("on") && !args[1].equalsIgnoreCase("off")) {
			return true;
		}
		return false;
	}

}