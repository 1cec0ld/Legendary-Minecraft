package com.gmail.ak1cec0ld.plugins.legendaries.listeners.entity;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {

    @EventHandler
    public void onEntityTakeDamage(EntityDamageEvent event){
        if(event.getEntity().hasMetadata("legendary") ||
          (event.getEntity().getVehicle()!=null && event.getEntity().getVehicle().hasMetadata("legendary"))){
            cancelEffects(event);
        }
        makeThrownTNTLessDestructive(event);
    }

    private void makeThrownTNTLessDestructive(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player)return;
        for(Entity nearby : event.getEntity().getNearbyEntities(20,20,20)){
            if(nearby.hasMetadata("legendary")){
                event.setCancelled(true);
            }
        }
    }

    private void cancelEffects(EntityDamageEvent event){
        if(event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) ||
           event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK) ||
           event.getCause().equals(EntityDamageEvent.DamageCause.DRYOUT)  ||
           event.getCause().equals(EntityDamageEvent.DamageCause.LAVA) ||
           event.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION) ||
           event.getCause().equals(EntityDamageEvent.DamageCause.DROWNING) ||
           event.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) ||
           event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)){
            event.setCancelled(true);
        }
    }
}
