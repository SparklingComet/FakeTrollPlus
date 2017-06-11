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

import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.shanerx.faketrollplus.commands.*;

public class Executor implements CommandExecutor {
	
	private FakeTrollPlus plugin;
	
	public Executor(FakeTrollPlus instance) {
		plugin = instance;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String command = cmd.getName();
		switch (command) {
		case "faketrollplus":
			new Faketrollplus(plugin).onCommand(sender, cmd, label, args);
			break;
		case "fakeop":
			new Fakeop(plugin).onCommand(sender, cmd, label, args);
			break;
		case "fakedeop":
			new Fakedeop(plugin).onCommand(sender, cmd, label, args);
			break;
		case "fakealert":
			new Fakealert(plugin).onCommand(sender, cmd, label, args);
			break;
		case "fakebroadcast":
			new Fakebroadcast(plugin).onCommand(sender, cmd, label, args);
			break;
		case "fakejoin":
			new Fakejoin(plugin).onCommand(sender, cmd, label, args);
			break;
		case "fakeleave":
			new Fakeleave(plugin).onCommand(sender, cmd, label, args);
			break;
		case "hurt":
			new Hurt(plugin).onCommand(sender, cmd, label, args);
			break;
		case "tprandom":
			new TpRandom(plugin).onCommand(sender, cmd, label, args);
			break;
		case "portal":
			new Portal(plugin).onCommand(sender, cmd, label, args);
			break;
		case "spam":
			new Spam(plugin).onCommand(sender, cmd, label, args);
			break;
		case "clearinv":
			new Clearinv(plugin).onCommand(sender, cmd, label, args);
			break;
		case "fakecrash":
			new Fakecrash(plugin).onCommand(sender, cmd, label, args);
			break;
		case "murder":
			new Murder(plugin).onCommand(sender, cmd, label, args);
			break;
		case "fakegod":
			new Fakegod(plugin).onCommand(sender, cmd, label, args);
			break;
		case "fakechat":
			new Fakechat(plugin).onCommand(sender, cmd, label, args);
			break;
		case "fakemsg":
			new Fakemsg(plugin).onCommand(sender, cmd, label, args);
			break;
		case "fakeafk":
			new Fakeafk(plugin).onCommand(sender, cmd, label, args);
			break;
		case "burn":
			new Burn(plugin).onCommand(sender, cmd, label, args);
			break;
		case "poison":
			new Poison(plugin).onCommand(sender, cmd, label, args);
			break;
		case "fakeban":
			new Fakeban(plugin).onCommand(sender, cmd, label, args);
			break;
		case "anvil":
			new Anvil(plugin).onCommand(sender, cmd, label, args);
			break;
		case "forcecmd":
			new Forcecmd(plugin).onCommand(sender, cmd, label, args);
			break;
		case "freeze":
			new Freeze(plugin).onCommand(sender, cmd, label, args);
			break;
		case "fakepay":
			new Fakepay(plugin).onCommand(sender, cmd, label, args);
			break;
		case "randominv":
			new RandomInv(plugin).onCommand(sender, cmd, label, args);
			break;
		case "gibberish":
			new Gibberish(plugin).onCommand(sender, cmd, label, args);
			break;
		case "renameitems":
			new RenameItems(plugin).onCommand(sender, cmd, label, args);
			break;
		case "launch":
			new Launch(plugin).onCommand(sender, cmd, label, args);
			break;
		case "swap":
			new Swap(plugin).onCommand(sender, cmd, label, args);
			break;
		case "replaceinv":
			new ReplaceInv(plugin).onCommand(sender, cmd, label, args);
			break;
		case "bury":
			new Bury(plugin).onCommand(sender, cmd, label, args);
			break;
		case "nopickup":
			new Nopickup(plugin).onCommand(sender, cmd, label, args);
			break;
		case "cobweb":
			new Cobweb(plugin).onCommand(sender, cmd, label, args);
			break;
		case "inventorylock":
			new Invlock(plugin).onCommand(sender, cmd, label, args);
			break;
		case "explode":
			new Explode(plugin).onCommand(sender, cmd, label, args);
			break;
		case "badfood":
			new Badfood(plugin).onCommand(sender, cmd, label, args);
			break;
		case "explodeblocks":
			new ExplodeBlocks(plugin).onCommand(sender, cmd, label, args);
			break;
		case "blacklist":
			new Blacklist(plugin).onCommand(sender, cmd, label, args);
			break;
		}
		
		if (!FakeTrollPlus.doLogging) {
			return true;
		}
		
		StringBuilder sb = new StringBuilder();
		for (String arg : args) {
			sb.append(" ").append(arg);
		}
		plugin.getLog().write("<" + new Date().toGMTString() + "> [ " + sender.getName() + " ] ran  ->   " + cmd.getName() + sb.toString() + "\n");
		plugin.getLog().flush();
		return true;
	}

}
