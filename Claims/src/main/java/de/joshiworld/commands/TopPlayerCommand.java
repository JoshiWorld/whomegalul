package de.joshiworld.commands;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.Claims;
import de.joshiworld.sql.JobsData;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitInfo;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.trait.LookClose;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import javax.xml.crypto.Data;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TopPlayerCommand implements CommandExecutor {
    private final Claims plugin;
    private final LuckPermsAPI luckPerms;

    public TopPlayerCommand(Claims plugin) {
        this.plugin = plugin;
        this.luckPerms = new LuckPermsAPI(this.plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if(!this.luckPerms.hasPermissionGroup("claims.top", player.getName())) return true;

        switch(args[0].toLowerCase()) {
            case "set":
                setTopNPC(player, args[1]);
                break;
            case "refresh":
                refreshNPC(player, args[1]);
                break;
            default:
                player.sendMessage(this.plugin.getPrefix() + " §cDu Huansohn");
                break;
        }
        return true;
    }

    private void setTopNPC(Player player, String job) {
        String top = null;

        switch(job.toLowerCase()) {
            case "holz":
                top = new JobsData(this.plugin).getTopLumber();
                break;
            case "miner":
                top = new JobsData(this.plugin).getTopMiner();
                break;
            case "hunter":
                top = new JobsData(this.plugin).getTopHunter();
                break;
            case "farmer":
                top = new JobsData(this.plugin).getTopFarmer();
                break;
            case "traveler":
                top = new JobsData(this.plugin).getTopTraveler();
                break;
            default:
                player.sendMessage(this.plugin.getPrefix() + " §cDu Huansohn schaffst es ja immernoch nicht");
                break;
        }

        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, top);
        npc.data().setPersistent(job.toLowerCase(), top);

        LookClose trait = new LookClose();
        trait.lookClose(true);
        npc.addTrait(trait);

        npc.spawn(player.getLocation());
    }

    private void refreshNPC(Player player, String job) {
        String top = null;

        switch(job.toLowerCase()) {
            case "holz":
                top = new JobsData(this.plugin).getTopLumber();
                break;
            case "miner":
                top = new JobsData(this.plugin).getTopMiner();
                break;
            case "hunter":
                top = new JobsData(this.plugin).getTopHunter();
                break;
            case "farmer":
                top = new JobsData(this.plugin).getTopFarmer();
                break;
            case "traveler":
                top = new JobsData(this.plugin).getTopTraveler();
                break;
            case "all":
                refreshAll();
                player.sendMessage(this.plugin.getPrefix() + " §aDu hast alle Top-Player refreshed!");
                break;
            default:
                player.sendMessage(this.plugin.getPrefix() + " §cDu Huansohn schaffst es ja immernoch nicht");
                break;
        }

        for(NPC npc : CitizensAPI.getNPCRegistry()) {
            if(npc.data().has(job.toLowerCase())) {
                Location loc = npc.getStoredLocation();
                npc.despawn();
                npc.setName(top);
                npc.spawn(loc);
            }
        }
    }

    private void refreshAll() {
        for(NPC npc : CitizensAPI.getNPCRegistry()) {
            if(npc.data().has("holz")) {
                Location loc = npc.getStoredLocation();
                npc.despawn();
                npc.setName(new JobsData(this.plugin).getTopLumber());
                npc.spawn(loc);
            }

            if(npc.data().has("miner")) {
                Location loc = npc.getStoredLocation();
                npc.despawn();
                npc.setName(new JobsData(this.plugin).getTopMiner());
                npc.spawn(loc);
            }

            if(npc.data().has("hunter")) {
                Location loc = npc.getStoredLocation();
                npc.despawn();
                npc.setName(new JobsData(this.plugin).getTopHunter());
                npc.spawn(loc);
            }

            if(npc.data().has("farmer")) {
                Location loc = npc.getStoredLocation();
                npc.despawn();
                npc.setName(new JobsData(this.plugin).getTopFarmer());
                npc.spawn(loc);
            }

            if(npc.data().has("traveler")) {
                Location loc = npc.getStoredLocation();
                npc.despawn();
                npc.setName(new JobsData(this.plugin).getTopTraveler());
                npc.spawn(loc);
            }
        }
    }
}
