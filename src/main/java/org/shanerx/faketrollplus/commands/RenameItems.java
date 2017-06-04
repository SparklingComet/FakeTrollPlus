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
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.Message;

public class RenameItems implements CommandExecutor {

	FakeTrollPlus plugin;

	public RenameItems(final FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!Message.verifyCommandSender(cmd, sender, "faketroll.renameitems", Message.getBool("rename-items.enable"), () -> args.length < 1)) {
			return false;
		}
		final Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Message.getString("invalid-target"));
			return false;
		}
		final Inventory targetInv = target.getInventory();

		if (args.length == 1) {
			for (final ItemStack item : targetInv.getContents()) {
				if (item == null) {
					continue;
				}
				final ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(Message.changeToGibberish(item.getItemMeta().getDisplayName()));
				item.setItemMeta(meta);
			}
			sender.sendMessage(ChatColor.GOLD + "You have changed all the names of " + ChatColor.RED + args[0]
					+ ChatColor.GOLD + " to gibberish!");
		} else {
			String string = "";
			for (int i = 1; i < args.length; i++) {
				string += args[i] + " ";
			}
			for (final ItemStack item : targetInv.getContents()) {
				if (item == null) {
					continue;
				}
				final ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(Message.col(string));
				item.setItemMeta(meta);
			}
			sender.sendMessage(Message.col("&6You have renamed all the items of &c" + args[0] + "&6 to &c" + string));
		}
		if (Message.getBool("rename-items.do-target-msg")) {
			target.sendMessage(Message.getString("rename-items.target-msg"));
		}
		return true;
	}

}