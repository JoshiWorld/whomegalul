package de.joshiworld.bungee.commands;

import de.joshiworld.bungee.Bungee;
import de.joshiworld.sql.SQLGetter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.sql.ResultSet;
import java.sql.SQLException;

public class bungeeunban extends Command {
    public bungeeunban() {
        super("bungeeunban");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(args.length==0) return;
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
