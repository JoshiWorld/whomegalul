package de.joshiworld.sql;

import de.joshiworld.bungee.Bungee;
import org.bukkit.Location;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLGetter {

    private Bungee plugin;
    public SQLGetter(Bungee plugin){
        this.plugin = plugin;
    }

    public void createTable(){
        PreparedStatement ps;
        try{
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playerhomes "
                + "(UUID VARCHAR(16),HOMENAME VARCHAR(25),LOCATION VARCHAR(100), PRIMARY KEY (UUID,HOMENAME))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createHome(UUID player,String Homename, Location loc){
        try{
            PreparedStatement ps;
            if(!searchHome(player)){
                ps = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO playerhomes" +
                        " (UUID, HOMENAME, LOCATION) VALUES (?,?,?)");
                ps.setString(1,player.toString());
                ps.setString(2,Homename);
                String location = (loc.serialize()).toString();
                ps.setString(3,location);
                ps.executeUpdate();
                return;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean searchHome(UUID player){
        try{
            PreparedStatement ps;
            ps = ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM playerhomes WHERE UUID=?");
            ps.setString(1, player.toString());
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


}
