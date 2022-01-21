package de.joshiworld.bukkit.commands;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.bukkit.main.Paper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class setwarp implements CommandExecutor {
    private final LuckPermsAPI luckPerms= new LuckPermsAPI(Paper.getInstance());
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if(!((cmd.getName().equals("setwarp"))&&(sender instanceof Player))) return true;
        Player player = (Player) sender;
        String loc = player.getLocation().serialize().toString();
        if(args.length != 1 && !(luckPerms.hasPermissionGroup("bungeefeatures.warp",player.getUniqueId()))) return true;
        String warpName = args[0];
        Paper.sendCustomData("setwarp",player,warpName,loc);
        return true;
    }
}
