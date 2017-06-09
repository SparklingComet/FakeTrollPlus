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

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.Message;

public class ReplaceInv implements CommandExecutor {

	private FakeTrollPlus plugin;

	public ReplaceInv(final FakeTrollPlus pl) {
		plugin = pl;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.replaceinv", Message.getBool("replaceinv.enable"), () -> args.length != 2 && args.length != 3)) {
			return false;
		}
		final Player target = plugin.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Message.PREFIX + Message.getString("invalid-target"));
			return true;
		}
		ItemStack is = null;
		int amount = 0;
		try {
			amount = args.length == 3 ? Integer.parseInt(args[2]) : 1;
			is = new ItemStack(Material.valueOf(args[1].toUpperCase()), amount);
		} catch (final Exception e) {
			sender.sendMessage(Message.PREFIX + Message.getString("replaceinv.invalid"));
			return false;
		}
		final Inventory inv = target.getInventory();
		for (int i = 0; i < inv.getSize(); i++) {
			if (inv.getItem(i) != null) {
				inv.setItem(i, is);
			}
		}
		sender.sendMessage(Message.PREFIX + Message.getString("replaceinv.sender").replace("%player%", target.getName()).replace("%item%", is.toString()));
		return true;
	}

}