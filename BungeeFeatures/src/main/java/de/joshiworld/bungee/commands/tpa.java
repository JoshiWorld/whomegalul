package de.joshiworld.bungee.commands;

import de.joshiworld.bungee.main.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class tpa extends Command implements TabExecutor {

    public tpa() {
        super("tpa");
    }
    HashMap<String,Long> cooldown = new HashMap<>();
    Long currenttime;
    final int cooldownTime = 30;
    Bungee plugin = Bungee.getInstance();
    @Override

    public void execute(CommandSender sender, String[] args) {
        currenttime = System.currentTimeMillis() / 1000;
        if (!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if(args.length!=1){
            player.sendMessage(new TextComponent(ChatColor.RED.toString()+"Please select a Player"));
            return;
        }
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
        if((target==null)||target.equals(player)){
            player.sendMessage(new TextComponent(ChatColor.RED+"Player not found"));
            return;
        }
        if(hascooldown(player))return;
        plugin.getTpa().put(player,target);
        player.sendMessage(new TextComponent(ChatColor.GOLD+ "Teleport request sent to "+ChatColor.RED + target));
        target.sendMessage(new TextComponent(ChatColor.RED + player.getName() + ChatColor.GOLD + " has requested to teleport to you"));
        target.sendMessage(new TextComponent(ChatColor.GOLD+"To teleport, type " + ChatColor.RED + " /tpaccept"));
        target.sendMessage(new TextComponent(ChatColor.GOLD+"To deny this request, type" + ChatColor.RED + " /tpdeny"));
        ScheduledTask tpaSchedule = ProxyServer.getInstance().getScheduler().schedule(Bungee.getInstance(), new Runnable() {
            public void run() {
                Bungee.getInstance().getTpa().remove(player);
            }
        }, 30, TimeUnit.SECONDS);
        plugin.getscheduleMap().put(player,tpaSchedule);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 0) {
            return Collections.emptyList();
        }
        List<ProxiedPlayer> players = ProxyServer.getInstance().getPlayers().stream().filter(player -> player.getName().startsWith(args[0])).collect(Collectors.toList());
        List<String> results = new ArrayList<>();
        players.forEach(player -> results.add(player.getName()));
        players.clear();
        return results;
    }


    public boolean hascooldown(ProxiedPlayer player) {
        String name = player.getName();
        if(!(cooldown.containsKey(name))){
            cooldown.put(name,currenttime);
            return false;
        }
        if(currenttime - cooldown.get(name)  < 30) {
            plugin.getLogger().info(String.valueOf(cooldown.get(name)));
            plugin.getLogger().info(String.valueOf(currenttime));
            Long remaining = 30 - (currenttime - cooldown.get(name));
            player.sendMessage(new TextComponent(ChatColor.RED + "You need to wait "+ ChatColor.GOLD + remaining + " seconds" + ChatColor.RED + " to use that command again." ));
        return true;
        }else{
            cooldown.put(name,currenttime);
            return false;
        }
    }
}


