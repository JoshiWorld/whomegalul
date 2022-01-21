package de.joshiworld.listeners;

import de.joshiworld.api.LevelAPI;
import de.joshiworld.main.Jobs;
import de.joshiworld.sql.JobsData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class TravelerListener implements Listener {
    private final Jobs plugin;

    public TravelerListener(Jobs plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTravel(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        JobsData jobsData = new JobsData(player.getName(), this.plugin);
        LevelAPI levelAPI = new LevelAPI(player, this.plugin);

        if(!jobsData.getJob().equalsIgnoreCase("traveler")) return;
        if(event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) return;
        if(!player.isOnGround()) return;

        levelAPI.addXP(0.005);
    }

}
