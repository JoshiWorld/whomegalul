package de.joshiworld.commands;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.TablistScore;
import de.joshiworld.sql.MaintenanceData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MaintenanceCommand implements CommandExecutor {
    private final TablistScore plugin;
    private final LuckPermsAPI luckPerms;

    public MaintenanceCommand(TablistScore plugin) {
        this.plugin = plugin;
        this.luckPerms = new LuckPermsAPI(this.plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if(!this.luckPerms.hasPermissionGroup("claims.maintenance.command", player.getUniqueId())) return true;

        if(new MaintenanceData(this.plugin).getMaintenance()) {
            new MaintenanceData(this.plugin).setMaintenance(false);
            player.sendMessage(this.plugin.getPrefix() + " §cWartungsmodus aus");
        } else {
            new MaintenanceData(this.plugin).setMaintenance(true);
            player.sendMessage(this.plugin.getPrefix() + " §aWartungsmodus an");
        }

        return true;
    }
}
