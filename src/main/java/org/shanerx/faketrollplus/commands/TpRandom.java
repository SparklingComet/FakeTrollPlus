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
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.Message;

public class TpRandom implements CommandExecutor {

	FakeTrollPlus plugin;

	public TpRandom(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.getBool("tprandom.enable")) {
			sender.sendMessage(
					ChatColor.translateAlternateColorCodes('&', Message.getString("message-for-disabled-cmds")));
			return false;
		}
		if (!sender.hasPermission("faketroll.tprandom")) {
			sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
			return false;
		}
		if (args.length != 1) {
			sender.sendMessage(ChatColor.GOLD + "Usage: /tprandom <target>");
			return false;
		}
		final Player target = plugin.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Message.getString("invalid-target")));
			return false;
		}
		final String target_name = target.getName();
		final double bounds = Message.getDouble("tprandom.tp-bounds");
		final double x = Math.random() * 2.0D * bounds - bounds;
		final double y = target.getLocation().getY();
		final double z = Math.random() * 2.0D * bounds - bounds;
		final World w = Bukkit.getServer().getWorld("world");
		target.teleport(new Location(w, x, y, z));
		sender.sendMessage(ChatColor.GOLD + target_name + " has been teleported to a random location!");
		if (!Message.getBool("tprandom.do-msg-to-target")) {
			return true;
		}
		target.sendMessage(ChatColor.translateAlternateColorCodes('&', Message.getString("tprandom.msg-to-target")));
		return true;
	}

}