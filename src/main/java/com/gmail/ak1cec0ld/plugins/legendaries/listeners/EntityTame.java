package com.gmail.ak1cec0ld.plugins.legendaries.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

public class EntityTame implements Listener {

    @EventHandler
    public void onEntityTame(EntityTameEvent event){
        if(event.getEntity().hasMetadata("legendary")){
            event.setCancelled(true);
        }
    }
}
