package de.joshiworld.listeners;

import de.joshiworld.api.LevelAPI;
import de.joshiworld.main.Jobs;
import de.joshiworld.sql.JobsData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class FarmerListener implements Listener {
    private final Jobs plugin;

    public FarmerListener(Jobs plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFarm(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        JobsData jobsData = new JobsData(player.getName(), this.plugin);
        LevelAPI levelAPI = new LevelAPI(player, this.plugin);

        if(!jobsData.getJob().equalsIgnoreCase("farmer")) return;

        switch(event.getBlock().getType()) {
            case SWEET_BERRIES:
                levelAPI.addXP(0.5);
                break;
            case BEETROOTS:
            case BEETROOT:
            case WHEAT:
            case CARROTS:
            case CARROT:
            case POTATO:
            case POTATOES:
            case MELON:
            case PUMPKIN:
                levelAPI.addXP(1.0);
                break;
        }
    }

}
