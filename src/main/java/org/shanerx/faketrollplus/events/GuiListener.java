package org.shanerx.faketrollplus.events;

import java.util.Collection;

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
import org.shanerx.faketrollplus.Message;
import org.shanerx.faketrollplus.core.GuiType;
import org.shanerx.faketrollplus.core.GuiUser;
import org.shanerx.faketrollplus.core.TrollEffect;
import org.shanerx.faketrollplus.core.TrollPlayer;

public class GuiListener implements Listener {
	
	private FakeTrollPlus ftp;
	
	public GuiListener(FakeTrollPlus ftp) {
		this.ftp = ftp;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGH)
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getClickedInventory() == null) {
			return;
		}
		String name = e.getClickedInventory().getTitle();
		ItemStack item = e.getCurrentItem();
		int size = e.getClickedInventory().getSize();
		Player p = (Player) e.getWhoClicked();
		
		if (!ChatColor.stripColor(name).startsWith("FakeTrollPlus - Troll GUI") || item == null) {
			return;
		}
		e.setCancelled(true);
		
		if (size == 9) {
			SkullMeta meta = (SkullMeta) e.getClickedInventory().getItem(0).getItemMeta();
			OfflinePlayer op = Bukkit.getOfflinePlayer(meta.getOwner());
			TrollPlayer tp = ftp.getUserCache().getTrollPlayer(op.getUniqueId());
			
			switch(item.getType()) {
			case SKULL_ITEM:
				Collection<TrollEffect> activeEffects = ftp.getUserCache().getTrollPlayer(op.getUniqueId()).activeEffects();

				StringBuffer sb = new StringBuffer();
				boolean hasEffects = activeEffects.size() > 0;
				for (TrollEffect te : activeEffects) {
					sb.append("&a").append(te.toString()).append("&e, ");
				}
				p.sendMessage(col("&eActive effects on &6" + op.getName() + "&e: " +
						(hasEffects ? sb.toString().substring(0, sb.toString().length() - 4) : "")));
				return;
				
			case ROTTEN_FLESH:
				tp.setBadfoodEffect(!tp.hasBadfoodEffect());
				p.sendMessage(col((tp.hasBadfoodEffect() ? "&cEnabled": "&cDisabled") + " &6Badfood &eeffect for player &6" + op.getName()));
				return;
				
			case EXP_BOTTLE:
				tp.setPickup(!tp.canPickup());
				p.sendMessage(col((!tp.canPickup() ? "&cEnabled": "&cDisabled") + " &6No-Pickup &eeffect for player &6" + op.getName()));
				return;
				
			case IRON_BOOTS:
				tp.setFrozen(!tp.isFrozen());
				p.sendMessage(col((tp.isFrozen() ? "&cEnabled": "&cDisabled") + " &6Freeze &eeffect for player &6" + op.getName()));
				return;
				
			case BOOK:
				tp.setGibberishChat(!tp.chatIsGibberish());
				p.sendMessage(col((tp.chatIsGibberish() ? "&cEnabled": "&cDisabled") + " &6Gibberish &eeffect for player &6" + op.getName()));
				return;
				
			case TRAPPED_CHEST:
				tp.setInvLocked(!tp.invIsLocked());
				p.sendMessage(col((tp.invIsLocked() ? "&cEnabled": "&cDisabled") + " &6Inventory-Lock &eeffect for player &6" + op.getName()));
				return;
				
			case TNT:
				tp.setExplodeMinedBlocksEffect(!tp.hasExplodeMinedBlocksEffect());
				p.sendMessage(col((tp.hasExplodeMinedBlocksEffect() ? "&cEnabled": "&cDisabled") + " &6Explode-Blocks &eeffect &efor player &6" + op.getName()));
				return;
				
			case IRON_DOOR:
				tp.setBlacklisted(!tp.isBlacklisted());
				p.sendMessage(col((tp.isBlacklisted() ? "&cBlacklisted": "&cUn-blacklisted") + "  &eplayer &6" + op.getName()));
				Bukkit.getPlayer(op.getUniqueId()).kickPlayer(Message.getString("blacklist"));
				return;
				
			default:
				return;
			}
		} else if (size != 54) {
			return;
			
		} else if (item.getType() == Material.SKULL_ITEM) {
			SkullMeta skull = (SkullMeta) item.getItemMeta();
			String victim = skull.getOwner();
			TrollPlayer troll = ftp.getUserCache().getTrollPlayer(p.getUniqueId());
			GuiUser guiUser = new GuiUser(troll);
			p.openInventory(guiUser.getEffects(victim));
			return;
			
		} else if (item.getType() == Material.PAPER) {
			int page = Integer.parseInt(e.getClickedInventory().getItem(4).getItemMeta()
					.getLore().get(1).split(" ")[2].substring(2));
			
			if (item.getItemMeta().getDisplayName().contains("Next")) {
				p.openInventory(new GuiUser(ftp.getUserCache().getTrollPlayer(p.getUniqueId())).getPlayerList(++page));
				return;
				
			} else {
				if (page == 1)
					return;
				p.openInventory(new GuiUser(ftp.getUserCache().getTrollPlayer(p.getUniqueId())).getPlayerList(--page));
				return;
			}
		}
	}
	
	// Just to shorten the Message.col(x) method.
	private String col(String msg) {
		return Message.col(msg);
	}

}