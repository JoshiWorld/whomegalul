package de.joshiworld.sql;

import de.joshiworld.main.Claims;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerData {
    private final Claims plugin;
    private final String player;

    public PlayerData(String player, Claims plugin) {
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



    // Get CLAIMS
    public List<Long> getClaims() {
        List<Long> claims = null;

        if(!playerExists()) {
            createPlayer();
            getClaims();
        }

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM who WHERE PLAYER= '" + this.player + "'");

            if(resultSet.next()) {
                resultSet.getString("CLAIMS");
            }

            if(resultSet.getString("CLAIMS").isEmpty()) {
                claims = new ArrayList<Long>();
            } else {
                int maxL = resultSet.getString("CLAIMS").length()-1;

                if(resultSet.getString("CLAIMS").isEmpty() || resultSet.getString("CLAIMS").contains("[]") || resultSet.getString("CLAIMS").contains("[ ]"))
                    return new ArrayList<Long>();
                String[] claimsArray = resultSet.getString("CLAIMS").substring(1, maxL).split(", ");
                List<Long> tempList = new ArrayList<>();

                for (String s : claimsArray) {
                    tempList.add(Long.valueOf(s));
                }

                claims = tempList;
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return claims;
    }

    // Set CLAIMS
    public void setClaims(List<Long> list) {
        if(!playerExists()) {
            createPlayer();
            setClaims(list);
        }

        this.plugin.getMySQL().update("UPDATE who SET CLAIMS = '" + list.toString() + "' WHERE PLAYER= '" + this.player + "';");
    }

    // Add CLAIMS
    public void addClaims(List<Long> list) {
        if(!playerExists()) {
            createPlayer();
            addClaims(list);
        }

        List<Long> claimList = getClaims() == null ? new ArrayList<>() : getClaims();
        claimList.addAll(list);

        setClaims(claimList);
    }

    // Remove CLAIMS
    public void removeClaims(List<Long> list) {
        if(!playerExists()) {
            createPlayer();
            removeClaims(list);
        }

        List<Long> claimList = getClaims();
        list.forEach(claimList::remove);

        setClaims(claimList);
    }



    // Get Trust
    public List<String> getTrusted() {
        List<String> trusted = null;

        if(!playerExists()) {
            createPlayer();
            getTrusted();
        }

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM who WHERE PLAYER= '" + this.player + "'");

            if(resultSet.next()) {
                resultSet.getString("TRUSTED");
            }

            if(resultSet.getString("TRUSTED").isEmpty()) {
                trusted = new ArrayList<String>();
            } else {
                int maxL = resultSet.getString("TRUSTED").length()-1;
                String[] trustArray = resultSet.getString("TRUSTED").substring(1, maxL).split(", ");
                List<String> tempList = new ArrayList<>();

                tempList.addAll(Arrays.asList(trustArray));

                trusted = tempList;
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trusted;
    }

    // Set Trust
    public void setTrusted(List<String> list) {
        if(!playerExists()) {
            createPlayer();
            setTrusted(list);
        }

        this.plugin.getMySQL().update("UPDATE who SET TRUSTED = '" + list.toString() + "' WHERE PLAYER= '" + this.player + "';");
    }

    // Add Trust
    public void addTrusted(String target) {
        if(!playerExists()) {
            createPlayer();
            addTrusted(target);
        }

        List<String> trustList = getTrusted() == null ? new ArrayList<>() : getTrusted();
        trustList.add(target);

        setTrusted(trustList);
    }

    // Remove Trust
    public void removeTrusted(String target) {
        if(!playerExists()) {
            createPlayer();
            removeTrusted(target);
        }

        List<String> trustList = getTrusted();
        trustList.remove(target);

        setTrusted(trustList);
    }



    // Get OtherClaims
    public List<String> getOtherClaims() {
        List<String> trusted = null;

        if(!playerExists()) {
            createPlayer();
            getOtherClaims();
        }

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM who WHERE PLAYER= '" + this.player + "'");

            if(resultSet.next()) {
                resultSet.getString("OTHERCLAIMS");
            }

            if(resultSet.getString("OTHERCLAIMS").isEmpty()) {
                trusted = new ArrayList<String>();
            } else {
                int maxL = resultSet.getString("OTHERCLAIMS").length()-1;
                String[] trustArray = resultSet.getString("OTHERCLAIMS").substring(1, maxL).split(", ");
                List<String> tempList = new ArrayList<>();

                tempList.addAll(Arrays.asList(trustArray));

                trusted = tempList;
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trusted;
    }

    // Set OtherClaims
    public void setOtherClaims(List<String> list) {
        if(!playerExists()) {
            createPlayer();
            setOtherClaims(list);
        }

        this.plugin.getMySQL().update("UPDATE who SET OTHERCLAIMS = '" + list.toString() + "' WHERE PLAYER= '" + this.player + "';");
    }

    // Add OtherClaims
    public void addOtherClaims(String target) {
        if(!playerExists()) {
            createPlayer();
            addOtherClaims(target);
        }

        List<String> trustList = getOtherClaims() == null ? new ArrayList<>() : getOtherClaims();
        trustList.add(target);

        setOtherClaims(trustList);
    }

    // Remove OtherClaims
    public void removeOtherClaims(String target) {
        if(!playerExists()) {
            createPlayer();
            removeOtherClaims(target);
        }

        List<String> trustList = getOtherClaims();
        trustList.remove(target);

        setOtherClaims(trustList);
    }

    // Check all Claims
    public boolean checkIfClaimed(Long chunk) {
        List<String> list = new ArrayList<>();

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM who");

            while(resultSet.next()) {
                if(!resultSet.getString(1).isEmpty()) list.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list.stream().anyMatch(claim -> new PlayerData(claim, this.plugin).getClaims().contains(chunk));
    }



    // Get Top Players
    public List<String> getTopPlayers(int top) {
        List<String> list = new ArrayList<>();

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM who ORDER BY MONEY DESC LIMIT " + top);

            while (resultSet.next()) {
                list.add(resultSet.getString(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
