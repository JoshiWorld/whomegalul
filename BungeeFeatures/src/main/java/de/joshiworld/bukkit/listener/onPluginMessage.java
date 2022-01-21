package de.joshiworld.bukkit.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class onPluginMessage implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] bytes) {
        if ( !channel.equalsIgnoreCase( "BungeeCord" ) ) return;
        ByteArrayDataInput in = ByteStreams.newDataInput( bytes );
        String subChannel = in.readUTF();
        switch (subChannel) {
            case "homedata":
                onHomedata(in);
                break;
            case "tpa":
                onTpa(in);
                break;
        }
    }

    private Location StringToLocation(String test){
        int leng = test.length()-1;
        test = test.substring(1,leng);
        Map<String, Object> LocMap = Arrays.stream(test.split(", "))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
        World init = Bukkit.getServer().getWorld("world");
        return new Location(init,0,0,0).deserialize(LocMap);
    }
    private void onHomedata(ByteArrayDataInput in){
        String playerID = in.readUTF();
        Location location = StringToLocation(in.readUTF());
        Bukkit.getLogger().info(playerID + " | "+ location);
        if(Bukkit.getPlayer(UUID.fromString(playerID)) == null)return;
        Player User = Bukkit.getPlayer(UUID.fromString(playerID));
        User.teleport(location);
    }
    private void onTpa(ByteArrayDataInput in){

        Player tpPlayer = Bukkit.getPlayer(UUID.fromString(in.readUTF()));
        Player target = Bukkit.getPlayer(UUID.fromString(in.readUTF()));
        tpPlayer.teleport(target.getLocation());

    }

}
