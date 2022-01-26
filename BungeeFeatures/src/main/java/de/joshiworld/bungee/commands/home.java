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
import java.util.*;
import java.util.concurrent.TimeUnit;

public class home extends Command implements TabExecutor {
    public home() {
        super("home");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;
        String server="lobby";
        String templocation="KEKW187QDH";
        final String home = getHomeName(args, player);
        if (home == null) return;

        String playerID = player.getUniqueId().toString();
        //test if home exists
        if(!(Bungee.getInstance().data.searchHome(playerID,home))) {
            player.sendMessage(new TextComponent(ChatColor.RED + "No Home with Name: " + home));
            return;
        }

        //get Home Info
        ResultSet results = Bungee.getInstance().data.getHome(playerID, home);
        try {
            if(results.next()){
                server = results.getString("Server");
                templocation = results.getString("LOCATION");
                player.sendMessage(new TextComponent(ChatColor.GOLD + "Teleporting to Home: " + home));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!(player.getServer().getInfo().getName().equals(server)))
            player.connect(ProxyServer.getInstance().getServerInfo(server));

        final String location = templocation;
        //add delay for server switch
        ProxyServer.getInstance().getScheduler().schedule(Bungee.getInstance(), new Runnable() {
            public void run() {
            Bungee.sendCustomData("homedata",player,location,"");
            }
        }, 1, TimeUnit.SECONDS);
    }



    private String getHomeName(String[] args, ProxiedPlayer player) {
        String home = "home";
        if (args.length == 1) home = args[0];
        else if (args.length > 1) {
            player.sendMessage(new TextComponent(ChatColor.RED + "Too many arguments"));
            home = null;
        }
        return home;
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return Collections.<String>emptyList();
        ProxiedPlayer player = (ProxiedPlayer) sender;
        return Bungee.getInstance().data.getHomeNames(player.getUniqueId().toString());
    }
}
