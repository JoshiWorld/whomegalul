package de.joshiworld.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.joshiworld.claims.ChunkBlocks;
import de.joshiworld.claims.ClaimBlocks;
import de.joshiworld.commands.ClaimCommand;
import de.joshiworld.commands.VanishCommand;
import de.joshiworld.listeners.PlayerChatListener;
import de.joshiworld.listeners.PlayerJoinListener;
import de.joshiworld.listeners.PlayerQuitListener;
import de.joshiworld.misc.Document;
import de.joshiworld.shop.AdminShopListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitStuff {
    public Claims plugin;
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();

    public InitStuff(Claims plugin) {
        this.plugin = plugin;
    }

    // Init All
    public void init() {
        this.plugin.initLuckPerms();
        createDirectories();
        createFiles();
        initCommands();
        initListeners();
    }


    // Init Commands
    private void initCommands() {
        addCommand("claim", new ClaimCommand(this.plugin));
        addCommand("vanish", new VanishCommand(this.plugin));
    }


    // Init Listeners
    private void initListeners() {
        addListener(new PlayerJoinListener(this.plugin));
        addListener(new PlayerQuitListener(this.plugin));
        addListener(new PlayerChatListener(this.plugin));
        addListener(new ChunkBlocks(this.plugin));
        addListener(new ClaimBlocks(this.plugin));
        addListener(new AdminShopListener(this.plugin));
    }



    // Add Listener
    private void addListener(Listener listener) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(listener, this.plugin);
    }

    // Add Command
    private void addCommand(String command, CommandExecutor commandExecutor) {
        this.plugin.getCommand(command).setExecutor(commandExecutor);
    }

    // Create Directories
    private void createDirectories() {
        File file = new File("plugins/Claims/Adminshop/");
        if(!file.exists()) {
            file.mkdirs();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="createFiles">
    private void createFiles() {
        File items = new File("plugins/Claims/Adminshop/items.json");
        if(!items.exists()) {
            try {
                items.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Claims.class.getName()).log(Level.SEVERE, null, ex);
            }

            Document document = Document.loadDocument(items);
            List<JSONObject> list = new ArrayList<>();
            JSONObject obj = new JSONObject();

            obj.put("Material", "stick");
            obj.put("Buy", "200");
            obj.put("Sell", "100");

            list.add(gson.fromJson(gson.toJson(obj), JSONObject.class));
            document.append("0", list);
            document.save(items);

            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "CREATED FIRST ITEM IN ADMINSHOP items.json");
        }
    }
    //</editor-fold>

}
