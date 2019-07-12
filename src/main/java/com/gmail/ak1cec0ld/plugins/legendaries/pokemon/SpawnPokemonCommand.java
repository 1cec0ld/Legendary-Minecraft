package com.gmail.ak1cec0ld.plugins.legendaries.pokemon;

import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.hoenn.Regice;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.hoenn.Regirock;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.kanto.Mew;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.sinnoh.Darkrai;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.sinnoh.Dialga;
import com.google.common.collect.ImmutableList;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;


public class SpawnPokemonCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if(!sender.hasPermission("legendary.spawn.all"))return false;
        if(!(sender instanceof Player))return false;
        Player player = (Player)sender;
        switch(args.length){
            case 0:
                sender.sendMessage("You need to specify a pokemon. Tabcomplete.");
                break;
            case 1:
                Location where = player.getLocation().add(0,3,0);
                String compareToNames = args[0].toLowerCase();
                switch (compareToNames){
                    case "mew":
                        Mew.spawn(where);
                        break;
                    case "dialga":
                        Dialga.spawn(where);
                        break;
                    case "regice":
                        Regice.spawn(where);
                        break;
                    case "regirock":
                        Regirock.spawn(where);
                        break;
                    case "darkrai":
                        Darkrai.spawn(where);
                        break;
                    default:
                        return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1:
                List<String> createdPokemon = ImmutableList.of(
                    "mew",
                    "dialga",
                    "regice",
                    "regirock",
                    "darkrai"
                );
                return createdPokemon;
            default:
                return ImmutableList.of();
        }
    }
}
