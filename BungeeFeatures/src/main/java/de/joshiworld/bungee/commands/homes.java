package de.joshiworld.bungee.commands;

import de.joshiworld.bungee.main.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;

public class homes extends Command{
    public homes() {
        super("homes");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;
        List<String> homeNames = Bungee.getInstance().data.getHomeNames(player.getUniqueId().toString());
        String homes = String.join(", ", homeNames);
        player.sendMessage(new TextComponent(ChatColor.GOLD.toString()+"Available Homes: " + homes));
    }


}
