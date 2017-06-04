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

	BADFOOD("Badfood"),

	NO_PICKUP("No-Pickup"),

	FREEZE("Freeze"),

	GIBBERISH("Gibberish"),

	INVENTORY_LOCK("Inventory-Lock"),

	EXPLODE_BLOCKS("Explode-Blocks"),

	BLACKLISTED("Blacklist");

	TrollEffect(String name) {
		this.name = name;
	}

	private String name;
	private String description;

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
	public final String getDescription() {
		return description;
	}

}