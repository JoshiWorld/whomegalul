package de.joshiworld.bukkit;


import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.joshiworld.bukkit.commands.PaperHomes;
import de.joshiworld.bukkit.commands.setwarp;
import de.joshiworld.bukkit.listener.onPrejoin;
import de.joshiworld.bukkit.listener.onPluginMessage;
import de.joshiworld.sql.MySQL;
import de.joshiworld.sql.SQLGetter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;


public final class Paper extends JavaPlugin implements Listener{
    public static Paper plugin;
    public MySQL SQL;
    public SQLGetter data;
    public LuckPerms luckPerms;

    public static Paper getInstance(){
        return plugin;
    }

    public void onEnable() {
        luckPerms = LuckPermsProvider.get();
        //SQL
        this.SQL = new MySQL();
        this.data = new SQLGetter(this);
        try {
            SQL.connect();
        } catch (SQLException | ClassNotFoundException e) {
            getLogger().info("Database not connected");
        }
        if(SQL.isConnected()){
            getLogger().info("Database is connected");
            getServer().getPluginManager().registerEvents(this, this);
        }


        plugin = this;
        getCommand("sethome").setExecutor(new PaperHomes());
        getCommand("setwarp").setExecutor(new setwarp());
        getServer().getPluginManager().registerEvents(new onPrejoin(), this);

        getServer().getMessenger().registerIncomingPluginChannel( this, "BungeeCord",new onPluginMessage());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getLogger().info( "BukkitFeatures driver enabled successfully." );

    }
    //@EventHandler

    public static void sendCustomData(String SubChannel, Player player, String data, String details){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        String uuid = player.getUniqueId().toString();
        out.writeUTF( SubChannel );
        out.writeUTF( uuid );
        out.writeUTF( data );
        out.writeUTF( details );
        player.getServer().sendPluginMessage(plugin,"BungeeCord",out.toByteArray());
    }



}
