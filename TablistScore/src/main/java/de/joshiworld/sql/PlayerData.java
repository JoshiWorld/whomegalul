package de.joshiworld.sql;

import de.joshiworld.api.BukkitSerialization;
import de.joshiworld.main.TablistScore;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.IOException;
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

    // Set Inventory
    public void setInventory(PlayerInventory inventory) {
        if(!playerExists()) {
            createPlayer();
            setInventory(inventory);
        }

        this.plugin.getMySQL().update("UPDATE who SET INV = '" + BukkitSerialization.itemStackArrayToBase64(inventory.getContents()) + "' WHERE PLAYER= '" + this.player + "';");
        this.plugin.getMySQL().update("UPDATE who SET STORAGE = '" + BukkitSerialization.itemStackArrayToBase64(inventory.getStorageContents()) + "' WHERE PLAYER= '" + this.player + "';");
        this.plugin.getMySQL().update("UPDATE who SET ARMOR = '" + BukkitSerialization.itemStackArrayToBase64(inventory.getArmorContents()) + "' WHERE PLAYER= '" + this.player + "';");
    }

    // Get Inventory
    public PlayerInventory getInventory() {
        PlayerInventory inventory = Bukkit.getPlayer(this.player).getInventory();

        if(!playerExists()) {
            createPlayer();
            getInventory();
        }

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM who WHERE PLAYER= '" + this.player + "'");

            if(resultSet.next()) {
                resultSet.getString("INV");
                resultSet.getString("STORAGE");
                resultSet.getString("ARMOR");
            }

            inventory.setContents(BukkitSerialization.itemStackArrayFromBase64(resultSet.getString("INV")));
            inventory.setStorageContents(BukkitSerialization.itemStackArrayFromBase64(resultSet.getString("STORAGE")));
            inventory.setArmorContents(BukkitSerialization.itemStackArrayFromBase64(resultSet.getString("ARMOR")));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return inventory;
    }

    // Set Enderchest
    public void setEnderChest(Inventory inventory) {
        if(!playerExists()) {
            createPlayer();
            setEnderChest(inventory);
        }

        this.plugin.getMySQL().update("UPDATE who SET ENDER = '" + BukkitSerialization.itemStackArrayToBase64(inventory.getContents()) + "' WHERE PLAYER= '" + this.player + "';");
        this.plugin.getMySQL().update("UPDATE who SET ENDERSTORE = '" + BukkitSerialization.itemStackArrayToBase64(inventory.getStorageContents()) + "' WHERE PLAYER= '" + this.player + "';");
    }

    // Get Enderchest
    public Inventory getEnderChest() {
        Inventory inventory = Bukkit.createInventory(null, 27, "§5Ender-Chest");

        if(!playerExists()) {
            createPlayer();
            getEnderChest();
        }

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM who WHERE PLAYER= '" + this.player + "'");

            if(resultSet.next()) {
                resultSet.getString("ENDER");
                resultSet.getString("ENDERSTORE");
            }

            inventory.setContents(BukkitSerialization.itemStackArrayFromBase64(resultSet.getString("ENDER")));
            inventory.setStorageContents(BukkitSerialization.itemStackArrayFromBase64(resultSet.getString("ENDERSTORE")));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return inventory;
    }

}
