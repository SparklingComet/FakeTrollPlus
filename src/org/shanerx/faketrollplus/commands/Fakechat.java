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

public class Fakechat implements CommandExecutor {

	FakeTrollPlus plugin;
	
	public Fakechat(FakeTrollPlus instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("fake-chat.enable")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			if (!sender.hasPermission("faketroll.fakechat")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			if (args.length < 2) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /fakechat <target> <message>");
				return true;
			}
			Player target = this.plugin.getServer().getPlayer(args[0]);
			boolean targetIsReal = false;
			if (target != null) targetIsReal = true;
			if ((target == null) && (!this.plugin.getConfig().getBoolean("fake-chat.allow-unexisting-nicks"))) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
				return true;
			}
			String msg = args[1];
			for (int i = 2; i < args.length; i++) {
				msg = msg + " " + args[i];
			}
			String fakeMsg = this.plugin.getConfig().getString("fake-chat.format");
			String displayName = null;
			if (targetIsReal) {
				displayName = target.getDisplayName();
			}
			else {
				displayName = args[0];
			}
			fakeMsg = fakeMsg.replace("{PLAYER}", displayName);
			fakeMsg = fakeMsg.replace("{MESSAGE}", msg);
			Bukkit.broadcastMessage(FakeTrollPlus.col(fakeMsg));

		return true;
	}
	
	

}
