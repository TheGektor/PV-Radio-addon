package com.example.plasmoradio.service;

import com.example.plasmoradio.model.Station;
import org.bukkit.Location;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class StationRegistry {

    private final Map<String, Station> byKey = new ConcurrentHashMap<>();

    private static String key(Location location) {
        return location.getWorld().getName() + ":" + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
    }

    public Station get(Location location) { return byKey.get(key(location)); }
    public void put(Station station) { byKey.put(key(station.getNoteBlockLocation()), station); }
    public Station remove(Location location) { return byKey.remove(key(location)); }
    public Collection<Station> all() { return byKey.values(); }
}

