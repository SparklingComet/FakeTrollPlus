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
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.utils.Message;

public class Forcecmd implements CommandExecutor {
	
	private FakeTrollPlus plugin;

	public Forcecmd(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.forcecmd", Message.getBool("force-cmd.enable"), () -> args.length < 2)) {
			return false;
		}
		
		final Player target = plugin.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Message.PREFIX + Message.getString("invalid-target"));
			return false;
		}
		
		StringBuilder forceCmd = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
			forceCmd.append(args[i]).append(" ");
		}
		
		sender.sendMessage(Message.PREFIX + Message.getString("force-cmd.sender").replace("%player%", target.getName()).replace("%command%", forceCmd.toString().trim()));
		plugin.getServer().dispatchCommand(target, forceCmd.toString().trim());
		return true;
	}

}