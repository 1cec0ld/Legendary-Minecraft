package com.gmail.ak1cec0ld.plugins.legendaries.listeners.misc;

import com.gmail.ak1cec0ld.plugins.legendaries.Legendaries;
import com.gmail.ak1cec0ld.plugins.legendaries.pokemon.Regirock;
import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class StrengthCommand implements CommandExecutor {

    private static final double distanceFromTriggerMax = 1.25;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player))return false;
        Player sender = (Player)commandSender;
        if(!sender.getWorld().getName().equals("Japan"))return false;

        int unarmedLevel = ExperienceAPI.getLevel(sender, PrimarySkillType.UNARMED);
        if(unarmedLevel < 10){
            sender.sendMessage("Nothing happens... Do you even lift?");
            return true;
        }

        Location regirockTrigger = new Location(Bukkit.getWorld("Japan"),-3288, 50, 1877);
        if(regirockTrigger.distanceSquared(sender.getLocation()) > distanceFromTriggerMax*distanceFromTriggerMax){
            sender.sendMessage("Nothing happens... Was that a tremor in the ground?");
            return true;
        }

        if(!isConfirming(sender)){
            sender.sendMessage("You're about to exert yourself. This will cause you to lose some Unarmed Levels. Use Strength again if you are sure.");
            setConfirming(sender);
            return true;
        }
        stopConfirming(sender);
        removeLevels(sender);
        Regirock.spawn(new Location(sender.getWorld(),-3225.0,52,1873.0));

        return true;
    }

    private boolean isConfirming(Player player){
        return player.hasMetadata("confirming-strength");
    }

    private void setConfirming(Player player){
        player.setMetadata("confirming-strength", new FixedMetadataValue(Legendaries.instance(), true));
    }

    private void stopConfirming(Player player){
        player.removeMetadata("confirming-strength", Legendaries.instance());
    }

    private void removeLevels(Player sender){
        ExperienceAPI.addLevel(sender,"Unarmed",-10);
    }
}
