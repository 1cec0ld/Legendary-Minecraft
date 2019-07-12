package com.gmail.ak1cec0ld.plugins.legendaries.pokemon.hoenn;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import com.gmail.ak1cec0ld.plugins.legendaries.util.NearestUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Regice {

    private static final double HEALTH = 750;
    private static final double STRENGTH = 20;
    private static final double ARROW_DAMAGE = 30;
    private static final Random r = new Random();
    private static LivingEntity entity;
    private static boolean spawned;
    private static int schedulerID;

    public static void spawn(Location loc){
        if(spawned)return;
        spawned = true;
        spawnRegice(loc);
        schedulerID = Legendaries.instance().getServer().getScheduler().scheduleSyncRepeatingTask(Legendaries.instance(), Regice::attack, 30L, 20L);
    }
    public static void die(){
        spawned = false;
        entity.remove();
        Legendaries.instance().getServer().getScheduler().cancelTask(schedulerID);
        reward(entity.getLocation());
    }
    private static void reward(Location loc){
        loc.getWorld().dropItemNaturally(loc,new ItemStack(Material.SHULKER_SHELL,1));
    }
    private static void attack(){
        int choice = r.nextInt(10);
        if(choice < 7){
            trackingShot();
        }
    }
    private static void spawnRegice(Location loc){
        entity = (LivingEntity)loc.getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE);
        ((PigZombie)entity).setAngry(true);
        entity.setPersistent(true);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,9999,2,false,false));
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HEALTH);
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(STRENGTH);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(2.00);
        entity.setHealth(HEALTH);

        entity.setMetadata("legendary", new FixedMetadataValue(Legendaries.instance(), "regice"));
    }
    private static void trackingShot(){
        if(!entity.isValid())return;
        Arrow shot = entity.launchProjectile(Arrow.class);
        shot.setDamage(ARROW_DAMAGE);
        shot.addCustomEffect(new PotionEffect(PotionEffectType.SLOW,r.nextInt(20)+20, r.nextInt(3)+1,false, false),true);
        shot.setGravity(false);
        shot.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
        int x = Legendaries.instance().getServer().getScheduler().scheduleSyncRepeatingTask(Legendaries.instance(), () -> {
            LivingEntity nearest = NearestUtil.getNearestPlayer(shot,20);
            if(shot.isInBlock() || nearest == null || !shot.isValid()){
                Legendaries.instance().getServer().getScheduler().cancelTask(shot.getMetadata("scheduler").get(0).asInt());
                shot.remove();
            } else {
                shot.setVelocity(nearest.getLocation().add(0, 0.25, 0).toVector().subtract(shot.getLocation().toVector()).normalize().multiply(0.35));
            }
        }, 5L, 5L);
        shot.setMetadata("scheduler", new FixedMetadataValue(Legendaries.instance(), x));
    }

}
