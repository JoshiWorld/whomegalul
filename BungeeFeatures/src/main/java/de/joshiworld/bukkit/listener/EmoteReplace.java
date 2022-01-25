package de.joshiworld.bukkit.listener;

import de.joshiworld.bukkit.main.Paper;
import de.joshiworld.bukkit.util.MessageEmoteReplace;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;


public class EmoteReplace implements Listener {
    public Paper plugin = Paper.plugin;

    @EventHandler
    public void onSignChange(SignChangeEvent event){
        String[] line = event.getLines();
        for(int i=0; i<line.length;i++){
           event.line(i, Component.text(new MessageEmoteReplace(plugin).replaceEmotes(line[i])));

        }
    }
}
