package com.example.plasmoradio.service;

import com.example.plasmoradio.config.RadioConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

public final class HologramService {

    private final Plugin plugin;
    private final RadioConfig config;
    private final Set<ArmorStand> activeHolograms = new HashSet<>();

    public HologramService(Plugin plugin, RadioConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void showTemporaryHologram(Location location, String text, int seconds) {
        World world = location.getWorld();
        if (world == null) return;
        ArmorStand stand = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setInvisible(true);
        stand.setMarker(true);
        stand.setGravity(false);
        stand.setCustomNameVisible(true);
        stand.setCustomName(text);
        activeHolograms.add(stand);

        int delayTicks = Math.max(1, seconds) * 20;
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            stand.remove();
            activeHolograms.remove(stand);
        }, delayTicks);
    }

    public void shutdown() {
        for (ArmorStand stand : activeHolograms) {
            stand.remove();
        }
        activeHolograms.clear();
    }
}

