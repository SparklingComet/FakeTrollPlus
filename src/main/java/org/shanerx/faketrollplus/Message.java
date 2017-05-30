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
import org.bukkit.configuration.file.FileConfiguration;

public enum Message {
	
	ACCESS_DENIED("&cYou do not have access to that command");
	
	public static String col(String x) {
		return ChatColor.translateAlternateColorCodes('&', x);
	}
	
	public static String col(Message x) {
		return ChatColor.translateAlternateColorCodes('&', x.toString());
	}
	
	private static FileConfiguration fc;
	private String message;
	
	Message(String msg) {
		this.message = msg;
	}
	
	@Override
	public String toString() {
		return col(message);
	}
	
	public static String getString(String s) {
		return col(fc.getString(s));
	}
	
	public static boolean getBool(String s) {
		return fc.getBoolean(s);
	}
	
	public static int getInt(String s) {
		return fc.getInt(s);
	}
	
	public static double getDouble(String s) {
		return fc.getDouble(s);
	}
	
	static void setConfig(FileConfiguration fc) {
		Message.fc = fc;
	}
	
	public static String changeToGibberish(String initialMsg) {
		final String chars = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int msgLength = initialMsg.length();
		String newMsg = "";

		for (int i = 0; i < msgLength; i++) {
			newMsg += String.valueOf(chars.charAt((int) (Math.random() * chars.length())));
		}
		return newMsg;
	}

}