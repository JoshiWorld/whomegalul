package de.joshiworld.bukkit.main;

import de.joshiworld.bukkit.commands.PaperHomes;
import de.joshiworld.bukkit.commands.setportal;
import de.joshiworld.bukkit.commands.setwarp;
import de.joshiworld.bukkit.listener.EmoteReplace;
import de.joshiworld.bukkit.listener.npcInteract;
import de.joshiworld.bukkit.listener.onPluginMessage;
import de.joshiworld.bukkit.listener.onPrejoin;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Paperinit {
    public Paper plugin;

    public Paperinit(Paper plugin) {
        this.plugin = plugin;
    }
    public void init() {
        initCommands();
        initListeners();
        SQLinit();
        plugin.luckPerms = LuckPermsProvider.get();
        plugin.getServer().getMessenger().registerIncomingPluginChannel( plugin, "BungeeCord",new onPluginMessage());
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
    }
    private void initCommands() {
        addCommand("sethome", new PaperHomes());
        addCommand("setportal", new setportal());
        addCommand("setwarp", new setwarp());
    }
    // Init Listeners
    private void initListeners() {
        addListener(new EmoteReplace());
        addListener(new npcInteract());
        plugin.getServer().getMessenger().registerIncomingPluginChannel( plugin, "BungeeCord",new onPluginMessage());
        addListener(new onPrejoin());
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

    public void SQLinit(){
        try {
            plugin.SQL.connect();
        } catch (SQLException | ClassNotFoundException e) {
            plugin.getLogger().info("Database not connected");
        }
        if(plugin.SQL.isConnected()){
            plugin.getLogger().info("Database is connected");
        }
    }


}
