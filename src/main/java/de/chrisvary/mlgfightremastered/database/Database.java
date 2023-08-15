package de.chrisvary.mlgfightremastered.database;

import de.chrisvary.mlgfightremastered.Main;
import de.chrisvary.mlgfightremastered.filemanager.FileManager;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private Connection connection;

    public Connection getConnection() throws SQLException {
        /*Establish connection between this plugin and the sql data base*/

        FileManager fileManager = Main.getInstance().getFileManager();
        if(connection != null)
            return this.connection;

        //Collects login information in config file
        String root = fileManager.getConfig().getString("SQL Data.root");
        String user = fileManager.getConfig().getString("SQL Data.user");
        String password = fileManager.getConfig().getString("SQL Data.password");

        Bukkit.getConsoleSender().sendMessage("Trying to connect to " + root);
        Connection connection = DriverManager.getConnection(root, user, password);

        this.connection = connection;

        Bukkit.getConsoleSender().sendMessage("Die Verbindung zur MLGFight Datenbank wurde hergestellt");
        return connection;

    }

    public void initialization() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " +
                "player_stats(uuid VARCHAR(36) PRIMARY KEY, name VARCHAR(30), kills int, " +
                "deaths int, playtime TIME, firstJoin DATE, lastPlayed DATE); ");
        stmt.executeUpdate();
        stmt.close();
    }
}
