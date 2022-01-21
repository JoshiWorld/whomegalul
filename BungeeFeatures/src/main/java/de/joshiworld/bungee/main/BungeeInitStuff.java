package de.joshiworld.bungee.main;

import de.joshiworld.bungee.commands.*;
import de.joshiworld.bungee.events.onPluginMessage;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;

import java.sql.SQLException;

public class BungeeInitStuff {

    public Bungee plugin;
    public BungeeInitStuff(Bungee plugin) {
        this.plugin = plugin;
    }
    public void init() {
        initCommands();
        initListeners();
        initSQL();
        plugin.getProxy().registerChannel( "BungeeCord" );
        plugin.getLogger().info("is ready");
    }
    private void initListeners() {
        addListener(new onPluginMessage());
    }
    private void initCommands() {
        addCommand(new deletehome());
        addCommand(new home());
        addCommand(new homes());
        addCommand(new warp());
        addCommand(new warps());
        addCommand(new deletewarp());
        addCommand(new bungeeban());
        addCommand(new bungeeunban());
        addCommand(new tpa());
        addCommand(new tpdeny());
        addCommand(new deletehome());
        addCommand(new tpaccept());
    }
    private void addCommand(Command command){
        plugin.getProxy().getPluginManager().registerCommand(plugin, command);
    }

    private void addListener(Listener listener){
        plugin.getProxy().getPluginManager().registerListener(plugin,listener);
    }
    private void initSQL(){
        try {
            plugin.SQL.connect();
        } catch (SQLException | ClassNotFoundException e) {
            plugin.getLogger().info("Database not connected");
        }
        if(plugin.SQL.isConnected()){
            plugin.getLogger().info("Database is connected");
            plugin.data.createTable();
        }
    }
}
