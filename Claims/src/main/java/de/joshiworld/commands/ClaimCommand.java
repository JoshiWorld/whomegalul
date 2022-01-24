package de.joshiworld.commands;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.items.ClaimItems;
import de.joshiworld.main.Claims;
import de.joshiworld.sql.ChunkData;
import de.joshiworld.sql.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClaimCommand implements CommandExecutor {
    private final Claims plugin;
    private final LuckPermsAPI luckPerms;

    public ClaimCommand(Claims plugin) {
        this.plugin = plugin;
        this.luckPerms = new LuckPermsAPI(this.plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player))
            return false;
        Player player = (Player) sender;

        if(args.length == 0) {
            sendHelp(player);
            return true;
        }

        switch(args[0]) {
            // Create Claims
            case "create":
                if(this.plugin.getClaimList().containsKey(player)) {
                    createClaim(player);
                    player.getInventory().remove(ClaimItems.getClaimItem());
                    return true;
                }

                this.plugin.getClaimList().put(player, new ArrayList<Long>());
                player.getInventory().addItem(ClaimItems.getClaimItem());
                break;

            // Create Claims
            case "remove":
                if(this.plugin.getClaimList().containsKey(player)) {
                    removeClaim(player);
                    player.getInventory().remove(ClaimItems.getClaimRemoveItem());
                    return true;
                }

                this.plugin.getClaimList().put(player, new ArrayList<Long>());
                player.getInventory().addItem(ClaimItems.getClaimRemoveItem());
                break;

            // Trust
            case "trust":
            case "untrust":
                if(args.length > 2) return true;

                if(Bukkit.getPlayer(args[1]) == null) return true;

                if(args[0].equals("trust")) addTrust(player, Bukkit.getPlayer(args[1]));
                else removeTrust(player, Bukkit.getPlayer(args[1]));
                break;
            case "trustlist":
                sendTrustlist(player);
                break;
            case "flaglist":
                sendFlaglist(player);
                break;
            case "info":
                sendInfo(player);
                break;

            case "buy":
                if(args.length > 2 || !isInteger(args[1])) return true;
                buyClaim(player, Integer.parseInt(args[1]));
                break;

            case "flag":
                if(args.length > 3) return true;
                flagSet(player, args[1], args[2]);
                break;

            case "clear":
                if(!this.luckPerms.hasPermissionGroup("claims.claims", player.getName())) return true;
                clearClaim(player, args[1]);
                break;
            case "showup":
                if(!this.luckPerms.hasPermissionGroup("claims.claims", player.getName())) return true;
                showClaim(player);
                break;
            case "ignore":
                if(!this.luckPerms.hasPermissionGroup("claims.claims", player.getName())) return true;
                ignoreClaim(player);
                break;

            // DEFAULT
            default:
                player.sendMessage(this.plugin.getPrefix() + " §cDu Huansohn");
                break;
        }

        return true;
    }

    // If user just types /claim
    private void sendHelp(Player player) {
        player.sendMessage("§7Alle Claim-Commands:");
        player.sendMessage("§a/claim create");
        player.sendMessage("§a/claim remove");
        player.sendMessage("§a/claim buy <amount>");
        player.sendMessage("§a/claim flag <flag> <true|false>");
        player.sendMessage("§a/claim trust <player>");
        player.sendMessage("§a/claim untrust <player>");
        player.sendMessage("§a/claim trustlist");
        player.sendMessage("§a/claim flaglist");
        player.sendMessage("§a/claim info");

        if(this.luckPerms.hasPermissionGroup("claims.claims", player.getName())) {
            player.sendMessage("§c/claim clear <player>");
            player.sendMessage("§c/claim showup");
            player.sendMessage("§c/claim ignore");
        }
    }

    // Clear claim
    private void clearClaim(Player player, String target) {
        PlayerData targetData = new PlayerData(target, this.plugin);
        List<Long> claims = targetData.getClaims();

        if(claims.isEmpty()) {
            player.sendMessage(this.plugin.getPrefix() + " §cDieser Spieler besitzt keine Claims!");
            return;
        }

        claims.clear();
        targetData.setClaims(claims);
        player.sendMessage(this.plugin.getPrefix() + " §aClaims wurden removed!");
    }

    // Showup Claim
    private void showClaim(Player player) {
        Long chunk = player.getLocation().getChunk().getChunkKey();
        ChunkData chunkData = new ChunkData(chunk, this.plugin);

        player.sendMessage(this.plugin.getPrefix() + " §aDieser Chunk gehört: §e" + chunkData.lookupChunkOwner());
    }

    // Send Claim Info
    private void sendInfo(Player player) {
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);
        int claimed = playerData.getClaims().size();
        int maxClaims = playerData.getMaxClaims();

        player.sendMessage(this.plugin.getPrefix() + " §c" + claimed + " §7/ §c" + maxClaims + " §aClaims sind verbraucht.");
    }

    // Buy Claims
    private void buyClaim(Player player, int amount) {
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);
        int price = 500;

        if(amount*price > playerData.getMoney()) {
            player.sendMessage(this.plugin.getPrefix() + " §cDu bist ein armer Schlucker du Hund!");
            return;
        }

        playerData.removeMoney(amount*price);
        playerData.addMaxClaims(amount);
        player.sendMessage(this.plugin.getPrefix() + " §aVallah Bruder du bist reich!");
    }

    // Flag List
    private void sendFlaglist(Player player) {
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);
        String message = this.plugin.getPrefix() + " §eAlle Flags§7: §cpvp, trading";

        if(playerData.getFlags().isEmpty()) player.sendMessage(message);
        else {
            for(String flag : playerData.getFlags()) {
                message = message.replaceAll(flag, "§a" + flag + "§c");
            }
            player.sendMessage(message);
        }
    }

    // Set Flag
    private void flagSet(Player player, String flag, String isAllowed) {
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);

        switch(flag.toLowerCase()) {
            case "pvp":
                if(isAllowed.equalsIgnoreCase("true")) {
                    if(!playerData.getFlags().isEmpty() && playerData.getFlags().contains("pvp")) return;

                    playerData.addFlags("pvp");
                    player.sendMessage(this.plugin.getPrefix() + " §aPVP Flag true");
                } else {
                    playerData.removeFlags("pvp");
                    player.sendMessage(this.plugin.getPrefix() + " §cPVP Flag false");
                }
                break;
            case "trading":
                if(isAllowed.equalsIgnoreCase("true")) {
                    if(!playerData.getFlags().isEmpty() && playerData.getFlags().contains("trading")) return;

                    playerData.addFlags("trading");
                    player.sendMessage(this.plugin.getPrefix() + " §aTrading Flag true");
                } else {
                    playerData.removeFlags("trading");
                    player.sendMessage(this.plugin.getPrefix() + " §cTrading Flag false");
                }
                break;
            default:
                player.sendMessage(this.plugin.getPrefix() + " §cDieser Flag existiert nicht!");
                break;
        }
    }

    // Ignore Claim
    private void ignoreClaim(Player player) {
        if(!this.plugin.getIgnoreList().contains(player)) {
            this.plugin.getIgnoreList().add(player);
            player.sendMessage(this.plugin.getPrefix() + " §aDu ignorierst jetzt Claims!");
        } else {
            this.plugin.getIgnoreList().remove(player);
            player.sendMessage(this.plugin.getPrefix() + " §cDu ignorierst jetzt keine Claims mehr!");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="/claim create">
    private void createClaim(Player player) {
        if(!finishClaim(player)) player.sendMessage(this.plugin.getPrefix() + " §cDu erstellst bereits ein Claim!");
        else player.sendMessage(this.plugin.getPrefix() + " §aDu hast BLA neue Chunks geclaimed!");
    }

    // Finish claim creation
    private boolean finishClaim(Player player) {
        if(!isDoneCreating(player)) return false;

        createChunk(player);
        return true;
    }

    // Is done creating claim?
    private boolean isDoneCreating(Player player) {
        return !this.plugin.getClaimList().get(player).isEmpty() &&
                this.plugin.getClaimList().get(player).size() == 2;
    }

    // Create
    private void createChunk(Player player) {
        int firstX = player.getWorld().getChunkAt(this.plugin.getClaimList().get(player).get(0)).getX();
        int firstZ = player.getWorld().getChunkAt(this.plugin.getClaimList().get(player).get(0)).getZ();
        int secondX = player.getWorld().getChunkAt(this.plugin.getClaimList().get(player).get(1)).getX();
        int secondZ = player.getWorld().getChunkAt(this.plugin.getClaimList().get(player).get(1)).getZ();

        int[] xCount = {firstX, secondX};
        Arrays.sort(xCount);

        List<Long> chunks = new ArrayList<>();
        for(int i = xCount[0]; i <= xCount[1]; i++) {
            int[] zCount = {firstZ, secondZ};
            Arrays.sort(zCount);

            for(int j = zCount[0]; j <= zCount[1]; j++) {
                chunks.add(player.getWorld().getChunkAt(i, j).getChunkKey());
            }
        }

        PlayerData playerData = new PlayerData(player.getName(), this.plugin);

        if((playerData.getClaims().size() + chunks.size()) > playerData.getMaxClaims()) {
            player.sendMessage(this.plugin.getPrefix() + " §cDiese Anzahl an Claims stehen dir nicht zur Verfügung!");
            return;
        }

        playerData.addClaims(chunks);
        this.plugin.getClaimList().remove(player);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="/claim remove">
    private void removeClaim(Player player) {
        if(!finishClaimRemove(player)) player.sendMessage(this.plugin.getPrefix() + " §cDu löscht bereits einen Claim!");
        else player.sendMessage(this.plugin.getPrefix() + " §cDu hast Claims removed!");
    }

    // Finish claim creation
    private boolean finishClaimRemove(Player player) {
        if(!isDoneCreatingRemove(player)) return false;

        removeChunk(player);
        return true;
    }

    // Is done creating claim?
    private boolean isDoneCreatingRemove(Player player) {
        return !this.plugin.getClaimList().get(player).isEmpty() &&
                this.plugin.getClaimList().get(player).size() == 2;
    }

    // Create
    private void removeChunk(Player player) {
        int firstX = player.getWorld().getChunkAt(this.plugin.getClaimList().get(player).get(0)).getX();
        int firstZ = player.getWorld().getChunkAt(this.plugin.getClaimList().get(player).get(0)).getZ();
        int secondX = player.getWorld().getChunkAt(this.plugin.getClaimList().get(player).get(1)).getX();
        int secondZ = player.getWorld().getChunkAt(this.plugin.getClaimList().get(player).get(1)).getZ();

        int[] xCount = {firstX, secondX};
        Arrays.sort(xCount);

        List<Long> chunks = new ArrayList<>();
        for(int i = xCount[0]; i <= xCount[1]; i++) {
            int[] zCount = {firstZ, secondZ};
            Arrays.sort(zCount);

            for(int j = zCount[0]; j <= zCount[1]; j++) {
                chunks.add(player.getWorld().getChunkAt(i, j).getChunkKey());
            }
        }

        PlayerData playerData = new PlayerData(player.getName(), this.plugin);
        playerData.removeClaims(chunks);

        this.plugin.getClaimList().remove(player);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="/claim trust">
    private void addTrust(Player player, Player target) {
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);
        PlayerData targetData = new PlayerData(target.getName(), this.plugin);

        if(target.getName().equals(player.getName())) {
            player.sendMessage(this.plugin.getPrefix() + " §cDu kannst dich nicht selber trusten!");
            return;
        }

        if(playerData.getTrusted().contains(target.getName())) {
            player.sendMessage(this.plugin.getPrefix() + " §cDieser Spieler ist bereits getrusted!");
            return;
        }

        playerData.addTrusted(target.getName());
        targetData.addOtherClaims(player.getName());

        target.sendMessage(this.plugin.getPrefix() + " §aDu wurdest von §e" + player.getName() + " §agetrustet!");
        player.sendMessage(this.plugin.getPrefix() + " §aDu hast §e" + target.getName() + " §aauf deinem Grundstück getrustet!");
    }

    private void removeTrust(Player player, Player target) {
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);
        PlayerData targetData = new PlayerData(target.getName(), this.plugin);

        if(target.getName().equals(player.getName())) {
            player.sendMessage(this.plugin.getPrefix() + " §cDu kannst dich nicht selber trusten!");
            return;
        }

        if(!playerData.getTrusted().contains(target.getName())) {
            player.sendMessage(this.plugin.getPrefix() + " §cDieser Spieler ist nicht getrusted!");
            return;
        }

        playerData.removeTrusted(target.getName());
        targetData.removeOtherClaims(player.getName());
        player.sendMessage(this.plugin.getPrefix() + " §aDu hast §e" + target.getName() + " §avon deinem Grundstück entfernt!");
    }

    private void sendTrustlist(Player player) {
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);
        List<String> trusted = playerData.getTrusted();

        if(trusted.isEmpty()) player.sendMessage(this.plugin.getPrefix() + " §cDu hast keine Freunde!! HAHAHA");
        else player.sendMessage(this.plugin.getPrefix() + " §aTrusted Spieler: §e" + trusted.toString().substring(1, trusted.toString().length()-1));
    }
    //</editor-fold>

    // Check if its integer
    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

}
