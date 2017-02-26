package org.shanerx.faketrollplus;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.shanerx.faketrollplus.commands.*;

public class Executor implements CommandExecutor {
	
	org.shanerx.faketrollplus.FakeTrollPlus plugin;
	
	public Executor(org.shanerx.faketrollplus.FakeTrollPlus instance) {
		plugin = instance;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		String cmd = arg1.getName();
		switch (cmd) {
		case "faketrollplus":
			new org.shanerx.faketrollplus.commands.FakeTrollPlus(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "fakeop":
			new Fakeop(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "fakedeop":
			new Fakedeop(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "fakealert":
			new Fakealert(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "fakebroadcast":
			new Fakebroadcast(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "fakejoin":
			new Fakejoin(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "fakeleave":
			new Fakeleave(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "hurt":
			new Hurt(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "tprandom":
			new TpRandom(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "portal":
			new Portal(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "spam":
			new Spam(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "clearinv":
			new Clearinv(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "fakecrash":
			new Fakecrash(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "murder":
			new Murder(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "fakegod":
			new Fakegod(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "fakechat":
			new Fakechat(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "fakemsg":
			new Fakemsg(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "fakeafk":
			new Fakeafk(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "burn":
			new Burn(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "poison":
			new Poison(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "fakeban":
			new Fakeban(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "anvil":
			new Anvil(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "forcecmd":
			new Forcecmd(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "freeze":
			new Freeze(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "fakepay":
			new Fakepay(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "randominv":
			new RandomInv(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "gibberish":
			new Gibberish(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "renameitems":
			new RenameItems(plugin).onCommand(arg0, arg1, arg2, arg3);
		case "launch":
			new Launch(plugin).onCommand(arg0, arg1, arg2, arg3);
		}
		
		if (!FakeTrollPlus.doLogging) {
			return true;
		}
		
		StringBuilder args = new StringBuilder();
		for (String arg : arg3) {
			args.append(" ").append(arg);
		}
		FakeTrollPlus.log.write("<" + new Date().toGMTString() + "> [ " + arg0.getName() + " ] ran  ->   " + arg1.getName() + args.toString() + "\n");
		FakeTrollPlus.log.flush();
		return true;
	}

}
