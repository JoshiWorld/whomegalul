package de.joshiworld.bungee.commands;

import de.joshiworld.bungee.main.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class msguser extends Command implements TabExecutor {
    public msguser() {
        super("msguser");
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if(args.length<2) return;
        ProxiedPlayer target = Bungee.getInstance().getInstance().getProxy().getPlayer(args[0]);
        if(target==null){
            player.sendMessage(new TextComponent(ChatColor.RED+"Player not found"));
            return;
        }

        String playerprefix= ChatColor.GRAY + "[You -> " + target.getName() +"]" + ChatColor.RESET;
        String targetprefix= ChatColor.GRAY + "["+ player.getName() + " -> You]" + ChatColor.RESET;
        String message= "";
        for (int i = 1; i <= args.length - 1; i++) {
            message += " "+args[i];
        }
        target.sendMessage(new TextComponent(targetprefix + message));
        player.sendMessage(new TextComponent(playerprefix + message));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length != 1) {
            return Collections.emptyList();
        }
        List<ProxiedPlayer> players = ProxyServer.getInstance().getPlayers().stream().filter(player -> player.getName().startsWith(args[0])).collect(Collectors.toList());
        List<String> results = new ArrayList<>();
        players.forEach(player -> results.add(player.getName()));
        players.clear();
        return results;
    }
}
