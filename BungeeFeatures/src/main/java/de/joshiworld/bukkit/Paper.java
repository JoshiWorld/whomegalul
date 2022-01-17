package de.joshiworld.bukkit;


import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.joshiworld.bukkit.commands.PaperHomes;
import de.joshiworld.bukkit.commands.setwarp;
import de.joshiworld.bukkit.listener.onPluginMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;



public final class Paper extends JavaPlugin implements Listener{
    public static Paper plugin;
    public void onEnable() {
        plugin = this;
        getCommand("sethome").setExecutor(new PaperHomes());
        getCommand("setwarp").setExecutor(new setwarp());

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
