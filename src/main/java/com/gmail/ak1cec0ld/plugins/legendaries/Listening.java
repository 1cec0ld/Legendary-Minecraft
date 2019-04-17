package com.gmail.ak1cec0ld.plugins.legendaries;

import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.Dialga;
import org.bukkit.Bukkit;
import org.bukkit.Location;

class Listening {

    Listening(){
        Legendaries.instance().getServer().getScheduler().runTaskTimerAsynchronously(Legendaries.instance(), this::listen,0L,200L);
    }

    private void listen(){
        if(Dialga.requirementsMet()) {
            Dialga.spawn(new Location(Bukkit.getWorld("Japan"),1104, 197.1, -3639));
        }
    }
}
