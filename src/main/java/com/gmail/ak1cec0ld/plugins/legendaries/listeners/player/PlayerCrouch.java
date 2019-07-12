package com.gmail.ak1cec0ld.plugins.legendaries.listeners.player;

import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.sinnoh.Darkrai;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerCrouch implements Listener {


    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent event){
        if(event.getPlayer().isSneaking())return;
        Darkrai.spawn(event.getPlayer().getLocation().add(0,3,0));
    }

}
