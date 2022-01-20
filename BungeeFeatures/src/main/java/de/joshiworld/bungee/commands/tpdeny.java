package de.joshiworld.bungee.commands;

import de.joshiworld.bungee.main.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class tpdeny extends Command {
    public tpdeny() {
        super("tpdeny");
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
            target.sendMessage(new TextComponent(ChatColor.GOLD.toString()+"Teleport request denied"));
            Bungee.getInstance().getTpa().remove(player);
        }
    }
}
