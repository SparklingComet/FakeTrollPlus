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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.shanerx.faketrollplus.core.TrollEffect;
import org.shanerx.faketrollplus.core.UserCache;
import org.shanerx.faketrollplus.events.ChatListener;
import org.shanerx.faketrollplus.events.EffectListeners;
import org.shanerx.faketrollplus.events.GuiListener;
import org.shanerx.faketrollplus.utils.Message;
import org.shanerx.faketrollplus.utils.Updater;
import org.shanerx.faketrollplus.utils.Updater.RelationalStatus;

public class FakeTrollPlus extends JavaPlugin {

	private File logs;
	private File logEntry;
	private PrintWriter log;
	static boolean doLogging;

	public final Updater VERSION = new Updater(getDescription(), Updater.BuildType.BETA);
	private volatile RelationalStatus buildRelation;

	private volatile UserCache usercache;
	
	private ProtocolManager protMan;
	
	@Override
	@SuppressWarnings("deprecation")
	public void onEnable() {
		Message.setConfig(getConfig());
		TrollEffect.setPlugin(this);
		saveDefaultConfig();
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new EffectListeners(this), this);
		pm.registerEvents(new GuiListener(this), this);
		
		protMan = ProtocolLibrary.getProtocolManager();
		protMan.addPacketListener(new ChatListener(this));

		Executor ex = new Executor(this);		
		for (String cmd : getDescription().getCommands().keySet()) {
			getCommand(cmd).setExecutor(ex);
		}

		logs = new File(getDataFolder(), "logs");
		if (!logs.exists()) {
			logs.mkdir();
		}
		doLogging = getConfig().getBoolean("enable-logs");
		
		Updater.setLogger(getLogger());
		
		if (!doLogging) {
			if (getConfig().getBoolean("check-updates")) {
				new Thread(() -> buildRelation = VERSION.checkCurrentVersion()).start();
			}
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

		log.println("---------------------- { FakeTrollPlus " + VERSION.getVersion()
				+ " by Lori00 } ----------------------");
		log.println("\n");
		log.println("[ " + date.toGMTString() + " ]");
		log.flush();

		usercache = UserCache.getInstance(new File(getDataFolder(), "usercache.json"), this);
		
		if (getConfig().getBoolean("check-updates")) {
			new Thread(() -> VERSION.checkCurrentVersion()).start();
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onDisable() {
		if (doLogging) {
			log.println(
					"----------------------------------------------------------------------------------------------------------");
			log.println("End of transcription - " + new Date().toGMTString());
			log.close();
		}
	}
	
	public PrintWriter getLog() {
		return log;
	}

	public UserCache getUserCache() {
		return usercache;
	}
	
	public RelationalStatus buildRelation() {
		return buildRelation;
	}

}