package de.joshiworld.sql;

import de.joshiworld.main.TablistScore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WhiteList {
    private final TablistScore plugin;
    private final String player;

    public WhiteList(String player, TablistScore plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    // Check ob Player existiert
    public boolean playerExists() {
        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM whitelist WHERE PLAYER = '" + this.player + "'");
            return (resultSet.next()) && (resultSet.getString("PLAYER") != null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Create Player
    public void createPlayer() {
        if(!playerExists()) {
            this.plugin.getMySQL().update("INSERT INTO whitelist(PLAYER, WHITELIST) VALUES ('" + this.player + "', '0');");
        }
    }

    // Get Whitelist
    public boolean getWhitelist() {
        boolean isListed = false;

        if(!playerExists()) {
            createPlayer();
            getWhitelist();
        }

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM whitelist WHERE PLAYER= '" + this.player + "'");

            if(resultSet.next()) {
                resultSet.getInt("WHITELIST");
            }

            if(resultSet.getInt("WHITELIST") == 0) isListed = false;
            else isListed = true;

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isListed;
    }

    // Set Whitelist
    public void setWhitelist(boolean whitelist) {
        if(!playerExists()) {
            createPlayer();
            setWhitelist(whitelist);
        }

        int list = whitelist ? 1 : 0;
        this.plugin.getMySQL().update("UPDATE whitelist SET WHITELIST = '" + list + "' WHERE PLAYER= '" + this.player + "';");
    }

    // Get Whitelist all
    public List<String> getWhitelistList() {
        List<String> list = new ArrayList<>();

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM whitelist");

            while(resultSet.next()) {
                if(!resultSet.getString("PLAYER").isEmpty() && resultSet.getInt("WHITELIST") == 1) {
                    list.add(resultSet.getString("PLAYER"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
