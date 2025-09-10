package com.example.plasmoradio.service;

import com.example.plasmoradio.config.RadioConfig;
import com.example.plasmoradio.model.Station;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class RadioNetworkService {

    private final RadioConfig config;
    private final StationRegistry registry;
    private final SignalService signalService;

    public RadioNetworkService(RadioConfig config, StationRegistry registry, SignalService signalService) {
        this.config = config;
        this.registry = registry;
        this.signalService = signalService;
    }

    public int computeComparatorPowerForReceiver(Location noteBlockLocation) {
        Station receiver = registry.get(noteBlockLocation);
        if (receiver == null) return 0;
        int wave = receiver.getWave();
        if (wave <= 0) return 0;

        int maxRadius = signalService.computeEffectiveRadius(noteBlockLocation.getBlock());
        int bestPower = 0;
        for (Station st : snapshot()) {
            if (!st.isBroadcasting()) continue;
            if (st.getWave() != wave) continue;
            if (!sameWorld(st.getNoteBlockLocation(), noteBlockLocation)) continue;
            double distance = st.getNoteBlockLocation().distance(noteBlockLocation);
            if (distance > maxRadius) continue;
            if (isBlocked(st.getNoteBlockLocation(), noteBlockLocation)) continue;
            // Map distance to redstone power: closer -> higher. At distance 0 => 15, at maxRadius => 1
            double ratio = Math.max(0.0, 1.0 - (distance / (double) maxRadius));
            int power = (int) Math.ceil(ratio * 15.0);
            if (power > bestPower) bestPower = power;
        }
        return bestPower;
    }

    private boolean sameWorld(Location a, Location b) {
        World wa = a.getWorld();
        World wb = b.getWorld();
        return wa != null && wa.equals(wb);
    }

    private boolean isBlocked(Location from, Location to) {
        BlockIterator it = new BlockIterator(from.getWorld(), from.toVector(), to.toVector().subtract(from.toVector()).normalize(), 0, (int) Math.ceil(from.distance(to)));
        while (it.hasNext()) {
            Block block = it.next();
            Material m = block.getType();
            if (config.getBlockingMaterials().contains(m)) {
                return true;
            }
        }
        return false;
    }

    private List<Station> snapshot() {
        Collection<Station> all = registry.all();
        return new ArrayList<>(all);
    }
}

