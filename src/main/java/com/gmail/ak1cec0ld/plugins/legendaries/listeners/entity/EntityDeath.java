package com.gmail.ak1cec0ld.plugins.legendaries.listeners.entity;

import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.Dialga;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.Mew;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if(event.getEntity().hasMetadata("legendary")){
            String pokemon = event.getEntity().getMetadata("legendary").get(0).asString();
            switch(pokemon.toLowerCase()){
                case "dialga":
                    Dialga.die();
                    break;
                case "mew":
                    Mew.die();
                    break;
                default:
                    break;
            }
        }
    }
}
