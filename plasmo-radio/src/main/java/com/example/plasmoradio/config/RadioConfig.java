package com.example.plasmoradio.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class RadioConfig {

    private final int waves;
    private final int hologramSeconds;
    private final int baseRadius;
    private final int radiusPerAntenna;
    private final int maxAntennas;
    private final Set<Material> blockingMaterials;
    private final int maxBroadcastersPerWave;

    private final String permUse;
    private final String permBroadcast;
    private final String permListen;
    private final String permAdmin;

    public RadioConfig(FileConfiguration config) {
        this.waves = config.getInt("waves", 15);
        this.hologramSeconds = config.getInt("hologram_seconds", 5);
        this.baseRadius = config.getInt("base_radius", 16);
        this.radiusPerAntenna = config.getInt("radius_per_antenna", 8);
        this.maxAntennas = Math.max(0, config.getInt("max_antennas", 5));
        this.maxBroadcastersPerWave = Math.max(1, config.getInt("max_broadcasters_per_wave", 3));

        List<String> materialNames = config.getStringList("blocking_materials");
        Set<Material> mats = new HashSet<>();
        for (String name : materialNames) {
            try {
                Material m = Material.valueOf(name);
                mats.add(m);
            } catch (IllegalArgumentException ignored) {
            }
        }
        this.blockingMaterials = Collections.unmodifiableSet(mats);

        this.permUse = config.getString("permissions.use", "plasmo.radio.use");
        this.permBroadcast = config.getString("permissions.broadcast", "plasmo.radio.broadcast");
        this.permListen = config.getString("permissions.listen", "plasmo.radio.listen");
        this.permAdmin = config.getString("permissions.admin", "plasmo.radio.admin");
    }

    public int getWaves() { return waves; }
    public int getHologramSeconds() { return hologramSeconds; }
    public int getBaseRadius() { return baseRadius; }
    public int getRadiusPerAntenna() { return radiusPerAntenna; }
    public int getMaxAntennas() { return maxAntennas; }
    public Set<Material> getBlockingMaterials() { return blockingMaterials; }
    public int getMaxBroadcastersPerWave() { return maxBroadcastersPerWave; }

    public String getPermUse() { return permUse; }
    public String getPermBroadcast() { return permBroadcast; }
    public String getPermListen() { return permListen; }
    public String getPermAdmin() { return permAdmin; }
}

