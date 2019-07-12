package com.gmail.ak1cec0ld.plugins.legendaries.listeners.entity;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.sinnoh.Darkrai;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.sinnoh.Dialga;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.kanto.Mew;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.hoenn.Regice;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.hoenn.Regirock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if(event.getEntity().hasMetadata("legendary")){
            event.getDrops().clear();
            String pokemon = event.getEntity().getMetadata("legendary").get(0).asString();
            switch(pokemon.toLowerCase()){
                case "dialga":
                    Dialga.die();
                    break;
                case "mew":
                    Mew.die();
                    break;
                case "regirock":
                    Regirock.die();
                    Legendaries.instance().getServer().dispatchCommand(Legendaries.instance().getServer().getConsoleSender(),
                        "fill -3283 51 1872 -3283 52 1873 minecraft:granite replace minecraft:air");
                    break;
                case "regice":
                    Regice.die();
                    Legendaries.instance().getServer().dispatchCommand(Legendaries.instance().getServer().getConsoleSender(),
                            "fill -4030 35 1167 -4030 36 1168 minecraft:granite replace minecraft:air");
                    break;
                case "darkrai":
                    Darkrai.die();
                    break;
                default:
                    break;
            }
        }
    }
}
