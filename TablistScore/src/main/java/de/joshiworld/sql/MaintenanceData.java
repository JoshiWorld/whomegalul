package de.joshiworld.sql;

import de.joshiworld.main.TablistScore;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaintenanceData {
    private final TablistScore plugin;

    public MaintenanceData(TablistScore plugin) {
        this.plugin = plugin;
    }

    // Get Whitelist
    public boolean getMaintenance() {
        boolean maintenance = false;

        try {
            ResultSet resultSet = this.plugin.getMySQL().query("SELECT * FROM wartung");

            if(resultSet.next()) {
                resultSet.getInt("WARTUNG");
            }

            if(resultSet.getInt("WARTUNG") == 0) maintenance = false;
            else maintenance = true;

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maintenance;
    }

    // Set Whitelist
    public void setMaintenance(boolean maintenance) {
        int list = maintenance ? 1 : 0;
        this.plugin.getMySQL().update("UPDATE wartung SET WARTUNG = '" + list + "' WHERE WAIT='server';");
    }
}
