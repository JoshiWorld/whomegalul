package de.joshiworld.main;

import de.joshiworld.sql.MySQL;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

// Jobs: Holzfäller, Miner, Hunter, Farmer, Traveler, (Fettsack)
public final class Jobs extends JavaPlugin {
    // Prefix etc.
    private final String PREFIX = "§7[§eWHO§7]";

    // MySQL & Instances
    private Jobs plugin;
    private LuckPerms luckperms;
    private MySQL mysql;

    // onEnable
    @Override
    public void onEnable() {
        this.plugin = this;
        connectSQL();
        new InitStuff(this.plugin).init();

        getLogger().info("§aJobs geladen!");
    }

    @Override
    public void onDisable() {

    }

    // Get Prefix
    public String getPrefix() {
        return this.PREFIX;
    }

    // Get SQL
    public MySQL getMySQL() {
        return this.mysql;
    }

    // Get LuckPerms
    public LuckPerms getLuckperms() {
        return this.luckperms;
    }

    // Setup MySQL Connection
    private void connectSQL() {
        mysql = new MySQL("localhost", "who", "123whoMEGALUL?", "who");
        mysql.update("CREATE TABLE IF NOT EXISTS who(PLAYER varchar(64), MONEY int, CLAIMS varchar(8000), TRUSTED varchar(1000), OTHERCLAIMS varchar(1000), INV varchar(8000), ARMOR varchar(8000), STORAGE varchar(8000), ENDER varchar(8000), ENDERSTORE varchar(8000))");
        mysql.update("CREATE TABLE IF NOT EXISTS jobs(PLAYER varchar(64), CURRENT varchar(64), " +
                "HOLZ_LVL int, HOLZ_XP double," +
                "MINER_LVL int, MINER_XP double," +
                "HUNTER_LVL int, HUNTER_XP double," +
                "FARMER_LVL int, FARMER_XP double," +
                "TRAVELER_LVL int, TRAVELER_XP double)"
        );
    }

    // Init LuckPerms
    public void initLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckperms = provider.getProvider();
        }
    }
}
