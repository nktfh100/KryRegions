package com.nktfh100.KryRegions.events;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class EntityInteract implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent ev) {
		if (ev.getRightClicked() instanceof ArmorStand) {
			if (ev.getRightClicked().getCustomName() != null && ev.getRightClicked().getCustomName().equals("KryRegions")) {
				ev.setCancelled(true);
			}
		}
	}
}
