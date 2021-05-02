package com.nktfh100.KryRegions.managers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.nktfh100.KryRegions.main.KryRegions;

public class ConfigManager {

	private KryRegions plugin;

	private ArrayList<World> enabledWorlds = new ArrayList<World>();
	private String guardianTexture = "";

	public ConfigManager(KryRegions instance) {
		this.plugin = instance;
	}

	public void loadConfig() {
		this.plugin.saveDefaultConfig();
		this.loadConfigVars();
	}

	public void loadConfigVars() {
		this.enabledWorlds.clear();

		this.plugin.reloadConfig();
		FileConfiguration config = this.plugin.getConfig();

		this.guardianTexture = config.getString("guardian-texture", "");
		
		for (String worldName : config.getStringList("enabled-worlds")) {
			World world = Bukkit.getWorld(worldName);
			if (world != null) {
				this.enabledWorlds.add(world);
			}
		}
	}

	public Boolean isWorldEnabled(String worldName) {
		for (World world : this.enabledWorlds) {
			if (world.getName().equals(worldName)) {
				return true;
			}
		}
		return false;
	}

	public String getGuardianTexture() {
		return guardianTexture;
	}
}
