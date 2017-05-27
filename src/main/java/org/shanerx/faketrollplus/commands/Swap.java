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
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Swap implements CommandExecutor {
	
	private FakeTrollPlus plugin;
	
	public Swap(FakeTrollPlus pl) {
		plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!this.plugin.getConfig().getBoolean("swap.enable")) {
			sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
			return false;
		}
		if (!sender.hasPermission("faketroll.swap")) {
			sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
			return false;
		}
		if (args.length != 2) {
			sender.sendMessage(ChatColor.GOLD + "Usage: /swap <target1> <target2>");
			return false;
		}
		Player t1 = this.plugin.getServer().getPlayer(args[0]);
		Player t2 = this.plugin.getServer().getPlayer(args[1]);
		if (t1 == null || t2 == null) {
			sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("swap.invalid-target")));
			return false;
		}
		Location loc1 = t1.getLocation();
		Location loc2 = t2.getLocation();
		t1.teleport(loc2);
		t2.teleport(loc1);
		if (plugin.getConfig().getBoolean("swap.send-message-to-target")) {
			String msg = plugin.getConfig().getString("swap.target-msg");
			t1.sendMessage(FakeTrollPlus.col(msg));
			t2.sendMessage(FakeTrollPlus.col(msg));
		}
		sender.sendMessage(FakeTrollPlus.col("&6You made &c" + args[0] + " &6swap location with &c" + args[1] + "&6!"));
		return true;
	}

}
