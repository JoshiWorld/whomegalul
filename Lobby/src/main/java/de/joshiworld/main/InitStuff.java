package de.joshiworld.main;

import de.joshiworld.commands.BauCommand;
import de.joshiworld.listeners.BlockEventListener;
import de.joshiworld.listeners.PlayerJoinListener;
import de.joshiworld.listeners.PlayerMoveListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class InitStuff {
    public Lobby plugin;

    public InitStuff(Lobby plugin) {
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
        addCommand("bau", new BauCommand(this.plugin));
    }


    // Init Listeners
    private void initListeners() {
        addListener(new BlockEventListener(this.plugin));
        addListener(new PlayerMoveListener(this.plugin));
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
