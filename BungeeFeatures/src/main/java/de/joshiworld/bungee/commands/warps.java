package de.joshiworld.bungee.commands;

import de.joshiworld.bungee.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;

public class warps extends Command {
    public warps() {
        super("warps");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;
        List<String> warpNames = Bungee.getInstance().data.getHomeNames("warp");
        String homes = String.join(", ", warpNames);
        player.sendMessage(new TextComponent(ChatColor.GOLD.toString()+"Available Warps: " + homes));
    }
}
