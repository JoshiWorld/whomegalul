package de.joshiworld.sql;

import de.joshiworld.main.Jobs;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JobsData {
    private final Jobs plugin;
    private final String player;

    public JobsData(String player, Jobs plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    // Check ob Player existiert
    public boolean playerExists() {
        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM jobs WHERE PLAYER = '" + this.player + "'");
            return (resultSet.next()) && (resultSet.getString("PLAYER") != null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Create Player
    public void createPlayer() {
        if(!playerExists()) {
            this.plugin.getMySQL().update("INSERT INTO jobs(PLAYER, CURRENT, HOLZ_LVL, HOLZ_XP, MINER_LVL, MINER_XP, HUNTER_LVL, HUNTER_XP, FARMER_LVL, FARMER_XP, TRAVELER_LVL, TRAVELER_XP) VALUES ('" + this.player + "', '', " +
                    "'0', '0.0'," +
                    "'0', '0.0'," +
                    "'0', '0.0'," +
                    "'0', '0.0'," +
                    "'0', '0.0'" +
                    ");"
            );
        }
    }

    // Get Job
    public String getJob() {
        String job = null;

        if(!playerExists()) {
            createPlayer();
            getJob();
        }

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM jobs WHERE PLAYER= '" + this.player + "'");

            if(resultSet.next()) {
                resultSet.getString("CURRENT");
            }

            job = resultSet.getString("CURRENT");

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return job;
    }

    // Set Job
    public void setJob(String job) {
        if(!playerExists()) {
            createPlayer();
            setJob(job);
        }

        this.plugin.getMySQL().update("UPDATE jobs SET CURRENT = '" + job.toLowerCase() + "' WHERE PLAYER= '" + this.player + "';");
    }

    // Get Job-Level
    public int getJobLvl(String job) {
        int level = 0;

        if(!playerExists()) {
            createPlayer();
            getJobLvl(job);
        }

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM jobs WHERE PLAYER= '" + this.player + "'");

            if(resultSet.next()) {
                resultSet.getInt(convertJobLvl(job));
            }

            level = resultSet.getInt(convertJobLvl(job));

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return level;
    }

    public void setJobLvl(String job, int lvl) {
        if(!playerExists()) {
            createPlayer();
            setJobLvl(job, lvl);
        }

        this.plugin.getMySQL().update("UPDATE jobs SET " + convertJobLvl(job) + " = '" + lvl + "' WHERE PLAYER= '" + this.player + "';");
    }

    // Get Job-Level
    public double getJobXP(String job) {
        double xp = 0;

        if(!playerExists()) {
            createPlayer();
            getJobLvl(job);
        }

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM jobs WHERE PLAYER= '" + this.player + "'");

            if(resultSet.next()) {
                resultSet.getDouble(convertJobXP(job));
            }

            xp = resultSet.getDouble(convertJobXP(job));

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return xp;
    }

    public void setJobXP(String job, double xp) {
        if(!playerExists()) {
            createPlayer();
            setJobXP(job, xp);
        }

        this.plugin.getMySQL().update("UPDATE jobs SET " + convertJobXP(job) + " = '" + xp + "' WHERE PLAYER= '" + this.player + "';");
    }

    // Convert String for Level
    private String convertJobLvl(String job) {
        String newJob = null;

        switch(job) {
            case "holzfäller":
                newJob = "HOLZ_LVL";
                break;
            case "miner":
                newJob = "MINER_LVL";
                break;
            case "hunter":
                newJob = "HUNTER_LVL";
                break;
            case "farmer":
                newJob = "FARMER_LVL";
                break;
            case "traveler":
                newJob = "TRAVELER_LVL";
                break;
            default:
                newJob = null;
                break;
        }

        return newJob;
    }

    // Convert String for XP
    private String convertJobXP(String job) {
        String newJob = null;

        switch(job) {
            case "holzfäller":
                newJob = "HOLZ_XP";
                break;
            case "miner":
                newJob = "MINER_XP";
                break;
            case "hunter":
                newJob = "HUNTER_XP";
                break;
            case "farmer":
                newJob = "FARMER_XP";
                break;
            case "traveler":
                newJob = "TRAVELER_XP";
                break;
            default:
                newJob = null;
                break;
        }

        return newJob;
    }
}
