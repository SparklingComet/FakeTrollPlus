package org.shanerx.faketrollplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shanerx.faketrollplus.FakeTrollPlus;

public class Fakeop implements CommandExecutor {

	FakeTrollPlus plugin;
	
	public Fakeop(FakeTrollPlus instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		/*  43 */     if (cmd.getName().equalsIgnoreCase("fakeop")) {
			/*  44 */       if (!this.plugin.getConfig().getBoolean("enable-fake-op")) {
			/*  45 */         sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("message-for-disabled-cmds")));
			/*  46 */         return true;
			/*     */       }
			/*  48 */       if (!sender.hasPermission("faketroll.fakeop")) {
			/*  49 */         sender.sendMessage(ChatColor.RED + "You do not have access to that command!");
			/*  50 */         return true;
			/*     */       }
			/*  52 */       if (args.length != 1) {
			/*  53 */         sender.sendMessage(ChatColor.GOLD + "Usage: /fakeop <target>");
			/*  54 */         return true;
			/*     */       }
			/*  56 */       Player target = this.plugin.getServer().getPlayer(args[0]);
			/*     */ 
			/*  61 */       if (target == null) {
			/*  62 */         sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("invalid-target")));
			/*  63 */         return true;
			/*     */       }
			/*  65 */       String target_name = target.getName();
			/*  66 */       if (!(sender instanceof Player)) {
			/*  67 */         target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&o[Server: Opped " + target_name + "]"));
			/*  68 */         return true;
			/*     */       }
			/*  70 */       target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&o[" + sender.getName() + ": Opped " + target_name + "]"));
			/*  71 */       sender.sendMessage(ChatColor.GOLD + "Fake-opped " + target_name + "!");
			/*     */     }
		
		return true;
	}

}
