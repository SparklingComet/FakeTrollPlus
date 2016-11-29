package me.github.ShanerX.FakeTrollPlus;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

	public class EventListeners implements Listener {
		
		FakeTrollPlus plugin;
		
		public EventListeners(FakeTrollPlus instance) {
			
			plugin = instance;

		}
		
		
		@EventHandler
		public void onPlayerJoin(PlayerJoinEvent e) {
			
			Player p = e.getPlayer();
			if ((p.isOp()) && (this.plugin.getConfig().getBoolean("motd.motd-to-ops"))) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("motd.motd")));
				return;
			}
			if ((!p.isOp()) && (this.plugin.getConfig().getBoolean("motd.motd-to-players"))) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("motd1")));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("motd2")));
				return;
			}

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
		
	}