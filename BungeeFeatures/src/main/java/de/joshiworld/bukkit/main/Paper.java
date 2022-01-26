package de.joshiworld.bukkit.main;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.joshiworld.sql.MySQL;
import de.joshiworld.sql.SQLGetter;
import net.luckperms.api.LuckPerms;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.Map;

public final class Paper extends JavaPlugin implements Listener{
    public static Paper plugin;
    public SQLGetter data;
    public LuckPerms luckPerms;
    public static Paper getInstance(){
        return plugin;
    }
    private Map<String,String> emoteList;

    public void onEnable() {
        plugin = this;
        new Paperinit(plugin).init();
        getLogger().info( "BukkitFeatures driver enabled successfully." );

    }
    public static void sendCustomData(String SubChannel, Player player, String data, String details){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        String uuid = player.getUniqueId().toString();
        out.writeUTF( SubChannel );
        out.writeUTF( uuid );
        out.writeUTF( data );
        out.writeUTF( details );
        player.getServer().sendPluginMessage(plugin,"BungeeCord",out.toByteArray());
    }
    public Map<String,String> getEmoteList(){
        return emoteList;
    }
    public void setEmoteList(Map<String,String> list){
        this.emoteList = list;
    }

}
