package de.joshiworld.api;

import de.joshiworld.main.Jobs;
import de.joshiworld.sql.JobsData;
import de.joshiworld.sql.PlayerData;
import net.kyori.adventure.bossbar.BossBar;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.w3c.dom.Text;

public class LevelAPI {
    private final Jobs plugin;
    private final Player player;

    public LevelAPI(Player player, Jobs plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    // Add Level
    public void addLevel() {
        JobsData jobsData = new JobsData(this.player.getName(), this.plugin);
        PlayerData playerData = new PlayerData(this.player.getName(), this.plugin);

        int lvl = jobsData.getJobLvl(jobsData.getJob()) + 1;
        jobsData.setJobLvl(jobsData.getJob(), lvl);
        playerData.addMoney(jobsData.getJobLvl(jobsData.getJob()) * 15);

        player.sendMessage(this.plugin.getPrefix() + " §aGlückwunsch! Du bist jetzt Level §e" + jobsData.getJobLvl(jobsData.getJob()) + " §ain §c" + jobsData.getJob());
    }

    // Add XP
    public void addXP(double xp) {
        JobsData jobsData = new JobsData(this.player.getName(), this.plugin);
        PlayerData playerData = new PlayerData(this.player.getName(), this.plugin);
        String job = jobsData.getJob();
        double jobXP = jobsData.getJobXP(job);
        double exponent = 2.0;

        jobsData.setJobXP(job, jobXP + xp);
        playerData.addMoney(jobsData.getJobLvl(jobsData.getJob()) * 2 * (int) Math.round(xp));

        if(jobsData.getJobXP(job) >= Math.pow((double) jobsData.getJobLvl(job) * 4, exponent)) {
            addLevel();
            jobsData.setJobXP(job, 0.0);
        }

        player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7§o+" + xp + " §7§oXP in " + job));
    }

    // Get XP - Left
    public double getMaxLvlXP() {
        JobsData jobsData = new JobsData(this.player.getName(), this.plugin);
        String job = jobsData.getJob();
        double exponent = 2.0;

        return Math.pow((double) jobsData.getJobLvl(job) * 4, exponent);
    }

}
