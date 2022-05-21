package me.byteful.plugin.stormtrooper;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class StormtrooperPlugin extends JavaPlugin implements Listener {
  public static double clamp(double val, double min, double max) {
    return Math.max(min, Math.min(max, val));
  }

  @Override
  public void onEnable() {
    saveDefaultConfig();
    Bukkit.getPluginManager().registerEvents(this, this);
  }

  @EventHandler
  public void onBowShoot(EntityShootBowEvent e) {
    if (e.getEntityType() != EntityType.SKELETON && getConfig().getBoolean("skeletons", true)) {
      return;
    }

    if (e.getEntityType() != EntityType.PLAYER && getConfig().getBoolean("players", false)) {
      return;
    }

    final Vector vec = e.getProjectile().getVelocity().clone();
    vec.add(Vector.getRandom().multiply(clamp(getConfig().getDouble("accuracy", 0.7), 0, 1)));
    e.getProjectile().setVelocity(vec);
  }
}
