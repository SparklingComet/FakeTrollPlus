/*
 *     Copyright 2016-2017 SparklingComet @ http://shanerx.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.shanerx.faketrollplus.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.utils.Message;

public class Bury implements CommandExecutor {
	
	private FakeTrollPlus plugin;

	public Bury(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.bury", Message.getBool("bury.enable"), () -> args.length != 1)) {
			return false;
		}
		final Player target = plugin.getTarget(args[0]);
		if (target == null) {
			sender.sendMessage(Message.PREFIX + Message.getString("invalid-target"));
			return false;
		}
		
		final double depth = Message.getDouble("bury.depth");
		Location loc = target.getLocation();
		loc = target.getWorld().getHighestBlockAt(loc).getLocation();
		target.teleport(new Location(loc.getWorld(), loc.getBlockX(), loc.getY() - depth, loc.getZ()));
		
		sender.sendMessage(Message.PREFIX + Message.getString("bury.sender").replace("%player%", target.getName()));
		if (plugin.getConfig().getBoolean("bury.do-msg-to-target")) {
			target.sendMessage(Message.getString("tprandom.msg-to-target"));
		}
		return true;
	}
}