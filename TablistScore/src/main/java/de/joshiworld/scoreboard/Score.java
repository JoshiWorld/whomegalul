package de.joshiworld.scoreboard;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.TablistScore;
import de.joshiworld.sql.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class Score {
    private TablistScore plugin;
    private Player player;
    private LuckPermsAPI luckPerms;

    public Score(Player player, TablistScore plugin) {
        this.player = player;
        this.plugin = plugin;
        this.luckPerms = new LuckPermsAPI(this.plugin);
    }

    //<editor-fold defaultstate="collapsed" desc="setScore">
    public void setScore() {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard board = scoreboardManager.getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "dummy");
        PlayerData playerData = new PlayerData(this.player.getName(), this.plugin);

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§8§l► §e§lWHOMEGALUL §8§l◄");

        objective.getScore("§6").setScore(5);
        objective.getScore("§aDein Kontostand§8:").setScore(4);
        Team coins = board.getTeam("coins") != null ? board.getTeam("coins") : board.registerNewTeam("coins");
        coins.setPrefix("§f" + playerData.getMoney() + " §6WHO's");
        coins.addEntry("§8");
        objective.getScore("§8").setScore(3);

        objective.getScore("§1").setScore(2);
        objective.getScore("§eOnline-Spieler§8:").setScore(1);
        Team online = board.getTeam("online") != null ? board.getTeam("online") : board.registerNewTeam("online");
        int on = Bukkit.getOnlinePlayers().size();
        online.setPrefix("§f" + on + "§f/187");
        online.addEntry("§3");
        objective.getScore("§3").setScore(0);

        this.player.setScoreboard(board);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="updateScore">
    public void updateScore(){
        Objective objective = this.player.getScoreboard().getObjective("test");

        if(objective == null || this.player.getScoreboard() == null) {
            setScore();
        }

        Scoreboard board = this.player.getScoreboard();

        Team coins = board.getTeam("coins") != null ? board.getTeam("coins") : board.registerNewTeam("coins");
        Team online = board.getTeam("online") != null ? board.getTeam("online") : board.registerNewTeam("online");

        PlayerData playerData = new PlayerData(this.player.getName(), this.plugin);
        coins.setPrefix("§f" + playerData.getMoney() + " §6WHO's");
        int on = Bukkit.getOnlinePlayers().size();
        online.setPrefix("§f" + on + "§f/187");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setTab">
    public void setTab() {
        if(this.player.getScoreboard() == null) {
            setScore();
        }

        Scoreboard board = this.player.getScoreboard();
        Team admin = board.getTeam("0001Admin") != null ? board.getTeam("0001Admin") : board.registerNewTeam("0001Admin");
        Team streamer = board.getTeam("0002Streamer") != null ? board.getTeam("0002Streamer") : board.registerNewTeam("0002Streamer");
        Team pepega = board.getTeam("0003Pepega") != null ? board.getTeam("0003Pepega") : board.registerNewTeam("0003Pepega");
        Team pleb = board.getTeam("0004Pleb") != null ? board.getTeam("0004Pleb") : board.registerNewTeam("0004Pleb");

        admin.setPrefix("\uE021" + " §aAdmin §7| §f");
        streamer.setPrefix("\uE022" + " §5Streamer §7| §f");
        pepega.setPrefix("§9Pepega §7| §f");
        pleb.setPrefix("§7Pleb §7| §f");

        Bukkit.getOnlinePlayers().forEach(all -> {
            switch(this.luckPerms.getGroup(all.getName()).getName()) {
                case "admin":
                    admin.addEntry(all.getName());
                    break;
                case "streamer":
                    streamer.addEntry(all.getName());
                    break;
                case "pepega":
                    pepega.addEntry(all.getName());
                    break;
                default:
                    pleb.addEntry(all.getName());
                    break;
            }
        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="updateTab">
    public void updateTab() {
        Scoreboard board = this.player.getScoreboard();

        if(board.getTeam("0001Admin") == null || board.getTeam("0002Streamer") == null || board.getTeam("0003Pepega") == null || board.getTeam("0004Pleb") == null) {
            setTab();
        }

        Team admin = board.getTeam("0001Admin");
        Team streamer = board.getTeam("0002Streamer");
        Team pepega = board.getTeam("0003Pepega");
        Team pleb = board.getTeam("0004Pleb");

        Bukkit.getOnlinePlayers().forEach(all -> {
            switch(this.luckPerms.getGroup(all.getName()).getName()) {
                case "admin":
                    admin.addEntry(all.getName());
                    break;
                case "streamer":
                    streamer.addEntry(all.getName());
                    break;
                case "pepega":
                    pepega.addEntry(all.getName());
                    break;
                default:
                    pleb.addEntry(all.getName());
                    break;
            }
        });
    }
    //</editor-fold>

}
