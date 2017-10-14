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
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.shanerx.faketrollplus.core.TrollEffect;
import org.shanerx.faketrollplus.core.UserCache;
import org.shanerx.faketrollplus.events.EffectListeners;
import org.shanerx.faketrollplus.events.GuiListener;
import org.shanerx.faketrollplus.utils.Database;
import org.shanerx.faketrollplus.utils.Message;
import org.shanerx.faketrollplus.utils.Updater;
import org.shanerx.faketrollplus.utils.Updater.RelationalStatus;

public class FakeTrollPlus extends JavaPlugin {

	private File logs;
	private File logEntry;
	private PrintWriter log;
	static boolean doLogging;

	public final Updater VERSION = new Updater(getDescription());
	private volatile RelationalStatus buildRelation;

	private volatile UserCache usercache;
	private volatile Database database;
	
	
	@Override
	@SuppressWarnings("deprecation")
	public void onEnable() {
		Message.setPlugin(this);
		TrollEffect.setPlugin(this);
		saveDefaultConfig();
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new EffectListeners(this), this);
		pm.registerEvents(new GuiListener(this), this);

		Executor ex = new Executor(this);		
		for (String cmd : getDescription().getCommands().keySet()) {
			getCommand(cmd).setExecutor(ex);
		}
		
		if (getConfig().getBoolean("database.use")) {
			new Thread(() -> {
				String type = getConfig().getString("database.type");
				String host = getConfig().getString("database.host");
				int port = getConfig().getInt("database.port");
				String user = getConfig().getString("database.user");
				String pass = getConfig().getString("database.password");
				String db = getConfig().getString("database.db-name");
				try {
					database = new Database(type, host, port, user, pass, db);
				} catch (Exception e) {
					getLogger().log(Level.SEVERE, "Could not establish a connection to the database. Please check your credentials and try again. Disabling plugin.", e);
					Bukkit.getPluginManager().disablePlugin(this);
					return;
				}
			}).start();
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
		if (database != null) {
			try {
				database.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
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
	
	public Database getDatabase() {
		return database;
	}
	
	public RelationalStatus buildRelation() {
		return buildRelation;
	}
	
	
	///////////
	// UTILS //
	///////////
	
	public Player getTarget(String arg) {
		return getConfig().getBoolean("exact-target") ? Bukkit.getPlayerExact(arg) : Bukkit.getPlayer(arg);
	}
}