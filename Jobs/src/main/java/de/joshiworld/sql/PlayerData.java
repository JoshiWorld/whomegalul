package de.joshiworld.sql;

import de.joshiworld.main.Jobs;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerData {
    private final Jobs plugin;
    private final String player;

    public PlayerData(String player, Jobs plugin) {
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
            this.plugin.getMySQL().update("INSERT INTO who(PLAYER, MONEY, CLAIMS, TRUSTED, OTHERCLAIMS, INV, STORAGE, ARMOR, ENDER, ENDERSTORE) VALUES ('" + this.player + "', '0', '', '', '', '', '', '', '', '');");
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
