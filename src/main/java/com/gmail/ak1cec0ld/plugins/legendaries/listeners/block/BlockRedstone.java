package com.gmail.ak1cec0ld.plugins.legendaries.listeners.block;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.Mew;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockRedstone implements Listener {

    @EventHandler
    public void onRedstoneUpdate(BlockRedstoneEvent event){
        //Legendaries.instance().getLogger().info("BlockRedstone " + event.getBlock().getLocation().getBlockX() + " "+ event.getBlock().getLocation().getBlockY() + " "+ event.getBlock().getLocation().getBlockZ() + " " + event.getNewCurrent() + " " + event.getOldCurrent() );
        if(event.getBlock().getLocation().getBlockX()!=17)return;
        if(event.getBlock().getLocation().getBlockY()!=65)return;
        if(event.getBlock().getLocation().getBlockZ()!=418)return;
        if(event.getNewCurrent() < event.getOldCurrent())return; //once per button press
        //Legendaries.instance().getLogger().info("Detected Mew's BlockRedstoneEvent!");
        if(!event.getBlock().hasMetadata("mew")){
            event.getBlock().setMetadata("mew", new FixedMetadataValue(Legendaries.instance(), 0));
        }
        int count = event.getBlock().getMetadata("mew").get(0).asInt();
        event.getBlock().setMetadata("mew", new FixedMetadataValue(Legendaries.instance(), count + 1));
        if(Mew.requirementsMet()){
            Mew.spawn(event.getBlock().getRelative(1,4,2).getLocation());
        }
    }

}
