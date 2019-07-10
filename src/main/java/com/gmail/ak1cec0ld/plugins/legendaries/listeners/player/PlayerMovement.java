package com.gmail.ak1cec0ld.plugins.legendaries.listeners.player;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.Regice;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerMovement implements Listener {

    private static final Location regiceTrigger = new Location(Bukkit.getWorld("Japan"), -4031.0, 35, 1168.0);
    private static final double distanceFromTriggerMax = 1.25;
    private static final long millisToHoldStill = 1000*60*2;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if(!event.getTo().getWorld().getName().equals("Japan"))return;
        if(event.getFrom().getX() == event.getTo().getX() &&
            event.getFrom().getY() == event.getTo().getY() &&
            event.getFrom().getZ() == event.getTo().getZ())return;
        Player moved = event.getPlayer();
        if(event.getTo().distanceSquared(regiceTrigger) > distanceFromTriggerMax*distanceFromTriggerMax){
            untagPlayerWhoMovedTooFar(moved);
            return;
        }
        if(moved.hasMetadata("unmoving") && moved.getMetadata("unmoving").size() > 0){
            if(!taggedLongEnough(moved))return;
            Regice.spawn(new Location(moved.getWorld(), -3969,36, 1169));
            untagPlayerWhoMovedTooFar(moved);
        } else {
            applyTagToPlayerInZone(moved);
        }
    }
    private boolean taggedLongEnough(Player player){
        if(!player.hasMetadata("unmoving"))return false;
        //Legendaries.debug(System.currentTimeMillis() + " - " + player.getMetadata("unmoving").get(0).asLong() + " > " + millisToHoldStill);
        return (System.currentTimeMillis() - player.getMetadata("unmoving").get(0).asLong()) > millisToHoldStill;
    }
    private void applyTagToPlayerInZone(Player player){
        //Legendaries.debug("Applied in-zone tag to " + player.getName());
        player.setMetadata("unmoving", new FixedMetadataValue(Legendaries.instance(),System.currentTimeMillis()));
    }
    private void untagPlayerWhoMovedTooFar(Player player){
        if(!player.hasMetadata("unmoving"))return;
        //Legendaries.debug("Removed in-zone tag to " + player.getName());
        player.removeMetadata("unmoving", Legendaries.instance());
    }
}
