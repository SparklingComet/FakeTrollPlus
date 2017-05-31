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
import org.bukkit.util.Vector;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.Message;

public class Launch implements CommandExecutor {

	FakeTrollPlus plugin;

	public Launch(final FakeTrollPlus plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.getBool("enable-launch")) {
			sender.sendMessage(Message.getString("message-for-disabled-cmds"));
			return true;
		}
		if (!sender.hasPermission("faketroll.launch")) {
			sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
			return true;
		}
		if (args.length != 2) {
			sender.sendMessage(ChatColor.GOLD + "Usage: /launch <target> <height>");
			return true;
		}
		final Player target = plugin.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Message.getString("invalid-target"));
			return true;
		}
		final double y = Double.parseDouble(args[1]);
		target.setVelocity(new Vector(0, y, 0));
		sender.sendMessage(Message.col("&c" + target.getName() + " &6has been launched in the air!"));
		target.sendMessage(ChatColor.BLUE + "Whooshh!");
		return true;
	}

}