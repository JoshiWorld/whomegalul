package de.joshiworld.bungee.commands;

import de.joshiworld.bungee.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class home extends Command {

    public home() {
        super("home");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            player.sendMessage(new TextComponent("Yep"));
            Bungee.sendCustomData("SetHome",player,"");
        }

    }
}
