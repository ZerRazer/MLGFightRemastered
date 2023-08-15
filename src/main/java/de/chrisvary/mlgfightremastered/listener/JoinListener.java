package de.chrisvary.mlgfightremastered.listener;

import de.chrisvary.mlgfightremastered.Main;
import de.chrisvary.mlgfightremastered.database.Database;
import de.chrisvary.mlgfightremastered.user.User;
import de.chrisvary.mlgfightremastered.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws SQLException {
        Database db = Main.getInstance().getDatabase();
        UserManager userManager = Main.getInstance().getUserManager();
        Player p = event.getPlayer();
        if(!isInDatabase(p, db)){
            insertPlayerToDatabase(p, db);
        }
        userManager.load(p);
    }

    private void insertPlayerToDatabase(Player player, Database db) throws SQLException {
        PreparedStatement stmt = db.getConnection().prepareStatement("INSERT INTO player_stats" +
                "(uuid, name, kills, deaths) VALUES (?, ?, ?, ?)");

        stmt.setString(1, player.getUniqueId().toString());
        stmt.setString(2, player.getName());
        stmt.setInt(3, 0);
        stmt.setInt(4, 0);

        stmt.executeUpdate();
        stmt.close();
    }
    private boolean isInDatabase(Player player, Database db) throws SQLException {
        ResultSet rs = db.getConnection().prepareStatement("SELECT * FROM player_stats WHERE " +
                "uuid = '" + player.getUniqueId() + "';").executeQuery();
        if(rs.next()){
            return true;
        }

        return false;
    }
}
