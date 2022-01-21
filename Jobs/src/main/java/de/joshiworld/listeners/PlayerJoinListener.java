package de.joshiworld.listeners;

import de.joshiworld.main.Jobs;
import de.joshiworld.sql.JobsData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final Jobs plugin;

    public PlayerJoinListener(Jobs plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        JobsData jobsData = new JobsData(player.getName(), this.plugin);

        if(jobsData.playerExists()) jobsData.createPlayer();
    }

}
