package de.chrisvary.mlgfightremastered.user;

import org.bukkit.entity.Player;

import java.util.UUID;

public class User {
    private UUID uuid;
    private String name;
    private int kills;
    private int deaths;

    public User(UUID uuid, String name, int kills, int deaths){
        this.uuid = uuid;
        this.name = name;
        this.kills = kills;
        this.deaths = deaths;

    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }
}
