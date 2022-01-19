package de.joshiworld.bukkit.commands;

import de.joshiworld.bukkit.Paper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class PaperHomes implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (cmd.getName().equals("sethome")){
            String loc = player.getLocation().serialize().toString();
            String homeName= ((args.length == 0) ? "home" : args[0]);
            Paper.sendCustomData("sethome",player,homeName,loc);
        }
        return true;
    }
}
