package de.joshiworld.bukkit.commands;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class setportal implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        Location loc = player.getLocation();
        if(args.length != 1) return true;
        String server = args[0];
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, server);
        npc.spawn(loc);
        return true;
    }
}
