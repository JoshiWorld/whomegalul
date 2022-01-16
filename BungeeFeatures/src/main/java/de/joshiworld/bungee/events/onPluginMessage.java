package de.joshiworld.bungee.events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import de.joshiworld.bungee.Bungee;
import de.joshiworld.sql.SQLGetter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import java.util.List;
import java.util.UUID;

public class onPluginMessage implements Listener {

    @EventHandler
    public void on(PluginMessageEvent event) {
        SQLGetter data = Bungee.getInstance().data;
        if ( !event.getTag().equalsIgnoreCase( "BungeeCord" ) )return;
        ByteArrayDataInput in = ByteStreams.newDataInput( event.getData() );
        String subChannel = in.readUTF();
        if ( subChannel.equalsIgnoreCase( "sethome" ) )
        {
            Server sender = (Server) event.getSender();
            String serverName = sender.getInfo().getName();
            String uuid = in.readUTF();
            String homeName= in.readUTF();
            String test = in.readUTF();
            List<String> homes= data.getHomeNames(uuid);
            if(homes.size()>=3){
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));
                player.sendMessage(new TextComponent(ChatColor.RED.toString()+ "You already have 3 Homes"));
                return;
            }
            data.createHome(uuid,homeName,serverName,test);
        }
    }
}
