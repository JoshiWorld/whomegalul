package de.joshiworld.bukkit.listener;

import de.joshiworld.bukkit.main.Paper;
import de.joshiworld.sql.SQLGetter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class onPrejoin implements Listener {

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent e){
        SQLGetter data = Paper.getInstance().data;
        String uuid = e.getUniqueId().toString();
        String name = e.getName();
        data.addUser(uuid,name);
        Paper.getInstance().getLogger().info(uuid + name);
        if(data.isBanned(uuid)){
            final TextComponent Banreason = Component.text(data.getBanreason(uuid));
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED,Banreason);
        }
    }
}
