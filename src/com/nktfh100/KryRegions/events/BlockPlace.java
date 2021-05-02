package com.nktfh100.KryRegions.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.nktfh100.KryRegions.main.KryRegions;

public class BlockPlace implements Listener {

	private KryRegions plugin;

	public BlockPlace(KryRegions instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onBlockBreak(BlockPlaceEvent ev) {
		if (plugin.getConfigManager().isWorldEnabled(ev.getBlock().getLocation().getWorld().getName())) {
			if (!ev.getPlayer().hasPermission("kryislands.admin")) {
				ev.setCancelled(true);
			}
		}
	}
}
