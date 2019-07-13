package com.gmail.ak1cec0ld.plugins.legendaries.pokemon.sinnoh;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import com.gmail.ak1cec0ld.plugins.legendaries.util.NearestUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Darkrai {


    private static final double HEALTH = 200;
    private static final double STRENGTH = 20;
    private static final Random r = new Random();
    private static LivingEntity entity;
    private static boolean spawned;
    private static int schedulerID;
    private static Set<Entity> cloudEntities;

    public static void spawn(Location loc){
        if(spawned)return;
        spawned = true;
        spawnDarkrai(loc);
        schedulerID = Legendaries.instance().getServer().getScheduler().scheduleSyncRepeatingTask(Legendaries.instance(), Darkrai::attack, 30L, 20L);
    }
    public static void die(){
        spawned = false;
        entity.remove();
        for (Entity each : cloudEntities){
            each.remove();
        }
        Legendaries.instance().getServer().getScheduler().cancelTask(schedulerID);
        reward(entity.getLocation());
    }
    private static void reward(Location loc){
        loc.getWorld().dropItemNaturally(loc,new ItemStack(Material.SHULKER_SHELL,1));
    }
    private static void attack(){
        int choice = r.nextInt(10);
        if(choice < 7){

        }
        relocate();
    }
    private static void spawnDarkrai(Location loc){
        entity = (LivingEntity)loc.getWorld().spawnEntity(loc, EntityType.GUARDIAN);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1, false, false));
        entity.setSilent(false);
        entity.setGravity(false);
        entity.setPersistent(true);
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HEALTH);
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(STRENGTH);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(2.00);
        entity.setHealth(HEALTH);

        entity.setMetadata("legendary", new FixedMetadataValue(Legendaries.instance(), "darkrai"));

        createCloud();
    }

    private static void createCloud(){
        AreaEffectCloud cloud;
        cloudEntities = new HashSet<>();
        for(int i = 0; i < 10; i++){
            cloud = (AreaEffectCloud) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.AREA_EFFECT_CLOUD);
            cloud.addCustomEffect(new PotionEffect(PotionEffectType.HARM,1,3), true);
            cloud.setColor(Color.BLACK);
            cloud.setRadiusOnUse(0);
            cloud.setRadius(.1f);
            cloud.setDuration(99999999);
            cloud.setSource(entity);
            cloud.setMetadata("legendary", new FixedMetadataValue(Legendaries.instance(), "darkrai_cloud"));

            cloudEntities.add(cloud);
        }
        for(Entity each : cloudEntities){
            int x = Legendaries.instance().getServer().getScheduler().scheduleSyncRepeatingTask(Legendaries.instance(), () -> {
                if(!each.isValid() || !entity.isValid()){
                    Legendaries.instance().getServer().getScheduler().cancelTask(each.getMetadata("scheduler").get(0).asInt());
                    each.remove();
                } else {
                    each.teleport(entity);
                }
            }, 5L, 5L);
            each.setMetadata("scheduler", new FixedMetadataValue(Legendaries.instance(), x));
        }
    }
    private static void relocate(){
        LivingEntity target = NearestUtil.getNearestPlayer(entity,20);
        if(target == null)return;
        if(entity.getLocation().distanceSquared(target.getLocation()) <= 5*5)return;
        //Legendaries.debug(entity.getLocation().distanceSquared(target.getLocation())+ "" );
        Vector target_pointing_to_entity = entity.getLocation().toVector().subtract(target.getLocation().add(0,1,0).toVector());
        Vector edgeOfSphere = target_pointing_to_entity.normalize().multiply(5).add(target.getLocation().toVector());
        //Legendaries.debug("edgeOfSphere coords: " + edgeOfSphere.toString());
        Vector self_pointing_to_edge = edgeOfSphere.subtract(entity.getLocation().toVector()).normalize().multiply(.05);
        entity.setVelocity(self_pointing_to_edge);
    }
}
