package de.joshiworld.bukkit.listener;

import de.joshiworld.bukkit.main.Paper;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class npcInteract implements Listener {
    @EventHandler
    public void onnpcInteract(NPCRightClickEvent e) {
        Player player = e.getClicker();
        switch (e.getNPC().getName()){
            case "Bauwelt":
                Paper.sendCustomData("Serverswitch",player,"bauwelt","");
                break;
            case "Farmwelt":
                Paper.sendCustomData("Serverswitch",player,"farmwelt","");
                break;
            case "Spawn":
                Paper.sendCustomData("Serverswitch",player,"lobby","");
                break;
        }
    }
}

