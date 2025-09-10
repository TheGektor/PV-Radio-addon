package com.example.plasmoradio.command;

import com.example.plasmoradio.config.RadioConfig;
import com.example.plasmoradio.model.Station;
import com.example.plasmoradio.service.StationRegistry;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class PlasmoRadioCommand implements CommandExecutor {

    private final StationRegistry registry;
    private final RadioConfig config;

    public PlasmoRadioCommand(StationRegistry registry, RadioConfig config) {
        this.registry = registry;
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission(config.getPermAdmin())) {
            sender.sendMessage("§cNo permission.");
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        Block target = player.getTargetBlockExact(6);
        if (target == null || target.getType() != Material.NOTE_BLOCK) {
            player.sendMessage("§eLook at a note block within 6 blocks.");
            return true;
        }
        Location loc = target.getLocation();

        if (args.length == 0) {
            sender.sendMessage("§eUsage: /" + label + " <create|delete|setwave|mode>");
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "create": {
                Station st = registry.get(loc);
                if (st != null) {
                    sender.sendMessage("§eStation already exists here.");
                    return true;
                }
                int wave = 1;
                registry.put(new Station(loc, wave, false));
                sender.sendMessage("§aCreated station at this note block.");
                return true;
            }
            case "delete": {
                Station removed = registry.remove(loc);
                sender.sendMessage(removed != null ? "§aDeleted station." : "§eNo station here.");
                return true;
            }
            case "setwave": {
                if (args.length < 2) {
                    sender.sendMessage("§eUsage: /" + label + " setwave <1-" + config.getWaves() + ">");
                    return true;
                }
                try {
                    int wave = Integer.parseInt(args[1]);
                    if (wave < 1 || wave > config.getWaves()) {
                        sender.sendMessage("§cWave out of range.");
                        return true;
                    }
                    Station st = registry.get(loc);
                    if (st == null) {
                        sender.sendMessage("§eNo station here. Use create first.");
                        return true;
                    }
                    st.setWave(wave);
                    sender.sendMessage("§aWave set to " + wave + ".");
                } catch (NumberFormatException ex) {
                    sender.sendMessage("§cInvalid number.");
                }
                return true;
            }
            case "mode": {
                if (args.length < 2) {
                    sender.sendMessage("§eUsage: /" + label + " mode <broadcast|receive>");
                    return true;
                }
                Station st = registry.get(loc);
                if (st == null) {
                    sender.sendMessage("§eNo station here. Use create first.");
                    return true;
                }
                String m = args[1].toLowerCase();
                if (m.equals("broadcast")) {
                    st.setBroadcasting(true);
                } else if (m.equals("receive")) {
                    st.setBroadcasting(false);
                } else {
                    sender.sendMessage("§cUnknown mode.");
                    return true;
                }
                sender.sendMessage("§aMode updated.");
                return true;
            }
            default:
                sender.sendMessage("§eUnknown subcommand.");
                return true;
        }
    }
}

