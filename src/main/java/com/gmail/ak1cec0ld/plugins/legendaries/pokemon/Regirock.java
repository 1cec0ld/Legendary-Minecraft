package com.gmail.ak1cec0ld.plugins.legendaries.pokemon;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import com.gmail.ak1cec0ld.plugins.legendaries.util.VelocityUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Regirock {

    private static double HEALTH = 10;
    private static double STRENGTH = 20;
    private static LivingEntity entity;
    private static boolean spawned;
    private static Random r = new Random();
    private static int schedulerID;


    public static boolean requirementsMet(Block b) {
        if(b.getMetadata("regirock").size() > 0 && b.getMetadata("regirock").get(0).asInt() > 10
                && !spawned){
            b.removeMetadata("regirock",Legendaries.instance());
            return true;
        }
        return false;
    }

    public static void spawn(Location loc) {
        spawned = true;
        spawnRegirock(EntityType.IRON_GOLEM,loc,HEALTH);
        schedulerID = Legendaries.instance().getServer().getScheduler().scheduleSyncRepeatingTask(Legendaries.instance(), Regirock::attack, 10L, 20L);
    }
    public static void die(){
        spawned = false;
        Legendaries.instance().getServer().getScheduler().cancelTask(schedulerID);

        //reward(entity.getLocation());
    }

    /*private static void reward(Location loc){

    }*/
    private static void attack(){
        int choice = r.nextInt(10);
        if(choice < 7){
            launch();
        }
    }
    private static void launch(){
        for(Entity eachEntity : entity.getNearbyEntities(20,20,20)){
            Entity thrown;
            if (eachEntity instanceof Player && ((Player)eachEntity).getGameMode().equals(GameMode.SURVIVAL)){
                thrown = entity.getWorld().spawn(entity.getLocation(),TNTPrimed.class);
                thrown.setVelocity(VelocityUtil.calculateVelocity(entity.getLocation().toVector(),eachEntity.getLocation().toVector(),2));
                ((TNTPrimed)thrown).setFuseTicks(30);
            }
        }
    }


    private static void spawnRegirock(EntityType eType, Location loc, double hp){
        entity = (LivingEntity)loc.getWorld().spawnEntity(loc, eType);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,9999,3,false,false));
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HEALTH);
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(STRENGTH);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1.00);
        entity.setHealth(hp);

        entity.setMetadata("legendary", new FixedMetadataValue(Legendaries.instance(), "mew"));
    }
}
