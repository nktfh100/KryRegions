package com.nktfh100.KryRegions.events;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.nktfh100.KryRegions.info.Region;
import com.nktfh100.KryRegions.main.KryRegions;

public class BlockBreak implements Listener {

	private KryRegions plugin;

	public BlockBreak(KryRegions instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent ev) {
		final Location loc = ev.getBlock().getLocation();
		if (plugin.getConfigManager().isWorldEnabled(loc.getWorld().getName())) {
			ArrayList<Region> regions = plugin.getRegionManager().geRegionsAt(loc);
			if (regions.size() == 0) {
				if (!ev.getPlayer().hasPermission("kryislands.admin")) {
					ev.setCancelled(true);
				}
				return;
			}
			final Material mat = ev.getBlock().getType();
			Boolean isFine = false;
			Region region = null;
			for (Region reg : regions) {
				if (reg.isMaterial(mat)) {
					isFine = true;
					region = reg;
					break;
				}
			}
			if (!isFine) {
				if (!ev.getPlayer().hasPermission("kryislands.admin")) {
					ev.setCancelled(true);
				}
				return;
			}
			final ArrayList<Block> blocksToRegen = new ArrayList<Block>();
			blocksToRegen.add(ev.getBlock());
			if (mat.toString().contains("ORE")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						loc.getBlock().setType(Material.BEDROCK);
					}
				}.runTaskLater(plugin, 1L);
			} else if (mat == Material.CACTUS || mat == Material.SUGAR_CANE) {
				Location loc_ = ev.getBlock().getLocation().clone();
				for (int i = 0; i < 10; i++) {
					loc_.add(0, 1, 0);
					if (loc_.getBlock().getType() == mat) {
						blocksToRegen.add(loc_.getBlock());
					} else {
						break;
					}
				}
			}
			final Boolean isCrop = ev.getBlock().getBlockData() instanceof Ageable ? true : false;
			final Region region_ = region;
			new BukkitRunnable() {
				@Override
				public void run() {
					if (plugin.isEnabled()) {
						for (Block block_ : blocksToRegen) {
							block_.setType(mat, false);
						}
						if (region_.getGuardianEnabled() && region_.getGuardian() != null) {
							region_.getGuardian().showParticles(loc);
						}
						if (isCrop) {
							Ageable ageable = (Ageable) loc.getBlock().getBlockData();
							ageable.setAge(ageable.getMaximumAge());
							loc.getBlock().setBlockData(ageable);
						}
					}
				}
			}.runTaskLater(plugin, 20L * region.getRegenTime() + 2L);
		}
	}
}
