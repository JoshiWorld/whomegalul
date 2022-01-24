package de.joshiworld.sql;

import de.joshiworld.main.Claims;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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

            if(tempList.isEmpty()) return false;

            tempList.forEach(all -> {
                String[] array = all.substring(1, all.length()-1).split(", ");

                for(String s : array) {
                    //if(!s.equalsIgnoreCase(" "))
                        finalList.add(Long.valueOf(s));
                }
            });

            list = finalList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list.contains(this.chunk);
    }



    // Lookup Chunk Owner
    public String lookupChunkOwner() {
        String owner = null;

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM who");
            List<String> players = new ArrayList<>();

            while(resultSet.next()) {
                if(!resultSet.getString("PLAYER").isEmpty()) {
                    players.add(resultSet.getString("PLAYER"));
                }
            }

            Optional<String> tempPlayer = players.stream().filter(player -> !new PlayerData(player, this.plugin).getClaims().isEmpty())
                    .collect(Collectors.toList()).stream().filter(player -> new PlayerData(player, this.plugin).getClaims().contains(chunk)).findFirst();
            if(tempPlayer.isPresent()) owner = tempPlayer.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return owner;
    }

}
