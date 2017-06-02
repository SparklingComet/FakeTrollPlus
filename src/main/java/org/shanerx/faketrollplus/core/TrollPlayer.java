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
package org.shanerx.faketrollplus.core;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.shanerx.faketrollplus.FakeTrollPlus;
import static org.shanerx.faketrollplus.core.TrollEffect.*;

@SuppressWarnings("unchecked")
public class TrollPlayer {

	private FakeTrollPlus ftp;
	private UUID uuid;

	TrollPlayer(UUID uuid, FakeTrollPlus plugin) {
		this.uuid = uuid;
		ftp = plugin;
	}

	TrollPlayer(OfflinePlayer op, FakeTrollPlus plugin) {
		this(op.getUniqueId(), plugin);
	}

	TrollPlayer(Player p, FakeTrollPlus plugin) {
		this(p.getUniqueId(), plugin);
	}

	/**
	 * Gets the {@link java.util.UUID} of the player associated with the
	 * TrollPlayer instance.
	 * 
	 * @return the UUID.
	 */
	public UUID getUUID() {
		return uuid;
	}

	/**
	 * Gets the instance {@link org.bukkit.OfflinePlayer} associated with the
	 * TrollPlayer instance.
	 * 
	 * @return the {@link org.bukkit.OfflinePlayer}.
	 */
	public OfflinePlayer getOfflinePlayer() {
		OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
		return op;
	}

	/**
	 * Gets the name of the player.
	 * 
	 * @return the name of the player.
	 */
	public String getName() {
		return getOfflinePlayer().getName();
	}

	/**
	 * Gets the {@link org.json.simple.JSONObject} holding all the information
	 * about the player, which is collected by the plugin.
	 * 
	 * @return the {@link org.json.simple.JSONObject} with the user's data.
	 */
	public JSONObject getUserInfo() {
		JSONObject root = ftp.getUserCache().getJSONConfiguration();
		JSONObject userInfo = (JSONObject) root.get(uuid.toString());
		return userInfo == null ? new JSONObject() : userInfo;
	}

	/**
	 * Whether the player can be trolled. <br>
	 * The trolling player needs to have proper permission.
	 * 
	 * @param isConsole
	 *            Whether the trolling entity is the console.
	 * @return {@code true} if the player can be trolled.
	 */
	public boolean canTroll(boolean isConsole) {
		if (!ftp.getConfig().getBoolean("exempt-admins"))
			return false;
		if (isConsole && ftp.getConfig().getBoolean("console-bypass"))
			return true;
		return !Bukkit.getPlayer(uuid).hasPermission("faketroll.exempt");
	}

	/**
	 * Whether the player is frozen.
	 * 
	 * @return {@code true} if frozen.
	 */
	public boolean isFrozen() {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		try {
			return root.containsKey(uuid.toString()) && (boolean) userInfo.get("isFrozen");
		} catch (NullPointerException npe) {
			return false;
		}
	}

	/**
	 * Whether the trolled player's chat messages will be changed to gibberish.
	 * 
	 * @return {@code true} if effect is enabled.
	 */
	public boolean chatIsGibberish() {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		try {
			return root.containsKey(uuid.toString()) && (boolean) userInfo.get("chatIsGibberish");
		} catch (NullPointerException npe) {
			return false;
		}
	}

	/**
	 * Whether the player's inventory is locked.
	 * 
	 * @return {@code true} if yes.
	 */
	public boolean invIsLocked() {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		try {
			return root.containsKey(uuid.toString()) && (boolean) userInfo.get("invIsLocked");
		} catch (NullPointerException npe) {
			return false;
		}
	}

	/**
	 * Whether the player can pickup items.
	 * 
	 * @return {@code false} if the No-Pickup effect is
	 *         <strong>enabled</strong>.
	 */
	public boolean canPickup() {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		try {
			if (!root.containsKey(uuid.toString())) {
				return true;
			}
			return (boolean) userInfo.get("canPickup");
		} catch (NullPointerException npe) {
			return false;
		}
	}

	/**
	 * Whether the player will be poisoned by any item they consume.
	 * 
	 * @return {@code true} if yes.
	 */
	public boolean hasBadfoodEffect() {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		try {
			return root.containsKey(uuid.toString()) && (boolean) userInfo.get("hasBadfoodEffect");
		} catch (NullPointerException npe) {
			return false;
		}
	}

	/**
	 * Whether mining a block will generate an explosion.
	 * 
	 * @return {@code true} if the player is affected.
	 */
	public boolean hasExplodeMinedBlocksEffect() {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		try {
			return root.containsKey(uuid.toString()) && (boolean) userInfo.get("hasExplodeMinedBlocksEffect");
		} catch (NullPointerException npe) {
			return false;
		}
	}

	/**
	 * Whether the player is blacklisted by the plugin.
	 * 
	 * @return {@code true} if the player is blacklisted.
	 */
	public boolean isBlacklisted() {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		try {
			return root.containsKey(uuid.toString()) && (boolean) userInfo.get("isBlacklisted");
		} catch (NullPointerException npe) {
			return false;
		}
	}

	/**
	 * Sets the player's freeze-status.
	 * 
	 * @param isFrozen
	 *            whether or not the player should freeze.
	 */
	public void setFrozen(boolean isFrozen) {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		userInfo.put("isFrozen", isFrozen);
		root.put(uuid.toString(), userInfo);
		try {
			PrintWriter write = new PrintWriter(uc.getUserCacheFile());
			write.write(root.toJSONString());
			write.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds or removes the Gibberish effect.
	 * 
	 * @param chatIsGibberish
	 *            whether the player should talk gibberish.
	 */
	public void setGibberishChat(boolean chatIsGibberish) {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		userInfo.put("chatIsGibberish", chatIsGibberish);
		root.put(uuid.toString(), userInfo);
		try {
			PrintWriter write = new PrintWriter(uc.getUserCacheFile());
			write.write(root.toJSONString());
			write.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the lock-state on the player's inventory.
	 * 
	 * @param invIsLocked
	 *            whether the player should be locked out or not.
	 */
	public void setInvLocked(boolean invIsLocked) {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		userInfo.put("invIsLocked", invIsLocked);
		root.put(uuid.toString(), userInfo);
		try {
			PrintWriter write = new PrintWriter(uc.getUserCacheFile());
			write.write(root.toJSONString());
			write.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the state of the No-Pickup effect.
	 * 
	 * @param canPickup
	 *            whether the player should be able to pickup dropped items.
	 */
	public void setPickup(boolean canPickup) {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		userInfo.put("canPickup", canPickup);
		root.put(uuid.toString(), userInfo);
		try {
			PrintWriter write = new PrintWriter(uc.getUserCacheFile());
			write.write(root.toJSONString());
			write.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds or lifts the Badfood effect.
	 * 
	 * @param hasBadfoodEffect
	 *            whether the player should be poisoned by food.
	 */
	public void setBadfoodEffect(boolean hasBadfoodEffect) {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		userInfo.put("hasBadfoodEffect", hasBadfoodEffect);
		root.put(uuid.toString(), userInfo);
		try {
			PrintWriter write = new PrintWriter(uc.getUserCacheFile());
			write.write(root.toJSONString());
			write.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add or lift the effect from the player.
	 * 
	 * @param hasExplodeMinedBlocksEffect
	 *            {@code true} if the blocks should explode.
	 */
	public void setExplodeMinedBlocksEffect(boolean hasExplodeMinedBlocksEffect) {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		userInfo.put("hasExplodeMinedBlocksEffect", hasExplodeMinedBlocksEffect);
		root.put(uuid.toString(), userInfo);
		try {
			PrintWriter write = new PrintWriter(uc.getUserCacheFile());
			write.write(root.toJSONString());
			write.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds or removes a player's UUID from the blacklist.
	 * 
	 * @param isBlacklisted
	 *            whether or not to blacklist the player.
	 */
	public void setBlacklisted(boolean isBlacklisted) {
		UserCache uc = ftp.getUserCache();
		JSONObject root = uc.getJSONConfiguration();
		JSONObject userInfo = getUserInfo();
		userInfo.put("isBlacklisted", isBlacklisted);
		root.put(uuid.toString(), userInfo);
		try {
			PrintWriter write = new PrintWriter(uc.getUserCacheFile());
			write.write(root.toJSONString());
			write.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return;
	}

	public Collection<TrollEffect> activeEffects() {
		List<TrollEffect> active = new ArrayList<>();
		if (hasBadfoodEffect()) {
			active.add(BADFOOD);
		}
		if (!canPickup()) {
			active.add(NO_PICKUP);
		}
		if (isFrozen()) {
			active.add(FREEZE);
		}
		if (chatIsGibberish()) {
			active.add(GIBBERISH);
		}
		if (invIsLocked()) {
			active.add(INVENTORY_LOCK);
		}
		if (hasExplodeMinedBlocksEffect()) {
			active.add(EXPLODE_BLOCKS);
		}
		if (isBlacklisted()) {
			active.add(BLACKLISTED);
		}
		return active;
	}

}