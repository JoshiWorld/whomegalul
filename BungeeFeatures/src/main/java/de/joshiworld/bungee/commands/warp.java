package de.joshiworld.bungee.commands;

import de.joshiworld.bungee.main.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class warp extends Command implements TabExecutor {
    public warp() {
        super("warp");
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) sender;
        String server="lobby";
        String templocation="KEKW187QDH";
        String uuid="";

        if(args.length != 1){
            player.sendMessage(new TextComponent(ChatColor.RED.toString()+"Usage /warp <warpname>"));
            return;
        }
        String warpName = args[0];
        String playerID = player.getUniqueId().toString();
        if(!(Bungee.getInstance().data.searchHome("warp",warpName))) {
            player.sendMessage(new TextComponent(ChatColor.RED + "No Warp with Name: " + warpName));
            return;
        }
        ResultSet results = Bungee.getInstance().data.getHome("warp", warpName);
        try {
            if(results.next()){
                server = results.getString("Server");
                templocation = results.getString("LOCATION");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!(player.getServer().getInfo().getName().equals(server)))
            player.connect(ProxyServer.getInstance().getServerInfo(server));
        final String location = templocation;
        //add delay for server switch+
        ProxyServer.getInstance().getScheduler().schedule(Bungee.getInstance(), new Runnable() {
            public void run() {
                Bungee.sendCustomData("homedata",player,location,"");
            }
        }, 1, TimeUnit.SECONDS);

    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return Collections.<String>emptyList();
        ProxiedPlayer player = (ProxiedPlayer) sender;
        return Bungee.getInstance().data.getHomeNames("warp");
    }
}
