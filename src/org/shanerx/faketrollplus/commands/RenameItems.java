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

public class RenameItems implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public RenameItems(FakeTrollPlus instance) {		
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if (!this.plugin.getConfig().getBoolean("rename-items.enable")) {
				sender.sendMessage(FakeTrollPlus.col(this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return false;
			}			
			if (!sender.hasPermission("faketroll.renameitems")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return false;
			}			
			if (args.length < 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /renameitems <target> [string]");
				return false;
			}			
			Player target = Bukkit.getServer().getPlayer(args[0]);			
			if (target == null) {
				sender.sendMessage(FakeTrollPlus.col(plugin.getConfig().getString("invalid-target")));
				return false;
			}
			
			final Inventory targetInv = target.getInventory();
			
			if (args.length == 1) {
				for ( ItemStack item : targetInv.getContents() ) {
					if (item == null) {
						continue;
					}
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(plugin.changeToGibberish(item.getItemMeta().getDisplayName()));
					item.setItemMeta(meta);
				}
				sender.sendMessage(ChatColor.GOLD + "You have changed all the names of " + ChatColor.RED + args[0] + ChatColor.GOLD + " to gibberish!");
			}
			else {
				String string = "";
				for ( int i = 1; i < args.length; i++ ) {
					string += args[i] + " ";
				}
				for ( ItemStack item : targetInv.getContents() ) {
					if (item == null) {
						continue;
					}
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(FakeTrollPlus.col(string));
					item.setItemMeta(meta);
				}
				sender.sendMessage(FakeTrollPlus.col("&6You have renamed all the items of &c" + args[0] + "&6 to &c" + string));
			}			
			if (plugin.getConfig().getBoolean("rename-items.do-target-msg")){
				target.sendMessage(FakeTrollPlus.col(plugin.getConfig().getString("rename-items.target-msg")));
			}
		
		return true;
	}

}