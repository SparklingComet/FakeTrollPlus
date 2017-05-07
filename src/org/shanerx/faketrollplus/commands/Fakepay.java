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

public class Fakepay implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Fakepay(FakeTrollPlus instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("fake-pay.enable")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}
			if (!sender.hasPermission("faketroll.fakepay")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}
			if (args.length != 2) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakepay <target> <amount>");
				return false;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return false;
			}
			Player troll = (Player)sender;
			String trollName = troll.getDisplayName();
			double amount = Double.parseDouble(args[1]);
			String targetMsg = this.plugin.getConfig().getString("fake-pay.format");
			targetMsg = targetMsg.replace("{SENDER}", trollName);
			targetMsg = targetMsg.replace("{AMOUNT}", String.valueOf(amount));
			target.sendMessage(FakeTrollPlus.col(targetMsg));
			sender.sendMessage(ChatColor.GOLD + "Faked transaction of $" + amount + " to " + target.getName());

		return true;
	}

}
