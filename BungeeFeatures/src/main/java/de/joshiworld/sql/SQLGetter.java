package de.joshiworld.sql;
import de.joshiworld.bukkit.main.Paper;
import de.joshiworld.bungee.main.Bungee;
import de.joshiworld.bungee.main.BungeeInitStuff;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLGetter {

    private Bungee plugin;
    public SQLGetter(Bungee plugin){
        this.plugin = plugin;
    }

    private Paper Paperplugin;
    public SQLGetter(Paper plugin){
        this.Paperplugin = plugin;
    }

    public void createTable(){
        PreparedStatement ps;
        try{
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS HomeList "
                + "(UUID VARCHAR(100),HOMENAME VARCHAR(25),LOCATION VARCHAR(128),SERVER VARCHAR(20), PRIMARY KEY (UUID,HOMENAME))");
            ps.executeUpdate();
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Userlist "
                    + "(UUID VARCHAR(100),USERNAME VARCHAR(25),BANNED tinyint(1),DESCRIPTION VARCHAR(100), PRIMARY KEY (UUID))");
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//-------Bungee
    public void createHome(String player,String homename,String server, String loc){
        connectSQL();
        try{
            PreparedStatement ps;
            if(!searchHome(player,homename)){
                ps = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO HomeList" +
                        " (UUID, HOMENAME, LOCATION, SERVER) VALUES (?,?,?,?)");
                ps.setString(1,player);
                ps.setString(2,homename);
                ps.setString(3,loc);
                ps.setString(4,server);
                ps.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean searchHome(String player, String home){
        connectSQL();
        try{
            PreparedStatement ps;
            ps = ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM HomeList WHERE UUID=? AND HOMENAME=?");
            ps.setString(1, player);
            ps.setString(2, home);
            ResultSet results = ps.executeQuery();
            if (results.next()){
                return true;
            }
            return false;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public void deleteHome(String player, String home){
        connectSQL();
        try{
            PreparedStatement ps;
            ps = ps = plugin.SQL.getConnection().prepareStatement("DELETE FROM HomeList WHERE UUID=? AND HOMENAME=?");
            ps.setString(1, player);
            ps.setString(2, home);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public ResultSet getHome(String player, String home){
        connectSQL();
        try{
            PreparedStatement ps;
            ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM HomeList WHERE UUID=? AND HOMENAME=?");
            ps.setString(1, player);
            ps.setString(2, home);
            return ps.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public List<String> getHomeNames(String player){
        connectSQL();
        List<String> homeNames = new ArrayList<String>();
        try{
            PreparedStatement ps;
            ps = plugin.SQL.getConnection().prepareStatement("SELECT HOMENAME FROM HomeList WHERE UUID=?");
            ps.setString(1, player);
            ResultSet results = ps.executeQuery();
            while(results.next()){
                homeNames.add(results.getString("HOMENAME"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return homeNames;
    }
    public ResultSet getUser(String username){
        connectSQL();
        try{
            PreparedStatement ps;
            ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM Userlist WHERE USERNAME LIKE ?");
            ps.setString(1, username);
            return ps.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public void deleteallHomes(String server){
        connectSQL();
        try{
            PreparedStatement ps;
            ps = ps = plugin.SQL.getConnection().prepareStatement("DELETE FROM HomeList WHERE SERVER=?");
            ps.setString(1, server);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void banUser(String uuid,String reason){
        connectSQL();
        try{
            PreparedStatement ps;
            ps = ps = plugin.SQL.getConnection().prepareStatement("UPDATE `Userlist` SET `BANNED` = '01',`DESCRIPTION` = ? WHERE `Userlist`.`UUID` = ?");
            ps.setString(1, reason);
            ps.setString(2, uuid);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void unbanUser(String uuid){
        connectSQL();
        try{
            PreparedStatement ps;
            ps = ps = plugin.SQL.getConnection().prepareStatement("UPDATE `Userlist` SET `BANNED` = '0',`DESCRIPTION` = '' WHERE `Userlist`.`UUID` = ?");
            ps.setString(1, uuid);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    //---Paper
    public boolean isNewUser(String uuid){
        connectPaperSQL();
        try{
            PreparedStatement ps;
            ps = ps = Paperplugin.SQL.getConnection().prepareStatement("SELECT * FROM Userlist WHERE UUID=?");
            ps.setString(1, uuid);
            ResultSet results = ps.executeQuery();
            return !results.next();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public void addUser(String uuid,String userame){
        connectPaperSQL();
        try{
            PreparedStatement ps;
            if(isNewUser(uuid)){
                ps = Paperplugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO Userlist" +
                        " (UUID, USERNAME, BANNED) VALUES (?,?,?)");
                ps.setString(1,uuid);
                ps.setString(2,userame);
                ps.setString(3,"0");
                ps.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean isBanned(String uuid){
        connectPaperSQL();
        try{
            PreparedStatement ps;
            ps = ps = Paperplugin.SQL.getConnection().prepareStatement("SELECT BANNED FROM Userlist WHERE UUID=?");
            ps.setString(1, uuid);
            ResultSet results = ps.executeQuery();
            if(!(results.next())) return false;
            return results.getInt("Banned") != 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public String getBanreason(String uuid){
        connectPaperSQL();
        try{
            PreparedStatement ps;
            ps = Paperplugin.SQL.getConnection().prepareStatement("SELECT DESCRIPTION FROM Userlist WHERE UUID=?");
            ps.setString(1, uuid);
            ResultSet results = ps.executeQuery();
            if(results.next()) return results.getString("DESCRIPTION");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "Error";
    }

    public void updateUsername(String username,String uuid){
        connectPaperSQL();
        try{
            if(isNewUser(uuid)) return;
            PreparedStatement ps;
            ps = Paperplugin.SQL.getConnection().prepareStatement("UPDATE Userlist SET USERNAME = ? WHERE UUID=?");
            ps.setString(1, username);
            ps.setString(2, uuid);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    //Sql check
    private void connectSQL(){
        try {
            if (plugin.SQL.isConnected()) return;
            plugin.SQL.connect();
        } catch (SQLException | ClassNotFoundException e) {
            plugin.getLogger().info("Database not connected");
        }
    }
    private void connectPaperSQL(){
        try {
            if (Paperplugin.SQL.isConnected()) return;
            Paperplugin.SQL.connect();
        } catch (SQLException | ClassNotFoundException e) {
            Paperplugin.getLogger().info("Database not connected");
        }
    }

}
