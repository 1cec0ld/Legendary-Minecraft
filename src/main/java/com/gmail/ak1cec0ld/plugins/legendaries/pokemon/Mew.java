package com.gmail.ak1cec0ld.plugins.legendaries.pokemon;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mew {
    private static double HEALTH = 750;
    private static double STRENGTH = 20;
    private static LivingEntity entity;
    private static boolean spawned;
    private static Random r = new Random();
    private static int schedulerID;
    private static List<EntityType> potentialAir;
    private static List<EntityType> potentialWater;

    public static boolean requirementsMet() {
        Block b = new Location(Bukkit.getWorld("Japan"),17, 65, 418).getBlock();
        if(b.getMetadata("mew").get(0).asInt() > 10
        && !spawned){
            b.removeMetadata("mew",Legendaries.instance());
            return true;
        }
        return false;
    }

    public static void spawn(Location loc) {
        fillPotentials();
        spawned = true;
        entity = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE);
        ((Zombie)entity).setBaby(true);
        ((PigZombie)entity).setAngry(true);
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HEALTH);
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(STRENGTH);
        entity.setHealth(HEALTH);
        entity.setMetadata("legendary", new FixedMetadataValue(Legendaries.instance(), "mew"));
        schedulerID = Legendaries.instance().getServer().getScheduler().scheduleSyncRepeatingTask(Legendaries.instance(), Mew::attack, 100L, 200L);
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
            transform();
        }
    }
    private static void transform(){
        double hp = entity.getHealth();
        EntityType next = getEntityType();

        entity.remove();
        entity = (LivingEntity)entity.getWorld().spawnEntity(entity.getLocation(), next);
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HEALTH);
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(STRENGTH);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1.00);
        entity.setHealth(hp);
        if(entity instanceof PigZombie) ((PigZombie)entity).setAngry(true);
        if(entity instanceof Zombie) ((Zombie)entity).setBaby(false);
        if(entity instanceof Creeper) ((Creeper)entity).setPowered(true);
        if(entity instanceof Creeper) ((Creeper)entity).setMaxFuseTicks(20);
        if(entity instanceof Creeper) ((Creeper)entity).setExplosionRadius(15);
        entity.setMetadata("legendary", new FixedMetadataValue(Legendaries.instance(), "mew"));
    }
    private static void fillPotentials(){
        potentialAir = new ArrayList<>();
        potentialWater = new ArrayList<>();
        potentialAir.add(EntityType.BLAZE);
        potentialAir.add(EntityType.CREEPER);
        potentialWater.add(EntityType.DROWNED);
        potentialWater.add(EntityType.ELDER_GUARDIAN);
        potentialAir.add(EntityType.ENDERMITE);
        potentialAir.add(EntityType.EVOKER);
        potentialAir.add(EntityType.GHAST);
        potentialWater.add(EntityType.GUARDIAN);
        potentialAir.add(EntityType.HUSK);
        potentialAir.add(EntityType.ILLUSIONER);
        potentialAir.add(EntityType.PIG_ZOMBIE);
        potentialAir.add(EntityType.SILVERFISH);
        potentialAir.add(EntityType.SKELETON);
        potentialAir.add(EntityType.STRAY);
        potentialAir.add(EntityType.VEX);
        potentialAir.add(EntityType.VINDICATOR);
        potentialAir.add(EntityType.WITCH);
        potentialAir.add(EntityType.WITHER_SKELETON);
        potentialAir.add(EntityType.ZOMBIE);
    }
    private static EntityType getEntityType(){
        if(entity.getLocation().getBlock().getType().equals(Material.WATER)){
            return potentialWater.get(r.nextInt(potentialWater.size()));
        }
        return potentialAir.get(r.nextInt(potentialAir.size()));
    }
}
