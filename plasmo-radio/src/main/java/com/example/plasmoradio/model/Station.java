package com.example.plasmoradio.model;

import org.bukkit.Location;

public final class Station {
    private final Location noteBlockLocation;
    private int wave;
    private boolean broadcasting;

    public Station(Location noteBlockLocation, int wave, boolean broadcasting) {
        this.noteBlockLocation = noteBlockLocation.clone();
        this.wave = wave;
        this.broadcasting = broadcasting;
    }

    public Location getNoteBlockLocation() { return noteBlockLocation.clone(); }
    public int getWave() { return wave; }
    public void setWave(int wave) { this.wave = wave; }
    public boolean isBroadcasting() { return broadcasting; }
    public void setBroadcasting(boolean broadcasting) { this.broadcasting = broadcasting; }
}

