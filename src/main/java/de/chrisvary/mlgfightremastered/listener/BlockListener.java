package de.chrisvary.mlgfightremastered.listener;

import de.chrisvary.mlgfightremastered.Main;
import de.chrisvary.mlgfightremastered.spielmanager.SpielManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.w3c.dom.events.Event;


public class BlockListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        SpielManager sm  = Main.getInstance().getSpielManager();
        if(!sm.getRunden().isEmpty()){
            if(event != null){
                if(gameIsRunning(sm, event.getPlayer())){
                    event.setCancelled(true);
                }
            }

        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        SpielManager sm  = Main.getInstance().getSpielManager();
        if(!sm.getRunden().isEmpty()){
            if(event != null){
                if(gameIsRunning(sm, event.getPlayer())){
                    event.setCancelled(true);
                }
            }

        }
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
