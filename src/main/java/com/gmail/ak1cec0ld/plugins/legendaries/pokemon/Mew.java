package com.gmail.ak1cec0ld.plugins.legendaries.pokemon;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.metadata.FixedMetadataValue;

public class Mew {
    private static double HEALTH = 100;
    private static LivingEntity entity;

    public static boolean requirementsMet() {
        Block b = new Location(Bukkit.getWorld("Japan"),75, 65, 418).getBlock();
        if(b.getMetadata("mew").get(0).asInt() > 10){
            return true;
        }
        return false;
    }

    public static void spawn(Location loc) {
        entity = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE);
        if(entity instanceof Zombie){
            ((Zombie)entity).setBaby(true);
        }
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HEALTH);
        entity.setHealth(HEALTH);
        entity.setMetadata("legendary", new FixedMetadataValue(Legendaries.instance(), "mew"));
    }

    public static void die(){

    }

    private static void reward(Location loc){

    }


}
