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
            case "clear":
                if(!this.luckPerms.hasPermissionGroup("claims.claims", player.getName())) return true;
                clearClaim(player, args[1]);
                break;
            case "showup":
                if(!this.luckPerms.hasPermissionGroup("claims.claims", player.getName())) return true;
                showClaim(player);
                break;

            // DEFAULT
            default:
                player.sendMessage(this.plugin.getPrefix() + " §cDu Hurensohn");
                break;
        }

        return true;
    }

    // If user just types /claim
    private void sendHelp(Player player) {
        player.sendMessage("§7Alle Claim-Commands:");
        player.sendMessage("§a/claim create");
        player.sendMessage("§a/claim remove");
        player.sendMessage("§a/claim trust <player>");
        player.sendMessage("§a/claim untrust <player>");
        player.sendMessage("§a/claim trustlist");

        if(this.luckPerms.hasPermissionGroup("claims.claims", player.getName())) {
            player.sendMessage("§c/claim clear <player>");
            player.sendMessage("§c/claim showup");
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

}
