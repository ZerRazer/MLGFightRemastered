package de.chrisvary.mlgfightremastered.spielmanager;

import de.chrisvary.mlgfightremastered.Main;
import de.chrisvary.mlgfightremastered.database.Database;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SpielManager {
    ArrayList<Spiel> runden;

    public SpielManager(){
        runden = new ArrayList<>();
    }

    public void sqlDatabasePreparation() throws SQLException {
        Database db = Main.getInstance().getDatabase();

        PreparedStatement stmt = db.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " +
                "mlgfight_locations(name VARCHAR(20), x INT, y INT, z INT, world VARCHAR(30)");
        stmt.executeUpdate();

        stmt.close();
    }

    public void createGame(Player p1, Player p2) throws SQLException {
        Spiel spiel = new Spiel(p1, p2);

    }


}
