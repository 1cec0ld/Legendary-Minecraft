package com.gmail.ak1cec0ld.plugins.legendaries.pokemon;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Random;

public class Dialga{
    private static final long ONLINE_MINUTES_REQUIRED = 60*6L;
    private static final double HEALTH = 750.0;
    private static LivingEntity entity;
    private static int schedulerID;
    private static boolean spawned = false;
    private static long lastSpawned;
    private static Random r = new Random();

    public static boolean requirementsMet() {
        if(lastSpawned > System.currentTimeMillis() - (1000 * 60 * ONLINE_MINUTES_REQUIRED))return false;
        if(spawned)return false;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getLastPlayed() <= System.currentTimeMillis() - (1000 * 60 * ONLINE_MINUTES_REQUIRED)
                && p.getLocation().distance(new Location(p.getWorld(),1104,197,-3641)) < 10) {
                return true;
            }
        }
        return false;
    }

    public static void spawn(Location loc) {
        spawned = true;
        lastSpawned = System.currentTimeMillis();
        entity = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HEALTH);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1.0);
        entity.setHealth(HEALTH);
        entity.setMetadata("legendary", new FixedMetadataValue(Legendaries.instance(), "dialga"));
        schedulerID = Legendaries.instance().getServer().getScheduler().scheduleSyncRepeatingTask(Legendaries.instance(), Dialga::attack, 0L, 110L);
    }

    public static void die(){
        Legendaries.instance().getServer().getScheduler().cancelTask(schedulerID);
        spawned = false;
        //reward(entity.getLocation());
    }

    /*private static void reward(Location loc){

    }*/

    private static void attack() {
        int choice = r.nextInt(10);
        if(choice < 7){
            roarOfTime(entity.getLocation());
        /*} else if(choice < 8){
            dracoMeteor(entity.getLocation());*/
        } else {
            futureSight(entity.getLocation());
        }
    }

    private static void roarOfTime(Location location) {
        double dmg = 5+r.nextDouble()*10;
        for (Entity e : entity.getNearbyEntities(50.0,50.0,50.0)) {
            if(e instanceof Player) {
                reduceAllDurabilities((Player)e);
                reduceAllPotionEffectDurations((Player)e);
                ((Player)e).playSound(location, Sound.ENTITY_HORSE_ANGRY, 100f, 100f);
                ((Player)e).playSound(location, Sound.ENTITY_HORSE_BREATHE, 100f, 100f);

            }
            if(e instanceof LivingEntity){
                if(!e.hasMetadata("legendary")) {
                    ((LivingEntity) e).damage(dmg, entity);
                }
            }
        }
    }
    private static void reduceAllDurabilities(Player p){
        for (ItemStack item : p.getInventory().getContents()) {
            if (item != null && item.hasItemMeta() && item.getItemMeta() instanceof Damageable) {
                ItemMeta meta = item.getItemMeta();
                ((Damageable) meta).setDamage(((Damageable) meta).getDamage() + 100+r.nextInt(50));
                item.setItemMeta(meta);
                if(((Damageable)meta).getDamage()>=item.getType().getMaxDurability()){
                    ((Damageable) meta).setDamage(0);
                    item.setItemMeta(meta);
                    item.setAmount(item.getAmount()-1);
                }
            }
        }
    }
    private static void reduceAllPotionEffectDurations(Player p){
        int duration;
        int amplifier;
        for(PotionEffectType each : PotionEffectType.values()){
            if(p.hasPotionEffect(each)){
                duration = p.getPotionEffect(each).getDuration();
                amplifier = p.getPotionEffect(each).getAmplifier();
                p.removePotionEffect(each);
                p.addPotionEffect(new PotionEffect(each,duration/2, amplifier),true);
            }
        }
    }/*
    private static void dracoMeteor(Location location){
        final World world = location.getWorld();
        for(int x = -30; x <= 30; x += 15){
            for(int z = -30; z <= 30; z += 10){
                world.spawn(new Location(world, location.getBlockX()+x,world.getHighestBlockYAt(location)+5,location.getBlockZ()+z), TNTPrimed.class);
            }
        }
    }*/
    private static void futureSight(Location location){
        HashMap<Entity,Location> previousLocs = new HashMap<>();
        for(Entity each : entity.getNearbyEntities(30.0,30.0,30.0)){
            previousLocs.put(each,each.getLocation());
        }
        Legendaries.instance().getServer().getScheduler().runTaskLater(Legendaries.instance(), () -> {
            for(Entity each : previousLocs.keySet()){
                each.teleport(previousLocs.get(each));
                previousLocs.remove(each);
            }
        },100L);
    }
}
