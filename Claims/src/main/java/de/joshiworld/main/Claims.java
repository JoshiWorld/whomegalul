package de.joshiworld.main;

import de.joshiworld.sql.JobsData;
import de.joshiworld.sql.MySQL;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Claims extends JavaPlugin {
    // GLOBAL PREFIX / SUFFIX
    private final String PREFIX = "§7[§eWHO§7]";

    // SQL Stuff
    private MySQL mysql;

    // Instance-Stuff
    private Claims plugin;
    private LuckPerms luckperms;

    private int updater;

    // ArrayLists & HashMaps
    private List<Player> vanishList = new ArrayList<>();
    private List<Player> ignoreList = new ArrayList<>();
    private Map<Player, List<Long>> claimList = new HashMap<>();

    @Override
    public void onEnable() {
        this.plugin = this;
        connectSQL();
        new InitStuff(this.plugin).init();
        startUpdater();

        this.plugin.getLogger().info(getPrefix() + " §aClaims loaded..");
    }

    @Override
    public void onDisable() {
        this.mysql.close();
        stopUpdater();
    }



    // Init LuckPerms
    public void initLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckperms = provider.getProvider();
        }
    }

    // Connect SQL
    private void connectSQL() {
        mysql = new MySQL("localhost", "who", "123whoMEGALUL?", "who");
        //mysql.update("CREATE TABLE IF NOT EXISTS who(PLAYER varchar(64), MONEY int, CLAIMS varchar(8000), FLAGS varchar(1000), MAXCL int, TRUSTED varchar(1000), OTHERCLAIMS varchar(1000), INV varchar(8000), ARMOR varchar(8000), STORAGE varchar(8000), ENDER varchar(8000), ENDERSTORE varchar(8000))");
    }

    // Prefix Getter
    public String getPrefix() {
        return PREFIX;
    }

    // LuckPerms Getter
    public LuckPerms getLuckperms() {
        return luckperms;
    }

    // MySQL Getter
    public MySQL getMySQL() {
        return mysql;
    }

    // Get Vanish-List
    public List<Player> getVanishList() {
        return vanishList;
    }

    // Get Ignore-List
    public List<Player> getIgnoreList() {
        return ignoreList;
    }

    // Get Claim List
    public Map<Player, List<Long>> getClaimList() {
        return claimList;
    }

    // ALL UPDATER
    private void refreshAllNpcs() {
        for(NPC npc : CitizensAPI.getNPCRegistry()) {
            if(npc.data().has("holz")) {
                Location loc = npc.getStoredLocation();
                npc.despawn();
                npc.setName(new JobsData(this.plugin).getTopLumber());
                npc.spawn(loc);
            }

            if(npc.data().has("miner")) {
                Location loc = npc.getStoredLocation();
                npc.despawn();
                npc.setName(new JobsData(this.plugin).getTopMiner());
                npc.spawn(loc);
            }

            if(npc.data().has("hunter")) {
                Location loc = npc.getStoredLocation();
                npc.despawn();
                npc.setName(new JobsData(this.plugin).getTopHunter());
                npc.spawn(loc);
            }

            if(npc.data().has("farmer")) {
                Location loc = npc.getStoredLocation();
                npc.despawn();
                npc.setName(new JobsData(this.plugin).getTopFarmer());
                npc.spawn(loc);
            }

            if(npc.data().has("traveler")) {
                Location loc = npc.getStoredLocation();
                npc.despawn();
                npc.setName(new JobsData(this.plugin).getTopTraveler());
                npc.spawn(loc);
            }
        }
    }

    private void startUpdater() {
        this.updater = Bukkit.getScheduler().scheduleAsyncRepeatingTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                refreshAllNpcs();
            }
        }, 0, 20*60);
    }

    private void stopUpdater() {
        if(Bukkit.getScheduler().isCurrentlyRunning(this.updater))
            Bukkit.getScheduler().cancelTask(this.updater);
    }
}
