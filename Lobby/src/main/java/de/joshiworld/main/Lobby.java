package de.joshiworld.main;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lobby extends JavaPlugin {
    private final String PREFIX = "§7[§eWHO§7]";

    private Lobby plugin;
    private LuckPerms luckperms;

    @Override
    public void onEnable() {
        this.plugin = this;
    }

    @Override
    public void onDisable() {

    }

    // Get Prefix
    public String getPrefix() {
        return this.PREFIX;
    }

    // Get LuckPerms
    public LuckPerms getLuckperms() {
        return this.luckperms;
    }

    // Init LuckPerms
    public void initLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckperms = provider.getProvider();
        }
    }
}
