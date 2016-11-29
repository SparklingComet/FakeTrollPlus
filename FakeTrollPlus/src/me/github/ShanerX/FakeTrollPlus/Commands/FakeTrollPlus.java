package me.github.ShanerX.FakeTrollPlus.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FakeTrollPlus implements CommandExecutor {
	
	me.github.ShanerX.FakeTrollPlus.FakeTrollPlus plugin;
	
	public FakeTrollPlus(me.github.ShanerX.FakeTrollPlus.FakeTrollPlus instance) {
		
		plugin = instance;
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("faketrollplus")) {
			
			if (args.length == 0) {
				if (!sender.hasPermission("faketrollplus.help") && plugin.getConfig().getBoolean("help.require-permission")) {
					sender.sendMessage(ChatColor.RED + "You do not have access to that command");
					return true;	
				}
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n \n &f----------&e[ &aHELP &e]&f---------- \n&aFakeTrollPlus &ev3.0.0 by Lori00 \n&bFor commands and permissions help go to &3&ngoo.gl/c8RL4G \n&cCommands:\n&e/faketrollplus &f- Display plugin help \n&e/fakeop &f- Make a player believe that they just got opped! \n&e/fakedeop &f- Make a player believe that they just got de-opped! \n&e/fakealert &f - Send a BungeeCord alert message to a single player \n&e/fakebroadcast &f - Send an Essentials broadcast message, which looks like server-wide broadcast, to a single player \n&e/fakejoin &f - Pretend that a certain player joined the game. \n&e/fakeleave &f - Pretend that a certain player left the game. \n&e/hurt &f - Be mean. \n&e/tprandom &f - Teleport a player to a random location. \n&e/portal &f - Banish the player by sending them very far away. \n&e/spam &f - Annoy a player. \n&e/clearinv &f - Clear someone's inventory. \n&e/fakecrash &f - Kick a player and show them a Java error message. \n&e/murder &f - Please don't do such an horrible thing! \n&e/fakegod &f - Make your target believe that they just got free god mode! \n&e/fakechat &f - Send a chat message pretending to be someone else. \n&e/fakemsg &f - Send a private message to someone pretending to be someone else. \n&e/fakeafk &f - Pretend to be afk or set another player afk. \n&e/burn &f - Be nasty. \n&e/poison &f - Poison a player for x seconds. \n&e/fakeban &f - Make a player believe they just got banned. \n&e/anvil &f - Spawn an anvil on the target's head! \n&e/forcecmd &f - Force a player to run a command. \n&e/freeze &f - Freeze a player! \n&e/fakepay &f - Fake a money transaction."));
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")) {
				if (!sender.hasPermission("faketrollplus.reload")) {
					sender.sendMessage(ChatColor.RED + "You do not have access to that command");
					return true;
				}
				this.plugin.reloadConfig();
				sender.sendMessage(ChatColor.AQUA + "Configuration file has been reloaded!");
				return true;
			}
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n \n &f----------&e[ &aHELP &e]&f---------- \n&aFakeTrollPlus &ev3.0.0 by Lori00 \n&bFor commands and permissions help go to &3&ngoo.gl/c8RL4G \n&cCommands:\n&e/faketrollplus &f- Display plugin help \n&e/fakeop &f- Make a player believe that they just got opped! \n&e/fakedeop &f- Make a player believe that they just got de-opped! \n&e/fakealert &f - Send a BungeeCord alert message to a single player \n&e/fakebroadcast &f - Send an Essentials broadcast message, which looks like server-wide broadcast, to a single player \n&e/fakejoin &f - Pretend that a certain player joined the game. \n&e/fakeleave &f - Pretend that a certain player left the game. \n&e/hurt &f - Be mean. \n&e/tprandom &f - Teleport a player to a random location. \n&e/portal &f - Banish the player by sending them very far away. \n&e/spam &f - Annoy a player. \n&e/clearinv &f - Clear someone's inventory. \n&e/fakecrash &f - Kick a player and show them a Java error message. \n&e/murder &f - Please don't do such an horrible thing! \n&e/fakegod &f - Make your target believe that they just got free god mode! \n&e/fakechat &f - Send a chat message pretending to be someone else. \n&e/fakemsg &f - Send a private message to someone pretending to be someone else. \n&e/fakeafk &f - Pretend to be afk or set another player afk. \n&e/burn &f - Be nasty. \n&e/poison &f - Poison a player for x seconds. \n&e/fakeban &f - Make a player believe they just got banned. \n&e/anvil &f - Spawn an anvil on the target's head! \n&e/forcecmd &f - Force a player to run a command. \n&e/freeze &f - Freeze a player! \n&e/fakepay &f - Fake a money transaction."));
		}
		
		return true;
	}

}
