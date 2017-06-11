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

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.utils.Message;

public class Launch implements CommandExecutor {
	
	private FakeTrollPlus plugin;

	public Launch(final FakeTrollPlus plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.launch", Message.getBool("launch.enable"), () -> {
			if (args.length != 2) {
				return true;
			} else {
				try {
					Double.parseDouble(args[1]);
					return false;
				} catch(NumberFormatException e) {
					return true;
				}
			}
		})) {
			return false;
		}
		
		final Player target = plugin.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Message.PREFIX + Message.getString("invalid-target"));
			return true;
		}

		final double y = Double.parseDouble(args[1]);
		target.setVelocity(target.getVelocity().add(new Vector(0, y, 0)));
		
		sender.sendMessage(Message.PREFIX + Message.getString("launch.sender")
				.replace("%player%", target.getName()));
		return true;
	}

}