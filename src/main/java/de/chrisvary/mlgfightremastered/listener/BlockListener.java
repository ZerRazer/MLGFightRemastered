package de.chrisvary.mlgfightremastered.listener;

import de.chrisvary.mlgfightremastered.Main;
import de.chrisvary.mlgfightremastered.spielmanager.SpielManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;


public class BlockListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        SpielManager spielManager  = Main.getInstance().getSpielManager();
        if(spielManager.isPlayerInRunde(event.getPlayer())){
            eventCancel(spielManager, event);
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        SpielManager spielManager = Main.getInstance().getSpielManager();
        if(spielManager.isPlayerInRunde(event.getPlayer()))
            eventCancel(spielManager, event);
    }
    private <T extends BlockEvent>boolean eventCancel(SpielManager sm, T event){
        Player p;
        if(event instanceof BlockBreakEvent)
            p = ((BlockBreakEvent)event).getPlayer();
        else{
            p = ((BlockPlaceEvent)event).getPlayer();
        }
        if(sm.isPlayerInRunde(p)){
            int index = sm.getIndexWherePlayer(p);
            if(index == -1)
                return false;
            if(!sm.getRunden().get(index).isRunning())
                ((Cancellable) event).setCancelled(true);
            if(event instanceof BlockBreakEvent){
                for(Block block : sm.getSpiel(p).getBlocks()){
                    if(block.equals(((BlockBreakEvent)event).getBlock())){
                        ((BlockBreakEvent) event).setCancelled(false);
                        return true;
                    }
                }
            }

        }
        return false;
    }

}
