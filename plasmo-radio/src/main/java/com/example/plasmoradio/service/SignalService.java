package com.example.plasmoradio.service;

import com.example.plasmoradio.config.RadioConfig;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public final class SignalService {

    private final RadioConfig config;

    public SignalService(RadioConfig config) {
        this.config = config;
    }

    public int computeEffectiveRadius(Block noteBlock) {
        int antennas = countAntennasAbove(noteBlock);
        antennas = Math.min(antennas, config.getMaxAntennas());
        return config.getBaseRadius() + antennas * config.getRadiusPerAntenna();
    }

    public int countAntennasAbove(Block base) {
        int count = 0;
        Block current = base.getRelative(BlockFace.UP);
        boolean rodFound = false;
        // We allow multiple lightning rods stacked; count up to max while no full blocks above rods
        for (int i = 0; i < config.getMaxAntennas(); i++) {
            if (current.getType() == Material.LIGHTNING_ROD) {
                rodFound = true;
                count++;
                current = current.getRelative(BlockFace.UP);
                if (isFullBlock(current.getType())) {
                    // blocked immediately above antenna mast
                    break;
                }
                continue;
            }
            break;
        }
        return count;
    }

    private boolean isFullBlock(Material material) {
        // Very rough heuristic: treat slabs and stairs as not full, others as full
        String name = material.name();
        if (name.endsWith("_SLAB") || name.endsWith("_STAIRS") || name.contains("GLASS_PANE") || name.endsWith("_FENCE") || name.endsWith("_WALL") || name.endsWith("_DOOR") || name.endsWith("TRAPDOOR")) {
            return false;
        }
        return material.isSolid();
    }
}

