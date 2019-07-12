package com.gmail.ak1cec0ld.plugins.legendaries.util;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;

public class NearestUtil {

    public static LivingEntity getNearestPlayer(Entity source, int radius){
        double minDistance = (radius+2)*(radius+2);
        LivingEntity nearest = null;
        for(Entity each : source.getNearbyEntities(radius,radius,radius)){
            if(each instanceof Player || each instanceof Tameable){
                if(each.getLocation().distanceSquared(source.getLocation()) < minDistance){
                    nearest = (LivingEntity)each;
                }
            }
        }
        return nearest;
    }
}
