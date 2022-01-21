package de.joshiworld.commands;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.TablistScore;
import de.joshiworld.sql.WhiteList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WhitelistCommand implements CommandExecutor {
    private final TablistScore plugin;
    private final LuckPermsAPI luckPerms;

    public WhitelistCommand(TablistScore plugin) {
        this.plugin = plugin;
        this.luckPerms = new LuckPermsAPI(this.plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if(!this.luckPerms.hasPermissionGroup("claims.whitelist", player.getUniqueId())) return true;

        switch(args.length) {
            case 1:
                if(!args[0].equalsIgnoreCase("list")) return true;
                sendWhitelist(player);
                break;
            case 2:
                switch(args[0]) {
                    case "add":
                        addWhitelist(player, args[1]);
                        break;
                    case "remove":
                        removeWhitelist(player, args[1]);
                        break;
                    default:
                        sendHelp(player);
                        break;
                }
                break;
            default:
                sendHelp(player);
                break;
        }

        return true;
    }

    private void sendHelp(Player player) {
        player.sendMessage("§7Whitelist-Command:");
        player.sendMessage("§a/globallist add <player>");
        player.sendMessage("§a/globallist remove <player>");
        player.sendMessage("§a/globallist list");
    }

    private void sendWhitelist(Player player) {
        WhiteList whiteList = new WhiteList(player.getName(), this.plugin);

        if(whiteList.getWhitelistList() == null || whiteList.getWhitelistList().isEmpty()) return;

        String list = "";
        for(String listed : whiteList.getWhitelistList()) {
            list += listed + ", ";
        }

        player.sendMessage(this.plugin.getPrefix() + " §aGlobal-List: §e" + list.substring(0, list.length() - 2));
    }

    private void addWhitelist(Player player, String target) {
        WhiteList whiteList = new WhiteList(target, this.plugin);

        if(!whiteList.playerExists()) whiteList.createPlayer();
        whiteList.setWhitelist(true);

        player.sendMessage(this.plugin.getPrefix() + " §aDu hast §e" + target + " §aauf die Whitelist gesetzt!");
    }

    private void removeWhitelist(Player player, String target) {
        WhiteList whiteList = new WhiteList(target, this.plugin);

        if(!whiteList.playerExists()) {
            player.sendMessage(this.plugin.getPrefix() + " §cDieser Spieler existiert nicht in der Datenbank! §e" + target);
            return;
        }
        whiteList.setWhitelist(false);

        player.sendMessage(this.plugin.getPrefix() + " §aDu hast §e" + target + " §avon der Whitelist entfernt!");
    }
}
