package de.joshiworld.commands;

import de.joshiworld.main.Claims;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClaimCommand implements CommandExecutor {
    private Claims plugin;

    public ClaimCommand(Claims plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player))
            return false;
        Player player = (Player) sender;

        if(args[0].equalsIgnoreCase("create")) {
            createChunk(player);
        }

        return true;
    }



    // Create
    private void createChunk(Player player) {
        Long chunk = player.getLocation().getChunk().getChunkKey();
        List<Long> list = this.plugin.getClaimedChunks().containsKey(player) ? this.plugin.getClaimedChunks().get(player) : new ArrayList<>();

        if(list.contains(chunk)) {
            player.sendMessage(this.plugin.getPrefix() + " §cDu besitzt diesen Chunk bereits");
            return;
        }

        if(!list.isEmpty()) {
            int firstX = player.getWorld().getChunkAt(list.get(0)).getX();
            int firstZ = player.getWorld().getChunkAt(list.get(0)).getZ();
            int secondX = player.getLocation().getChunk().getX();
            int secondZ = player.getLocation().getChunk().getZ();
            //int x = firstX > secondX ? firstX - secondX + 1 : secondX - firstX + 1;
            //int z = firstZ > secondZ ? firstZ - secondZ + 1 : secondZ - firstZ + 1;

            int[] xCount = {firstX, secondX};
            Arrays.sort(xCount);

            for(int i = xCount[0]; i <= xCount[1]; i++) {
                int[] zCount = {firstZ, secondZ};
                Arrays.sort(zCount);

                for(int j = zCount[0]; j <= zCount[1]; j++) {
                    list.add(player.getWorld().getChunkAt(i, j).getChunkKey());
                }
            }
        } else {
            list.add(chunk);
        }
        this.plugin.getClaimedChunks().put(player, list);

        player.sendMessage(this.plugin.getPrefix() + " §aChunks claimed!");
    }
}
