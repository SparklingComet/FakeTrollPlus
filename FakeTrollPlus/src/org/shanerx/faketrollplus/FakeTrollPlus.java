package org.shanerx.faketrollplus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.shanerx.faketrollplus.utils.ConfigFile;
import org.shanerx.faketrollplus.utils.PluginBuild;

import net.md_5.bungee.api.ChatColor;


	public class FakeTrollPlus extends JavaPlugin {
		
		static File logs;
		static File logEntry;
		public static PrintWriter log;
		static boolean doLogging;
		
		public static String version;
		public static ArrayList<String> frozenPlayers = new ArrayList<String>();
		public static ArrayList<String> gibberish = new ArrayList<String>();
		public static ArrayList<Player> inventoryLock = new ArrayList<Player>();
		

		@SuppressWarnings("deprecation")
		public void onEnable() {
			version = getDescription().getVersion();
			
			saveDefaultConfig();

			PluginManager pm = Bukkit.getServer().getPluginManager();
			pm.registerEvents(new EventListeners(this), this);
			
			getCommand("faketrollplus").setExecutor(new Executor(this));
			getCommand("fakeop").setExecutor(new Executor(this));				
			getCommand("fakedeop").setExecutor(new Executor(this));			
			getCommand("fakealert").setExecutor(new Executor(this));			
			getCommand("fakebroadcast").setExecutor(new Executor(this));	
			getCommand("fakejoin").setExecutor(new Executor(this));			
			getCommand("fakeleave").setExecutor(new Executor(this));			
			getCommand("hurt").setExecutor(new Executor(this));					
			getCommand("tprandom").setExecutor(new Executor(this));			
			getCommand("portal").setExecutor(new Executor(this));				
			getCommand("spam").setExecutor(new Executor(this));					
			getCommand("clearinv").setExecutor(new Executor(this));			
			getCommand("fakecrash").setExecutor(new Executor(this));			
			getCommand("murder").setExecutor(new Executor(this));				
			getCommand("fakegod").setExecutor(new Executor(this));				
			getCommand("fakechat").setExecutor(new Executor(this));			
			getCommand("fakemsg").setExecutor(new Executor(this));				
			getCommand("fakeafk").setExecutor(new Executor(this));				
			getCommand("burn").setExecutor(new Executor(this));					
			getCommand("poison").setExecutor(new Executor(this));				
			getCommand("fakeban").setExecutor(new Executor(this));				
			getCommand("anvil").setExecutor(new Executor(this));					
			getCommand("forcecmd").setExecutor(new Executor(this));			
			getCommand("freeze").setExecutor(new Executor(this));				
			getCommand("fakepay").setExecutor(new Executor(this));				
			getCommand("randominv").setExecutor(new Executor(this));			
			getCommand("gibberish").setExecutor(new Executor(this));
			getCommand("renameitems").setExecutor(new Executor(this));
			getCommand("inventorylock").setExecutor(new Executor(this));
			getCommand("launch").setExecutor(new Executor(this));

			logs = new File(getDataFolder(), "logs");
			if (!logs.exists()) {
				logs.mkdir();
			}
			
			doLogging = getConfig().getBoolean("enable-logs");
			
			if (!doLogging) {
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
			
			log.println("---------------------- { FakeTrollPlus " + PluginBuild.getVersion() + " by Lori00 } ----------------------");
			log.println("\n");
			log.println("[ " + date.toGMTString() + " ]");
			log.flush();
		}

		
		@SuppressWarnings("deprecation")
		public void onDisable() {
			if (doLogging) {
				log.println("----------------------------------------------------------------------------------------------------------");
				log.println("End of transcription - " + new Date().toGMTString());
				log.flush();
			}
		}


		public boolean canTroll(CommandSender sender, Player target, String cmd) {
				
			if ((!(sender instanceof Player)) && (getConfig().getBoolean("console-bypass"))) {
				return true;
			}
			
			return (!target.hasPermission("faketroll.exempt." + cmd)) || (!getConfig().getBoolean("exempt-admins"));
		}
		
		
		public String changeToGibberish(String initialMsg) {
			
			final String chars = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			int msgLength = initialMsg.length();
			String newMsg = "";
			
			for (int i = 0; i < msgLength; i++) {
				newMsg += String.valueOf(chars.charAt((int)(Math.random() * chars.length())));
			}
			return newMsg;
		}
		
		public static String col(String s) {
			return ChatColor.translateAlternateColorCodes('&', s);
		}
		
	}
	
	