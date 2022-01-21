package de.joshiworld.bukkit.commands;

import de.joshiworld.bukkit.main.Paper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
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
            player.sendMessage(new TextComponent(ChatColor.GREEN.toString()+"Created new home: "+homeName));
        }
        return true;
    }
}
