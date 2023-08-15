package de.chrisvary.mlgfightremastered;

import de.chrisvary.mlgfightremastered.commands.Mlgfight;
import de.chrisvary.mlgfightremastered.database.Database;
import de.chrisvary.mlgfightremastered.filemanager.FileManager;
import de.chrisvary.mlgfightremastered.listener.DeathListener;
import de.chrisvary.mlgfightremastered.listener.JoinListener;
import de.chrisvary.mlgfightremastered.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Main extends JavaPlugin {
    private static Main instance;
    private Database database;
    private FileManager fileManager;
    private UserManager userManager;
    @Override
    public void onLoad(){
        instance = this;
        fileManager = new FileManager();
        database = new Database();
        userManager = new UserManager();

        try {
            database.getConnection();
            database.initialization();
            userManager.loadOnlinePlayer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        listener();
        commands();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            userManager.save();
            database.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void commands(){
        getCommand("mlgfight").setExecutor(new Mlgfight());
    }
    public void listener(){
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new DeathListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    public Database getDatabase() {
        return database;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
