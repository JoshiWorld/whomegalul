package de.joshiworld.listeners;

import de.joshiworld.api.LevelAPI;
import de.joshiworld.main.Jobs;
import de.joshiworld.sql.JobsData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HunterListener implements Listener {
    private final Jobs plugin;

    public HunterListener(Jobs plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKillMob(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();

        JobsData jobsData = new JobsData(player.getName(), this.plugin);
        LevelAPI levelAPI = new LevelAPI(player, this.plugin);

        if(!jobsData.getJob().equalsIgnoreCase("hunter")) return;

        switch(event.getEntity().getType()) {
            case ZOMBIE:
            case SKELETON:
            case SPIDER:
            case CREEPER:
            case BLAZE:
            case PIGLIN:
                levelAPI.addXP(0.5);
                break;
            case CAVE_SPIDER:
            case SLIME:
            case WITHER_SKELETON:
            case PHANTOM:
            case HUSK:
            case HOGLIN:
                levelAPI.addXP(1.0);
                break;
            case GUARDIAN:
            case ENDERMAN:
            case MAGMA_CUBE:
                levelAPI.addXP(2.0);
                break;
            case EVOKER:
            case EVOKER_FANGS:
            case ZOGLIN:
            case PILLAGER:
            case ZOMBIE_VILLAGER:
                levelAPI.addXP(3.0);
                break;
            case GHAST:
            case ELDER_GUARDIAN:
            case SHULKER:
            case PIGLIN_BRUTE:
            case SILVERFISH:
                levelAPI.addXP(4.0);
                break;
            case WITCH:
            case ILLUSIONER:
            case ZOMBIFIED_PIGLIN:
                levelAPI.addXP(5.0);
                break;
            case ENDERMITE:
            case RAVAGER:
                levelAPI.addXP(10.0);
                break;
        }
    }

}
