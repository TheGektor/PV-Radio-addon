package com.example.plasmoradio.service;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Lectern;

public final class StationUtils {
    private StationUtils() {}

    public static int getWaveFromNearbyLectern(Block noteBlock, int maxWaves) {
        for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST}) {
            Block adj = noteBlock.getRelative(face);
            if (adj.getType() == Material.LECTERN) {
                Lectern lectern = (Lectern) adj.getState();
                if (lectern.getInventory() != null && lectern.getInventory().getItem(0) != null) {
                    int page = lectern.getPage();
                    if (page >= 1 && page <= maxWaves) {
                        return page;
                    }
                }
            }
        }
        return -1;
    }
}

