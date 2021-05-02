package com.nktfh100.KryRegions.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nktfh100.KryRegions.info.Region;
import com.nktfh100.KryRegions.inventory.RegionsInv;
import com.nktfh100.KryRegions.main.KryRegions;

public class KryRegionsCommand implements CommandExecutor {

	private KryRegions plugin;

	public KryRegionsCommand(KryRegions instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if (sender.hasPermission("kryregions.admin")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "------------------------------------------");
				sender.sendMessage("       " + ChatColor.GOLD + "" + ChatColor.BOLD + "KryRegions");
				sender.sendMessage(ChatColor.YELLOW + "/kryregions manage" + ChatColor.WHITE + " - " + ChatColor.GOLD + "Open the admin GUI");
				sender.sendMessage(ChatColor.YELLOW + "/kryregions add <name>" + ChatColor.WHITE + " - " + ChatColor.GOLD + "Add a new region");
				sender.sendMessage(ChatColor.YELLOW + "/kryregions reload" + ChatColor.WHITE + " - " + ChatColor.GOLD + "Reload all configs");
				sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "------------------------------------------");
				return true;
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("manage")) {
					RegionsInv.openRegionManager(player);
					return true;
				} else if (args[0].equalsIgnoreCase("reload")) {
					plugin.getConfigManager().loadConfig();
					plugin.getMessagesManager().loadAll();
					plugin.getRegionManager().loadRegions();
					plugin.getMessagesManager().sendMessage(player, "configs-reloaded");
					return true;
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("add")) {
					Location loc = player.getLocation();
					Region newRegion = new Region(args[1], loc, loc, 5, false, null);
					plugin.getRegionManager().addRegion(newRegion, true);
					plugin.getMessagesManager().sendMessage(player, "region-created", args[1]);
					return true;
				}
			}
		} else {
			plugin.getMessagesManager().sendMessage(player, "no-permission");
		}
		return false;
	}

}
