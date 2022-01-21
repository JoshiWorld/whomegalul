package de.joshiworld.commands;

import de.joshiworld.api.LevelAPI;
import de.joshiworld.main.Jobs;
import de.joshiworld.sql.JobsData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JobCommand implements CommandExecutor {
    private final Jobs plugin;

    public JobCommand(Jobs plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        switch(args.length) {
            // "/job list" oder "/job level"
            case 1:
                switch(args[0]) {
                    case "list":
                        sendJobList(player);
                        break;
                    case "level":
                        sendCurrentJobLevel(player);
                        break;
                    default:
                        sendHelp(player);
                        break;
                }
                break;

            // "/job change <job>" oder "/job info <job>"
            case 2:
                switch(args[0]) {
                    case "change":
                        switch(args[1]) {
                            case "Holzfäller":
                            case "holzfäller":
                            case "Miner":
                            case "miner":
                            case "Hunter":
                            case "hunter":
                            case "Farmer":
                            case "farmer":
                            case "Traveler":
                            case "traveler":
                                changeJob(player, args[1]);
                                break;
                            default:
                                sendHelp(player);
                                break;
                        }
                        break;
                    case "info":
                        switch(args[1]) {
                            case "Holzfäller":
                            case "holzfäller":
                                jobInfoLumberjack(player);
                                break;
                            case "Miner":
                            case "miner":
                                jobInfoMiner(player);
                                break;
                            case "Hunter":
                            case "hunter":
                                jobInfoHunter(player);
                                break;
                            case "Farmer":
                            case "farmer":
                                jobInfoFarmer(player);
                                break;
                            case "Traveler":
                            case "traveler":
                                jobInfoTraveler(player);
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
                break;

            // Wenn Command falsche Syntax, etc. hat
            default:
                sendHelp(player);
                break;
        }

        return true;
    }

    //<editor-fold defaultstate="collapsed" desc="Jobs Info">
    // Job Help
    private void sendHelp(Player player) {
        player.sendMessage("§7Alle Jobs-Commands:");
        player.sendMessage("§a/job list");
        player.sendMessage("§a/job change <job>");
        player.sendMessage("§a/job level");
        player.sendMessage("§a/job info <job>");
    }

    // Job List
    private void sendJobList(Player player) {
        // Jobs: Holzfäller, Miner, Hunter, Farmer, Traveler, (Fettsack)
        player.sendMessage("§eVerfügbare Jobs: §aHolzfäller, Miner, Hunter, Farmer, Traveler");
    }

    // Job Info: Holzfäller
    private void jobInfoLumberjack(Player player) {
        player.sendMessage("§eHolzfäller-Info:");
        player.sendMessage("§7Der Holzfäller verdient sein Geld durch das Abbauen von Rohholz.");
    }

    // Job Info: Miner
    private void jobInfoMiner(Player player) {
        player.sendMessage("§eMiner-Info:");
        player.sendMessage("§7Der Miner verdient sein Geld durch das Abbauen von Rohmaterialien.");
        player.sendMessage("§7Dazu gehören: §eDiamanterz, Golderz, Eisenerz, Kupfererz,");
        player.sendMessage("§eSmaragderz, Lapislazuli, Redstoneerz, Kohleerz, Netherquartz,");
        player.sendMessage("§eNethergold und Antiker Schrott");
    }

    // Job Info: Hunter
    private void jobInfoHunter(Player player) {
        player.sendMessage("§eHunter-Info:");
        player.sendMessage("§7Der Hunter verdient sein Geld durch das Töten von Monstern.");
        player.sendMessage("§7Darunter zählen alle feindlichen Mobs.");
    }

    // Job Info: Farmer
    private void jobInfoFarmer(Player player) {
        player.sendMessage("§eFarmer-Info:");
        player.sendMessage("§7Der Farmer verdient sein Geld durch das Ernten von Farmland.");
        player.sendMessage("§7Dazu gehören: §eKartoffeln, Karotten, Weizen, Rote Beete,");
        player.sendMessage("§eMelonen und Kürbisse");
    }

    // Job Info: Traveler
    private void jobInfoTraveler(Player player) {
        player.sendMessage("§eTraveler-Info:");
        player.sendMessage("§7Der Traveler verdient sein Geld durch das Zurücklegen von langen Strecken. §e§bZU FUß!");
    }
    //</editor-fold>

    private void sendCurrentJobLevel(Player player) {
        JobsData jobsData = new JobsData(player.getName(), this.plugin);
        LevelAPI levelAPI = new LevelAPI(player, this.plugin);

        player.sendMessage(this.plugin.getPrefix() + " §eDein aktueller Job ist: §a" + jobsData.getJob());
        player.sendMessage(this.plugin.getPrefix() + " §aLevel: §c" + jobsData.getJobLvl(jobsData.getJob()));
        player.sendMessage(this.plugin.getPrefix() + " §aXP: §c" + jobsData.getJobXP(jobsData.getJob()) + " §7/ §c" + levelAPI.getMaxLvlXP());
    }

    private void changeJob(Player player, String job) {
        JobsData jobsData = new JobsData(player.getName(), this.plugin);
        jobsData.setJob(job.toLowerCase());
        player.sendMessage(this.plugin.getPrefix() + " §aDu hast den Job §e" + job + " §aangenommen!");
    }
}
