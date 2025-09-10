package com.example.plasmoradio.listener;

import com.example.plasmoradio.PlasmoRadioPlugin;
import com.example.plasmoradio.config.RadioConfig;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.material.Comparator;

public final class RedstoneListener implements Listener {

    private final PlasmoRadioPlugin plugin;
    private final RadioConfig config;

    public RedstoneListener(PlasmoRadioPlugin plugin, RadioConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    // Detect powering changes under note blocks (broadcast mode)
    @EventHandler
    public void onRedstone(BlockRedstoneEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.REDSTONE_BLOCK) return;
        Block above = block.getRelative(0, 1, 0);
        if (above.getType() != Material.NOTE_BLOCK) return;
        // placeholder: toggle broadcast mode for that station
    }

    // Future: output comparator signal strength near note block receivers
    @EventHandler
    public void onPhysics(BlockPhysicsEvent event) {
        // This is a placeholder; actual comparator power setting requires block data updates via analog signal emitters
    }
}

