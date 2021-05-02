package com.nktfh100.KryRegions.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.nktfh100.KryRegions.inventory.CustomHolder;
import com.nktfh100.KryRegions.inventory.Icon;

public class InvClick implements Listener {

	@EventHandler
	public void invClick(InventoryClickEvent ev) {
		Player player = (Player) ev.getWhoClicked();
		Inventory inv = ev.getClickedInventory();
		if (inv != null && inv.getHolder() != null && inv.getHolder() instanceof CustomHolder) {
			ev.setCancelled(true);
			CustomHolder customHolder = (CustomHolder) ev.getView().getTopInventory().getHolder();
			Icon icon = customHolder.getIcon(ev.getRawSlot());
			if (icon != null) {
				icon.executeActions(player, ev);
			}
			customHolder.click(ev);
			return;
		}
	}
}
