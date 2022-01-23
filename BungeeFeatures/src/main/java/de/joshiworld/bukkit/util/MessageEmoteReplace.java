package de.joshiworld.bukkit.util;

import de.joshiworld.bukkit.main.Paper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageEmoteReplace {
    public Paper plugin;
    Map<String,String>  emoteList;
    public MessageEmoteReplace(Paper plugin) {
        this.plugin = plugin;
        emoteList = plugin.getEmoteList();
    }
    public Map<String,String> create(){
        return Stream.of(new String[][] {
                { "anele", "\uE001" },
                { "badman", "\uE002" },
                { "bedge", "\uE003" },
                { "bruh", "\uE004" },
                { "clueless", "\uE005" },
                { "copium", "\uE006" },
                { "dankhug", "\uE007" },
                { "despair", "\uE008" },
                { "flushed", "\uE009" },
                { "gachigasm", "\uE00a" },
                { "hmmm", "\uE00b" },
                { "maaan", "\uE00c" },
                { "monkaw", "\uE00d" },
                { "okayge", "\uE00e" },
                { "okayman", "\uE00f" },
                { "pag", "\uE010" },
                { "pain", "\uE011" },
                { "pausechamp", "\uE012" },
                { "peepowow", "\uE013" },
                { "pepw", "\uE014" },
                { "pogo", "\uE015" },
                { "sadge", "\uE016" },
                { "shrugman", "\uE017" },
                { "susge", "\uE018" },
                { "weirdge", "\uE019"},
                { "weirdman", "\uE01a"},
                { "wicked", "\uE01b"},
                { "wokege", "\uE01c"},
                { "zulul", "\uE01d"},
                { "kekw", "\uE01e"},
                { "poglin", "\uE01f"},
                { "omegalul", "\uE020"},
                { "admin", "\uE021"},
                { "streamer", "\uE022"}
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    }
    public String replaceEmotes(String message){
        String tempMessage = message.toLowerCase();
        List<String> test = emoteList.keySet().stream().filter(tempMessage::contains).collect(Collectors.toList());
        for (String tempE : test) {
            message = message.replaceAll("(?i)"+tempE, ChatColor.WHITE+emoteList.get(tempE)+ChatColor.RESET);
        }
        return message;
    }
    public Component getEmotes(String message){
        String tempMessage = message.toLowerCase();
        List<String> test = emoteList.keySet().stream().filter(tempMessage::contains).collect(Collectors.toList());
        for (String tempE : test) {
            message += test + " ";
        }
        return Component.text(message).color(TextColor.color(255,255,255));
    }
}
