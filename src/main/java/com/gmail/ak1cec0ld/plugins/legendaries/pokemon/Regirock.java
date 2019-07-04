package com.gmail.ak1cec0ld.plugins.legendaries.pokemon;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import com.gmail.ak1cec0ld.plugins.legendaries.util.VelocityUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;

public class Regirock {

    private static final double HEALTH = 750;
    private static final double STRENGTH = 40;
    private static final Random r = new Random();
    private static LivingEntity entity;
    private static boolean spawned;
    private static int schedulerID;

    public static void spawn(Location loc) {
        if(spawned)return;
        spawned = true;
        spawnRegirock(loc);
        Legendaries.instance().getServer().dispatchCommand(Legendaries.instance().getServer().getConsoleSender(),
                "fill -3283 51 1872 -3283 52 1873 minecraft:air replace minecraft:granite");
    }
    public static void die(){
        spawned = false;
        Legendaries.instance().getServer().getScheduler().cancelTask(schedulerID);
        Legendaries.instance().getServer().dispatchCommand(Legendaries.instance().getServer().getConsoleSender(),
                "fill -3283 51 1872 -3283 52 1873 minecraft:granite replace minecraft:air");
        reward(entity.getLocation());
    }

    private static void reward(Location loc){
        loc.getWorld().dropItemNaturally(loc,new ItemStack(Material.SHULKER_SHELL,1));
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
                try {
                    thrown.setVelocity(VelocityUtil.calculateVelocity(entity.getLocation().toVector(), eachEntity.getLocation().toVector(), 1));
                } catch (IllegalArgumentException e){
                    thrown.setVelocity(new Vector(0,1,0));
                }
            }
        }
    }

    private static void spawnRegirock(Location loc){
        entity = (LivingEntity)loc.getWorld().spawnEntity(loc, EntityType.HUSK);
        entity.setPersistent(true);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,9999,2,false,false));
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HEALTH);
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(STRENGTH);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(2.00);
        entity.setHealth(Regirock.HEALTH);

        entity.setMetadata("legendary", new FixedMetadataValue(Legendaries.instance(), "regirock"));
    }
}
