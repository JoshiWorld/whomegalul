package de.joshiworld.bungee.commands;

import de.joshiworld.bungee.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class deletewarp extends Command {
    public deletewarp() {
        super("deletewarp");
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;
        String uuid = player.getUniqueId().toString();
        if(args.length==0) {
            player.sendMessage( new TextComponent(ChatColor.RED.toString()+"Specify a Warp"));
            return;
        }
        if(Bungee.getInstance().data.searchHome("warp",args[0])){
            Bungee.getInstance().data.deleteHome("warp",args[0]);
            player.sendMessage(new TextComponent(ChatColor.RED.toString()+"Warp deleted Successfully"));
            return;
        }
        player.sendMessage(new TextComponent(ChatColor.RED.toString()+"Could not find warp with given name"));

    }
}
