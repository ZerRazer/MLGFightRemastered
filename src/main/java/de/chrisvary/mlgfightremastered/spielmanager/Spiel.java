package de.chrisvary.mlgfightremastered.spielmanager;

import de.chrisvary.mlgfightremastered.Main;
import de.chrisvary.mlgfightremastered.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;


public class Spiel {
    private int runde;
    private boolean running;
    private Player player1, player2;
    private HashMap<String, Location> locations;
    private Inventory lobbyInventory, gameInventory;

    public Spiel(Player player1, Player player2) throws SQLException {
        runde = 0;
        running = false;
        this.player1 = player1;
        this.player2 = player2;

        locations = new HashMap<>();
        locations.put("player1_spawn", null);
        locations.put("player2_spawn", null);
        locations.put("lobbyspawn", null);

        loadSpawnPoints();
    }

    private void loadSpawnPoints() throws SQLException {
        Database db = Main.getInstance().getDatabase();
        Statement stmt = db.getConnection().createStatement();

        locations.forEach((name, location) -> {
            int x, y, z;
            String world;

            try {
                ResultSet rs = stmt.executeQuery("SELECT * FROM mlgfight_locations WHERE name = " + name);
                x = rs.getInt(2);
                y = rs.getInt(3);
                z = rs.getInt(4);
                world = rs.getString(5);
                Location loc = new Location(Bukkit.getWorld(world), x, y, z);

                locations.put(name, loc);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
