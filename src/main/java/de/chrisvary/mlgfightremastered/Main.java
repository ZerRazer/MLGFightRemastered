package de.chrisvary.mlgfightremastered;

import de.chrisvary.mlgfightremastered.database.Database;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Main extends JavaPlugin {
    private static Main instance;
    private Database database;
    @Override
    public void onLoad(){
        instance = this;
        try {
            database.getConnection();
            database.initialization();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void onEnable() {
        // Plugin startup logic


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return instance;
    }

    public Database getDatabase() {
        return database;
    }
}
