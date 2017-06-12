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

public class Fakemsg implements CommandExecutor {
	
	private FakeTrollPlus plugin;

	public Fakemsg(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.fakemsg", Message.getBool("fake-msg.enable"), () -> args.length < 3)) {
			return false;
		}
		
		final Player target = plugin.getTarget(args[0]);
		if (target == null) {
			sender.sendMessage(Message.PREFIX + Message.getString("invalid-target"));
			return false;
		}
		
		String fakeName;
		try {
			final Player fakePlayer = plugin.getServer().getPlayer(args[1]);
			fakeName = fakePlayer.getDisplayName();
		} catch (final NullPointerException e) {
			fakeName = args[1];
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 2; i < args.length; i++) {
			sb.append(args[i]).append(" ");
		}
		
		String fakeMsg = Message.getString("fake-msg.format");
		fakeMsg = fakeMsg.replace("{PLAYER}", fakeName);
		fakeMsg = fakeMsg.replace("{MESSAGE}", sb.toString().trim());
		
		target.sendMessage(Message.col(fakeMsg));
		sender.sendMessage(Message.PREFIX + Message.getString("fake-msg.sender").replace("%player%", target.getName()));
		return true;
	}

}