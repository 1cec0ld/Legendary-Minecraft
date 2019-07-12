package com.gmail.ak1cec0ld.plugins.legendaries;

import com.gmail.ak1cec0ld.plugins.legendaries.listeners.Listening;
import com.gmail.ak1cec0ld.plugins.legendaries.listeners.block.BlockRedstone;
import com.gmail.ak1cec0ld.plugins.legendaries.listeners.entity.EntityDamage;
import com.gmail.ak1cec0ld.plugins.legendaries.listeners.entity.EntityDeath;
import com.gmail.ak1cec0ld.plugins.legendaries.listeners.entity.EntityTame;
import com.gmail.ak1cec0ld.plugins.legendaries.listeners.entity.PotionChange;
import com.gmail.ak1cec0ld.plugins.legendaries.listeners.misc.StrengthCommand;
import com.gmail.ak1cec0ld.plugins.legendaries.listeners.player.PlayerCrouch;
import com.gmail.ak1cec0ld.plugins.legendaries.listeners.player.PlayerMovement;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.SpawnPokemonCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Legendaries extends JavaPlugin {
    private static Legendaries instance;

    @Override
    public void onEnable(){
        instance = this;
        new Listening();
        getServer().getPluginManager().registerEvents(new EntityDeath(),instance);
        getServer().getPluginManager().registerEvents(new EntityTame(), instance);
        getServer().getPluginManager().registerEvents(new EntityDamage(), instance);
        getServer().getPluginManager().registerEvents(new PotionChange(), instance);

        getServer().getPluginManager().registerEvents(new BlockRedstone(), instance);
        getServer().getPluginManager().registerEvents(new PlayerMovement(), instance);
        //getServer().getPluginManager().registerEvents(new PlayerCrouch(), instance);

        getServer().getPluginCommand("strength").setExecutor(new StrengthCommand());



        getServer().getPluginCommand("spawnpokemon").setExecutor(new SpawnPokemonCommand());
    }
    public static Legendaries instance(){
        return instance;
    }
    public static void debug(String input){
        instance().getLogger().info("[Legendaries-debug] " + input);
    }
}