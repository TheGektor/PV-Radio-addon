package com.example.plasmoradio.listener;

import com.example.plasmoradio.PlasmoRadioPlugin;
import com.example.plasmoradio.config.RadioConfig;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Lectern;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public final class LecternListener implements Listener {

    private final PlasmoRadioPlugin plugin;
    private final RadioConfig config;

    public LecternListener(PlasmoRadioPlugin plugin, RadioConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    // Detect page turn on lectern to update wave; Bukkit doesn't have a direct event for page change,
    // so we react on right-click and re-evaluate state if lectern is adjacent to a note block.
    @EventHandler
    public void onLecternInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != Material.LECTERN) return;
        Block lecternBlock = event.getClickedBlock();
        // If a note block adjacent, we could notify future PV integration to switch group
        if (isAdjacentToNoteBlock(lecternBlock)) {
            // placeholder: switch wave mapping
        }
    }

    @EventHandler
    public void onLecternPlace(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() != Material.LECTERN) return;
        Block b = event.getBlockPlaced();
        if (isAdjacentToNoteBlock(b)) {
            // placeholder: binding created
        }
    }

    private boolean isAdjacentToNoteBlock(Block block) {
        for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST}) {
            if (block.getRelative(face).getType() == Material.NOTE_BLOCK) return true;
        }
        return false;
    }
}

