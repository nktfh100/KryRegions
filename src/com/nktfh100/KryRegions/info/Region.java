package com.nktfh100.KryRegions.info;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class Region {

	private String configKey;

	private Location corner1;
	private Location corner2;

	private ArrayList<Material> blocks = new ArrayList<Material>();

	private Integer regenTime;

	private Boolean guardianEnabled = false;
	private Location guardianLoc = null;
	private Guardian guardian = null;

	public Region(String configKey, Location corner1, Location corner2, Integer regenTime, Boolean guardianEnabled, Location guardianLoc) {
		this.configKey = configKey;
		this.corner1 = corner1;
		this.corner2 = corner2;
		this.regenTime = regenTime;
		this.guardianEnabled = guardianEnabled;
		this.guardianLoc = guardianLoc;
		if (this.guardianEnabled) {
			if (guardianLoc != null) {
				this.guardian = new Guardian(this.guardianLoc);
			} else {
				this.guardian = new Guardian(this.corner1);
			}
		}
	}

	public Region(ConfigurationSection regionSC, String configKey) {
		this.configKey = configKey;

		this.regenTime = regionSC.getInt("regen", 5);

		World world = Bukkit.getWorld(regionSC.getString("world"));

		ConfigurationSection corner1SC = regionSC.getConfigurationSection("corner1");
		this.corner1 = new Location(world, corner1SC.getInt("x"), corner1SC.getInt("y"), corner1SC.getInt("z"));

		ConfigurationSection corner2SC = regionSC.getConfigurationSection("corner2");
		this.corner2 = new Location(world, corner2SC.getInt("x"), corner2SC.getInt("y"), corner2SC.getInt("z"));

		for (String blockStr : regionSC.getStringList("blocks")) {
			this.blocks.add(Material.getMaterial(blockStr));
		}
		this.guardianEnabled = regionSC.getBoolean("guardianEnabled", false);

		if (guardianEnabled) {
			ConfigurationSection guardianSC = regionSC.getConfigurationSection("guardian");
			if (guardianSC != null) {
				this.guardianLoc = new Location(world, guardianSC.getDouble("x"), guardianSC.getDouble("y"), guardianSC.getDouble("z"));
				this.guardian = new Guardian(this.guardianLoc);
			} else {
				this.guardian = new Guardian(this.corner1);
			}
		}
	}

	public void createGuardian() {
		this.guardian = new Guardian(this.guardianLoc != null ? this.guardianLoc : this.corner1);
		this.guardian.create();
	}

	public void addblock(Material block) {
		this.blocks.add(block);
	}

	public Boolean isInside(Location loc) {
		if ((loc.getX() >= this.corner1.getX()) && (loc.getX() <= this.corner2.getX()) || (loc.getX() <= this.corner1.getX()) && (loc.getX() >= this.corner2.getX())) {
			if ((loc.getZ() >= this.corner1.getZ()) && (loc.getZ() <= this.corner2.getZ()) || (loc.getZ() <= this.corner1.getZ()) && (loc.getZ() >= this.corner2.getZ())) {
				return true;
			}
		}
		return false;
	}

	public Boolean isMaterial(Material mat) {
		for (Material mat_ : this.blocks) {
			if (mat_ == mat) {
				return true;
			}
		}
		return false;
	}

	public Location getCorner1() {
		return corner1;
	}

	public void setCorner1(Location corner1) {
		this.corner1 = corner1;
	}

	public Location getCorner2() {
		return corner2;
	}

	public void setCorner2(Location corner2) {
		this.corner2 = corner2;
	}

	public Integer getRegenTime() {
		return regenTime;
	}

	public void setRegenTime(Integer regenTime) {
		this.regenTime = regenTime;
	}

	public ArrayList<Material> getBlocks() {
		return this.blocks;
	}

	public String getConfigKey() {
		return configKey;
	}

	public Guardian getGuardian() {
		return guardian;
	}

	public Location getGuardianLoc() {
		return guardianLoc;
	}

	public Boolean getGuardianEnabled() {
		return guardianEnabled;
	}

	public void setGuardianEnabled(Boolean to) {
		this.guardianEnabled = to;
	}

	public void setGuardianLoc(Location to) {
		this.guardianLoc = to;
	}

}
