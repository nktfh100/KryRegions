package com.nktfh100.KryRegions.info;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.nktfh100.KryRegions.main.KryRegions;
import com.nktfh100.KryRegions.utils.Utils;

public class Guardian {

	private Location location;
	private ArmorStand armorStand;
	private BukkitTask runnable = null;

	public Guardian(Location loc) {
		this.location = loc;
	}

	@SuppressWarnings("deprecation")
	public void create() {
		this.armorStand = (ArmorStand) this.location.getWorld().spawnEntity(this.location, EntityType.ARMOR_STAND);
		this.armorStand.setInvulnerable(true);
		this.armorStand.setCollidable(false);
		this.armorStand.setVisible(false);
		this.armorStand.setGravity(false);
		this.armorStand.setHelmet(Utils.createSkull(KryRegions.getInstance().getConfigManager().getGuardianTexture(), "", 1));
		this.armorStand.setCustomName("KryRegions");
		this.armorStand.setCustomNameVisible(false);
		Guardian guardian = this;
		this.runnable = new BukkitRunnable() {
			Boolean goUp = true;
			int i = 0;

			@Override
			public void run() {
				if (guardian.getArmorStand() == null || guardian.getArmorStand().isDead()) {
					this.cancel();
					return;
				}
				guardian.getArmorStand().setHeadPose(guardian.getArmorStand().getHeadPose().add(0, 0.4, 0));
				if (goUp) {
					guardian.getArmorStand().teleport(guardian.getArmorStand().getLocation().add(0, 0.32, 0));
				} else {
					guardian.getArmorStand().teleport(guardian.getArmorStand().getLocation().add(0, -0.32, 0));
				}
				i++;
				if (i > 3) {
					goUp = !goUp;
					i = 0;
				}
			}
		}.runTaskTimer(KryRegions.getInstance(), 1L, 4L);
	}

	public void showParticles(Location blockPlacedLoc) {
		if (this.armorStand == null || this.armorStand.isDead()) {
			return;
		}
		World world = this.armorStand.getWorld();
		if (blockPlacedLoc.getWorld() != world) {
			return;
		}
		Location armorStandLoc = this.armorStand.getEyeLocation();
		Vector vec = blockPlacedLoc.clone().subtract(armorStandLoc).toVector();
		double vecLength = vec.length();
		vec.normalize();
		DustOptions options = new DustOptions(Color.BLACK, 1);
		vec.multiply(2.5);
		armorStandLoc.add(vec);
		for (double i = 0; i < vecLength; i += 2.5) {
			world.spawnParticle(Particle.REDSTONE, armorStandLoc, 1, 0.1, 0.1, 0.1, 0.1, options);
			armorStandLoc.add(vec);
		}
	}

	public void delete() {
		if (this.armorStand != null) {
			this.armorStand.remove();
			this.armorStand = null;
		}
		if (this.runnable != null) {
			this.runnable.cancel();
			this.runnable = null;
		}
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location to) {
		this.location = to;
	}

	public ArmorStand getArmorStand() {
		return armorStand;
	}

}
