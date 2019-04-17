package com.gmail.ak1cec0ld.plugins.legendaries;


import com.gmail.ak1cec0ld.plugins.legendaries.listeners.EntityDeath;
import com.gmail.ak1cec0ld.plugins.legendaries.listeners.EntityTame;
import org.bukkit.plugin.java.JavaPlugin;

public class Legendaries extends JavaPlugin {
    private static Legendaries instance;

    @Override
    public void onEnable(){
        instance = this;
        new Listening();
        getServer().getPluginManager().registerEvents(new EntityDeath(),instance);
        getServer().getPluginManager().registerEvents(new EntityTame(), instance);
    }
    public static Legendaries instance(){
        return instance;
    }

}