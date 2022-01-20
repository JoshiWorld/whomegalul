package de.joshiworld.commands;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.Claims;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VanishCommand implements CommandExecutor {
    private final Claims plugin;
    private final LuckPermsAPI luckPerms;

    public VanishCommand(Claims plugin) {
        this.plugin = plugin;
        luckPerms = new LuckPermsAPI(this.plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player))
            return false;
        Player player = (Player) sender;

        if(args.length != 0 || !luckPerms.hasPermissionGroup("claims.vanish", player.getUniqueId())) return true;

        if(!this.plugin.getVanishList().contains(player)) vanishPlayer(player);
        else unVanishPlayer(player);

        return true;
    }

    // Vanish
    private void vanishPlayer(Player player) {
        this.plugin.getVanishList().add(player);

        Bukkit.getOnlinePlayers().forEach(players -> {
            players.hidePlayer(this.plugin, player);
        });

        player.setGameMode(GameMode.SPECTATOR);

        String quit = "§7[§c-§7] " + ChatColor.translateAlternateColorCodes('&', this.luckPerms.getGroupPrefix(player.getName())) + player.getName();
        Bukkit.broadcast(new TextComponent(quit));
    }

    // Unvanish
    private void unVanishPlayer(Player player) {
        this.plugin.getVanishList().remove(player);

        Bukkit.getOnlinePlayers().forEach(players -> {
            players.showPlayer(this.plugin, player);
        });

        player.setGameMode(GameMode.SURVIVAL);

        String join = "§7[§a+§7] " + ChatColor.translateAlternateColorCodes('&', this.luckPerms.getGroupPrefix(player.getName())) + player.getName();
        Bukkit.broadcast(new TextComponent(join));
    }
}
