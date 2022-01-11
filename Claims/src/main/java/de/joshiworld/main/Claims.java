package de.joshiworld.main;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Claims extends JavaPlugin {
    // GLOBAL PREFIX / SUFFIX
    private final String PREFIX = "§7[§eWHO§7]";

    // Instance-Stuff
    private Claims plugin;
    private LuckPerms luckperms;

    // ArrayLists
    private List<Player> vanishList = new ArrayList<>();

    @Override
    public void onEnable() {
        plugin = this;
        new InitStuff(plugin).init();

        System.out.println(PREFIX + " §aClaims loaded..");
    }

    @Override
    public void onDisable() { }



    // Init LuckPerms
    public void initLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckperms = provider.getProvider();
        }
    }

    // Prefix Getter
    public String getPrefix() {
        return PREFIX;
    }

    // LuckPerms Getter
    public LuckPerms getLuckperms() {
        return luckperms;
    }

    // Get Plugin-Instance
    public Claims getInstance() {
        return plugin;
    }

    // Get Vanish-List
    public List<Player> getVanishList() {
        return vanishList;
    }
}
