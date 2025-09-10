package com.example.plasmoradio;

import com.example.plasmoradio.config.RadioConfig;
import com.example.plasmoradio.listener.LecternListener;
import com.example.plasmoradio.listener.NoteBlockListener;
import com.example.plasmoradio.listener.RedstoneListener;
import com.example.plasmoradio.service.HologramService;
import com.example.plasmoradio.service.RadioNetworkService;
import com.example.plasmoradio.service.SignalService;
import com.example.plasmoradio.service.StationRegistry;
import com.example.plasmoradio.command.PlasmoRadioCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlasmoRadioPlugin extends JavaPlugin {

    private RadioConfig radioConfig;
    private HologramService hologramService;
    private StationRegistry stationRegistry;
    private SignalService signalService;
    private RadioNetworkService networkService;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.radioConfig = new RadioConfig(getConfig());
        this.hologramService = new HologramService(this, radioConfig);
        this.stationRegistry = new StationRegistry();
        this.signalService = new SignalService(radioConfig);
        this.networkService = new RadioNetworkService(radioConfig, stationRegistry, signalService);

        getServer().getPluginManager().registerEvents(new NoteBlockListener(this, radioConfig, hologramService), this);
        getServer().getPluginManager().registerEvents(new LecternListener(this, radioConfig), this);
        getServer().getPluginManager().registerEvents(new RedstoneListener(this, radioConfig), this);

        getCommand("plasmo-radio").setExecutor(new PlasmoRadioCommand(stationRegistry, radioConfig));

        getLogger().info("PlasmoRadio enabled");
    }

    @Override
    public void onDisable() {
        if (hologramService != null) {
            hologramService.shutdown();
        }
        getLogger().info("PlasmoRadio disabled");
    }

    public RadioConfig getRadioConfig() {
        return radioConfig;
    }

    public HologramService getHologramService() {
        return hologramService;
    }
}
