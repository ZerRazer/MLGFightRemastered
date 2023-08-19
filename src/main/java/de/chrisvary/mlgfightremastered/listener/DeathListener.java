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
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event ){
        Player p = event.getEntity().getPlayer();
        UserManager userManager = Main.getInstance().getUserManager();
        if(p != null){
            if(Main.getInstance().getSpielManager().gameIsRunning(p)){
                int index = getUserIndex(p.getUniqueId());
                if(index == -1)
                    return;
                User user = userManager.getUsers().get(index);
                user.setDeaths(user.getDeaths() + 1);
                p.sendMessage("Du hast derzeit " + user.getDeaths() + " Deaths");
            }

        }
        //changes value of killer kills
        onKill(event);
    }
    public void onKill(PlayerDeathEvent event){
        UserManager userManager = Main.getInstance().getUserManager();
        SpielManager spielManager = Main.getInstance().getSpielManager();
        Player p;
        if(event.getEntity() instanceof Player){
            Player player = event.getEntity().getPlayer();
            if(spielManager.gameIsRunning(event.getEntity().getPlayer())){
                if(spielManager.getSpiel(player).getPlayer1().equals(player)){
                    p = spielManager.getSpiel(player).getPlayer2();
                }
                else{
                    p = spielManager.getSpiel(player).getPlayer1();
                }
                if(spielManager.gameIsRunning(p)){
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
    @EventHandler
    public void onRespawnEvent(PlayerRespawnEvent event){
        Player p = event.getPlayer();

        SpielManager spielManager = Main.getInstance().getSpielManager();
        if(spielManager.gameIsRunning(p)){
            p.sendMessage("RESPAWNED");
            if(spielManager.getSpiel(p).getPlayer1().equals(p))
                event.setRespawnLocation(spielManager.getSpiel(p).getPlayer1Spawn());
            else
                event.setRespawnLocation(spielManager.getSpiel(p).getPlayer2Spawn());
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

}
