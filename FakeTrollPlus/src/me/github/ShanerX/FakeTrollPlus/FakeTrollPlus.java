package me.github.ShanerX.FakeTrollPlus;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.github.ShanerX.FakeTrollPlus.Commands.Anvil;
import me.github.ShanerX.FakeTrollPlus.Commands.Burn;
import me.github.ShanerX.FakeTrollPlus.Commands.Clearinv;
import me.github.ShanerX.FakeTrollPlus.Commands.Fakeafk;
import me.github.ShanerX.FakeTrollPlus.Commands.Fakealert;
import me.github.ShanerX.FakeTrollPlus.Commands.Fakeban;
import me.github.ShanerX.FakeTrollPlus.Commands.Fakebroadcast;
import me.github.ShanerX.FakeTrollPlus.Commands.Fakechat;
import me.github.ShanerX.FakeTrollPlus.Commands.Fakecrash;
import me.github.ShanerX.FakeTrollPlus.Commands.Fakedeop;
import me.github.ShanerX.FakeTrollPlus.Commands.Fakegod;
import me.github.ShanerX.FakeTrollPlus.Commands.Fakejoin;
import me.github.ShanerX.FakeTrollPlus.Commands.Fakeleave;
import me.github.ShanerX.FakeTrollPlus.Commands.Fakemsg;
import me.github.ShanerX.FakeTrollPlus.Commands.Fakeop;
import me.github.ShanerX.FakeTrollPlus.Commands.Fakepay;
import me.github.ShanerX.FakeTrollPlus.Commands.Forcecmd;
import me.github.ShanerX.FakeTrollPlus.Commands.Freeze;
import me.github.ShanerX.FakeTrollPlus.Commands.Gibberish;
import me.github.ShanerX.FakeTrollPlus.Commands.Hurt;
import me.github.ShanerX.FakeTrollPlus.Commands.Murder;
import me.github.ShanerX.FakeTrollPlus.Commands.Poison;
import me.github.ShanerX.FakeTrollPlus.Commands.Portal;
import me.github.ShanerX.FakeTrollPlus.Commands.RandomInv;
import me.github.ShanerX.FakeTrollPlus.Commands.RenameItems;
import me.github.ShanerX.FakeTrollPlus.Commands.Spam;
import me.github.ShanerX.FakeTrollPlus.Commands.TpRandom;


	public class FakeTrollPlus extends JavaPlugin {
		
		public static ArrayList<String> frozenPlayers = new ArrayList<String>();
		public static ArrayList<String> gibberish = new ArrayList<String>();

		public void onEnable() {
			
			FileConfiguration config = getConfig();
			config.options().copyDefaults(true);
			this.saveConfig();

			PluginManager pm = Bukkit.getServer().getPluginManager();
			pm.registerEvents(new EventListeners(this), this);

			getCommand("faketrollplus").setExecutor(new me.github.ShanerX.FakeTrollPlus.Commands.FakeTrollPlus(this));
			getCommand("fakeop").setExecutor(new Fakeop(this));				
			getCommand("fakedeop").setExecutor(new Fakedeop(this));			
			getCommand("fakealert").setExecutor(new Fakealert(this));			
			getCommand("fakebroadcast").setExecutor(new Fakebroadcast(this));	
			getCommand("fakejoin").setExecutor(new Fakejoin(this));			
			getCommand("fakeleave").setExecutor(new Fakeleave(this));			
			getCommand("hurt").setExecutor(new Hurt(this));					
			getCommand("tprandom").setExecutor(new TpRandom(this));			
			getCommand("portal").setExecutor(new Portal(this));				
			getCommand("spam").setExecutor(new Spam(this));					
			getCommand("clearinv").setExecutor(new Clearinv(this));			
			getCommand("fakecrash").setExecutor(new Fakecrash(this));			
			getCommand("murder").setExecutor(new Murder(this));				
			getCommand("fakegod").setExecutor(new Fakegod(this));				
			getCommand("fakechat").setExecutor(new Fakechat(this));			
			getCommand("fakemsg").setExecutor(new Fakemsg(this));				
			getCommand("fakeafk").setExecutor(new Fakeafk(this));				
			getCommand("burn").setExecutor(new Burn(this));					
			getCommand("poison").setExecutor(new Poison(this));				
			getCommand("fakeban").setExecutor(new Fakeban(this));				
			getCommand("anvil").setExecutor(new Anvil(this));					
			getCommand("forcecmd").setExecutor(new Forcecmd(this));			
			getCommand("freeze").setExecutor(new Freeze(this));				
			getCommand("fakepay").setExecutor(new Fakepay(this));				
			getCommand("randominv").setExecutor(new RandomInv(this));			
			getCommand("gibberish").setExecutor(new Gibberish(this));
			getCommand("renameitems").setExecutor(new RenameItems(this));

		}

		
		
		public void onDisable() 
		{
			saveConfig();
			
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
		
	}
	
	