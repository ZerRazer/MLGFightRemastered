package de.chrisvary.mlgfightremastered.commands;

import de.chrisvary.mlgfightremastered.Main;
import de.chrisvary.mlgfightremastered.user.UserManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Mlgfight implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(sender instanceof Player){
            Player p = (Player)sender;
            p.sendMessage("Hallo");
            UserManager userManager = Main.getInstance().getUserManager();
            if(args.length == 1){
                switch(args[0]){
                    case "start":
                        break;
                    case "stop":
                        break;
                    case "reload":

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
