package com.gmail.ak1cec0ld.plugins.legendaries.listeners.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {

    @EventHandler
    public void onEntityTakeDamage(EntityDamageEvent event){
        if(event.getEntity().hasMetadata("legendary") ||
          (event.getEntity().getVehicle()!=null && event.getEntity().getVehicle().hasMetadata("legendary")))cancelEffects(event);
    }
    private void cancelEffects(EntityDamageEvent event){
        if(event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) ||
           event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK) ||
           event.getCause().equals(EntityDamageEvent.DamageCause.DRYOUT)  ||
           event.getCause().equals(EntityDamageEvent.DamageCause.LAVA) ||
           event.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION) ||
           event.getCause().equals(EntityDamageEvent.DamageCause.DROWNING)){
            event.setCancelled(true);
        }
    }
}
