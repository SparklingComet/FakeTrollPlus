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