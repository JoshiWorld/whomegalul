package de.joshiworld.bungee.commands;

import com.sun.org.apache.xpath.internal.operations.Bool;
import de.joshiworld.bukkit.util.MessageEmoteReplace;
import de.joshiworld.bungee.main.Bungee;
import net.luckperms.api.messenger.Messenger;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import javax.swing.*;
import java.util.Map;

public class emotes extends Command {
    public emotes() {
        super("emotes");
    }
    Bungee plugin = Bungee.getInstance();
    Map<String,String> emoteList = new MessageEmoteReplace(plugin).emoteList;
    Object[][] arr =
            emoteList.entrySet().stream()
                    .map(e -> new Object[]{e.getKey(), e.getValue()})
                    .toArray(Object[][]::new);

    @Override
    public void execute(CommandSender sender, String[] args) {
        int page;
        String pageString= ((args.length == 0) ? "1" : args[0]);
        try{
            page = Integer.parseInt(pageString);
        }catch(NumberFormatException e){
            page = 1;
        }
        createEmotelist(page,arr,sender);



    }

    public void createEmotelist(int site,Object[][] list,CommandSender sender){
        final int pagesize=8;
        final int maxpage = list.length/pagesize;
        if((site*pagesize>list.length)||(site==0)){
            sender.sendMessage(new TextComponent(ChatColor.RED+"Not a valid Page"));
            return;
        }

        int i = site*pagesize;
        sender.sendMessage(new TextComponent(ChatColor.GOLD+"--- Emote List-Page: "+site+"/"+maxpage+" ---"));
        while((i<site*pagesize+pagesize)&&(i<list.length)){
                sender.sendMessage(new TextComponent(ChatColor.GOLD + list[i][0].toString()+" -> "+ ChatColor.RESET +list[i][1].toString()));
                i++;
        }
            sender.sendMessage(new TextComponent(ChatColor.GOLD+"---/emotes <Pagenumber> ---"));
    }
}
