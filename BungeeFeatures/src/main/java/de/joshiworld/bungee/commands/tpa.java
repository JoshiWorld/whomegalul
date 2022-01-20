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

public class tpa extends Command implements TabExecutor {
    public tpa() {
        super("tpa");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Bungee plugin = Bungee.getInstance();
        if (!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if(args.length!=1){
            player.sendMessage(new TextComponent(ChatColor.RED.toString()+"Please select a Player"));
            return;
        }
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
        if(target == null){
            player.sendMessage(new TextComponent(ChatColor.RED.toString()+"Player not found"));
            return;
        }
        plugin.getTpa().put(player,target);
        player.sendMessage(new TextComponent(ChatColor.GOLD.toString()+ "Teleport request sent to "+ChatColor.RED.toString()+target));
        target.sendMessage(new TextComponent(ChatColor.RED.toString()+ player + ChatColor.GOLD.toString() + " has requested to teleport to you"));
        target.sendMessage(new TextComponent(ChatColor.GOLD.toString()+"To teleport, type " + ChatColor.RED.toString() + " /tpaccept"));
        target.sendMessage(new TextComponent(ChatColor.GOLD.toString()+"To deny this request, type" + ChatColor.RED.toString() + " /tpdeny"));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 0) {
            return Collections.emptyList();
        }
        List<ProxiedPlayer> players = ProxyServer.getInstance().getPlayers().stream().filter(player -> player.getName().startsWith(args[0])).collect(Collectors.toList());
        List<String> results = new ArrayList<>();
        players.forEach(player -> results.add(player.getName()));
        players.clear(); // get rid of some space & memory
        return results;
    }
}
