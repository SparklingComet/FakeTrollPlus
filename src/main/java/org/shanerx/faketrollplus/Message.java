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
package org.shanerx.faketrollplus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.shanerx.faketrollplus.utils.function.Test;

public enum Message {
	
	PREFIX("%PREFIX%[FakeTrollPlus] &f %PRIMARY% "),
	
	ACCESS_DENIED("You do not have %SECONDARY%access%PRIMARY% to that command!"),
	
	PLAYER_ONLY("Sorry, but this command is only available to %SECONDARY%players%PRIMARY%."),
	
	INVALID_ARGS("Invalid args, please try again. Usage: %SECONDAyRY%%usage%"),
		
	RELOAD_CONFIG("Configuration file has been reloaded!");
	
	public static String col(String x) {
		return ChatColor.translateAlternateColorCodes('&', x);
	}
	
	public static String col(Message x) {
		return ChatColor.translateAlternateColorCodes(Colour.COLOUR_SYMBOL, x.toString());
	}
	
	private static FileConfiguration fc;
	private String message;
	
	Message(String msg) {
		this.message = msg;
	}
	
	@Override
	public String toString() {
		return Colour.parse((this == PREFIX ? "" : PREFIX.toString()) + message);
	}
	
// CONFIG UTILS
	
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
	
// GENERAL UTILS
	
	public static String changeToGibberish(String initialMsg) {
		final String chars = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int msgLength = initialMsg.length();
		String newMsg = "";

		for (int i = 0; i < msgLength; i++) {
			newMsg += String.valueOf(chars.charAt((int) (Math.random() * chars.length())));
		}
		return newMsg;
	}
	
	public static boolean verifyCommandSender(Command cmd, CommandSender sender,
			String permission, boolean enable, Test checkArgs) {
		if (!enable) {
			sender.sendMessage(Message.getString("message-for-disabled-cmds"));
			return false;
		} else if (!sender.hasPermission(permission)) {
			sender.sendMessage(PREFIX.toString() + ACCESS_DENIED.toString());
			return false;
		} else if (checkArgs.test()) {
			sender.sendMessage(col(PREFIX.toString() + INVALID_ARGS.toString().replace("%usage%", cmd.getUsage())));
			return false;
		}
		return true;
	}
	
	public enum Colour {
		
		PRIMARY('f'),
		
		SECONDARY('3'),
		
		PREFIX('a');
		
		private char colourCode;
		public static final char COLOUR_SYMBOL = '&';
		
		Colour(char colourCode) {
			this.colourCode = colourCode;
		}
		
		public char colourCode() {
			return colourCode;
		}
		
		@Override
		public String toString() {
			return col(String.valueOf(colourCode));
		}
		
		public static String parse(String msg) {
			return col(msg
					.replace("%PRIMARY%", COLOUR_SYMBOL + PRIMARY.toString())
					.replace("%SECONDARY%", COLOUR_SYMBOL + SECONDARY.toString())
					.replace("%PREFIX%", COLOUR_SYMBOL + PREFIX.toString()));
		}
	}

}