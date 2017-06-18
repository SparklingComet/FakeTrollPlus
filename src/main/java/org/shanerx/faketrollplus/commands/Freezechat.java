/*
 *
 *  *     Copyright 2016-2017 SparklingComet @ http://shanerx.org
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *          http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.shanerx.faketrollplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.core.TrollPlayer;
import org.shanerx.faketrollplus.utils.Message;

public class Freezechat implements CommandExecutor {
	
	private FakeTrollPlus plugin;
	
	public Freezechat(FakeTrollPlus plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.freezechat", Message.getBool("freeze-chat.enable"), () -> args.length != 1)) {
			return false;
		}
		
		if (!plugin.USE_PROTOCOL_LIB) {
			sender.sendMessage(Message.NO_PROTOCOL_LIB.toString());
			return false;
		}
		
		final Player target = plugin.getTarget(args[0]);
		if (target == null) {
			sender.sendMessage(Message.PREFIX + Message.getString("invalid-target"));
			return false;
		}
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(target);
		
		if (!tp.chatIsFrozen() && tp.canBeTrolledBy(sender)) {
			sender.sendMessage(Message.PREFIX + Message.getString("no-admin-trolling"));
			return false;
		}
		tp.freezeChat(!tp.chatIsFrozen());
		
		sender.sendMessage(Message.PREFIX + Message.getString("freeze-chat.sender").replace("%player%", target.getName()));
		if (Message.getBool("freeze-chat.target.do-target-msg")) {
			if (tp.chatIsFrozen()) {
				target.sendMessage(Message.getString("freeze-chat.target.toggle-on"));
			} else {
				target.sendMessage(Message.getString("freeze-chat.target.toggle-off"));
			}
		}
		return false;
	}
}