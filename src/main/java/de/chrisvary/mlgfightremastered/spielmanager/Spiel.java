package de.chrisvary.mlgfightremastered.spielmanager;

import com.sun.tools.javac.file.Locations;
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
    private HashMap<String, Location> locations;
    private Inventory lobbyInventory, gameInventory;
    private Player player1, player2;

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
    public Spiel(Player player) throws SQLException {
        runde = 0;
        running = false;
        this.player1 = player;
        this.player2 = null;

        locations = new HashMap<>();
        locations.put("player1_spawn", null);
        locations.put("player2_spawn", null);
        locations.put("lobbyspawn", null);

        loadSpawnPoints();
    }
    public void runGame(){
        if(running)
            return;
        if(player1 != null && player2 != null){
            player1.teleport(locations.get("player1_spawn"));
            player2.teleport(locations.get("player2_spawn"));

            running = true;

            player1.sendMessage("Das Spiel beginnt!");
            player2.sendMessage("Das Spiel beginnt!");

        }
    }

    private void loadSpawnPoints() throws SQLException {
        Database db = Main.getInstance().getDatabase();
        Statement stmt = db.getConnection().createStatement();

        locations.forEach((name, location) -> {
            int x, y, z;
            String world;

            try {
                //ResultSet rs = stmt.executeQuery("SELECT * FROM mlgfight_locations WHERE name = " + name);
                PreparedStatement state = db.getConnection().prepareStatement("SELECT * FROM mlgfight_locations WHERE name = ?");
                state.setString(1, name);
                ResultSet rs = state.executeQuery();
                rs.next();
                x = rs.getInt(2);
                y = rs.getInt(3);
                z = rs.getInt(4);
                world = rs.getString(5);
                Location loc = new Location(Bukkit.getWorld(world), x, y, z);

                locations.put(name, loc);
                state.close();
                rs.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void joinPLayer(Player p){
        if(this.player2 == null){
            this.player2 = p;
            p.teleport(locations.get("lobbyspawn"));
            p.sendMessage("Du bist dem Spiel gejoint. Dein Gegner ist: " + player1.getName());
            player1.sendMessage(player2.getName() + " ist dem Spiel beigetreten");
        }
    }

    public int getRunde() {
        return runde;
    }

    public boolean isRunning() {
        return running;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
    public Location getLobbySpawn(){
        return locations.get("lobbyspawn");
    }
}
