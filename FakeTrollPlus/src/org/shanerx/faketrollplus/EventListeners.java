package org.shanerx.faketrollplus;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

	public class EventListeners implements Listener {
		
		FakeTrollPlus plugin;
		
		public EventListeners(FakeTrollPlus instance) {
			
			plugin = instance;

		}
				
		@EventHandler
		public void onPlayerMove(PlayerMoveEvent e) {
			
			Player p = e.getPlayer();
			if ((p.hasPermission("faketroll.exempt.freeze")) && (this.plugin.getConfig().getBoolean("exempt-admins"))) {
				return;
			}
			if (FakeTrollPlus.frozenPlayers.contains(p.getName())) {
				e.setCancelled(true);
				if (!this.plugin.getConfig().getBoolean("freeze.do-move-attempt-msg")) return;
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("freeze.move-attempt-msg")));
			}
			
		}
		
		
		@EventHandler
		public void onAsyncChatEvent(AsyncPlayerChatEvent e) {
			
			if (FakeTrollPlus.gibberish.contains(e.getPlayer().getName())) {	
				String randomString = plugin.changeToGibberish(e.getMessage());
				e.setMessage(randomString);
			}
		}
		
		
		@EventHandler
		public void onPickup(PlayerPickupItemEvent e) {
			if (FakeTrollPlus.noPickup.contains(e.getPlayer().getUniqueId())) {
				e.setCancelled(true);
			}
		}
		
		
		@EventHandler
		public void onPlayerInventory(InventoryClickEvent e) {
			Player p = (Player) e.getWhoClicked();
			if (FakeTrollPlus.inventoryLock.contains(p.getUniqueId())) {
				e.setCancelled(true);
				if (plugin.getConfig().getBoolean("inventory-lock.do-target-msg")) {
					p.sendMessage(FakeTrollPlus.col(plugin.getConfig().getString("inventory-lock.target-msg")));
				}
			}
		}
		
	}