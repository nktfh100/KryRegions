package com.nktfh100.KryRegions.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import com.nktfh100.KryRegions.info.Region;
import com.nktfh100.KryRegions.main.KryRegions;

public class RegionManager {

	private KryRegions plugin;

	private File regionsConfigFIle;
	private YamlConfiguration regionsConfig;
	private ArrayList<Region> regions = new ArrayList<Region>();

	public RegionManager(KryRegions instance) {
		this.plugin = instance;
	}

	public void loadRegions() {
		this.regions.clear();

		this.regionsConfigFIle = new File(this.plugin.getDataFolder(), "regions.yml");
		if (!this.regionsConfigFIle.exists()) {
			try {
				this.plugin.saveResource("regions.yml", false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.regionsConfig = YamlConfiguration.loadConfiguration(this.regionsConfigFIle);

		for (String regionKey : this.regionsConfig.getConfigurationSection("regions").getKeys(false)) {
			this.regions.add(new Region(this.regionsConfig.getConfigurationSection("regions." + regionKey), regionKey));
		}

		new BukkitRunnable() {
			@Override
			public void run() {
				for (Region region : regions) {
					if (region.getGuardian() != null) {
						region.getGuardian().create();
					}
				}
			}
		}.runTaskLater(plugin, 20L);
	}

	public void addRegion(Region reg, Boolean saveToConfig) {
		this.regions.add(reg);

		if (saveToConfig) {
			this.saveRegion(reg);
		}
	}

	public void deleteRegion(Region reg) {
		if (reg.getGuardianEnabled() || reg.getGuardian() != null) {
			reg.getGuardian().delete();
		}
		this.regions.remove(reg);
		this.regionsConfig.set("regions." + reg.getConfigKey(), null);
		try {
			this.regionsConfig.save(this.regionsConfigFIle);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveRegion(Region reg) {
		ConfigurationSection regSC = this.regionsConfig.createSection("regions." + reg.getConfigKey());

		regSC.set("regen", reg.getRegenTime());
		regSC.set("world", reg.getCorner1().getWorld().getName());
		regSC.set("corner1.x", reg.getCorner1().getBlockX());
		regSC.set("corner1.y", reg.getCorner1().getBlockY());
		regSC.set("corner1.z", reg.getCorner1().getBlockZ());
		regSC.set("corner2.x", reg.getCorner2().getBlockX());
		regSC.set("corner2.y", reg.getCorner2().getBlockY());
		regSC.set("corner2.z", reg.getCorner2().getBlockZ());

		regSC.set("guardianEnabled", reg.getGuardianEnabled());

		if (reg.getGuardianEnabled() && reg.getGuardianLoc() != null) {
			regSC.set("guardian.x", reg.getGuardianLoc().getX());
			regSC.set("guardian.y", reg.getGuardianLoc().getY());
			regSC.set("guardian.z", reg.getGuardianLoc().getZ());
		}

		ArrayList<String> blocksStr = new ArrayList<String>();
		for (Material mat : reg.getBlocks()) {
			blocksStr.add(mat.toString());
		}
		regSC.set("blocks", blocksStr);

		try {
			this.regionsConfig.save(this.regionsConfigFIle);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Region> geRegionsAt(Location loc) {
		ArrayList<Region> out = new ArrayList<Region>();
		for (Region region : this.regions) {
			if (region.isInside(loc)) {
				out.add(region);
			}
		}
		return out;
	}

	public ArrayList<Region> getRegions() {
		return this.regions;
	}
}
