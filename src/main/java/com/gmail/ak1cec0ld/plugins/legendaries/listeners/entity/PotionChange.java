package com.gmail.ak1cec0ld.plugins.legendaries.listeners.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

public class PotionChange implements Listener {

    @EventHandler
    public void onPotionUpdate(EntityPotionEffectEvent event){
        if(!event.getEntity().hasMetadata("legendary"))return;
        if(event.getNewEffect().getType().equals(PotionEffectType.SPEED))return;
        event.setCancelled(true);
    }
}
