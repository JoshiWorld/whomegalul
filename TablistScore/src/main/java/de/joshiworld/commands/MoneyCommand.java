package de.joshiworld.commands;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.TablistScore;
import de.joshiworld.scoreboard.Score;
import de.joshiworld.sql.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MoneyCommand implements CommandExecutor {
    private TablistScore plugin;

    public MoneyCommand(TablistScore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);

        if(args.length == 0) {
            getMoney(player, playerData);
        } else if(args.length == 1) {
            if(!new LuckPermsAPI(this.plugin).hasPermissionGroup("claims.money", player.getUniqueId())) return true;
            if(Bukkit.getPlayer(args[0]) == null) return true;

            getPlayerMoney(player, Bukkit.getPlayer(args[0]));
        } else if(args.length == 3) {
            if(!new LuckPermsAPI(this.plugin).hasPermissionGroup("claims.money", player.getUniqueId())) return true;
            if(Bukkit.getPlayer(args[1]) == null) return true;
            if(!isInteger(args[2])) return true;

            Player target = Bukkit.getPlayer(args[1]);

            switch(args[0]) {
                case "set":
                    setMoney(player, target, Integer.parseInt(args[2]));
                    break;
                case "add":
                    addMoney(player, target, Integer.parseInt(args[2]));
                    break;
                case "remove":
                    removeMoney(player, target, Integer.parseInt(args[2]));
                    break;
                default:
                    player.sendMessage(this.plugin.getPrefix() + " §cFalsche Argumente");
                    break;
            }
        } else {
            player.sendMessage(this.plugin.getPrefix() + " §cFalsche Argumente");
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

    // Wenn nur "/money" eingegeben wird
    private void getMoney(Player player, PlayerData playerData) {
        if(playerData.getMoney() <= 0) player.sendMessage(this.plugin.getPrefix() + " §cBruder du bist ganzschön Arm. DU OPFER!!");
        else player.sendMessage(this.plugin.getPrefix() + " §aDeine Kontostand: §e" + playerData.getMoney());
    }

    // Player Money "/money <player>"
    private void getPlayerMoney(Player player, Player target) {
        player.sendMessage(this.plugin.getPrefix() + " §e" + target.getName() + "'s §aKontostand: §c" + new PlayerData(target.getName(), this.plugin).getMoney());
    }

    // Player Set Money "/money set <player> <money>"
    private void setMoney(Player player, Player target, int amount) {
        PlayerData targetData = new PlayerData(target.getName(), this.plugin);
        targetData.setMoney(amount);
        new Score(target, this.plugin).updateScore();
        player.sendMessage(this.plugin.getPrefix() + " §aMoney von §e" + target.getName() + " §aauf §c" + amount + " §agesetzt.");
    }

    // Player Add Money "/money add <player> <money>"
    private void addMoney(Player player, Player target, int amount) {
        PlayerData targetData = new PlayerData(target.getName(), this.plugin);
        targetData.addMoney(amount);
        new Score(target, this.plugin).updateScore();
        player.sendMessage(this.plugin.getPrefix() + " §aMoney von §e" + target.getName() + " §awurden §c" + amount + " §ahinzugefügt.");
    }

    // Player Remove Money "/money remove <player> <money>"
    private void removeMoney(Player player, Player target, int amount) {
        PlayerData targetData = new PlayerData(target.getName(), this.plugin);
        targetData.removeMoney(amount);
        new Score(target, this.plugin).updateScore();
        player.sendMessage(this.plugin.getPrefix() + " §aMoney von §e" + target.getName() + " §awurden §c" + amount + " §aweggenommen.");
    }
}
