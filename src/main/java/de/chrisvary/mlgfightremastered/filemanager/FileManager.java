package de.chrisvary.mlgfightremastered.filemanager;

import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private final YamlConfiguration config;
    private final File file;

    public FileManager(){
        //Folder file
        File dir = new File("./plugins/MLGfight/");

        if(!dir.exists()){
            dir.mkdirs();
        }

        this.file = new File(dir, "DatabaseConnection.yml");
        if(!this.file.exists()){
            try {
                this.file.createNewFile();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }

    public void save(){
        try{
            this.config.save(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
