package de.joshiworld.sql;

import java.sql.*;

public class MySQL {
    private final String PREFIX = "[MySQL]";

    // Database Stuff
    private final String host;
    private final String username;
    private final String password;
    private final String database;

    // MySQL Stuff
    private Connection connection;



    public MySQL(String host, String username, String password, String database) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
        connect();
    }



    // Verbindung herstellen
    public void connect() {
        try {
            this.connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + this.host + ":3306/" + this.database + "?useSSL=false", this.username, this.password);
            System.out.println(this.PREFIX + " Verbindung wurde hergestellt!");
        } catch (SQLException e) {
            System.out.println(this.PREFIX + " Fehler beim Verbinden: " + e.getMessage());
        }
    }

    // Verbindung schließen
    public void close() {
        try {
            if(this.connection != null) {
                this.connection.close();
                System.out.println(this.PREFIX + " Verbindung wurde getrennt!");
            }
        } catch (SQLException e) {
            System.out.println(this.PREFIX + " Fehler beim Trennen der Verbindung: " + e.getMessage());
        }
    }

    // SQL Updates
    public void update(String sql) {
        try {
            this.connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(this.PREFIX + " Fehler beim Eintrag: " + e.getMessage());
        }
    }

    // ResultSet
    public ResultSet query(String query) {
        ResultSet resultSet = null;

        try {
            Statement statement = (Statement) this.connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            connect();
            System.err.println(e);
        }

        return resultSet;
    }

}
