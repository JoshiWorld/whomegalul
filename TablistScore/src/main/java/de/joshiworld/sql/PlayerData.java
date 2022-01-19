package de.joshiworld.sql;

import de.joshiworld.main.TablistScore;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerData {
    private final TablistScore plugin;
    private final String player;

    public PlayerData(String player, TablistScore plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    // Check ob Player existiert
    public boolean playerExists() {
        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM who WHERE PLAYER = '" + this.player + "'");
            return (resultSet.next()) && (resultSet.getString("PLAYER") != null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Create Player
    public void createPlayer() {
        if(!playerExists()) {
            this.plugin.getMySQL().update("INSERT INTO who(PLAYER, MONEY, CLAIMS, TRUSTED, OTHERCLAIMS) VALUES ('" + this.player + "', '0', '', '', '');");
        }
    }



    // Get Money
    public int getMoney() {
        int money = 0;

        if(!playerExists()) {
            createPlayer();
            getMoney();
        }

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM who WHERE PLAYER= '" + this.player + "'");

            if(resultSet.next()) {
                resultSet.getInt("MONEY");
            }

            money = resultSet.getInt("MONEY");

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return money;
    }

    // Set Money
    public void setMoney(int money) {
        if(!playerExists()) {
            createPlayer();
            setMoney(money);
        }

        this.plugin.getMySQL().update("UPDATE who SET MONEY = '" + money + "' WHERE PLAYER= '" + this.player + "';");
    }

    // Add Money
    public void addMoney(int money) {
        if(!playerExists()) {
            createPlayer();
            addMoney(money);
        }

        int add = getMoney() + money;
        setMoney(add);
    }

    // Remove Money
    public void removeMoney(int money) {
        if(!playerExists()) {
            createPlayer();
            removeMoney(money);
        }

        int remove = getMoney() - money;
        setMoney(remove);
    }

}
