package de.joshiworld.main;

import de.joshiworld.sql.MySQL;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

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

    // ArrayLists & HashMaps
    private List<Player> vanishList = new ArrayList<>();
    private Map<Player, List<Long>> claimList = new HashMap<>();

    @Override
    public void onEnable() {
        this.plugin = this;
        connectSQL();
        new InitStuff(this.plugin).init();

        this.plugin.getLogger().info(getPrefix() + " §aClaims loaded..");
    }

    @Override
    public void onDisable() {
        this.mysql.close();
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
        mysql.update("CREATE TABLE IF NOT EXISTS who(PLAYER varchar(64), MONEY int, CLAIMS varchar(8000), TRUSTED varchar(1000), OTHERCLAIMS varchar(1000))");
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

    // Get Claim List
    public Map<Player, List<Long>> getClaimList() {
        return claimList;
    }
}
