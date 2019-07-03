package com.gmail.ak1cec0ld.plugins.legendaries.pokemon;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import com.gmail.ak1cec0ld.plugins.legendaries.util.VelocityUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Regirock {

    private static final double HEALTH = 10;
    private static final double STRENGTH = 40;
    private static final Random r = new Random();
    private static LivingEntity entity;
    private static boolean spawned;
    private static int schedulerID;

    public static void spawn(Location loc) {
        if(spawned)return;
        spawned = true;
        spawnRegirock(EntityType.HUSK,loc,HEALTH);
        Legendaries.instance().getServer().dispatchCommand(Legendaries.instance().getServer().getConsoleSender(),
                "fill -3283 51 1872 -3283 52 1873 minecraft:air replace minecraft:granite");
        schedulerID = Legendaries.instance().getServer().getScheduler().scheduleSyncRepeatingTask(Legendaries.instance(), Regirock::attack, 15L, 20L);
    }
    public static void die(){
        spawned = false;
        Legendaries.instance().getServer().getScheduler().cancelTask(schedulerID);
        Legendaries.instance().getServer().dispatchCommand(Legendaries.instance().getServer().getConsoleSender(),
                "fill -3283 51 1872 -3283 52 1873 minecraft:granite replace minecraft:air");
        reward(entity.getLocation());
    }

    private static void reward(Location loc){

    }
    private static void attack(){
        int choice = r.nextInt(10);
        if(choice < 7){
            rockBlast();
        }
    }
    private static void rockBlast(){
        for(Entity eachEntity : entity.getNearbyEntities(20,20,20)){
            Entity thrown;
            if (eachEntity instanceof Player && ((Player)eachEntity).getGameMode().equals(GameMode.SURVIVAL)){
                thrown = entity.getWorld().spawn(entity.getLocation(),TNTPrimed.class);
                ((TNTPrimed)thrown).setFuseTicks(25);
                thrown.setVelocity(VelocityUtil.calculateVelocity(entity.getLocation().toVector(),eachEntity.getLocation().toVector(),1));
            }
        }
    }


    private static void spawnRegirock(EntityType eType, Location loc, double hp){
        entity = (LivingEntity)loc.getWorld().spawnEntity(loc, eType);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,9999,2,false,false));
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HEALTH);
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(STRENGTH);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(2.00);
        entity.setHealth(hp);

        entity.setMetadata("legendary", new FixedMetadataValue(Legendaries.instance(), "regirock"));
    }
}
