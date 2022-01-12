package de.joshiworld.bukkit;


import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;


public final class Bukkit extends JavaPlugin implements Listener, PluginMessageListener {

    public void onEnable() {
        getServer().getMessenger().registerIncomingPluginChannel( this, "BungeeCord", this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getLogger().info( "BukkitFeatures driver enabled successfully." );

    }

    public void onPluginMessageReceived(String channel, Player player, byte[] bytes)
    {
        if ( !channel.equalsIgnoreCase( "BungeeCord" ) ) return;
        ByteArrayDataInput in = ByteStreams.newDataInput( bytes );
        String subChannel = in.readUTF();
        if ( subChannel.equalsIgnoreCase( "MySubChannel" ) )
        {
            String data1 = in.readUTF();
            int data2 = in.readInt();
            getLogger().info(String.valueOf(data1 + " | "+ data2));
            // do things with the data
        }
    }



}
