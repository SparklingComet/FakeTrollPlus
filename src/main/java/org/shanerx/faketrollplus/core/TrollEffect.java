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

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.utils.Message;

import java.io.Serializable;

/**
 * An enum containing all the trolling effects the plugin adds to the game.
 */
public enum TrollEffect implements Serializable{

	BADFOOD((short) 1, "Badfood", "badfood", "hasBadfoodEffect", Material.ROTTEN_FLESH),

	NO_PICKUP((short) 2, "No-Pickup", "nopickup", "canPickup", Material.EXP_BOTTLE),

	FREEZE((short) 3, "Freeze", "freeze", "isFrozen", Material.IRON_BOOTS),

	GIBBERISH((short) 4, "Gibberish", "gibberish", "chatIsGibberish", Material.BOOK),

	INVENTORY_LOCK((short) 5, "Inventory-Lock", "inventorylock", "invIsLocked", Material.TRAPPED_CHEST),

	EXPLODE_BLOCKS((short) 6, "Explode-Blocks", "explodeblocks", "hasExplodeMinedBlocksEffect", Material.TNT),

	BLACKLISTED((short) 7, "Blacklist", "blacklist", "isBlacklisted", Material.IRON_DOOR),

	FAKEWORLDGUARD((short) 8, "Fake-WorldGuard", "fakeworldguard", "isFakeWorldGuard", Material.BARRIER);
	
	TrollEffect(short key,String name, String id, String config, Material guiItem) {
		this.key = key;
		this.name = name;
		this.id = id;
		this.config = config;
		this.guiItem = guiItem;
	}
	
	private static FakeTrollPlus plugin;
	
	private short key;
	private transient String name;
	private transient String id;
	private transient String config;
	private transient Material guiItem;

	/**
	 * Gets the effect's name (Different from
	 * {@link java.lang.Enum#toString()}).
	 *
	 * @return the name
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Gets the description of the effect (which equals the
	 * description of the command used to toggle it, as
	 * specified in the {@link org.bukkit.plugin.PluginDescriptionFile}.
	 *
	 * @return the description
	 */
	public String description() {
		return plugin.getCommand(id).getDescription();
	}
	
	/**
	 * Returns the name of the command used to toggle the effect.
	 *
	 * @return the command name
	 */
	public String id() {
		return id;
	}
	
	/**
	 * Returns the name of the configuration key in the
	 * {@link org.json.simple.JSONObject} which refers
	 * to the player's data.
	 *
	 * @return the key of the config map.
	 */
	public String config() {
		return config;
	}
	
	/**
	 * Returns the {@link org.bukkit.Material} constant
	 * representing this effect in the Troll GUI.
	 *
	 * @return the Material.
	 */
	public Material guiItem() {
		return guiItem;
	}
	
	/**
	 * Returns a short unique to the effect, which can be used to identify or store the effect in memory.
	 *
	 * @return the key.
	 *
	 * @see #fromKey(short)
	 * @see #serialize()
	 * @see #deserialize(String)
	 * @see java.io.Serializable
	 */
	public short key() {
		return key;
	}
	
	/**
	 * Returns whether the effect is enabled in the config.
	 *
	 * @return true if enabled.
	 */
	public boolean isEnabled() {
		String key;
		switch (this) {
			case BADFOOD:
				key = "badfood.enable";
				break;
			case NO_PICKUP:
				key = "no-pickup.enable";
				break;
			case FREEZE:
				key = "freeze.enable";
				break;
			case GIBBERISH:
				key = "gibberish.enable";
				break;
			case INVENTORY_LOCK:
				key = "inventory-lock.enable";
				break;
			case EXPLODE_BLOCKS:
				key = "explode-blocks.enable";
				break;
			case BLACKLISTED:
				key = "enable-blacklist";
				break;
			default: key = null;
		}
		return Message.getBool(key);
	}
	
	/**
	 * Identifies the effect that this key belongs to. Useful when storing keys in maps, lists or as serialized data.
	 *
	 * @param key the key
	 * @return the effect
	 *
	 * @see #key()
	 * @see #serialize()
	 * @see #deserialize(String)
	 * @see java.io.Serializable
	 */
	public static TrollEffect fromKey(short key) {
		switch (key) {
			case 1: return BADFOOD;
			case 2: return NO_PICKUP;
			case 3: return FREEZE;
			case 4: return GIBBERISH;
			case 5: return INVENTORY_LOCK;
			case 6: return EXPLODE_BLOCKS;
			case 7: return BLACKLISTED;
			default: return null;
		}
	}
	
	/**
	 * Sets the {@link org.shanerx.faketrollplus.FakeTrollPlus} instance.
	 * This is required to retrieve information from the {@link org.bukkit.plugin.PluginDescriptionFile}
	 * (aka the plugin.yml) and MUST be called in {@link JavaPlugin#onEnable()}.
	 *
	 * @param plugin the {@link org.shanerx.faketrollplus.FakeTrollPlus} instance
	 */
	public static void setPlugin(FakeTrollPlus plugin) {
		TrollEffect.plugin = plugin;
	}
	
// SERIALIZABLE IMPLEMENTATION:
	
	/**
	 * Returns a serialized form of the TrollEffect.
	 *
	 * @return a String representing the serialized data.
	 *
	 * @see #key()
	 * @see #deserialize(String)
	 * @see java.io.Serializable
	 */
	public String serialize() {
		return "effectKey_" + key;
	}
	
	/**
	 * Retrieves the TrollEffect from a previously serialized object.
	 *
	 * @param serializedData a serialized data.
	 * @return the TrollEffect enum constant.
	 *
	 * @see #fromKey(short)
	 * @see #serialize()
	 * @see java.io.Serializable
	 */
	public static TrollEffect deserialize(String serializedData) {
		String[] arr = serializedData.split("_");
		if (arr.length != 2) {
			return null;
		} else if (!arr[0].equalsIgnoreCase("effectKey")){
			return null;
		} else {
			try {
				return TrollEffect.fromKey(Short.parseShort(arr[1]));
			} catch (NumberFormatException e) {
				return null;
			}
		}
	}
}