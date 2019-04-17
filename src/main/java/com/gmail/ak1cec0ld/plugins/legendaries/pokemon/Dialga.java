package com.gmail.ak1cec0ld.plugins.legendaries.pokemon;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Random;

public class Dialga{
    private static final long ONLINE_MINUTES_REQUIRED = 6;
    private static final double HEALTH = 500.0;
    private static LivingEntity entity;
    private static int schedulerID;
    private static boolean spawned = false;
    private static long lastSpawned;
    private static Random r = new Random();

    public static boolean requirementsMet() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getLastPlayed() <= System.currentTimeMillis() - (1000 * 60 * ONLINE_MINUTES_REQUIRED)
                && lastSpawned <= System.currentTimeMillis() - (1000 * 60 * ONLINE_MINUTES_REQUIRED)
                && p.getLocation().distance(new Location(p.getWorld(),1104,197,-3641)) < 10
                && !spawned) {
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
        entity.setHealth(HEALTH);
        entity.setMetadata("legendary_dialga", new FixedMetadataValue(Legendaries.instance(), true));
        schedulerID = Legendaries.instance().getServer().getScheduler().scheduleSyncRepeatingTask(Legendaries.instance(), Dialga::attack, 0L, 100L);
    }

    public static void die(){
        Legendaries.instance().getServer().getScheduler().cancelTask(schedulerID);
        spawned = false;
    }

    private static void attack() {
        int choice = r.nextInt(2);
        switch(choice){
            case 0:
                roarOfTime(entity.getLocation());
                break;
            case 1:
                Bukkit.getLogger().info("not roar of time");
                break;
        }
    }

    private static void roarOfTime(Location location) {
        double dmg = 5+r.nextDouble()*10;
        for (Entity e : entity.getNearbyEntities(50.0,50.0,50.0)) {
            if(e instanceof Player) {
                Player p = (Player) e;
                p.playSound(location, Sound.ENTITY_HORSE_ANGRY, 100f, 100f);
                p.playSound(location, Sound.ENTITY_HORSE_BREATHE, 100f, 100f);
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
                if((lastSpawned = 1) == 1 ){

                }
            }
            if(e instanceof LivingEntity){
                ((LivingEntity)e).damage(dmg,entity);
            }
        }
    }
}
