package de.chrisvary.mlgfightremastered.user;

import de.chrisvary.mlgfightremastered.Main;
import de.chrisvary.mlgfightremastered.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class UserManager {
    ArrayList<User> users;

    public UserManager(){
        users = new ArrayList<>();
    }

    public void load() throws SQLException {
        Database db = Main.getInstance().getDatabase();
        ResultSet rs = db.getConnection().createStatement().executeQuery("SELECT * FROM player_stats");

        if(rs.next()){
            User user = new User(UUID.fromString(rs.getString(1)), rs.getString(2),
                rs.getInt(3), rs.getInt(4));
            users.add(user);
        }
        rs.close();
    }
    public void save() throws SQLException {
        Database db = Main.getInstance().getDatabase();
        Connection con = db.getConnection();

        users.forEach(user -> {
            UUID uuid = user.getUuid();
            String name = user.getName();
            int kills = user.getKills();
            int deaths = user.getDeaths();

            try {
                PreparedStatement stmt = con.prepareStatement("INSERT INTO player_stats" +
                        "(uuid, name, kills, deaths) VALUES (?, ?, ?, ?)");
                stmt.setString(1, uuid.toString());
                stmt.setString(2, name);
                stmt.setInt(3, kills);
                stmt.setInt(4, deaths);

                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

}