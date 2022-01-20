package de.joshiworld.main;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Lobby extends JavaPlugin {
    private final String PREFIX = "§7[§eWHO§7]";

    private Lobby plugin;
    private LuckPerms luckperms;

    private List<Player> build = new ArrayList<>();

    @Override
    public void onEnable() {
        this.plugin = this;
        new InitStuff(this.plugin).init();

        getLogger().info("§aLobby-Plugin geladen!");
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

    // Build-List
    public List<Player> getBuildList() {
        return this.build;
    }

    // Init LuckPerms
    public void initLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckperms = provider.getProvider();
        }
    }
}
