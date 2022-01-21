package de.joshiworld.bungee.commands;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.bungee.main.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
public class deleteserverhomes extends Command {
    public deleteserverhomes() {
        super("deleteserverhomes","bungeefeatures.warp");
    }
    private final LuckPermsAPI luckPerms= new LuckPermsAPI(Bungee.getInstance());

    @Override
    public void execute(CommandSender sender, String[] args) {
            if (!(sender instanceof ProxiedPlayer)) return;
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (args.length != 1 || !(luckPerms.hasPermissionGroup("bungeefeatures.warp", player.getUniqueId())))
                return;
            Bungee.getInstance().data.deleteallHomes(args[0]);

    }
}
