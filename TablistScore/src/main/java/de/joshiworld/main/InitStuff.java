package de.joshiworld.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.joshiworld.listeners.PlayerChatListener;
import de.joshiworld.listeners.PlayerJoinListener;
import de.joshiworld.listeners.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class InitStuff {
    public TablistScore plugin;

    public InitStuff(TablistScore plugin) {
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
        addListener(new PlayerQuitListener(this.plugin));
        addListener(new PlayerChatListener(this.plugin));
        addListener(new PlayerJoinListener(this.plugin));
    }



    // Add Listener
    private void addListener(Listener listener) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(listener, this.plugin);
    }

    // Add Command
    private void addCommand(String command, CommandExecutor commandExecutor) {
        this.plugin.getCommand(command).setExecutor(commandExecutor);
    }

}
