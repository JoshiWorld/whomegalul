package de.joshiworld.main;

import org.bukkit.plugin.java.JavaPlugin;

public final class Claims extends JavaPlugin {
    private static final String PREFIX = "§7[§eWHO§7]";

    @Override
    public void onEnable() {
        new InitStuff(this).init();

        System.out.println(PREFIX + " §aClaims loaded..");
    }

    @Override
    public void onDisable() { }



    // Prefix Getter
    public static String getPrefix() {
        return PREFIX;
    }
}
