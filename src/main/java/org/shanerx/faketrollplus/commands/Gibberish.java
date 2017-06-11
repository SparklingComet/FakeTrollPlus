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

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.utils.Message;
import org.shanerx.faketrollplus.core.TrollPlayer;

public class Gibberish implements CommandExecutor {
	
	private FakeTrollPlus plugin;

	public Gibberish(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.gibberish", Message.getBool("gibberish.enable"), () -> args.length != 1)) {
			return false;
		}
		
		final Player p = Bukkit.getPlayer(args[0]);
		if (p == null) {
			sender.sendMessage(Message.PREFIX + Message.getString("invalid-target"));
			return false;
		}
		
		final TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());
		if (tp.chatIsGibberish()) {
			tp.setGibberishChat(false);
			sender.sendMessage(Message.PREFIX + Message.getString("gibberish.sender.toggle-off").replace("%player%", p.getName()));
			return true;
		}
		
		tp.setGibberishChat(true);
		sender.sendMessage(Message.PREFIX + Message.getString("gibberish.sender.toggle-on").replace("%player%", p.getName()));
		return true;
	}

}