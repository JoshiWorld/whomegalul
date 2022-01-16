package de.joshiworld.sql;

import de.joshiworld.bungee.Bungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createHome(String player,String homename,String server, String loc){
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
                return;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean searchHome(String player, String home){
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
        try{
            PreparedStatement ps;
            ps = ps = plugin.SQL.getConnection().prepareStatement("DELETE FROM HomeList WHERE UUID=? AND HOMENAME=?");
            ps.setString(1, player);
            ps.setString(2, home);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return;
    }
    public ResultSet getHome(String player, String home){
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


}
