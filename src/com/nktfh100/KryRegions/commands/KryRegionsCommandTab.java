package com.nktfh100.KryRegions.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class KryRegionsCommandTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> COMMANDS = new ArrayList<>();
		int arg = 0;
		if (!sender.hasPermission("kryregions.admin")) {
			return COMMANDS;
		}
		if (args.length == 1) {
			arg = 0;
			COMMANDS.add("manage");
			COMMANDS.add("add");
		}

		final List<String> completions = new ArrayList<>();
		StringUtil.copyPartialMatches(args[arg], COMMANDS, completions);

		Collections.sort(completions);
		return completions;
	}

}
