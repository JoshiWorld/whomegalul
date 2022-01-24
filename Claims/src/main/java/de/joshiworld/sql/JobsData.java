package de.joshiworld.sql;

import de.joshiworld.main.Claims;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobsData {
    private final Claims plugin;

    public JobsData(Claims plugin) {
        this.plugin = plugin;
    }

    // Get Top Holzfäller
    public String getTopLumber() {
        String topPlayer = null;

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM jobs ORDER BY HOLZ_LVL DESC LIMIT " + 1);

            if(resultSet.next())
                resultSet.getString(1);

            topPlayer = resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topPlayer;
    }

    // Get Top Miner
    public String getTopMiner() {
        String topPlayer = null;

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM jobs ORDER BY MINER_LVL DESC LIMIT " + 1);

            if(resultSet.next())
                resultSet.getString(1);

            topPlayer = resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topPlayer;
    }

    // Get Top Hunter
    public String getTopHunter() {
        String topPlayer = null;

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM jobs ORDER BY HUNTER_LVL DESC LIMIT " + 1);

            if(resultSet.next())
                resultSet.getString(1);

            topPlayer = resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topPlayer;
    }

    // Get Top Farmer
    public String getTopFarmer() {
        String topPlayer = null;

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM jobs ORDER BY FARMER_LVL DESC LIMIT " + 1);

            if(resultSet.next())
                resultSet.getString(1);

            topPlayer = resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topPlayer;
    }

    // Get Top Traveler
    public String getTopTraveler() {
        String topPlayer = null;

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM jobs ORDER BY TRAVELER_LVL DESC LIMIT " + 1);

            if(resultSet.next())
                resultSet.getString(1);

            topPlayer = resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topPlayer;
    }
}
