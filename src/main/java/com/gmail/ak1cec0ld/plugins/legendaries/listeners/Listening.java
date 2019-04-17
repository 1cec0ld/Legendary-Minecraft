package com.gmail.ak1cec0ld.plugins.legendaries.listeners;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.Dialga;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Listening {

    public Listening(){
        Legendaries.instance().getServer().getScheduler().runTaskTimerAsynchronously(Legendaries.instance(), this::listen,0L,200L);
    }

    private void listen(){
        if(Dialga.requirementsMet()) {
            Dialga.spawn(new Location(Bukkit.getWorld("Japan"),1104, 197.1, -3639));
        }
    }
}
