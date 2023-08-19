package de.chrisvary.mlgfightremastered.spielmanager;

import de.chrisvary.mlgfightremastered.Main;
import de.chrisvary.mlgfightremastered.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class SpielManager {
    ArrayList<Spiel> runden;

    public SpielManager() throws SQLException {
        runden = new ArrayList<>();
    }

    public void createGame(Player p) throws SQLException {
        int index = getIndexWherePlayer(p);
        if(index != -1){
            p.sendMessage("Du bist bereits in einem Spiel drinnen!");
            return;
        }
        Spiel spiel = new Spiel(p);
        runden.add(spiel);
        if(spiel.getLobbySpawn() == null)
            p.teleport(Bukkit.getWorld("world").getSpawnLocation());
        else
            p.teleport(spiel.getLobbySpawn());
        p.sendMessage("[MLGFight]Das Spiel wurde gestartet");
    }
    public boolean gameIsRunning(Player p){
        if(isPlayerInRunde(p)){
            int index = getIndexWherePlayer(p);
            if(index == -1)
                return false;
            return getRunden().get(index).isRunning();
        }
        return false;
    }
    public boolean isPlayerInRunde(Player p){
        for(Spiel spiel : runden){
            if(spiel.getPlayer1().equals(p))
                return true;
            if(spiel.getPlayer2().equals(p))
                return true;
        }
        return false;
    }
    public Spiel getSpiel(Player p){
        int index = getIndexWherePlayer(p);
        if(index != -1)
            return runden.get(index);
        return null;
    }
    public int getIndexWherePlayer(Player p){
        int index = 0;
        for(Spiel spiel : runden){
            if(spiel.getPlayer1() != null)
                if(spiel.getPlayer1().equals(p))
                    return index;
            if(spiel.getPlayer2() != null)
                if(spiel.getPlayer2().equals(p))
                    return index;
            index++;
        }
        return -1;
    }
    public int getRoundToJoin(){
        int index = 0;
        for(Spiel spiel : runden){
            if(spiel.getPlayer2() == null){
                return index;
            }
            index++;
        }
        return -1;
    }
    public void setSpawn(Location loc, String name) throws SQLException {
        Database db = Main.getInstance().getDatabase();
        PreparedStatement stmt = db.getConnection().prepareStatement("INSERT INTO mlgfight_locations(name, x, y, z, world) " +
                    "VALUES (?,?,?,?, ?) ON DUPLICATE KEY UPDATE x = ?, y = ?, z = ?, world = ?");
        stmt.setString(1, name);
        stmt.setInt(2, (int) loc.getX());
        stmt.setInt(3, (int) loc.getY());
        stmt.setInt(4, (int) loc.getZ());
        stmt.setString(5, Objects.requireNonNull(loc.getWorld()).getName());

        stmt.setInt(6, (int) loc.getX());
        stmt.setInt(7, (int) loc.getY());
        stmt.setInt(8, (int) loc.getZ());
        stmt.setString(9, Objects.requireNonNull(loc.getWorld()).getName());
        stmt.executeUpdate();




    }
    public void stopGame(Player p){
        int index = getIndexWherePlayer(p);
        if(index == -1) {
            p.sendMessage("Du bist in keinem Spiel drinnen");
            return;
        }
        runden.remove(index);
        p.sendMessage("Das Game wurde gestoppt");
    }

    public ArrayList<Spiel> getRunden() {
        return runden;
    }
}
