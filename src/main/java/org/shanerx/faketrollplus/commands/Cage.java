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
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.utils.Message;

public class Cage implements CommandExecutor {
	
	private FakeTrollPlus plugin;
	
	public Cage(final FakeTrollPlus instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.cage", Message.getBool("cage.enable"), () -> args.length != 1)) {
			return false;
		}
		
		final Player target = plugin.getTarget(args[0]);
		if (target == null) {
			sender.sendMessage(Message.PREFIX + Message.getString("invalid-target"));
			return false;
		}
		
		spawnCage(target);
		sender.sendMessage(Message.PREFIX + Message.getString("cage.sender").replace("%player%", target.getName()));
		return true;
	}
	
	@SuppressWarnings("deprecation")
	private void spawnCage(LivingEntity target) {
		// Filling the floor:
		Location floor = target.getLocation().add(0, -1, 0);
		for (int x = -1; x < 2; x++) {
			for (int z = -1; z < 2; z++) {
				floor.clone().add(x, 0, z).getBlock().setType(Material.BEDROCK);
			}
		}
		
		// Filling the ceiling:
		Location ceil = target.getLocation().add(0, 2, 0);
		for (int x = -1; x < 2; x++) {
			for (int z = -1; z < 2; z++) {
				ceil.clone().add(x, 0, z).getBlock().setType(Material.BEDROCK);
			}
		}
		
		// Making sure there are no obstructing blocks:
		Location middle = target.getLocation();
		middle.getBlock().setType(Material.AIR);
		middle.clone().add(0, 1, 0).getBlock().setType(Material.AIR);
		
		for (int x = -1; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				for (int z = -1; z < 2; z++) {
					if (x == 0 && z == 0) continue;
					middle.clone().add(x, y, z).getBlock().setType(Material.getMaterial(101));
				}
			}
		}
	}
}