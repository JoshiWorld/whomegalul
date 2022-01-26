package de.joshiworld.bungee.commands;

import de.joshiworld.bungee.main.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class deletehome extends Command {
    public deletehome() {
        super("deletehome");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return;
            ProxiedPlayer player = (ProxiedPlayer) sender;
            String uuid = player.getUniqueId().toString();
            if(args.length==0) {
                player.sendMessage( new TextComponent(ChatColor.RED+"Specify a Homename"));
                return;
            }
            if(Bungee.getInstance().data.searchHome(uuid,args[0])){
                Bungee.getInstance().data.deleteHome(uuid,args[0]);
                player.sendMessage(new TextComponent(ChatColor.RED+"Home deleted Successfully"));
                return;
            }
            player.sendMessage(new TextComponent(ChatColor.RED+"Could not find Home with given name"));

    }
}
