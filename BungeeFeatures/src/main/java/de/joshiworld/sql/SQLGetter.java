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
    //Sql check
    private void connectSQL(){
        try {
            if (plugin.SQL.isConnected()) return;
            plugin.SQL.connect();
        } catch (SQLException | ClassNotFoundException e) {
            plugin.getLogger().info("Database not connected");
        }
    }

}
