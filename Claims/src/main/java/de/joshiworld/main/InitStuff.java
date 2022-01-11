package de.joshiworld.main;

import de.joshiworld.listeners.PlayerChatListener;
import de.joshiworld.listeners.PlayerJoinListener;
import de.joshiworld.listeners.PlayerQuitListener;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class InitStuff {
    public Claims plugin;

    public InitStuff(Claims plugin) {
        this.plugin = plugin;
    }

    // Init All
    public void init() {
        this.plugin.initLuckPerms();
        initCommands();
        initListeners();
    }


    // Init Commands
    private void initCommands() {

    }


    // Init Listeners
    private void initListeners() {
        addListener(new PlayerJoinListener(this.plugin));
        addListener(new PlayerQuitListener(this.plugin));
        addListener(new PlayerChatListener(this.plugin));
    }



    // Add Listener
    private void addListener(Listener listener) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(listener, this.plugin);
    }

}
