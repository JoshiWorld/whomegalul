package de.joshiworld.bungee.main;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.joshiworld.sql.MySQL;
import de.joshiworld.sql.SQLGetter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.Collection;
import java.util.HashMap;

public final class Bungee extends Plugin{
    private static Bungee instance;
    public MySQL SQL;
    public SQLGetter data;
    public LuckPerms luckPerms;
    private HashMap<ProxiedPlayer, ProxiedPlayer> tpa = new HashMap<>();
    private HashMap<ProxiedPlayer, ScheduledTask> scheduleMap = new HashMap<>();

    public static Bungee getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.SQL = new MySQL();
        this.data = new SQLGetter(this);
        luckPerms = LuckPermsProvider.get();
        new BungeeInitStuff(instance).init();
    }

    @Override
    public void onDisable() {
        SQL.disconnect();
    }

    public static void sendCustomData(String SubChannel, ProxiedPlayer player, String data, String data2) {
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

    public HashMap<ProxiedPlayer,ProxiedPlayer> getTpa(){
        return tpa;
    }
    public HashMap<ProxiedPlayer,ScheduledTask> getscheduleMap(){
        return scheduleMap;
    }
}
