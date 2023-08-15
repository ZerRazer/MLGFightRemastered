package de.chrisvary.mlgfightremastered.filemanager;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileManager {
    private YamlConfiguration config;
    private File file;

    public FileManager(){
        File file = new File(".\");
    }
}
