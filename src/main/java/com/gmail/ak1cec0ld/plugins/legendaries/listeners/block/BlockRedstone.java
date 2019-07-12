package com.gmail.ak1cec0ld.plugins.legendaries.listeners.block;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.kanto.Mew;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockRedstone implements Listener {

    @EventHandler
    public void onRedstoneUpdate(BlockRedstoneEvent event){
        //checkForMew(event);
    }
    private void checkForMew(BlockRedstoneEvent event){
        if(event.getBlock().getLocation().getBlockX() != 17)return;
        if(event.getBlock().getLocation().getBlockY() != 65)return;
        if(event.getBlock().getLocation().getBlockZ() != 418)return;
        if(event.getNewCurrent() < event.getOldCurrent())return; //once per button press
        if(!event.getBlock().hasMetadata("mew")){
            event.getBlock().setMetadata("mew", new FixedMetadataValue(Legendaries.instance(), 0));
        }
        int count = event.getBlock().getMetadata("mew").get(0).asInt();
        event.getBlock().setMetadata("mew", new FixedMetadataValue(Legendaries.instance(), count + 1));
        Block b = event.getBlock();

        if(b.getMetadata("mew").size() > 0 && b.getMetadata("mew").get(0).asInt() > 10){
            b.removeMetadata("mew",Legendaries.instance());
            Mew.spawn(event.getBlock().getRelative(1,4,2).getLocation());
        }
    }
}
