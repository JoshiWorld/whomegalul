package de.joshiworld.main;

import de.joshiworld.sql.MySQL;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class TablistScore extends JavaPlugin {
    private final String PREFIX = "§7[§eWHO§7]";

    private TablistScore plugin;
    private LuckPerms luckperms;

    private MySQL mysql;

    @Override
    public void onEnable() {
        this.plugin = this;
        connectSQL();
        new InitStuff(this.plugin).init();

        getLogger().info("§aTablist loaded");
    }

    @Override
    public void onDisable() {
        this.mysql.close();
    }

    public String getPrefix() {
        return this.PREFIX;
    }

    public LuckPerms getLuckperms() {
        return this.luckperms;
    }

    public MySQL getMySQL() {
        return mysql;
    }

    // Init LuckPerms
    public void initLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckperms = provider.getProvider();
        }
    }

    private void connectSQL() {
        mysql = new MySQL("localhost", "who", "123whoMEGALUL?", "who");
        mysql.update("CREATE TABLE IF NOT EXISTS who(PLAYER varchar(64), MONEY int, CLAIMS varchar(8000), TRUSTED varchar(1000), OTHERCLAIMS varchar(1000), INV varchar(8000), ARMOR varchar(8000), STORAGE varchar(8000), ENDER varchar(8000), ENDERSTORE varchar(8000))");
    }

}
