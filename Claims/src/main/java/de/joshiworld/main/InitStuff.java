package de.joshiworld.main;

import de.joshiworld.claims.ChunkBlocks;
import de.joshiworld.claims.ClaimBlocks;
import de.joshiworld.commands.ClaimCommand;
import de.joshiworld.commands.VanishCommand;
import de.joshiworld.listeners.PlayerChatListener;
import de.joshiworld.listeners.PlayerJoinListener;
import de.joshiworld.listeners.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
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
        addCommand("claim", new ClaimCommand(this.plugin));
        addCommand("vanish", new VanishCommand(this.plugin));
    }


    // Init Listeners
    private void initListeners() {
        addListener(new PlayerJoinListener(this.plugin));
        addListener(new PlayerQuitListener(this.plugin));
        addListener(new PlayerChatListener(this.plugin));
        addListener(new ChunkBlocks(this.plugin));
        addListener(new ClaimBlocks(this.plugin));
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
