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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.shanerx.faketrollplus.core.UserCache;
import org.shanerx.faketrollplus.events.EffectListeners;
import org.shanerx.faketrollplus.events.GuiListener;
import org.shanerx.faketrollplus.utils.PluginBuild;

public class FakeTrollPlus extends JavaPlugin {

	static File logs;
	static File logEntry;
	public static PrintWriter log;
	static boolean doLogging;

	private static String version;
	public static Logger console;

	private volatile UserCache usercache;
	
	public static String getVersion() {
		return version;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onEnable() {
		version = getDescription().getVersion();
		console = getLogger();
		Message.setConfig(getConfig());
		saveDefaultConfig();
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new EffectListeners(this), this);
		pm.registerEvents(new GuiListener(this), this);

		Executor ex = new Executor(this);		
		for (String cmd : getDescription().getCommands().keySet()) {
			getCommand(cmd).setExecutor(ex);
		}

		logs = new File(getDataFolder(), "logs");
		if (!logs.exists()) {
			logs.mkdir();
		}
		doLogging = getConfig().getBoolean("enable-logs");
		
		if (!doLogging) {
			if (getConfig().getBoolean("check-updates"))
				new Thread(() -> PluginBuild.checkCurrentVersion()).start();
			return;
		}

		Date date = new Date();
		logEntry = new File(logs, date.toGMTString().replaceAll(":", "-") + ".txt");

		try {
			logEntry.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			log = new PrintWriter(logEntry);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		log.println("---------------------- { FakeTrollPlus " + PluginBuild.getVersion()
				+ " by Lori00 } ----------------------");
		log.println("\n");
		log.println("[ " + date.toGMTString() + " ]");
		log.flush();

		usercache = UserCache.getInstance(new File(getDataFolder(), "usercache.json"), this);
		
		if (getConfig().getBoolean("check-updates"))
			new Thread(() -> PluginBuild.checkCurrentVersion()).start();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onDisable() {
		if (doLogging) {
			log.println(
					"----------------------------------------------------------------------------------------------------------");
			log.println("End of transcription - " + new Date().toGMTString());
			log.flush();
		}
	}

	public UserCache getUserCache() {
		return usercache;
	}

}