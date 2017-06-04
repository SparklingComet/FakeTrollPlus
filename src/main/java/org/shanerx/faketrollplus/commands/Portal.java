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

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.Message;

public class Portal implements CommandExecutor {

	FakeTrollPlus plugin;

	public Portal(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.portal", Message.getBool("enable-portal"), () -> args.length != 1)) {
			return false;
		}
		final Player target = plugin.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Message.getString("invalid-target"));
			return false;
		}
		final String target_name = target.getName();
		String world = target.getLocation().getWorld().getName();
		if (world.contains("_the_end")) {
			sender.sendMessage(ChatColor.GOLD + "Player " + target_name + " is already there!");
			return false;
		}
		if (world.contains("_nether")) {
			world = world.replace("_nether", "");
		}
		sender.sendMessage(ChatColor.GOLD + target_name + " has been warped far away!");
		World end = plugin.getServer().getWorld(target.getWorld().getName() + "_the_end");
		final Location spawn = end.getHighestBlockAt(end.getSpawnLocation()).getLocation();
		target.teleport(spawn);
		return true;
	}

}