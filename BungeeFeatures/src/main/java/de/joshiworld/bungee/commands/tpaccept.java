package de.joshiworld.bungee.commands;

import de.joshiworld.bungee.main.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class tpaccept extends Command {
    public tpaccept() {
        super("tpaccept");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer target = (ProxiedPlayer) sender;

        HashMap<ProxiedPlayer,ProxiedPlayer> tpa = Bungee.getInstance().getTpa();
        Optional<ProxiedPlayer> playerOptional = tpa.entrySet().stream()
                .filter(e -> target.equals(e.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
        if(!playerOptional.isPresent()) return;
        ProxiedPlayer player = playerOptional.get();

        if(!(target.getServer().getInfo().equals(player.getServer().getInfo())))
            player.connect(ProxyServer.getInstance().getServerInfo(target.getServer().getInfo().getName()));

        ProxyServer.getInstance().getScheduler().schedule(Bungee.getInstance(), new Runnable() {
            String uuidTarget = target.getUniqueId().toString();

            public void run() {
                Bungee.sendCustomData("tpa", player, uuidTarget,"");
                removePlayersFromMap(player, target);
                player.sendMessage(new TextComponent(ChatColor.GOLD.toString() + "Teleport request accepted"));
                target.sendMessage(new TextComponent(ChatColor.GOLD.toString() + "Teleport request accepted"));
            }
        }, 1, TimeUnit.SECONDS);

        Bungee.getInstance().getscheduleMap().get(player).cancel();

    }

    private void removePlayersFromMap(ProxiedPlayer player, ProxiedPlayer target) {
        if(Bungee.getInstance().getTpa().containsKey(player)) Bungee.getInstance().getTpa().remove(player);
    }
}
