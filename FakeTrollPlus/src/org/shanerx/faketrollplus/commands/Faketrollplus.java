package org.shanerx.faketrollplus.commands;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.utils.PluginBuild;

public class Faketrollplus implements CommandExecutor {
	
	FakeTrollPlus plugin;
	
	public Faketrollplus(FakeTrollPlus instance) {
		plugin = instance;
	}
	
	public static final String HELP = FakeTrollPlus.col("\n \n &f----------&e[ &aHELP &e]&f---------- "
			+ "\n&aFakeTrollPlus &e" + PluginBuild.getVersion() + " by Lori00 "
			+ "\n&bFor commands and permissions help go to &3&ngoo.gl/c8RL4G "
			+ "\n&cCommands:"
			+ "\n&e/faketrollplus &f- Display plugin help "
			+ "\n&e/fakeop &f- Make a player believe that they just got opped! "
			+ "\n&e/fakedeop &f- Make a player believe that they just got de-opped! "
			+ "\n&e/fakealert &f - Send a BungeeCord alert message to a single player "
			+ "\n&e/fakebroadcast &f - Send an Essentials broadcast message, which looks like server-wide broadcast, to a single player "
			+ "\n&e/fakejoin &f - Pretend that a certain player joined the game. "
			+ "\n&e/fakeleave &f - Pretend that a certain player left the game. "
			+ "\n&e/hurt &f - Be mean. "
			+ "\n&e/tprandom &f - Teleport a player to a random location. "
			+ "\n&e/portal &f - Banish the player by sending them very far away. "
			+ "\n&e/spam &f - Annoy a player. "
			+ "\n&e/clearinv &f - Clear someone's inventory. "
			+ "\n&e/fakecrash &f - Kick a player and show them a Java error message. "
			+ "\n&e/murder &f - Please don't do such an horrible thing! "
			+ "\n&e/fakegod &f - Make your target believe that they just got free god mode! "
			+ "\n&e/fakechat &f - Send a chat message pretending to be someone else. "
			+ "\n&e/fakemsg &f - Send a private message to someone pretending to be someone else. "
			+ "\n&e/fakeafk &f - Pretend to be afk or set another player afk. "
			+ "\n&e/burn &f - Be nasty. "
			+ "\n&e/poison &f - Poison a player for x seconds. "
			+ "\n&e/fakeban &f - Make a player believe they just got banned. "
			+ "\n&e/anvil &f - Spawn an anvil on the target's head! "
			+ "\n&e/forcecmd &f - Force a player to run a command. "
			+ "\n&e/freeze &f - Freeze a player! "
			+ "\n&e/fakepay &f - Fake a money transaction."
			+ "\n&e/randominv &f - Randomly make a player open their inventory. "
			+ "\n&e/gibberish &f - Change everything a player types in chat to gibberish! "
			+ "\n&e/renameitems &f - Rename all the items inside a player's inventory! "
			+ "\n&e/launch &f - Launch a player in the air!"
			+ "\n&e/swap &f - Make a player swap locations with another player."
			+ "\n&e/replaceinv &f - Replace all items in a player's inventory with something else."
			+ "\n&e/bury &f - Bury a player."
			+ "\n&e/nopickup &f - Prevent a player from picking up items!"
			+ "\n&e/cobweb &f - Trap a player into a cobweb."
			+ "\n&e/inventorylock &f - Lock a player out of their inventory."
			+ "\n&e/explode &f - Explode a player."
			+ "\n&e/badfood &f - Make a player get poisoned by everything they eat."
			+ "\n&e/explodeblocks &f - Blow up every block a player mines.");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			
			if (args.length == 0) {
				if (!sender.hasPermission("faketroll.help") && plugin.getConfig().getBoolean("help.require-permission")) {
					sender.sendMessage(ChatColor.RED + "You do not have access to that command");
					return false;	
				}
				sender.sendMessage(HELP);
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")) {
				if (!sender.hasPermission("faketroll.reload")) {
					sender.sendMessage(ChatColor.RED + "You do not have access to that command");
					return false;
				}
				this.plugin.reloadConfig();
				sender.sendMessage(ChatColor.AQUA + "Configuration file has been reloaded!");
				return true;
			}
			if (!sender.hasPermission("faketroll.help") && plugin.getConfig().getBoolean("help.require-permission")) {
				sender.sendMessage(ChatColor.RED + "You do not have access to that command");
				return false;	
			}
			sender.sendMessage(HELP);
			
		return true;
	}

}