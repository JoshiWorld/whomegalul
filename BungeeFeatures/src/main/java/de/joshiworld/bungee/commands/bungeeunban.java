package de.joshiworld.bungee.commands;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.bungee.main.Bungee;
import de.joshiworld.sql.SQLGetter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.ResultSet;
import java.sql.SQLException;

public class bungeeunban extends Command {
    public bungeeunban() {
        super("bungeeunban","bungeefeatures.ban");
    }
    private final LuckPermsAPI luckPerms= new LuckPermsAPI(Bungee.getInstance());

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if(args.length == 0 || !(luckPerms.hasPermissionGroup("bungeefeatures.ban",player.getUniqueId()))) return;
        try {
            ResultSet result = Bungee.getInstance().data.getUser(args[0]);
            if(!(result.next()))return;
            String uuid = result.getString("UUID");
            SQLGetter data = Bungee.getInstance().data;
            data.unbanUser(uuid);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
