package de.joshiworld.bungee.commands;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.bungee.main.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class deletewarp extends Command {
    public deletewarp() {
        super("deletewarp","bungeefeatures.warp");
    }
    private final LuckPermsAPI luckPerms= new LuckPermsAPI(Bungee.getInstance());

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;


        String uuid = player.getUniqueId().toString();
        if(args.length == 0 || !(luckPerms.hasPermissionGroup("bungeefeatures.warp",player.getUniqueId())))return;

        if(Bungee.getInstance().data.searchHome("warp",args[0])){
            Bungee.getInstance().data.deleteHome("warp",args[0]);
            player.sendMessage(new TextComponent(ChatColor.RED+"Warp deleted Successfully"));
            return;
        }
        player.sendMessage(new TextComponent(ChatColor.RED+"Could not find warp with given name"));

    }
}
