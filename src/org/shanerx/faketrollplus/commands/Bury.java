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

public class Bury implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Bury(FakeTrollPlus instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("bury")) {
			if (!this.plugin.getConfig().getBoolean("bury.enable")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.bury")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /bury <target>");
				return false;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			double depth = this.plugin.getConfig().getDouble("bury.depth");
			Location loc = target.getLocation();
			loc = target.getWorld().getHighestBlockAt(loc).getLocation();
			target.teleport(new Location(loc.getWorld(), loc.getBlockX(), loc.getY() - depth, loc.getZ()));
			sender.sendMessage(FakeTrollPlus.col("&c" + target.getName() + " &6has been buried alive!"));
			if (!this.plugin.getConfig().getBoolean("bury.do-msg-to-target")) {
				return true;
			}
			target.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("tprandom.msg-to-target")));
		}
		
		return true;
	}

}
