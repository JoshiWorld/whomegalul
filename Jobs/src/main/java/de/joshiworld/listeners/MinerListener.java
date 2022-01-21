package de.joshiworld.listeners;

import de.joshiworld.api.LevelAPI;
import de.joshiworld.main.Jobs;
import de.joshiworld.sql.JobsData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MinerListener implements Listener {
    private final Jobs plugin;

    public MinerListener(Jobs plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        JobsData jobsData = new JobsData(player.getName(), this.plugin);
        LevelAPI levelAPI = new LevelAPI(player, this.plugin);

        if(!jobsData.getJob().equalsIgnoreCase("miner")) return;
        if(event.getBlock().hasMetadata("player")) return;

        switch(event.getBlock().getType()) {
            case COPPER_ORE:
            case DEEPSLATE_COPPER_ORE:
            case DEEPSLATE_REDSTONE_ORE:
            case NETHER_QUARTZ_ORE:
            case REDSTONE_ORE:
            case DEEPSLATE_COAL_ORE:
            case COAL_ORE:
                levelAPI.addXP(0.5);
                break;
            case DEEPSLATE_IRON_ORE:
            case IRON_ORE:
            case NETHER_GOLD_ORE:
                levelAPI.addXP(1.0);
                break;
            case GOLD_ORE:
            case DEEPSLATE_GOLD_ORE:
            case LAPIS_ORE:
            case DEEPSLATE_LAPIS_ORE:
                levelAPI.addXP(2.0);
                break;
            case DIAMOND_ORE:
            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
            case DEEPSLATE_DIAMOND_ORE:
                levelAPI.addXP(5.0);
                break;
        }
    }

}
