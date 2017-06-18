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

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.utils.Message;
import org.shanerx.faketrollplus.core.GuiUser;
import org.shanerx.faketrollplus.core.TrollEffect;
import static org.shanerx.faketrollplus.core.TrollEffect.*;
import org.shanerx.faketrollplus.core.TrollPlayer;

public class GuiListener implements Listener {
	
	private FakeTrollPlus ftp;
	
	public GuiListener(FakeTrollPlus ftp) {
		this.ftp = ftp;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGH)
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getClickedInventory() == null || e.getCurrentItem() == null) {
			return;
		}
		
		String name = e.getClickedInventory().getTitle();
		ItemStack item = e.getCurrentItem();
		int size = e.getClickedInventory().getSize();
		Player p = (Player) e.getWhoClicked();
		
		if (!ChatColor.stripColor(name).equals(ChatColor.stripColor(Message.getString("gui.title"))) || item == null) {
			return;
		}
		
		e.setCancelled(true);
		
		if (size == 9) {
			SkullMeta meta = (SkullMeta) e.getClickedInventory().getItem(0).getItemMeta();
			OfflinePlayer op = Bukkit.getOfflinePlayer(meta.getOwner());
			TrollPlayer tp = ftp.getUserCache().getTrollPlayer(op.getUniqueId());
			
			switch(item.getType()) {
			case SKULL_ITEM:
				Set<TrollEffect> activeEffects = ftp.getUserCache().getTrollPlayer(op.getUniqueId()).activeEffects();

				StringBuilder sb = new StringBuilder();
				boolean hasEffects = activeEffects.size() > 0;
				for (TrollEffect te : activeEffects) {
					sb.append("&a").append(te.toString()).append("&e, ");
				}
				p.sendMessage(col("&eActive effects on &6" + op.getName() + "&e: " +
						(hasEffects ? sb.toString().substring(0, sb.toString().length() - 4) : "")));
				return;
				
			case ROTTEN_FLESH:
				if (!BADFOOD.isEnabled()) {
					p.sendMessage(Message.GUI_EFFECT_DISABLED.toString());
					return;
				} else if (!tp.canBeTrolledBy(p)) {
					p.sendMessage(Message.PREFIX + Message.getString("no-admin-trolling"));
					return;
				}
				
				tp.setBadfoodEffect(!tp.hasBadfoodEffect());
				p.sendMessage(col((tp.hasBadfoodEffect() ? "&cEnabled": "&cDisabled") + " &6Badfood &eeffect for player &6" + op.getName()));
				return;
				
			case EXP_BOTTLE:
				if (!NO_PICKUP.isEnabled()) {
					p.sendMessage(Message.GUI_EFFECT_DISABLED.toString());
					return;
				} else if (!tp.canBeTrolledBy(p)) {
					p.sendMessage(Message.PREFIX + Message.getString("no-admin-trolling"));
					return;
				}
				
				tp.setPickup(!tp.canPickup());
				p.sendMessage(col((!tp.canPickup() ? "&cEnabled": "&cDisabled") + " &6No-Pickup &eeffect for player &6" + op.getName()));
				return;
				
			case IRON_BOOTS:
				if (!FREEZE.isEnabled()) {
					p.sendMessage(Message.GUI_EFFECT_DISABLED.toString());
					return;
				} else if (!tp.canBeTrolledBy(p)) {
					p.sendMessage(Message.PREFIX + Message.getString("no-admin-trolling"));
					return;
				}
				
				tp.setFrozen(!tp.isFrozen());
				p.sendMessage(col((tp.isFrozen() ? "&cEnabled": "&cDisabled") + " &6Freeze &eeffect for player &6" + op.getName()));
				return;
				
			case BOOK:
				if (!GIBBERISH.isEnabled()) {
					p.sendMessage(Message.GUI_EFFECT_DISABLED.toString());
					return;
				} else if (!tp.canBeTrolledBy(p)) {
					p.sendMessage(Message.PREFIX + Message.getString("no-admin-trolling"));
					return;
				}
				
				tp.setGibberishChat(!tp.chatIsGibberish());
				p.sendMessage(col((tp.chatIsGibberish() ? "&cEnabled": "&cDisabled") + " &6Gibberish &eeffect for player &6" + op.getName()));
				return;
				
			case TRAPPED_CHEST:
				if (!INVENTORY_LOCK.isEnabled()) {
					p.sendMessage(Message.GUI_EFFECT_DISABLED.toString());
					return;
				} else if (!tp.canBeTrolledBy(p)) {
					p.sendMessage(Message.PREFIX + Message.getString("no-admin-trolling"));
					return;
				}
				
				tp.setInvLocked(!tp.invIsLocked());
				p.sendMessage(col((tp.invIsLocked() ? "&cEnabled": "&cDisabled") + " &6Inventory-Lock &eeffect for player &6" + op.getName()));
				return;
				
			case TNT:
				if (!EXPLODE_BLOCKS.isEnabled()) {
					p.sendMessage(Message.GUI_EFFECT_DISABLED.toString());
					return;
				} else if (!tp.canBeTrolledBy(p)) {
					p.sendMessage(Message.PREFIX + Message.getString("no-admin-trolling"));
					return;
				}
				
				tp.setExplodeMinedBlocksEffect(!tp.hasExplodeMinedBlocksEffect());
				p.sendMessage(col((tp.hasExplodeMinedBlocksEffect() ? "&cEnabled": "&cDisabled") + " &6Explode-Blocks &eeffect &efor player &6" + op.getName()));
				return;
				
			case IRON_DOOR:
				if (!BLACKLISTED.isEnabled()) {
					p.sendMessage(Message.GUI_EFFECT_DISABLED.toString());
					return;
				} else if (!tp.canBeTrolledBy(p)) {
					p.sendMessage(Message.PREFIX + Message.getString("no-admin-trolling"));
					return;
				}
				
				tp.setBlacklisted(!tp.isBlacklisted());
				p.sendMessage(col((tp.isBlacklisted() ? "&cBlacklisted": "&cUn-blacklisted") + "  &eplayer &6" + op.getName()));
				Bukkit.getPlayer(op.getUniqueId()).kickPlayer(Message.getString("blacklist"));
				return;
				
/*			case LEASH:
				if (!FREEZE_CHAT.isEnabled()) {
					p.sendMessage(Message.GUI_EFFECT_DISABLED.toString());
					return;
				} else if (!tp.canBeTrolledBy(p)) {
					p.sendMessage(Message.PREFIX + Message.getString("no-admin-trolling"));
					return;
				}
				
				if (ftp.useProtocolLib()) {
					tp.freezeChat(!tp.chatIsFrozen());
					p.sendMessage(col((tp.chatIsFrozen() ? "&cEnabled": "&cDisabled") + "  &6Chat-Freeze &eeffect for &6" + op.getName()));
					return;
				} else {
					p.sendMessage(col("You need to install ProtocolLib for this effect to worj!"));
					return;
				}*/
			default:
				return;
			}
		} else if (size != Message.getInt("gui.rows") * 9 + 9) {
			return;
			
		} else if (item.getType() == Material.SKULL_ITEM) {
			SkullMeta skull = (SkullMeta) item.getItemMeta();
			String victim = skull.getOwner();
			TrollPlayer troll = ftp.getUserCache().getTrollPlayer(p.getUniqueId());
			GuiUser guiUser = new GuiUser(troll);
			p.openInventory(guiUser.getEffects(victim));
			
		} else if (item.getType() == Material.PAPER) {
			int page = Integer.parseInt(e.getClickedInventory().getItem(4).getItemMeta()
					.getLore().get(1).split(" ")[2].substring(2));
			
			if (item.getItemMeta().getDisplayName().contains("Next")) {
				p.openInventory(new GuiUser(ftp.getUserCache().getTrollPlayer(p.getUniqueId())).getPlayerList(++page));
			} else {
				if (page != 1) {
					p.openInventory(new GuiUser(ftp.getUserCache().getTrollPlayer(p.getUniqueId())).getPlayerList(--page));
				}
			}
		}
	}
	
	// Just to shorten the Message.col(x) method.
	private String col(String msg) {
		return Message.PREFIX + Message.col(msg);
	}
}