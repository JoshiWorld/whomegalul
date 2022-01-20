package de.joshiworld.bungee.events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import de.joshiworld.bungee.main.Bungee;
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
        Server sender = (Server) event.getSender();
        String uuid = in.readUTF();
        switch (subChannel) {
            case "sethome":
                onSethome(in,sender,uuid,data);
                break;
            case "setwarp":
                onSethome(in,sender,"warp",data);
                break;
            case "Serverswitch":
                onServerswitch(in,uuid);
                break;
        }

    }

    public void onSethome(ByteArrayDataInput in,Server sender,String uuid, SQLGetter data){
        String serverName = sender.getInfo().getName();
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
    public void onServerswitch(ByteArrayDataInput in,String uuid){
        String server= in.readUTF();
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));
        player.connect(ProxyServer.getInstance().getServerInfo(server));
    }

}
