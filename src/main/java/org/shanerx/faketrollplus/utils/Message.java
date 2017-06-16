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
package org.shanerx.faketrollplus.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.shanerx.faketrollplus.utils.function.Test;

public enum Message {
	
	PREFIX("NOT-ACCESSIBLE"),
	
	ACCESS_DENIED("You do not have &3access&f to this command!"),
	
	PLAYER_ONLY("Sorry, but this command is only available to &3players&f."),
	
	INVALID_ARGS("Invalid args, please try again. Usage: &3%usage%"),
	
	NO_PROTOCOL_LIB("&3ProtocolLib &fis required to perform this action. Please install it and retry."),

	GUI_EFFECT_DISABLED("&3Sorry, but this effect has been &3disabled &fin the config."),
	
	RELOAD_CONFIG("Configuration file has been &3reloaded&f!"),
	
	HELP_COMMAND_NOT_FOUND("&3/%command% &fcould not be found."),
	
	COMMAND_HELP("&3Command help for: &7/%command%\n&7Description: &f%description%\n&7Usage: &f%usage%\n&7Aliases: &f%aliases%");
	
	public static char COLOUR_SYMBOL = '&';
	
	public static String col(String x) {
		return ChatColor.translateAlternateColorCodes('&', x);
	}
	
	public static String col(Message x) {
		return ChatColor.translateAlternateColorCodes(COLOUR_SYMBOL, x.toString());
	}
	
	private static FileConfiguration fc;
	private String message;
	
	Message(String msg) {
		this.message = msg;
	}
	
	@Override
	public String toString() {
		return this == PREFIX ? (fc.getBoolean("use-prefix") ? Message.getString("prefix") : "") : col(PREFIX.toString() + message);
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
	
	public static void setConfig(FileConfiguration fc) {
		Message.fc = fc;
	}
	
// GENERAL UTILS
	
	public static String changeToGibberish(String initialMsg) {
		final String chars = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int msgLength = initialMsg.length();
		
		StringBuilder newMsg = new StringBuilder();
		for (int i = 0; i < msgLength; i++) {
			newMsg.append(String.valueOf(chars.charAt((int) (Math.random() * chars.length()))));
		}
		return newMsg.toString();
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

}