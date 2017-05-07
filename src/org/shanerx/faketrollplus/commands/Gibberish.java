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
import org.shanerx.faketrollplus.core.TrollPlayer;

public class Gibberish implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Gibberish(FakeTrollPlus instance) {
		plugin = instance;
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("enable-gibberish")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.gibberish")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}			
			if (args.length != 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /gibberish <target>");
				return false;
			}
			Player p = Bukkit.getPlayer(args[0]);
			if (p == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());
			if (tp.chatIsGibberish()) {
				tp.setGibberishChat(false);
				sender.sendMessage(ChatColor.RED + args[0] + ChatColor.GOLD + " will no longer talk gibberish!");
				return true;
			}
			tp.setGibberishChat(true);
			sender.sendMessage(ChatColor.RED + args[0] + ChatColor.GOLD + " will now talk gibberish!");

		return true;
	}

}