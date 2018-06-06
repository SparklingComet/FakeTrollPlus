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
package org.shanerx.faketrollplus.events;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.utils.Message;
import org.shanerx.faketrollplus.core.data.TrollPlayer;

@SuppressWarnings("unused")
public class EffectListeners implements Listener {

	private FakeTrollPlus plugin;

	public EffectListeners(FakeTrollPlus instance) {

		plugin = instance;

	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());

		if (tp.isFrozen()) {
			e.setCancelled(true);
			if (!Message.getBool("freeze.do-move-attempt-msg"))
				return;
			p.sendMessage(Message.getString("freeze.move-attempt-msg"));
		}
	}

	@EventHandler
	public void onAsyncChatEvent(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());

		if (tp.chatIsGibberish()) {
			String randomString = Message.changeToGibberish(e.getMessage());
			e.setMessage(randomString);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());

		if (!tp.canPickup() || tp.isFakeWorldGuard()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerInventory(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());

		if (tp.invIsLocked()) {
			e.setCancelled(true);
			if (Message.getBool("inventory-lock.do-target-msg")) {
				p.sendMessage(Message.getString("inventory-lock.target-msg"));
			}
		}
	}

	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
		Player p = e.getPlayer();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());

		if (tp.hasBadfoodEffect()) {
			int duration = Message.getInt("badfood.duration") * 20;
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, duration, 1));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p.getUniqueId());
		Location loc = e.getBlock().getLocation();

		if (tp.isFakeWorldGuard()){
			tp.sendFakeWorldGuardMsg();
			e.setCancelled(true);
		}
		else if (tp.hasExplodeMinedBlocksEffect()) {
			float power = (float) Message.getDouble("explode-blocks.power");
			boolean setFire = Message.getBool("explode-blocks.set-fire");
			loc.getWorld().createExplosion(loc, power, setFire);
			e.setExpToDrop(0);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p);

		if (tp.isFakeWorldGuard()) {
			tp.sendFakeWorldGuardMsg();
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (e.getEntityType() != EntityType.PLAYER) {
			return;
		}
		Player p = (Player) e.getEntity();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p);

		if (tp.isFakeWorldGuard()) {
			tp.sendFakeWorldGuardMsg();
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryOpen(InventoryOpenEvent e) {
		if (e.getInventory().getType() != InventoryType.PLAYER) {
			return;
		}

		Player p = (Player) e.getPlayer();
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(p);

		if (tp.isFakeWorldGuard()) {
			tp.sendFakeWorldGuardMsg();
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPreLogin(PlayerLoginEvent e) {
		TrollPlayer tp = plugin.getUserCache().getTrollPlayer(e.getPlayer().getUniqueId());
		if (tp.isBlacklisted()) {
			e.disallow(Result.KICK_BANNED, Message.getString("blacklist"));
		}
	}
}