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

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.Message;

public class Poison implements CommandExecutor {
	
	private FakeTrollPlus plugin;

	public Poison(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.poison", Message.getBool("poison.enable"), () -> args.length != 2)) {
			return false;
		}
		
		final Player target = plugin.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Message.PREFIX + Message.getString("invalid-target"));
			return false;
		}
		
		try {
			final int time = Integer.parseInt(args[1]);
			if (target.getGameMode() == GameMode.CREATIVE || target.getGameMode() == GameMode.SPECTATOR) {
				sender.sendMessage(Message.PREFIX + Message.getString("poison.failure"));
				return false;
			}
			sender.sendMessage(Message.PREFIX + Message.getString("poison.sender")
					.replace("%player%", target.getName())
					.replace("%time%", Integer.toString(time)));
			target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time * 20, 2));

		} catch (final NumberFormatException e) {
			sender.sendMessage(Message.PREFIX + Message.getString("poison.time"));
			return false;
		}
		return true;
	}

}