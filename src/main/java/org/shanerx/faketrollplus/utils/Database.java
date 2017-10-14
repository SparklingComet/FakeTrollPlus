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
package org.shanerx.faketrollplus.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class represents a wrapper of the database data
 * and provides handle-functions that make any interaction
 * with the database easier.
 *
 * @author SparklingComet
 * Direct URL: https://gist.github.com/SparklingComet/28e47e352c3619009ba3f0ad41b2d542
 */
public class Database {
	
	private String host, user, database;
	private int port;
	private String dbType;
	private Connection connection;
	private Statement statement;
	
	public static final String TABLE_NAME = "ftp_data";
	
	/**
	 * Constructs a new {@link Database} object.
	 *
	 * @param hostname The hostname. May be an IP, a domain or simply 'localhost'.
	 * @param port The port (default: 3306).
	 * @param username The login for the database.
	 * @param password The password for the database
	 *                   - will <b>NOT</b> be stored as a field and will <b>NOT</b>
	 *                   be accessible through getters or even reflection.
	 * @param database The name of the database.
	 * @throws SQLException Thrown when a connection may not be established.
	 * @throws ClassNotFoundException Thrown if the {@code com.mysql.jdbc.Driver class}
	 * cannot be found.
	 */
	public Database(String dbType, String hostname, int port, String username, String password, String database) throws SQLException, ClassNotFoundException {
		this.dbType = dbType;
		host = hostname;
		this.port = port;
		user = username;
		this.database = database;
		openConnection(password);
		statement = connection.createStatement();
		initializeDatabase();
	}
	
	/**
	 * Returns the host. May be an IP, a domain or simply 'localhost'.
	 * @return The address to the database's server.
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * Returns the login (username). Required for authentication purposes.
	 * @return The username.
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * Returns the database's name.
	 * @return The database.
	 */
	public String getDatabase() {
		return database;
	}
	
	/**
	 * Returns the {@link java.sql.Connection object}.
	 * Used to retrieve the {@link java.sql.Statement object}
	 * in order to execute queries and updates.
	 *
	 * @return The {@link java.sql.Connection}.
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Returns the {@link java.sql.Statement object}.
	 * @return The {@link java.sql.Statement}.
	 */
	public Statement getStatement() {
		return statement;
	}
	
	
	//////////////////////
	// FUNCTIONAL STUFF //
	//////////////////////
	
	/**
	 * Opens the connection for the first time.
	 * <br>
	 * If the connection is already open, nothing will happen.
	 *
	 * @param password The database password.
	 *                    Passed as a function parameter for security purposes.
	 * @throws SQLException Thrown when a connection cannot be established.
	 * @throws ClassNotFoundException Thrown when the {@link com.mysql.jdbc.Driver class} is not found.
	 */
	protected void openConnection(String password) throws SQLException, ClassNotFoundException {
		if (connection != null && !connection.isClosed()) {
			return;
		}
		
		synchronized (this) {
			if (connection != null && !connection.isClosed()) {
				return;
			}
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:" + dbType + "://" + this.host+ ":" + this.port + "/" + this.database, this.user, password);
		}
	}
	
	/**
	 * Closes the current connection gracefully.
	 * Should <b>always</b> be called when the plugin
	 * is disabled to avoid memory leaks unclean throttling.
	 * <br>
	 * When the connection is not open, nothing will happen.
	 *
	 * @throws SQLException Thrown when there is no stable
	 * connection with the database's server and commands
	 * cannot be executed successfully.
	 */
	public void closeConnection() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}
	
	/**
	 * Creates the table if it does not exist.
	 * @throws SQLException
	 */
	protected void initializeDatabase() throws SQLException {
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
				"uuid VARCHAR(64), " +
				"badfood INT default 0, " +
				"no_pickup INT default 0, " +
				"freeze INT default 0, " +
				"gibberish INT default 0," +
				"inventory_lock INT default 0, " +
				"explode_blocks INT default 0, " +
				"blacklisted INT default 0, " +
				"freeze_chat INT default 0," +
				");");
	}
}