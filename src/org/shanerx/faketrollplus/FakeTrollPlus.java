package org.shanerx.faketrollplus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.shanerx.faketrollplus.core.UserCache;
import org.shanerx.faketrollplus.utils.PluginBuild;

import net.md_5.bungee.api.ChatColor;

public class FakeTrollPlus extends JavaPlugin {

	static File logs;
	static File logEntry;
	public static PrintWriter log;
	static boolean doLogging;

	public static String version;
	public static Logger console;

	private UserCache usercache;

	@Override
	@SuppressWarnings("deprecation")
	public void onEnable() {
		version = getDescription().getVersion();
		console = getLogger();
		
		saveDefaultConfig();
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new EventListeners(this), this);

		Executor ex = new Executor(this);
		getCommand("faketrollplus").setExecutor(ex);
		getCommand("fakeop").setExecutor(ex);
		getCommand("fakedeop").setExecutor(ex);
		getCommand("fakealert").setExecutor(ex);
		getCommand("fakebroadcast").setExecutor(ex);
		getCommand("fakejoin").setExecutor(ex);
		getCommand("fakeleave").setExecutor(ex);
		getCommand("hurt").setExecutor(ex);
		getCommand("tprandom").setExecutor(ex);
		getCommand("portal").setExecutor(ex);
		getCommand("spam").setExecutor(ex);
		getCommand("clearinv").setExecutor(ex);
		getCommand("fakecrash").setExecutor(ex);
		getCommand("murder").setExecutor(ex);
		getCommand("fakegod").setExecutor(ex);
		getCommand("fakechat").setExecutor(ex);
		getCommand("fakemsg").setExecutor(ex);
		getCommand("fakeafk").setExecutor(ex);
		getCommand("burn").setExecutor(ex);
		getCommand("poison").setExecutor(ex);
		getCommand("fakeban").setExecutor(ex);
		getCommand("anvil").setExecutor(ex);
		getCommand("forcecmd").setExecutor(ex);
		getCommand("freeze").setExecutor(ex);
		getCommand("fakepay").setExecutor(ex);
		getCommand("randominv").setExecutor(ex);
		getCommand("gibberish").setExecutor(ex);
		getCommand("renameitems").setExecutor(ex);
		getCommand("launch").setExecutor(ex);
		getCommand("swap").setExecutor(ex);
		getCommand("replaceinv").setExecutor(ex);
		getCommand("bury").setExecutor(ex);
		getCommand("nopickup").setExecutor(ex);
		getCommand("cobweb").setExecutor(ex);
		getCommand("inventorylock").setExecutor(ex);
		getCommand("explode").setExecutor(ex);
		getCommand("badfood").setExecutor(ex);
		getCommand("explodeblocks").setExecutor(ex);

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

	public String changeToGibberish(String initialMsg) {

		final String chars = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int msgLength = initialMsg.length();
		String newMsg = "";

		for (int i = 0; i < msgLength; i++) {
			newMsg += String.valueOf(chars.charAt((int) (Math.random() * chars.length())));
		}
		return newMsg;
	}

	public static String col(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public UserCache getUserCache() {
		return usercache;
	}

}