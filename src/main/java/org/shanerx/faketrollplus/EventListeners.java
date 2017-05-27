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
package org.shanerx.faketrollplus;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.shanerx.faketrollplus.core.TrollPlayer;

public class EventListeners implements Listener {

	FakeTrollPlus plugin;

	public EventListeners(FakeTrollPlus instance) {

		plugin = instance;

	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());
		if (tp.isFrozen()) {
			e.setCancelled(true);
			if (!this.plugin.getConfig().getBoolean("freeze.do-move-attempt-msg"))
				return;
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					this.plugin.getConfig().getString("freeze.move-attempt-msg")));
		}

	}

	@EventHandler
	public void onAsyncChatEvent(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());
		if (tp.chatIsGibberish()) {
			String randomString = plugin.changeToGibberish(e.getMessage());
			e.setMessage(randomString);
		}
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());
		if (!tp.canPickup()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerInventory(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());
		if (tp.invIsLocked()) {
			e.setCancelled(true);
			if (plugin.getConfig().getBoolean("inventory-lock.do-target-msg")) {
				p.sendMessage(FakeTrollPlus.col(plugin.getConfig().getString("inventory-lock.target-msg")));
			}
		}
	}

	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
		Player p = e.getPlayer();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());
		if (tp.hasBadfoodEffect()) {
			int duration = plugin.getConfig().getInt("badfood.duration") * 20;
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, duration, 1));
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());
		Location loc = e.getBlock().getLocation();
		if (tp.hasExplodeMinedBlocksEffect()) {
			float power = (float) plugin.getConfig().getDouble("explode-blocks.power");
			boolean setFire = plugin.getConfig().getBoolean("explode-blocks.set-fire");
			loc.getWorld().createExplosion(loc, power, setFire);
		}
	}

}