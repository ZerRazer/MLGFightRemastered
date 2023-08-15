package de.chrisvary.mlgfightremastered.database;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private Connection connection;

    public Connection getConnection() throws SQLException {
        if(connection != null)
            return this.connection;

        String root = "jdbc:mysql://localhost/mlgfight";
        String user = "root";
        String passwort = "admin";

        Connection connection = DriverManager.getConnection(root, user, passwort);

        this.connection = connection;

        Bukkit.getConsoleSender().sendMessage("Die Verbindung zur MLGFight Datenbank wurde hergestellt");
        return connection;
    }

    public void initialization() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " +
                "player_stats(uuid VARCHAR(36) PRIMARY KEY, name VARCHAR(30), kills int, " +
                "deaths int, playtime TIME, firstJoin DATE, lastPlayed DATE) ")
        stmt.executeUpdate();
        stmt.close();
    }
}
