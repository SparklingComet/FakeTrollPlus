/*
 *
 *      Copyright (c) 2016-2017 SparklingComet @ http://shanerx.org
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.shanerx.faketrollplus.core.data;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.core.TrollEffect;
import org.shanerx.faketrollplus.utils.Database;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RemoteUserCache implements UserCache {
	
	private FakeTrollPlus plugin;
	private Database database;
	
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
	 * @param database
	 *            the {@link org.shanerx.faketrollplus.utils.Database} where the data is saved.
	 * @param plugin
	 *            the current Plugin instance.
	 */
	private RemoteUserCache(Database database, FakeTrollPlus plugin) {
		this.database = database;
		this.plugin = plugin;
		
		try {
			if (database.getConnection().isClosed()) {
				throw new IllegalStateException("Database connection must already be established!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructs the UserCache object. <br>
	 * The class follows the singleton pattern. No more than one instance should
	 * be created. <br>
	 * The software is not liable for any damages caused by accessing the
	 * storage file in an asynchronous way nor by different instances.
	 *
	 * @param database
	 *            the {@link org.shanerx.faketrollplus.utils.Database} where the data is saved.
	 * @param ftp
	 *            the current Plugin instance.
	 * @return the current instance of the class.
	 */
	public static UserCache getInstance(Database database, FakeTrollPlus ftp) {
		if (uc == null) {
			uc = new RemoteUserCache(database, ftp);
		}
		return uc;
	}
	
	/**
	 * Gets the {@link FakeTrollPlus} instance, which
	 * can be casted to {@link JavaPlugin} and
	 * {@link Plugin}.
	 *
	 * @return the {@link FakeTrollPlus} instance.
	 */
	@Override
	public FakeTrollPlus getPlugin() {
		return plugin;
	}
	
	/**
	 * Gets the database object containing the information
	 *
	 * @return the {@link org.shanerx.faketrollplus.utils.Database} instance.
	 */
	public Database getDatabase() {
		return database;
	}
	
	/**
	 * Returns an instance of {@link JSONObject}, which contains
	 * all the user data collected by the plugin.
	 *
	 * @return the {@link JSONObject}.
	 */
	@Override
	public JSONObject getJSONConfiguration() {
		try {
			return mapData();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns an instance of {@link JSONObject}, containing the
	 * specific data related to that {@link UUID}. <br>
	 * {@code null} if there are no records about that player. If the value
	 * {@code null} is returned, the player may still have joined once, they
	 * have never been trolled.
	 *
	 * @param uuid the uuid of the player.
	 *
	 * @return the {@link JSONObject} representing the data
	 * related to the UUID.
	 */
	@Override
	public JSONObject getPlayerInfo(UUID uuid) {
		try {
			return (JSONObject) mapData().get(uuid.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns an instance of {@link JSONObject}, containing the
	 * specific data related to that {@link UUID}. <br>
	 * {@code null} if there are no records about that player. If the value
	 * {@code null} is returned, the player may still have joined once, they
	 * have never been trolled.
	 *
	 * @param op the {@link OfflinePlayer} instance
	 *
	 * @return the {@link JSONObject} representing the data
	 * related to the {@link OfflinePlayer}.
	 */
	@Override
	public JSONObject getPlayerInfo(OfflinePlayer op) {
		try {
			return (JSONObject) mapData().get(op.getUniqueId().toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns an instance of {@link JSONObject}, containing the
	 * specific data related to that {@link UUID}. <br>
	 * {@code null} if there are no records about that player. If the value
	 * {@code null} is returned, the player may still have joined once, they
	 * have never been trolled.
	 *
	 * @param p the {@link Player} instance
	 *
	 * @return the {@link JSONObject} representing the data
	 * related to the {@link Player}.
	 */
	@Override
	public JSONObject getPlayerInfo(Player p) {
		try {
			return (JSONObject) mapData().get(p.getUniqueId().toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets the instance of {@link TrollPlayer}
	 * which represents the player known by that {@link UUID}.
	 *
	 * @param uuid the UUID of the player.
	 *
	 * @return the {@link TrollPlayer} known by
	 * that UUID.
	 */
	@Override
	public TrollPlayer getTrollPlayer(UUID uuid) {
		return new TrollPlayer(uuid, plugin);
	}
	
	/**
	 * Gets the instance of {@link TrollPlayer}
	 * which represents the player known by the {@link UUID} of the
	 * {@link OfflinePlayer}.
	 *
	 * @param op the player.
	 *
	 * @return the {@link TrollPlayer}.
	 */
	@Override
	public TrollPlayer getTrollPlayer(OfflinePlayer op) {
		return new TrollPlayer(op.getUniqueId(), plugin);
	}
	
	/**
	 * Gets the instance of {@link TrollPlayer}
	 * which represents the player known by the {@link UUID} of the
	 * {@link Player}.
	 *
	 * @param p the player.
	 *
	 * @return the {@link TrollPlayer}.
	 */
	@Override
	public TrollPlayer getTrollPlayer(Player p) {
		return new TrollPlayer(p.getUniqueId(), plugin);
	}
	
	/**
	 * Updates the data on the hard drive.
	 *
	 * @param uniqueId the UUID of the user.
	 *
	 * @param usr the {@link JSONObject} containing the information.
	 */
	protected void update(UUID uniqueId, JSONObject usr) {
		String uuid = uniqueId.toString();
		try {
			ResultSet rs = database.getStatement().executeQuery("SELECT uuid FROM " + Database.TABLE_NAME + " WHERE uuid = " + uuid);
			boolean exists = rs.next();
			
			if (exists) {
				StringBuilder update = new StringBuilder();
				update
						.append("UPDATE " + Database.TABLE_NAME + " SET ")
						.append("uuid = ")
						.append(uuid);
				
				for (TrollEffect effect : TrollEffect.values()) {
					update.append(", ")
							.append(effect.toString().toLowerCase())
							.append(" = ")
							.append(((String) usr.get((effect.config()))).equalsIgnoreCase("true") ? 1 : 0);
				}
				update
						.append(" WHERE uuid = ")
						.append(uuid)
						.append(";");
				
				database.getStatement().executeUpdate(update.toString());
			
			} else {
				StringBuilder insert = new StringBuilder();
				insert
						.append("INSERT INTO " + Database.TABLE_NAME + " (uuid, ");
				
				for (TrollEffect effect : TrollEffect.values()) {
					insert
							.append(effect.toString().toLowerCase())
							.append(", ");
				}
				
				insert = new StringBuilder(insert.substring(0, insert.length() - 2))
				.append(") VALUES (")
				.append(uuid)
				.append(", ");
				
				for (TrollEffect effect : TrollEffect.values()) {
					insert
							.append(((String) usr.get((effect.config()))).equalsIgnoreCase("true") ? 1 : 0)
							.append(", ");
				}
				insert = new StringBuilder(insert.substring(0, insert.length() - 2))
						.append(";");
				
				database.getStatement().executeUpdate(insert.toString());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private JSONObject mapData() throws SQLException {
		JSONObject root = new JSONObject();
		ResultSet rs = database.getStatement().executeQuery("SELECT * FROM " + Database.TABLE_NAME);
		
		while (rs.next()) {
			JSONObject usr = new JSONObject();
			
			for (TrollEffect key : TrollEffect.values()) {
				usr.put(key.config(), rs.getBoolean(key.toString().toLowerCase()));
			}
			root.put(rs.getString("uuid"), usr);
		}
		
		return root;
	}
}