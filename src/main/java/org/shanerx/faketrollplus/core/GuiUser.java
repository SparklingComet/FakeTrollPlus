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
package org.shanerx.faketrollplus.core;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.shanerx.faketrollplus.core.data.TrollPlayer;
import org.shanerx.faketrollplus.utils.Message;


/**
 * This class is a wrapper, used to make a player open the trolling GUI.
 */
public class GuiUser {

	private TrollPlayer tp;

	/**
	 * Instantiates a new gui user. Requires a
	 * {@link org.shanerx.faketrollplus.core.data.TrollPlayer} that refers to the
	 * user who is being forced to open the inventory-gui.
	 * 
	 * @param tp
	 *            the {@link org.shanerx.faketrollplus.core.data.TrollPlayer}
	 *            instance
	 */
	public GuiUser(final TrollPlayer tp) {
		this.tp = tp;
	}

	/**
	 * Gets the player.
	 * 
	 * @return the player
	 */
	public TrollPlayer getPlayer() {
		return tp;
	}

	/**
	 * Sets the player.
	 * 
	 * @param tp
	 *            the new player
	 */
	public void setPlayer(final TrollPlayer tp) {
		this.tp = tp;
	}

	/**
	 * Gets the player list.
	 * 
	 * @param page
	 *            the page of the gui.
	 * @return an instance of {@link org.bukkit.inventory.Inventory}
	 */
	public Inventory getPlayerList(final int page) {
		int slots = Message.getInt("gui.rows") * 9 + 9;
		Inventory gui = Bukkit.createInventory(null, slots, Message.getString("gui.title"));
		Object[] list = Bukkit.getOnlinePlayers().toArray();

		ItemStack slime = new ItemStack(Material.SLIME_BALL, list.length);
		ItemMeta info = slime.getItemMeta();
		info.setDisplayName(gui.getTitle());
		info.setLore(Arrays.asList(ChatColor.BLUE + "Players online: " + ChatColor.RED + list.length,
				ChatColor.BLUE + "GUI Page: " + ChatColor.RED + page));
		slime.setItemMeta(info);
		gui.setItem(4, slime);

		ItemStack previous = new ItemStack(Material.PAPER);
		ItemMeta prev = previous.getItemMeta();
		prev.setDisplayName(ChatColor.RED + "Previous page");
		previous.setItemMeta(prev);

		ItemStack next = new ItemStack(Material.PAPER);
		ItemMeta nextMeta = next.getItemMeta();
		nextMeta.setDisplayName(ChatColor.RED + "Next page");
		next.setItemMeta(nextMeta);

		gui.setItem(0, previous);
		gui.setItem(8, next);

		try {
			for (int i = 9; i < slots; i++) {
				Player p = (Player) list[(page - 1) * slots + (i - 9)];
				ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1);
				SkullMeta sm = (SkullMeta) skull.getItemMeta();
				sm.setDisplayName(p.getName());
				sm.setOwner(p.getName());
				skull.setItemMeta(sm);
				gui.setItem(i, skull);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return gui;
	}

	/**
	 * Gets the effects-gui.
	 * 
	 * @param player
	 *            the victim's name. Used to represent the victim's skull.
	 * @return an instance of {@link org.bukkit.inventory.Inventory}
	 */
	public Inventory getEffects(final String player) {
		Inventory gui = Bukkit.createInventory(null, 9, Message.getString("gui.title"));

		ItemStack p = new ItemStack(Material.SKULL_ITEM, 1);
		SkullMeta skull = (SkullMeta) p.getItemMeta();
		skull.setOwner(player);
		skull.setDisplayName(ChatColor.YELLOW + "Troll options for: " + ChatColor.GREEN + player);
		p.setItemMeta(skull);
		
		ItemStack badfood = getEffectItem(TrollEffect.BADFOOD);

		ItemStack noPickup = getEffectItem(TrollEffect.NO_PICKUP);

		ItemStack freeze = getEffectItem(TrollEffect.FREEZE);

		ItemStack gibberish = getEffectItem(TrollEffect.GIBBERISH);

		ItemStack invLock = getEffectItem(TrollEffect.INVENTORY_LOCK);

		ItemStack explodeBlocks = getEffectItem(TrollEffect.EXPLODE_BLOCKS);

		ItemStack blacklist = getEffectItem(TrollEffect.BLACKLISTED);

		ItemStack fakeWorldGuard = getEffectItem(TrollEffect.FAKEWORLDGUARD);
		
		gui.setContents(
				new ItemStack[] { p, badfood, noPickup, freeze, gibberish, invLock, explodeBlocks, blacklist, fakeWorldGuard});
		return gui;
	}
	
	private ItemStack getEffectItem(TrollEffect effect) {
		ItemStack stack = new ItemStack(effect.guiItem(), 1);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(effect.toString());
		stack.setItemMeta(meta);
		return stack;
	}
}