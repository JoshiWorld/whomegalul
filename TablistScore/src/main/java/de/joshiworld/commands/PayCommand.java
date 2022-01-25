package de.joshiworld.commands;

import de.joshiworld.main.TablistScore;
import de.joshiworld.scoreboard.Score;
import de.joshiworld.sql.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PayCommand implements CommandExecutor {
    private final TablistScore plugin;

    public PayCommand(TablistScore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if(args.length != 2 || !new PlayerData(args[0], this.plugin).playerExists() || !isInteger(args[1])) {
            player.sendMessage(this.plugin.getPrefix() + " §cUsage: /pay <player> <money>");
            return true;
        }

        PlayerData playerData = new PlayerData(player.getName(), this.plugin);
        PlayerData targetData = new PlayerData(args[0], this.plugin);
        int amount = Integer.parseInt(args[1]);

        if(amount > playerData.getMoney()) {
            player.sendMessage(this.plugin.getPrefix() + " §cDu hast nicht so viel Geld");
            return true;
        }

        targetData.addMoney(amount);
        playerData.removeMoney(amount);

        player.sendMessage(this.plugin.getPrefix() + " §aDu hast §e" + args[0] + " §c" + amount + " §cWHO's §aüberwiesen!");
        new Score(player, this.plugin).updateScore();

        if(Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {
            Bukkit.getPlayer(args[0]).sendMessage(this.plugin.getPrefix() + " §e" + player.getName() + " §ahat dir §c" + amount + " §cWHO's §aüberwiesen!");
            new Score(Bukkit.getPlayer(args[0]), this.plugin).updateScore();
        }
        return true;
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
