package de.joshiworld.bungee.commands;

import de.joshiworld.bungee.Bungee;
import de.joshiworld.sql.SQLGetter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class bungeeban extends Command {
    public bungeeban() {
        super("bungeeban");
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        try {
            if(args.length==0) return;

            ResultSet result = Bungee.getInstance().data.getUser(args[0]);
            if(!(result.next()))return;
            String uuid = result.getString("UUID");
            SQLGetter data = Bungee.getInstance().data;
            ProxiedPlayer bannedUser = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));
            String Banreason = "Banned";
            if(args.length>1) {
                Banreason = "";
                for (int i = 1; i <= args.length - 1; i++) {
                    Banreason+= " "+args[i];
                }
            }
            data.banUser(uuid,Banreason);
            bannedUser.disconnect(new TextComponent(Banreason));
            if(!(sender instanceof ProxiedPlayer)){
                Bungee.getInstance().getLogger().info("Banned User: "+args[0]);
                return;
            }
            sender.sendMessage(new TextComponent("Banned User: "+args[0]));

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
