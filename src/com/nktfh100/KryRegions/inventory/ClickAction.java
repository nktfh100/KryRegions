package com.nktfh100.KryRegions.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ClickAction {

	void execute(Player player, InventoryClickEvent ev);
}
