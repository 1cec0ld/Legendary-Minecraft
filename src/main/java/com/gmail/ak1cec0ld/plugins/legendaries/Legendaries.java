package com.gmail.ak1cec0ld.plugins.legendaries;


import com.gmail.ak1cec0ld.plugins.legendaries.listeners.Listening;
import com.gmail.ak1cec0ld.plugins.legendaries.listeners.block.BlockRedstone;
import com.gmail.ak1cec0ld.plugins.legendaries.listeners.entity.EntityDeath;
import com.gmail.ak1cec0ld.plugins.legendaries.listeners.entity.EntityTame;
import org.bukkit.plugin.java.JavaPlugin;

public class Legendaries extends JavaPlugin {
    private static Legendaries instance;

    @Override
    public void onEnable(){
        instance = this;
        new Listening();
        getServer().getPluginManager().registerEvents(new EntityDeath(),instance);
        getServer().getPluginManager().registerEvents(new EntityTame(), instance);

        getServer().getPluginManager().registerEvents(new BlockRedstone(), instance);
    }
    public static Legendaries instance(){
        return instance;
    }

}