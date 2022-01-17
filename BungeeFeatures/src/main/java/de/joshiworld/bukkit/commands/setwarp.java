package de.joshiworld.bukkit.commands;

import de.joshiworld.bukkit.Paper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class setwarp implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        String warpName="";
        Player player = (Player) sender;
        String loc = player.getLocation().serialize().toString();
        if(args.length != 1){
            player.sendMessage(new TextComponent(ChatColor.RED.toString() + "Command usage: /setwarp <warpname>"));
            return false;
        }
            warpName = args[0];
        Paper.sendCustomData("setwarp",player,warpName,loc);
        return true;
    }
}
