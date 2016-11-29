package me.github.ShanerX.FakeTrollPlus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.github.ShanerX.FakeTrollPlus.FakeTrollPlus;

public class RenameItems implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public RenameItems(FakeTrollPlus instance) {
		
		plugin = instance;
		
	}
	
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("renameitems")) {
			
			if (!this.plugin.getConfig().getBoolean("rename-items.enable")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
				return true;
			}
			
			if (!sender.hasPermission("faketroll.renameitems")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
				return true;
			}
			
			if (args.length < 1) {
				sender.sendMessage(ChatColor.GOLD + "Usage: /renameitems <target> [string]");
				return true;
			}
			
			Player target = Bukkit.getServer().getPlayer(args[0]);
			
			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("invalid-target")));
				return true;
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
					meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', string));
					item.setItemMeta(meta);
				}
				sender.sendMessage(ChatColor.GOLD + "You have renamed all the items of " + ChatColor.RED + args[0]
					+ ChatColor.GOLD + " to "+ ChatColor.RED + ChatColor.translateAlternateColorCodes('&', string));
			}
			
			if (plugin.getConfig().getBoolean("rename-items.do-target-msg")){
				target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("rename-items.target-msg")));
				return true;
			}
			
			return true;				
		}
		
		return true;
	}

}
