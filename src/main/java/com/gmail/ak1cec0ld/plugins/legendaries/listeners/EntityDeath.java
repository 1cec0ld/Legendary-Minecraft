package com.gmail.ak1cec0ld.plugins.legendaries.listeners;

import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.Dialga;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if(event.getEntity().hasMetadata("legendary_dialga")){
            Dialga.die();
        }
    }
}
