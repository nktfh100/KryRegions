package com.nktfh100.KryRegions.inventory;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.nktfh100.KryRegions.info.Region;
import com.nktfh100.KryRegions.main.KryRegions;
import com.nktfh100.KryRegions.utils.Utils;

public class RegionsInv {

	public static void openRegionManager(Player player) {
		KryRegions plugin = KryRegions.getInstance();
		CustomHolder invHolder = new CustomHolder(54, plugin.getMessagesManager().getMsg("region-manager-title"));

		Utils.addBorder(invHolder.getInventory());

		for (Region region : plugin.getRegionManager().getRegions()) {
			Material mat = Material.STONE;
			if (!region.getBlocks().isEmpty()) {
				mat = region.getBlocks().get(0);
			}
			ItemStack regionItem = Utils.createItem(mat, ChatColor.YELLOW + "" + ChatColor.BOLD + "Region: " + region.getConfigKey(), 1, ChatColor.YELLOW + "Click to edit this region",
					ChatColor.YELLOW + "Blocks: " + region.getBlocks().size());
			Icon icon = new Icon(regionItem);
			icon.addClickAction(new ClickAction() {
				@Override
				public void execute(Player player, InventoryClickEvent ev) {
					openEditRegion(player, region);
				}
			});
			invHolder.addIcon(icon);
		}

		player.openInventory(invHolder.getInventory());
	}

	public static void openEditRegion(Player player, Region region) {
		KryRegions plugin = KryRegions.getInstance();
		CustomHolder invHolder = new CustomHolder(54, plugin.getMessagesManager().getMsg("region-editing-title", region.getConfigKey()));

		Utils.addBorder(invHolder.getInventory());

		Material mat = Material.STONE;
		if (!region.getBlocks().isEmpty()) {
			mat = region.getBlocks().get(0);
		}

		invHolder.getInventory().setItem(0, Utils.createItem(mat, ChatColor.YELLOW + "Region: " + region.getConfigKey()));

		invHolder.setIcon(45, new Icon(Utils.createItem(Material.ARROW, ChatColor.YELLOW + "Back")).addClickAction(new ClickAction() {
			@Override
			public void execute(Player player, InventoryClickEvent ev) {
				openRegionManager(player);
			}
		}));

		invHolder.addIcon(
				new Icon(Utils.createItem(mat, ChatColor.YELLOW + "Blocks", 1, ChatColor.GREEN + "Blocks amount: " + region.getBlocks().size(), ChatColor.GREEN + "Click to add / remove block types"))
						.addClickAction(new ClickAction() {
							@Override
							public void execute(Player player, InventoryClickEvent ev) {
								openEditRegionBlocks(player, region);
							}
						}));

		invHolder.addIcon(new Icon(Utils.createItem(Material.END_CRYSTAL, ChatColor.YELLOW + "Corner-1", 1, ChatColor.GREEN + Utils.locationToStringB(region.getCorner1()),
				ChatColor.GREEN + "Click to change to your current location")).addClickAction(new ClickAction() {
					@Override
					public void execute(Player player, InventoryClickEvent ev) {
						region.setCorner1(player.getLocation());
						plugin.getRegionManager().saveRegion(region);
						openEditRegion(player, region);
						player.sendMessage(ChatColor.GREEN + "Successfully changed corner-1 for region " + region.getConfigKey());
					}
				}));

		invHolder.addIcon(
				new Icon(Utils.createItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Teleport to corner 1", 1, ChatColor.GREEN + "Click to teleport to corner 1")).addClickAction(new ClickAction() {
					@Override
					public void execute(Player player, InventoryClickEvent ev) {
						player.teleport(region.getCorner1());
					}
				}));

		invHolder.addIcon(new Icon(Utils.createItem(Material.END_CRYSTAL, ChatColor.YELLOW + "Corner-2", 1, ChatColor.GREEN + Utils.locationToStringB(region.getCorner2()),
				ChatColor.GREEN + "Click to change to your current location")).addClickAction(new ClickAction() {
					@Override
					public void execute(Player player, InventoryClickEvent ev) {
						region.setCorner2(player.getLocation());
						plugin.getRegionManager().saveRegion(region);
						openEditRegion(player, region);
						player.sendMessage(ChatColor.GREEN + "Successfully changed corner-2 for region " + region.getConfigKey());
					}
				}));

		invHolder.addIcon(
				new Icon(Utils.createItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Teleport to corner 2", 1, ChatColor.GREEN + "Click to teleport to corner 2")).addClickAction(new ClickAction() {
					@Override
					public void execute(Player player, InventoryClickEvent ev) {
						player.teleport(region.getCorner2());
					}
				}));

		invHolder.addIcon(new Icon(Utils.createItem(Material.CLOCK, ChatColor.YELLOW + "Regen Time: " + region.getRegenTime() + "s", 1, ChatColor.GREEN + "Left click to add 1 second",
				ChatColor.GREEN + "Right click to remove 1 second")).addClickAction(new ClickAction() {
					@Override
					public void execute(Player player, InventoryClickEvent ev) {
						if (ev.getClick() == ClickType.LEFT) {
							region.setRegenTime(region.getRegenTime() + 1);
						} else if (ev.getClick() == ClickType.RIGHT) {
							if (region.getRegenTime() == 0) {
								return;
							}
							region.setRegenTime(region.getRegenTime() - 1);
						}
						plugin.getRegionManager().saveRegion(region);
						openEditRegion(player, region);
					}
				}));

		invHolder.addIcon(new Icon(Utils.createItem(region.getGuardianEnabled() ? Material.LIME_DYE : Material.RED_DYE, ChatColor.YELLOW + "Guardian enabled: " + region.getGuardianEnabled(), 1,
				ChatColor.GREEN + "Click to enable / disable")).addClickAction(new ClickAction() {
					@Override
					public void execute(Player player, InventoryClickEvent ev) {
						region.setGuardianEnabled(!region.getGuardianEnabled());
						if (region.getGuardianEnabled()) {
							region.createGuardian();
						} else if (region.getGuardian() != null) {
							region.getGuardian().delete();
						}
						plugin.getRegionManager().saveRegion(region);
						openEditRegion(player, region);
					}
				}));

		invHolder.addIcon(new Icon(Utils.createSkull(plugin.getConfigManager().getGuardianTexture(), ChatColor.YELLOW + "Guardian location", 1,
				ChatColor.GREEN + Utils.locationToStringB(region.getGuardianLoc()), ChatColor.GREEN + "Click to change to your location")).addClickAction(new ClickAction() {
					@Override
					public void execute(Player player, InventoryClickEvent ev) {
						Location loc = player.getLocation();
						region.setGuardianLoc(loc);
						if (region.getGuardian() != null) {
							region.getGuardian().setLocation(loc);
							region.getGuardian().getArmorStand().teleport(loc);
						}
						plugin.getRegionManager().saveRegion(region);
						openEditRegion(player, region);
					}
				}));

		invHolder.setIcon(53, new Icon(Utils.createItem(Material.TNT, ChatColor.RED + "Remove this region", 1, ChatColor.YELLOW + "Click to delete this region")).addClickAction(new ClickAction() {
			@Override
			public void execute(Player player, InventoryClickEvent ev) {
				plugin.getRegionManager().deleteRegion(region);
				openRegionManager(player);
			}
		}));

		player.openInventory(invHolder.getInventory());
	}

	public static void openEditRegionBlocks(Player player, Region region) {
		KryRegions plugin = KryRegions.getInstance();
		CustomHolder invHolder = new CustomHolder(54, plugin.getMessagesManager().getMsg("region-blocks-title", region.getConfigKey()));

		Utils.addBorder(invHolder.getInventory());

		Material mat = Material.STONE;
		Material mat1 = Material.WHEAT;
		if (!region.getBlocks().isEmpty()) {
			mat = region.getBlocks().get(0);
			if (region.getBlocks().size() > 1) {
				mat1 = region.getBlocks().get(region.getBlocks().size() - 1);
			}
		}

		invHolder.getInventory().setItem(0, Utils.createItem(mat, ChatColor.YELLOW + "Region: " + region.getConfigKey()));
		invHolder.getInventory().setItem(1, Utils.createItem(mat1, ChatColor.YELLOW + "Region Blocks"));

		invHolder.setIcon(45, new Icon(Utils.createItem(Material.ARROW, ChatColor.YELLOW + "Back")).addClickAction(new ClickAction() {
			@Override
			public void execute(Player player, InventoryClickEvent ev) {
				openEditRegion(player, region);
			}
		}));

		invHolder.getInventory().setItem(49,
				Utils.createItem(Material.PAPER, ChatColor.YELLOW + "How to add blocks:", 1, ChatColor.GREEN + "Drag blocks from your inventory", ChatColor.GREEN + "to add them!"));

		for (Material blockMat : region.getBlocks()) {
			invHolder.addIcon(new Icon(Utils.createItem(blockMat, ChatColor.YELLOW + blockMat.toString(), 1, ChatColor.GREEN + "Click to remove this block")).addClickAction(new ClickAction() {
				@Override
				public void execute(Player player, InventoryClickEvent ev) {
					region.getBlocks().remove(blockMat);
					plugin.getRegionManager().saveRegion(region);
					openEditRegionBlocks(player, region);
				}
			}));
		}

		invHolder.setGlobalClickAction(new ClickAction() {
			@SuppressWarnings("deprecation")
			@Override
			public void execute(Player player, InventoryClickEvent ev) {
				if (ev.getCursor() != null && ev.getCursor().getType() != Material.AIR) {
					final Material clickedWith = ev.getCursor().getType();
					if (!region.getBlocks().contains(clickedWith)) {
						region.addblock(clickedWith);
						plugin.getRegionManager().saveRegion(region);
						ev.setCursor(new ItemStack(Material.AIR, 1));
						openEditRegionBlocks(player, region);
						player.sendMessage(ChatColor.GREEN + "Successfully added block " + clickedWith + " to region " + region.getConfigKey());
					}
				}
			}
		});

		player.openInventory(invHolder.getInventory());
	}

}
