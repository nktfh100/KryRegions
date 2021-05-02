package com.nktfh100.KryRegions.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Utils {

	final public static String ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String locationToStringB(Location loc) {
		if (loc == null || loc.getWorld() == null) {
			return "()";
		}
		String output = "(" + loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + ")";
		return output;
	}

	public static ItemStack setItemName(ItemStack item, String name, ArrayList<String> lore) {
		if (item != null && item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
			ArrayList<String> metaLore = new ArrayList<String>();

			for (String lore_ : lore) {
				metaLore.add(lore_);
			}
			meta.setLore(metaLore);
			item.setItemMeta(meta);
		}
		return item;
	}

	public static ItemStack setItemLore(ItemStack item, ArrayList<String> lore) {
		if (item != null && item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();
			ArrayList<String> metaLore = new ArrayList<String>();
			for (String lore_ : lore) {
				metaLore.add(ChatColor.translateAlternateColorCodes('&', lore_));
			}
			meta.setLore(metaLore);
			item.setItemMeta(meta);
		}
		return item;
	}

	public static ItemStack enchantedItem(ItemStack item, Enchantment ench, int lvl) {
		if (item != null && item.getType() != Material.AIR && item.getItemMeta() != null) {
			ItemMeta meta = item.getItemMeta();
			meta.addEnchant(ench, lvl, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(meta);
		}
		return item;
	}

	public static ItemStack createItem(Material mat, String name) {
		ItemStack item = new ItemStack(mat);
		if (item != null && item.getItemMeta() != null && item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(name);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			item.setItemMeta(meta);
		}
		return (item);
	}

	public static ItemStack createItem(Material mat, String name, int amount) {
		ItemStack item = createItem(mat, name);
		item.setAmount(amount);
		return (item);
	}

	public static ItemStack createItem(Material mat, String name, int amount, String... lore) {
		ItemStack item = createItem(mat, name, amount);
		if (item != null && item.getItemMeta() != null && item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();

			ArrayList<String> metaLore = new ArrayList<String>();

			for (String lorecomments : lore) {
				metaLore.add(lorecomments);
			}
			meta.setLore(metaLore);
			item.setItemMeta(meta);
		}
		return (item);
	}

	public static ItemStack createItem(Material mat, String name, int amount, ArrayList<String> lore) {
		ItemStack item = createItem(mat, name, amount);
		if (item != null && item.getItemMeta() != null && item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();

			meta.setLore(lore);
			item.setItemMeta(meta);
		}
		return (item);
	}

	public static ItemStack createEnchantedItem(Material mat, String name, Enchantment ench, int lvl, String... lore) {
		ItemStack item = createItem(mat, name, 1);
		if (item != null && item.getItemMeta() != null && item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();
			meta.addEnchant(ench, lvl, true);
			ArrayList<String> metaLore = new ArrayList<String>();

			for (String lorecomments : lore) {
				metaLore.add(lorecomments);
			}
			meta.setLore(metaLore);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(meta);
		}
		return (item);
	}

	public static ItemStack addItemFlag(ItemStack item, ItemFlag... flag) {
		if (item != null && item.getItemMeta() != null && item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();
			meta.addItemFlags(flag);
			item.setItemMeta(meta);
		}
		return item;
	}

	public static float getRandomFloat(float min, float max) {
		Random rand = new Random();
		return rand.nextFloat() * (max - min) + min;
	}

	public static int getRandomNumberInRange(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	final static String colors_ = "123456789abcdfe";

	public static String getRandomColors() {
		String str = "";
		for (int i = 0; i < 4; i++) {
			String char_ = String.valueOf(colors_.charAt(getRandomNumberInRange(0, colors_.length() - 1)));
			str = str + "&" + char_;
		}
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	final static char[] chars_ = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890".toCharArray();

	public static String getRandomString(int length) {
		String randomString = "";

		final Random random = new Random();
		for (int i = 0; i < length; i++) {
			randomString = randomString + chars_[random.nextInt(chars_.length)];
		}

		return randomString;
	}

	public static String reverseString(String str) {

		StringBuilder strBuilder = new StringBuilder();

		strBuilder.append(str);
		strBuilder = strBuilder.reverse();
		return strBuilder.toString();

	}

	public static ItemStack createSkull(String url, String name, int amount, ArrayList<String> lore) {
		ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
		if (url.isEmpty()) {
			return head;
		}
		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", url));

		try {
			Field profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);

		} catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
			error.printStackTrace();
		}
		headMeta.setDisplayName(name);
		head.setAmount(amount);
		ArrayList<String> metaLore = new ArrayList<String>();

		for (String lorecomments : lore) {
			metaLore.add(lorecomments);
		}
		headMeta.setLore(metaLore);
		head.setItemMeta(headMeta);
		return head;
	}

	public static ItemStack createSkull(String url, String name, int amount, String... lore) {
		ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
		if (url.isEmpty()) {
			return head;
		}
		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", url));

		try {
			Field profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);

		} catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
			error.printStackTrace();
		}
		headMeta.setDisplayName(name);
		head.setAmount(amount);
		ArrayList<String> metaLore = new ArrayList<String>();

		for (String lorecomments : lore) {
			metaLore.add(lorecomments);
		}
		headMeta.setLore(metaLore);
		head.setItemMeta(headMeta);
		return head;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getHead(String player) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
		try {
			SkullMeta skull = (SkullMeta) item.getItemMeta();
			skull.setOwner(player);
			item.setItemMeta(skull);
			return item;
		} catch (Exception e) {
			Bukkit.getLogger().info("Couldnt get " + player + "'s head");
			e.printStackTrace();
			return item;
		}
	}

	public static void addBorder(Inventory inv, Material mat) {
		ItemStack border = Utils.createItem(mat, " ");
		if (inv.getSize() == 45) {
			for (int slot = 0; slot < inv.getSize(); slot++) {
				if (inv.getItem(slot) == null) {
					if ((slot >= 0 && slot <= 8) || (slot >= 36 && slot <= 44) || (slot % 9 == 0) || ((slot - 8) % 9 == 0)) {
						inv.setItem(slot, border);
					}
				}
			}
		} else if (inv.getSize() == 54) {
			for (int slot = 0; slot < inv.getSize(); slot++) {
				if (inv.getItem(slot) == null) {
					if ((slot >= 0 && slot <= 8) || (slot >= 45 && slot <= 53) || (slot % 9 == 0) || ((slot - 8) % 9 == 0)) {
						inv.setItem(slot, border);
					}
				}
			}
		}

	}

	public static void addBorder(Inventory inv) {
		addBorder(inv, Material.BLUE_STAINED_GLASS_PANE);
	}

	public static void fillInv(Inventory inv) {
		fillInv(inv, Material.BLACK_STAINED_GLASS_PANE);
	}

	public static void fillInv(Inventory inv, Material mat) {
		ItemStack item = Utils.createItem(mat, " ");
		for (int slot = 0; slot < inv.getSize(); slot++) {
			inv.setItem(slot, item);
		}
	}

	public static void sendActionBar(Player player, String message) {
		if (!player.isOnline())
			return;

		if (message == null)
			message = "";

		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
	}
}