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
 * An enum used to identify the GUIs created by this plugin.
 */
public enum GuiType {
	
	/**
	 * Identifies the GUI containing a list of all online players.
	 */
	PLAYERS((byte) 0),
	
	/**
	 * Identifies the GUI containing the effects available for execution.
	 */
	EFFECTS((byte) 1);
	
	private byte key;
	
	GuiType(byte key) {
		this.key = key;
	}
	
	/**
	 * Returns a short unique to the gui type, which can be used to identify or store the effect in memory.
	 * @return the key.
	 */
	public byte key() {
		return key;
	}
	
	/**
	 * Identifies the effect that this key belongs to. Useful when storing keys in maps, lists or as serialized data.
	 * @param key the key
	 * @return the GuiType
	 */
	public static GuiType fromKey(byte key) {
		return (key == 0) ? PLAYERS : ((key == 1) ? EFFECTS : null);
	}

}