package com.gmail.ak1cec0ld.plugins.legendaries.pokemon.kanto;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import com.gmail.ak1cec0ld.plugins.legendaries.util.VelocityUtil;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Mew {
    private static double HEALTH = 10;
    private static double STRENGTH = 20;
    private static LivingEntity entity;
    private static boolean spawned;
    private static Random r = new Random();
    private static int schedulerID;
    //private static List<EntityType> potentialAir;
    //private static List<EntityType> potentialWater;

    public static void spawn(Location loc) {
        if(spawned)return;
        //fillPotentials();
        spawned = true;
        spawnMew(EntityType.PIG_ZOMBIE,loc,HEALTH);
        schedulerID = Legendaries.instance().getServer().getScheduler().scheduleSyncRepeatingTask(Legendaries.instance(), Mew::attack, 10L, 20L);
    }

    public static void die(){
        spawned = false;
        entity.remove();
        Legendaries.instance().getServer().getScheduler().cancelTask(schedulerID);
        reward(entity.getLocation());
    }

    private static void reward(Location loc){

    }
    private static void attack(){
        int choice = r.nextInt(10);
        if(choice < 7){
            //transform();
            launch();
        }
    }
    private static void launch(){
        for(Entity eachEntity : entity.getNearbyEntities(20,20,20)){
            Entity thrown;
            if (eachEntity instanceof Player){
                thrown = entity.getWorld().spawn(entity.getLocation(),TNTPrimed.class);
                thrown.setVelocity(VelocityUtil.calculateVelocity(entity.getLocation().toVector(),eachEntity.getLocation().toVector(),2));
                ((TNTPrimed)thrown).setFuseTicks(30);
            }
        }
    }

    /*private static void transform(){
        double hp = entity.getHealth();
        EntityType next = getEntityType();

        entity.remove();
        spawnMew(next,entity.getLocation(),hp);
    }*/
    private static void spawnMew(EntityType eType, Location loc, double hp){
        entity = (LivingEntity)loc.getWorld().spawnEntity(loc, eType);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,9999,3,false,false));
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HEALTH);
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(STRENGTH);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1.00);
        entity.setHealth(hp);
        if(entity instanceof PigZombie) ((PigZombie)entity).setAngry(true);
        if(entity instanceof Zombie) ((Zombie)entity).setBaby(true);
        entity.setMetadata("legendary", new FixedMetadataValue(Legendaries.instance(), "mew"));
    }
    /*private static void fillPotentials(){
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
    }*/
    /*private static EntityType getEntityType(){
        if(entity.getLocation().getBlock().getType().equals(Material.WATER)){
            return potentialWater.get(r.nextInt(potentialWater.size()));
        }
        return potentialAir.get(r.nextInt(potentialAir.size()));
    }*/
    /*private static void transform_riding_method(){
        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,99999,1,false,false),true);
        List<Entity> riders = entity.getPassengers();
        for(Entity each : riders){
            entity.setHealth(((LivingEntity)each).getHealth());
            each.remove();
        }
        int mob = r.nextInt(5);
        Entity rider;
        if(mob < 2){
            rider = entity.getWorld().spawnEntity(entity.getLocation(),EntityType.SKELETON);
        } else if(mob < 3) {
            rider = entity.getWorld().spawnEntity(entity.getLocation(), EntityType.GUARDIAN);
        } else if(mob < 4){
            rider = entity.getWorld().spawnEntity(entity.getLocation(),EntityType.GHAST);
        } else {
            rider = entity.getWorld().spawnEntity(entity.getLocation(),EntityType.ELDER_GUARDIAN);
        }
        ((LivingEntity)rider).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HEALTH);
        ((LivingEntity)rider).setHealth(entity.getHealth());
        entity.addPassenger(rider);
    }*/
}
