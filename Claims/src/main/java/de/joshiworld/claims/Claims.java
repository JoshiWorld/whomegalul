package de.joshiworld.claims;

import org.bukkit.plugin.java.JavaPlugin;

public final class Claims extends JavaPlugin {
    private static final String PREFIX = "§7[§eWHO§7]";

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    // Prefix Getter
    public static String getPrefix() {
        return PREFIX;
    }
}
