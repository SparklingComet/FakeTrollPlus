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
package org.shanerx.faketrollplus.core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class UserCache {

	private FakeTrollPlus plugin;
	private File cacheFile;

	private static UserCache uc;

	/**
	 * Constructs the UserCache object. <br>
	 * The class follows the singleton pattern. No more than one instance should
	 * be created. <br>
	 * The software is not liable for any damages caused by accessing the
	 * storage file in an asynchronous way nor by different instances.
	 * <strong>The constructor should only be called by this class. No
	 * implementations of the API should need to call this. The current instance
	 * of this class can be found at the location
	 * {@link org.shanerx.faketrollplus.FakeTrollPlus}!</strong>
	 * 
	 * @param usercache
	 *            the {@link java.io.File} where the data is saved.
	 * @param plugin
	 *            the current Plugin instance.
	 */
	private UserCache(File usercache, FakeTrollPlus plugin) {
		this.cacheFile = usercache;
		this.plugin = plugin;

		if (!usercache.exists()) {
			try {
				usercache.createNewFile();
				PrintWriter pw = new PrintWriter(cacheFile);
				pw.write("{\n}");
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Constructs the UserCache object. <br>
	 * The class follows the singleton pattern. No more than one instance should
	 * be created. <br>
	 * The software is not liable for any damages caused by accessing the
	 * storage file in an asynchronous way nor by different instances.
	 * 
	 * @param file
	 *            the {@link java.io.File} where the data is saved.
	 * @param ftp
	 *            the current Plugin instance.
	 * @return the current instance of the class.
	 */
	public static UserCache getInstance(File file, FakeTrollPlus ftp) {
		if (uc == null) {
			uc = new UserCache(file, ftp);
		}
		return uc;
	}

	/**
	 * Gets the {@link org.shanerx.faketrollplus.FakeTrollPlus} instance, which
	 * can be casted to {@link org.bukkit.plugin.java.JavaPlugin} and
	 * {@link org.bukkit.plugin.Plugin}.
	 * 
	 * @return the {@link org.shanerx.faketrollplus.FakeTrollPlus} instance.
	 */
	public FakeTrollPlus getPlugin() {
		return plugin;
	}

	/**
	 * Gets the file object representing the actual file where the user
	 * information is stored. <br>
	 * Equivalent to {@code {@link java.io.File} = new
	 * {@link java.io.File}({@link org.shanerx.faketrollplus.FakeTrollPlus#getDataFolder()},
	 * "usercache.json");} <br>
	 * The plugin will automatically (re-)generate the file.
	 * 
	 * @return the {@link java.io.File} object representing the file on the disk
	 *         where the data is saved.
	 */
	public File getUserCacheFile() {
		return cacheFile;
	}

	/**
	 * Returns an instance of {@link org.json.simple.JSONObject}, which contains
	 * all the user data collected by the plugin.
	 * 
	 * @return the {@link org.json.simple.JSONObject}.
	 */
	public JSONObject getJSONConfiguration() {
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(new FileReader(cacheFile));
			return obj;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns an instance of {@link org.json.simple.JSONObject}, containing the
	 * specific data related to that {@link java.util.UUID}. <br>
	 * {@code null} if there are no records about that player. If the value
	 * {@code null} is returned, the player may still have joined once, they
	 * have never been trolled.
	 * 
	 * @param uuid
	 *            the uuid of the player.
	 * @return the {@link org.json.simple.JSONObject} representing the data
	 *         related to the UUID.
	 */
	public JSONObject getPlayerInfo(UUID uuid) {
		return (JSONObject) getJSONConfiguration().get(uuid.toString());
	}

	/**
	 * Returns an instance of {@link org.json.simple.JSONObject}, containing the
	 * specific data related to that {@link java.util.UUID}. <br>
	 * {@code null} if there are no records about that player. If the value
	 * {@code null} is returned, the player may still have joined once, they
	 * have never been trolled.
	 * 
	 * @param uuid
	 *            the uuid of the player.
	 * @return the {@link org.json.simple.JSONObject} representing the data
	 *         related to the {@link org.bukkit.OfflinePlayer}.
	 */
	public JSONObject getPlayerInfo(OfflinePlayer op) {
		return (JSONObject) getJSONConfiguration().get(op.getUniqueId().toString());
	}

	/**
	 * Returns an instance of {@link org.json.simple.JSONObject}, containing the
	 * specific data related to that {@link java.util.UUID}. <br>
	 * {@code null} if there are no records about that player. If the value
	 * {@code null} is returned, the player may still have joined once, they
	 * have never been trolled.
	 * 
	 * @param uuid
	 *            the player.
	 * @return the {@link org.json.simple.JSONObject} representing the data
	 *         related to the {@link org.bukkit.entity.Player}.
	 */
	public JSONObject getPlayerInfo(Player p) {
		return (JSONObject) getJSONConfiguration().get(p.getUniqueId().toString());
	}

	/**
	 * Gets the instance of {@link org.shanerx.faketrollplus.core.TrollPlayer}
	 * which represents the player known by that {@link java.util.UUID}.
	 * 
	 * @param uuid
	 *            the UUID of the player.
	 * @return the {@link org.shanerx.faketrollplus.core.TrollPlayer} known by
	 *         that UUID.
	 */
	public TrollPlayer getTrollPlayer(UUID uuid) {
		return new TrollPlayer(uuid, plugin);
	}

	/**
	 * Gets the instance of {@link org.shanerx.faketrollplus.core.TrollPlayer}
	 * which represents the player known by the {@link java.util.UUID} of the
	 * {@link org.bukkit.OfflinePlayer}.
	 * 
	 * @param op
	 *            the player.
	 * @return the {@link org.shanerx.faketrollplus.core.TrollPlayer}.
	 */
	public TrollPlayer getTrollPlayer(OfflinePlayer op) {
		return new TrollPlayer(op, plugin);
	}

	/**
	 * Gets the instance of {@link org.shanerx.faketrollplus.core.TrollPlayer}
	 * which represents the player known by the {@link java.util.UUID} of the
	 * {@link org.bukkit.entity.Player}.
	 * 
	 * @param p
	 *            the player.
	 * @return the {@link org.shanerx.faketrollplus.core.TrollPlayer}.
	 */
	public TrollPlayer getTrollPlayer(Player p) {
		return new TrollPlayer(p, plugin);
	}

}