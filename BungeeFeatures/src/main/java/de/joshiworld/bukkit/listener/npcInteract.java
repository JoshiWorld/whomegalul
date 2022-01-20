package de.joshiworld.bukkit.listener;

import de.joshiworld.bukkit.main.Paper;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class npcInteract implements Listener {
    @EventHandler
    public void onnpcInteract(NPCRightClickEvent e) {
        if((e.getNPC().data().get("Teleport")==null))return;
        Player p = e.getClicker();
        String server = e.getNPC().data().get("Server");
        Paper.sendCustomData("Serverswitch",p,server,"");
    }
}

