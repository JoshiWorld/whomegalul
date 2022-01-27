package de.joshiworld.main;

import de.joshiworld.commands.JobCommand;
import de.joshiworld.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class InitStuff {
    public Jobs plugin;

    public InitStuff(Jobs plugin) {
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
        addCommand("job", new JobCommand(this.plugin));
    }


    // Init Listeners
    private void initListeners() {
        addListener(new PlayerJoinListener(this.plugin));
        addListener(new BlockPlaceListener(this.plugin));
        addListener(new HolzListener(this.plugin));
        addListener(new MinerListener(this.plugin));
        addListener(new HunterListener(this.plugin));
        //addListener(new FarmerListener(this.plugin));
        //addListener(new TravelerListener(this.plugin));
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
