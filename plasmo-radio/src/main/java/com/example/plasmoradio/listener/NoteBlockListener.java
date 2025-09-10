package com.example.plasmoradio.listener;

import com.example.plasmoradio.PlasmoRadioPlugin;
import com.example.plasmoradio.config.RadioConfig;
import com.example.plasmoradio.service.HologramService;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public final class NoteBlockListener implements Listener {

    private final PlasmoRadioPlugin plugin;
    private final RadioConfig config;
    private final HologramService holograms;

    public NoteBlockListener(PlasmoRadioPlugin plugin, RadioConfig config, HologramService holograms) {
        this.plugin = plugin;
        this.config = config;
        this.holograms = holograms;
    }

    @EventHandler
    public void onShiftRightClickNoteBlock(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block clicked = event.getClickedBlock();
        if (clicked == null || clicked.getType() != Material.NOTE_BLOCK) return;

        Player player = event.getPlayer();
        if (!player.isSneaking()) return;
        if (!player.hasPermission(config.getPermUse())) return;

        // Determine current wave via attached lectern page
        int wave = com.example.plasmoradio.service.StationUtils.getWaveFromNearbyLectern(clicked, config.getWaves());
        if (wave <= 0) return;

        holograms.showTemporaryHologram(clicked.getLocation().add(0.5, 1.5, 0.5), "Волна " + wave, config.getHologramSeconds());
    }

    
}

