package com.gmail.ak1cec0ld.plugins.legendaries.listeners.block;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.Mew;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class BlockRedstone implements Listener {

    @EventHandler
    public void onRedstoneUpdate(BlockRedstoneEvent event){
        if(event.getBlock().getLocation().getBlockX()!=75)return;
        if(event.getBlock().getLocation().getBlockY()!=65)return;
        if(event.getBlock().getLocation().getBlockZ()!=418)return;
        if(event.getNewCurrent() > event.getOldCurrent())return; //once per button press
        Legendaries.instance().getLogger().info("Detected Mew's BlockRedstoneEvent!");
        if(!event.getBlock().hasMetadata("mew")){
            event.getBlock().setMetadata("mew", new FixedMetadataValue(Legendaries.instance(), 0));
        }
        int count = event.getBlock().getMetadata("mew").get(0).asInt();
        event.getBlock().setMetadata("mew", new FixedMetadataValue(Legendaries.instance(), count + 1));
        if(Mew.requirementsMet()){
            Mew.spawn(event.getBlock().getRelative(0,4,0).getLocation());
        }
    }

}
