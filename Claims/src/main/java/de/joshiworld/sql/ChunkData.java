package de.joshiworld.sql;

import de.joshiworld.main.Claims;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChunkData {
    private final Long chunk;
    private final Claims plugin;

    public ChunkData(Long chunk, Claims plugin) {
        this.chunk = chunk;
        this.plugin = plugin;
    }


    // Check all Claims
    public boolean checkBlockForChunk() {
        List<Long> list = new ArrayList<>();

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM who");

            List<String> allList = new ArrayList<>();

            while(resultSet.next()) {
                if(!resultSet.getString("PLAYER").isEmpty()) {
                    allList.add(resultSet.getString("CLAIMS"));
                }
            }

            int maxL = allList.toString().length()-1;
            String[] claimsArray = allList.toString().substring(1, maxL).split("], ");
            List<String> tempList = new ArrayList<>();

            tempList.addAll(Arrays.asList(claimsArray));

            List<Long> finalList = new ArrayList<>();
            tempList.forEach(all -> {
                String[] array = all.substring(1, all.length()-1).split(", ");

                for(String s : array) {
                    finalList.add(Long.valueOf(s));
                }
            });

            list = finalList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list.contains(this.chunk);
    }




/*
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
*/

}
