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

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.utils.Message;

public class Swap implements CommandExecutor {

	private FakeTrollPlus plugin;

	public Swap(final FakeTrollPlus pl) {
		plugin = pl;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.swap", Message.getBool("swap.enable"), () -> args.length != 2)) {
			return false;
		}
		
		final Player t1 = plugin.getTarget(args[0]);
		final Player t2 = plugin.getTarget(args[1]);
		
		if (t1 == null || t2 == null) {
			sender.sendMessage(Message.PREFIX + Message.getString("swap.invalid-target"));
			return false;
		}
		
		final Location loc1 = t1.getLocation();
		final Location loc2 = t2.getLocation();
		
		t1.teleport(loc2);
		t2.teleport(loc1);
		
		if (Message.getBool("swap.send-message-to-target")) {
			final String msg = Message.getString("swap.target-msg");
			t1.sendMessage(Message.col(msg));
			t2.sendMessage(Message.col(msg));
		}
		
		sender.sendMessage(Message.PREFIX + Message.getString("swap.sender").replace("%player1%",
				t1.getName()).replace("%player2%", t2.getName()));
		return true;
	}
}