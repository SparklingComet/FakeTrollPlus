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

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.utils.Message;

public class Anvil implements CommandExecutor {

	private FakeTrollPlus plugin;

	public Anvil(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.anvil", Message.getBool("anvil.enable"), () -> args.length != 1)) {
			return false;
		}
		
		final Player target = plugin.getTarget(args[0]);
		if (target == null) {
			sender.sendMessage(Message.PREFIX + Message.getString("invalid-target"));
			return false;
		}
		
		final int x = target.getLocation().getBlockX();
		final int y = target.getLocation().getBlockY();
		final int z = target.getLocation().getBlockZ();
		
		final World world = target.getLocation().getWorld();
		final Block block = world.getBlockAt(x, y + 2, z);
		
		block.setTypeId(145);
		sender.sendMessage(Message.PREFIX + Message.getString("anvil.sender").replace("%player%", target.getName()));
		return true;
	}
}