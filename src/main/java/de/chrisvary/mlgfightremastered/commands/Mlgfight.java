package de.chrisvary.mlgfightremastered.commands;

import de.chrisvary.mlgfightremastered.Main;
import de.chrisvary.mlgfightremastered.spielmanager.SpielManager;
import de.chrisvary.mlgfightremastered.user.UserManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Mlgfight implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(sender instanceof Player){
            Player p = (Player)sender;
            Location loc = p.getLocation();
            UserManager userManager = Main.getInstance().getUserManager();
            SpielManager spielManager = Main.getInstance().getSpielManager();
            if(args.length == 1){
                switch(args[0]){
                    case "lobby":
                        try {
                            spielManager.setSpawn(loc, "lobbyspawn");
                            p.sendMessage("Spawn wurde gesetzt");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "player1":
                        try {
                            spielManager.setSpawn(loc, "player1_spawn");
                            p.sendMessage("Spawn wurde gesetzt");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "player2":
                        try {
                            spielManager.setSpawn(loc, "player2_spawn");
                            p.sendMessage("Spawn wurde gesetzt");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "run":
                        int indexRun = spielManager.getIndexWherePlayer(p);
                        if(indexRun != -1){
                            spielManager.getRunden().get(indexRun).runGame();
                        }
                        break;
                    case "start":
                        try {
                            spielManager.createGame(p);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "stop":
                        spielManager.stopGame(p);
                        break;
                    case "join":
                        int indexRoundToJoin = spielManager.getRoundToJoin();
                        if(indexRoundToJoin != -1)
                            spielManager.getRunden().get(indexRoundToJoin).joinPLayer(p);
                        else
                            p.sendMessage("Leider konnte keine Runde gefunden werden");
                        break;
                    case "reload":
                        try {
                            userManager.loadOnlinePlayer();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "data":
                        p.sendMessage("Hallo");
                        userManager.getUsers().forEach(user -> {
                            p.sendMessage("-------" + user.getUuid() + "--------");
                            p.sendMessage("Spieler: " + user.getName());
                            p.sendMessage("Tode: " + user.getDeaths());
                            p.sendMessage("Kills: " + user.getKills());
                        });
                        break;
                }
            }
        }
        return false;
    }
}
