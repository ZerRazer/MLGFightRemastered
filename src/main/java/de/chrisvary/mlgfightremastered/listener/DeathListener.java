package de.chrisvary.mlgfightremastered.listener;

import de.chrisvary.mlgfightremastered.Main;
import de.chrisvary.mlgfightremastered.spielmanager.SpielManager;
import de.chrisvary.mlgfightremastered.user.User;
import de.chrisvary.mlgfightremastered.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event ){
        Player p = event.getEntity().getPlayer();
        UserManager userManager = Main.getInstance().getUserManager();
        if(p != null){
            if(gameIsRunning(Main.getInstance().getSpielManager(), p)){
                int index = getUserIndex(p.getUniqueId());
                if(index == -1)
                    return;
                User user = userManager.getUsers().get(index);

                user.setDeaths(user.getDeaths() + 1);
                p.sendMessage("Du hast derzeit " + user.getDeaths() + " Deaths");

            }

        }
    }
    @EventHandler
    public void onKill(PlayerDeathEvent event){
        UserManager userManager = Main.getInstance().getUserManager();
        if(event.getEntity() instanceof Player){

            if(event.getEntity().getKiller() != null){
                Player p = event.getEntity().getKiller();
                if(gameIsRunning(Main.getInstance().getSpielManager(), p)){
                    int index = getUserIndex(p.getUniqueId());
                    if(index == -1)
                        return;
                    User user = userManager.getUsers().get(index);
                    user.setKills(user.getKills() + 1);
                    p.sendMessage("Deine aktuellen Kills betragen: " + user.getKills());
                }

            }

        }
    }
    private int getUserIndex(UUID uuid){
        UserManager userManager = Main.getInstance().getUserManager();
        for(int i = 0; i < userManager.getUsers().size(); i++){
            User user = userManager.getUsers().get(i);
            if(user.getUuid().equals(uuid)){
                return i;
            }
        }
        return -1;
    }
    private boolean gameIsRunning(SpielManager sm, Player p){
        if(sm.isPlayerInRunde(p)){
            int index = sm.getIndexWherePlayer(p);
            if(index == -1)
                return false;
            if(!sm.getRunden().get(index).isRunning())
                return true;
        }
        return false;
    }
}
