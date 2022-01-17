package de.joshiworld.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.joshiworld.bungee.commands.*;
import de.joshiworld.bungee.events.onPluginMessage;
import de.joshiworld.sql.MySQL;
import de.joshiworld.sql.SQLGetter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.sql.SQLException;
import java.util.Collection;

public final class Bungee extends Plugin implements Listener {
    private static Bungee instance;
    public MySQL SQL;
    public SQLGetter data;

    public static Bungee getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        //SQL Setup
        this.SQL = new MySQL();
        this.data = new SQLGetter(this);
        try {
            SQL.connect();
        } catch (SQLException | ClassNotFoundException e) {
            getLogger().info("Database not connected");
        }
        if(SQL.isConnected()){
            getLogger().info("Database is connected");
            data.createTable();
            getProxy().getPluginManager().registerListener(this, this);
        }

        //Commands and Listeners
        getProxy().getPluginManager().registerCommand(this, new deletehome());
        getProxy().getPluginManager().registerCommand(this, new home());
        getProxy().getPluginManager().registerCommand(this, new homes());
        getProxy().getPluginManager().registerCommand(this, new warp());
        getProxy().getPluginManager().registerCommand(this, new deletewarp());

        getProxy().getPluginManager().registerListener(this,new onPluginMessage());
        //Plugin Message
        getProxy().registerChannel( "BungeeCord" );

        getLogger().info("is ready");
    }

    @Override
    public void onDisable() {
        SQL.disconnect();
    }

    public static void sendCustomData(String SubChannel, ProxiedPlayer player, String data, String data2)
    {
        String uuid = String.valueOf(player.getUniqueId());
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
        if ( networkPlayers == null || networkPlayers.isEmpty() ) return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF( SubChannel );
        out.writeUTF( uuid );
        out.writeUTF( data );
        out.writeUTF( data2 );
        player.getServer().getInfo().sendData( "BungeeCord", out.toByteArray() );
    }


}
