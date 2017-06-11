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

/**
 * An enum containing all the trolling effects the plugin adds to the game.
 */
public enum TrollEffect {

	BADFOOD((short) 1, "Badfood"),

	NO_PICKUP((short) 2, "No-Pickup"),

	FREEZE((short) 3, "Freeze"),

	GIBBERISH((short) 4, "Gibberish"),

	INVENTORY_LOCK((short) 5, "Inventory-Lock"),

	EXPLODE_BLOCKS((short) 6, "Explode-Blocks"),

	BLACKLISTED((short) 7, "Blacklist");

	TrollEffect(short key,String name) {
		this.key = key;
		this.name = name;
	}

	private String name;
	private String description;
	private short key;

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
	 * Gets the description of the effect.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns a short unique to the effect, which can be used to identify or store the effect in memory.
	 * @return the key.
	 */
	public short key() {
		return key;
	}
	
	/**
	 * Identifies the effect that this key belongs to. Useful when storing keys in maps, lists or as serialized data.
	 * @param key the key
	 * @return the effect
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

}