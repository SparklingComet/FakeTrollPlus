/*
 *     Copyright 2016-2017 ShanerX @ http://shanerx.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class ReplaceInv implements CommandExecutor {
	
	private FakeTrollPlus plugin;
	
	public ReplaceInv(FakeTrollPlus pl) {
		plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!this.plugin.getConfig().getBoolean("enable-replaceinv")) {
			sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
			return false;
		}
		if (!sender.hasPermission("faketroll.replaceinv")) {
			sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
			return false;
		}
		if (args.length != 2 && args.length != 3) {
			sender.sendMessage(ChatColor.GOLD + "Usage: /replaceinv <target> <item> [amount]");
			return false;
		}
		Player target = this.plugin.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("invalid-target")));
			return true;
		}
		ItemStack is = null;
		int amount = 0;
		try {
			amount = args.length == 3 ? Integer.parseInt(args[2]) : 1;
			is = new ItemStack(Material.valueOf(args[1].toUpperCase()), amount);
		} catch(Exception e) {
			sender.sendMessage(FakeTrollPlus.col("&6Invalid item!"));
			return false;
		}
		Inventory inv = target.getInventory();
		for (int i = 0; i < inv.getSize(); i++) {
			if (inv.getItem(i) != null) {
				inv.setItem(i, is);
			}
		}
		sender.sendMessage(FakeTrollPlus.col("&6You replaced all &c" + args[0] + "&6's items with " + is.getType().name() + " x " + Integer.toString(amount) + "&6!"));
		return true;
	}

}
