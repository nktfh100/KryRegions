package com.nktfh100.KryRegions.main;

import org.bukkit.plugin.java.JavaPlugin;

import com.nktfh100.KryRegions.commands.KryRegionsCommand;
import com.nktfh100.KryRegions.commands.KryRegionsCommandTab;
import com.nktfh100.KryRegions.events.BlockBreak;
import com.nktfh100.KryRegions.events.BlockPlace;
import com.nktfh100.KryRegions.events.EntityInteract;
import com.nktfh100.KryRegions.events.InvClick;
import com.nktfh100.KryRegions.info.Region;
import com.nktfh100.KryRegions.managers.ConfigManager;
import com.nktfh100.KryRegions.managers.MessagesManager;
import com.nktfh100.KryRegions.managers.RegionManager;

public class KryRegions extends JavaPlugin {

	private static KryRegions instance;

	public KryRegions() {
		instance = this;
	}

	private ConfigManager configManager;
	private MessagesManager messagesManager;
	private RegionManager regionManager;

	@Override
	public void onEnable() {

		this.configManager = new ConfigManager(this);
		this.configManager.loadConfig();
		this.messagesManager = new MessagesManager(this);
		this.messagesManager.loadAll();
		this.regionManager = new RegionManager(this);
		this.regionManager.loadRegions();

		this.getServer().getPluginManager().registerEvents(new BlockBreak(this), this);
		this.getServer().getPluginManager().registerEvents(new BlockPlace(this), this);
		this.getServer().getPluginManager().registerEvents(new InvClick(), this);
		this.getServer().getPluginManager().registerEvents(new EntityInteract(), this);

		this.getCommand("kryregions").setExecutor(new KryRegionsCommand(this));
		this.getCommand("kryregions").setTabCompleter(new KryRegionsCommandTab());
	}

	@Override
	public void onDisable() {
		for (Region region : this.regionManager.getRegions()) {
			if (region.getGuardianEnabled() && region.getGuardian() != null) {
				region.getGuardian().delete();
			}
		}
	}

	public static KryRegions getInstance() {
		return instance;
	}

	public RegionManager getRegionManager() {
		return regionManager;
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public MessagesManager getMessagesManager() {
		return messagesManager;
	}

}
