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
        if (!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer target = (ProxiedPlayer) sender;
        HashMap<ProxiedPlayer,ProxiedPlayer> tpa = Bungee.getInstance().getTpa();
        Optional<ProxiedPlayer> player = tpa.entrySet().stream()
                .filter(e -> target.equals(e.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
        if(player.isPresent()){
            if(!(target.getServer().getInfo().equals(player.get().getServer().getInfo())))
                player.get().connect(ProxyServer.getInstance().getServerInfo(target.getServer().getInfo().getName()));
            ProxyServer.getInstance().getScheduler().schedule(Bungee.getInstance(), new Runnable() {
                String uuidTarget = target.getUniqueId().toString();
                public void run() {
                    Bungee.sendCustomData("tpa",player.get(),uuidTarget,"");
                    player.get().sendMessage(new TextComponent(ChatColor.GOLD.toString()+"Teleport request accepted"));
                }
            }, 1, TimeUnit.SECONDS);
            return;
        }

    }
}
