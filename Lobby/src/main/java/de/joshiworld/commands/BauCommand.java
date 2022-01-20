package de.joshiworld.commands;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.Lobby;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BauCommand implements CommandExecutor {
    private Lobby plugin;

    public BauCommand(Lobby plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if(!new LuckPermsAPI(this.plugin).hasPermissionGroup("claims.bau", player.getUniqueId())) return true;
        if(args.length != 0) return true;

        if(!this.plugin.getBuildList().contains(player)) {
            this.plugin.getBuildList().add(player);
            player.sendMessage(this.plugin.getPrefix() + " §aDu bist jetzt im Bau-Modus");
        } else {
            this.plugin.getBuildList().remove(player);
            player.sendMessage(this.plugin.getPrefix() + " §cDu bist jetzt nicht mehr im Bau-Modus");
        }

        return true;
    }
}
