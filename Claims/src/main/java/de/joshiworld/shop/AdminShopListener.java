package de.joshiworld.shop;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import de.joshiworld.api.PlayerHead;
import de.joshiworld.main.Claims;
import de.joshiworld.misc.Document;
import de.joshiworld.sql.PlayerData;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;

/**
 *
 * @author JoshiWorld
 */
public class AdminShopListener implements Listener {
    private final Claims plugin;

    public AdminShopListener(Claims plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAdminShopOpen(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();

        if(!e.getRightClicked().getName().equalsIgnoreCase("Admin - Shop")) return;
        Stream<NPCRegistry> registryStream = StreamSupport.stream(CitizensAPI.getNPCRegistries().spliterator(), true);
        NPCRegistry registry = registryStream.filter(npc -> npc.isNPC(e.getRightClicked())).findFirst().get();

        NPC npc = registry.getNPC(e.getRightClicked());

        if(npc.getFullName().equalsIgnoreCase("Admin - Shop")) {
            npc.faceLocation(p.getLocation());
            Inventory inv = Bukkit.createInventory(null, 54, "§cAdmin - Shop");

            File file = new File("plugins/Claims/Adminshop/items.json");
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(AdminShopListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Document document = Document.loadDocument(file);

            for(int i = 0; i < 45; i++) {
                List list = document.getList(String.valueOf(i));

                if(list != null) {
                    JSONObject obj = (JSONObject) list.get(0);

                    ItemStack item = new ItemStack(Material.matchMaterial(String.valueOf(obj.get("Material"))));
                    ItemMeta meta = item.getItemMeta();

                    List<String> lore = new ArrayList<>();
                    lore.add("§7Buy: §e" + String.valueOf(obj.get("Buy")));
                    lore.add("§7Sell: §e" + String.valueOf(obj.get("Sell")));
                    meta.setLore(lore);

                    item.setItemMeta(meta);

                    inv.addItem(item);
                } else {
                    break;
                }
            }

            p.openInventory(inv);
        }
    }

    @EventHandler
    public void onAdminInvInteract(InventoryClickEvent e) {
        if(e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            Document document = Document.loadDocument(new File("plugins/Claims/Adminshop/items.json"));

            for(int i = 0; i < 45; i++) {
                List list = document.getList(String.valueOf(i));

                if(list != null) {
                    JSONObject obj = (JSONObject) list.get(0);

                    if(e.getView().getTitle().equalsIgnoreCase("§cAdmin - Shop") ||
                            e.getView().getTitle().equalsIgnoreCase("§c" + String.valueOf(obj.get("Material")).toUpperCase()) ||
                            e.getView().getTitle().equalsIgnoreCase("§aKaufen") ||
                            e.getView().getTitle().equalsIgnoreCase("§cVerkaufen")) {
                        e.setCancelled(true);

                        if(e.getSlotType() == InventoryType.SlotType.CONTAINER && e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR)) {
                            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aKaufen") &&
                                    e.getView().getTitle().equalsIgnoreCase("§c" + String.valueOf(obj.get("Material")).toUpperCase())) {
                                Inventory inv = Bukkit.createInventory(null, 9, "§aKaufen");

                                //<editor-fold defaultstate="collapsed" desc="item">
                                ItemStack item = new ItemStack(Material.matchMaterial(String.valueOf(obj.get("Material"))));
                                ItemMeta itemMeta = item.getItemMeta();
                                itemMeta.setDisplayName("§aKaufen");
                                List<String> buyList = new ArrayList<>();
                                int price = Integer.parseInt(String.valueOf(obj.get("Buy"))) * item.getAmount();
                                buyList.add("§7Preis: §e" + price);
                                itemMeta.setLore(buyList);
                                item.setItemMeta(itemMeta);
                                inv.setItem(4, item);
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="Minus">
                                inv.setItem(2, PlayerHead.getHeadByURL("§c-1",
                                        "http://textures.minecraft.net/texture/ee3d8225f8f5b98597df15fd92bf6959aefd4c5bec9914dd63caa0c329c7a06b",
                                        1));
                                inv.setItem(1, PlayerHead.getHeadByURL("§c-5",
                                        "http://textures.minecraft.net/texture/b23d7e7711fb79d96c5bbffab174466a6cfc2b7db023288301ea44b1ea013e7",
                                        5));
                                inv.setItem(0, PlayerHead.getHeadByURL("§c-10",
                                        "http://textures.minecraft.net/texture/b23d7e7711fb79d96c5bbffab174466a6cfc2b7db023288301ea44b1ea013e7",
                                        10));
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="Plus">
                                inv.setItem(6, PlayerHead.getHeadByURL("§a+1",
                                        "http://textures.minecraft.net/texture/a3ea6512713b7bb8fee377a98732378757f26df8434d0faf897adb7564",
                                        1));
                                inv.setItem(7, PlayerHead.getHeadByURL("§a+5",
                                        "http://textures.minecraft.net/texture/127e89165945c4943c6e6372fc8c2ee0dd916e84685872b27a9e3d2b7761df",
                                        5));
                                inv.setItem(8, PlayerHead.getHeadByURL("§a+10",
                                        "http://textures.minecraft.net/texture/127e89165945c4943c6e6372fc8c2ee0dd916e84685872b27a9e3d2b7761df",
                                        10));
                                //</editor-fold>



                                p.openInventory(inv);



                            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cVerkaufen") &&
                                    e.getView().getTitle().equalsIgnoreCase("§c" + String.valueOf(obj.get("Material")).toUpperCase())) {
                                Inventory inv = Bukkit.createInventory(null, 9, "§cVerkaufen");

                                //<editor-fold defaultstate="collapsed" desc="item">
                                ItemStack item = new ItemStack(Material.matchMaterial(String.valueOf(obj.get("Material"))));
                                ItemMeta itemMeta = item.getItemMeta();
                                itemMeta.setDisplayName("§cVerkaufen");
                                List<String> buyList = new ArrayList<>();
                                int price = Integer.parseInt(String.valueOf(obj.get("Sell"))) * item.getAmount();
                                buyList.add("§7Preis: §e" + price);
                                itemMeta.setLore(buyList);
                                item.setItemMeta(itemMeta);
                                inv.setItem(4, item);
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="Minus">
                                inv.setItem(2, PlayerHead.getHeadByURL("§c-1",
                                        "http://textures.minecraft.net/texture/ee3d8225f8f5b98597df15fd92bf6959aefd4c5bec9914dd63caa0c329c7a06b",
                                        1));
                                inv.setItem(1, PlayerHead.getHeadByURL("§c-5",
                                        "http://textures.minecraft.net/texture/b23d7e7711fb79d96c5bbffab174466a6cfc2b7db023288301ea44b1ea013e7",
                                        5));
                                inv.setItem(0, PlayerHead.getHeadByURL("§c-10",
                                        "http://textures.minecraft.net/texture/b23d7e7711fb79d96c5bbffab174466a6cfc2b7db023288301ea44b1ea013e7",
                                        10));
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="Plus">
                                inv.setItem(6, PlayerHead.getHeadByURL("§a+1",
                                        "http://textures.minecraft.net/texture/a3ea6512713b7bb8fee377a98732378757f26df8434d0faf897adb7564",
                                        1));
                                inv.setItem(7, PlayerHead.getHeadByURL("§a+5",
                                        "http://textures.minecraft.net/texture/127e89165945c4943c6e6372fc8c2ee0dd916e84685872b27a9e3d2b7761df",
                                        5));
                                inv.setItem(8, PlayerHead.getHeadByURL("§a+10",
                                        "http://textures.minecraft.net/texture/127e89165945c4943c6e6372fc8c2ee0dd916e84685872b27a9e3d2b7761df",
                                        10));
                                //</editor-fold>



                                p.openInventory(inv);



                            } else if(e.getView().getTitle().equalsIgnoreCase("§aKaufen")) {
                                //<editor-fold defaultstate="collapsed" desc="-">
                                if(e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§c-")) {
                                    if(e.getClickedInventory().getContents()[4].getType().getKey().getKey().equalsIgnoreCase(String.valueOf(obj.get("Material")))) {
                                        if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c-1")) {
                                            int amount = e.getClickedInventory().getContents()[4].getAmount();
                                            if((amount - 1) > 0) {
                                                e.getClickedInventory().getContents()[4].setAmount(amount - 1);

                                                ItemMeta meta = e.getClickedInventory().getContents()[4].getItemMeta();
                                                List lore = meta.getLore();
                                                lore.remove(0);

                                                int price = Integer.parseInt(String.valueOf(obj.get("Buy")));
                                                int newPrice = price * e.getClickedInventory().getContents()[4].getAmount();

                                                lore.add("§7Preis: §e" + String.valueOf(newPrice));
                                                meta.setLore(lore);
                                                e.getClickedInventory().getContents()[4].setItemMeta(meta);
                                            }
                                        } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c-5")) {
                                            int amount = e.getClickedInventory().getContents()[4].getAmount();
                                            if((amount - 5) > 0) {
                                                e.getClickedInventory().getContents()[4].setAmount(amount - 5);

                                                ItemMeta meta = e.getClickedInventory().getContents()[4].getItemMeta();
                                                List lore = meta.getLore();
                                                lore.remove(0);

                                                int price = Integer.parseInt(String.valueOf(obj.get("Buy")));
                                                int newPrice = price * e.getClickedInventory().getContents()[4].getAmount();

                                                lore.add("§7Preis: §e" + String.valueOf(newPrice));
                                                meta.setLore(lore);
                                                e.getClickedInventory().getContents()[4].setItemMeta(meta);
                                            }
                                        } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c-10")) {
                                            int amount = e.getClickedInventory().getContents()[4].getAmount();
                                            if((amount - 10) > 0) {
                                                e.getClickedInventory().getContents()[4].setAmount(amount - 10);

                                                ItemMeta meta = e.getClickedInventory().getContents()[4].getItemMeta();
                                                List lore = meta.getLore();
                                                lore.remove(0);

                                                int price = Integer.parseInt(String.valueOf(obj.get("Buy")));
                                                int newPrice = price * e.getClickedInventory().getContents()[4].getAmount();

                                                lore.add("§7Preis: §e" + String.valueOf(newPrice));
                                                meta.setLore(lore);
                                                e.getClickedInventory().getContents()[4].setItemMeta(meta);
                                            }
                                        }
                                    }

                                    //</editor-fold>

                                    //<editor-fold defaultstate="collapsed" desc="+">
                                } else if(e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a+")) {
                                    if(e.getClickedInventory().getContents()[4].getType().getKey().getKey().equalsIgnoreCase(String.valueOf(obj.get("Material")))) {
                                        if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a+1")) {
                                            int amount = e.getClickedInventory().getContents()[4].getAmount();

                                            if((amount + 1) <= 64) {
                                                e.getClickedInventory().getContents()[4].setAmount(amount + 1);

                                                ItemMeta meta = e.getClickedInventory().getContents()[4].getItemMeta();
                                                List lore = meta.getLore();
                                                lore.remove(0);

                                                int price = Integer.parseInt(String.valueOf(obj.get("Buy")));
                                                int newPrice = price * e.getClickedInventory().getContents()[4].getAmount();

                                                lore.add("§7Preis: §e" + String.valueOf(newPrice));
                                                meta.setLore(lore);
                                                e.getClickedInventory().getContents()[4].setItemMeta(meta);
                                                return;
                                            }
                                        } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a+5")) {
                                            int amount = e.getClickedInventory().getContents()[4].getAmount();
                                            if((amount + 5) <= 64) {
                                                e.getClickedInventory().getContents()[4].setAmount(amount + 5);

                                                ItemMeta meta = e.getClickedInventory().getContents()[4].getItemMeta();
                                                List lore = meta.getLore();
                                                lore.remove(0);

                                                int price = Integer.parseInt(String.valueOf(obj.get("Buy")));
                                                int newPrice = price * e.getClickedInventory().getContents()[4].getAmount();

                                                lore.add("§7Preis: §e" + String.valueOf(newPrice));
                                                meta.setLore(lore);
                                                e.getClickedInventory().getContents()[4].setItemMeta(meta);
                                            }
                                        } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a+10")) {
                                            int amount = e.getClickedInventory().getContents()[4].getAmount();
                                            if((amount + 10) <= 64) {
                                                e.getClickedInventory().getContents()[4].setAmount(amount + 10);

                                                ItemMeta meta = e.getClickedInventory().getContents()[4].getItemMeta();
                                                List lore = meta.getLore();
                                                lore.remove(0);

                                                int price = Integer.parseInt(String.valueOf(obj.get("Buy")));
                                                int newPrice = price * e.getClickedInventory().getContents()[4].getAmount();

                                                lore.add("§7Preis: §e" + String.valueOf(newPrice));
                                                meta.setLore(lore);
                                                e.getClickedInventory().getContents()[4].setItemMeta(meta);
                                            }
                                        }
                                    }
                                    //</editor-fold>
                                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aKaufen") &&
                                        e.getCurrentItem().getType().getKey().getKey().equalsIgnoreCase(String.valueOf(obj.get("Material")))) {
                                    String priceList = e.getCurrentItem().getItemMeta().getLore().get(0).substring(11);

                                    PlayerData playerData = new PlayerData(p.getName(), this.plugin);

                                    if((playerData.getMoney() - Integer.valueOf(priceList)) >= 0) {
                                        if(Arrays.asList(p.getInventory().getStorageContents()).contains(null)) {
                                            playerData.removeMoney(Integer.valueOf(priceList));
                                            p.getInventory().addItem(new ItemStack(Material.matchMaterial(String.valueOf(obj.get("Material"))), e.getCurrentItem().getAmount()));
                                            p.sendMessage("§aDu hast gerade §e" + e.getCurrentItem().getAmount() + " §aStück §c" +
                                                    String.valueOf(obj.get("Material")).toUpperCase() + " §agekauft");


                                        } else if(p.getInventory().contains(e.getCurrentItem().getType())) {
                                            for(ItemStack items : p.getInventory().getContents()) {
                                                if(items != null) {
                                                    if(items.getType().equals(e.getCurrentItem().getType())) {
                                                        if((items.getAmount() + e.getCurrentItem().getAmount()) <= 64) {
                                                            playerData.removeMoney(Integer.valueOf(priceList));
                                                            p.getInventory().addItem(new ItemStack(Material.matchMaterial(String.valueOf(obj.get("Material"))), e.getCurrentItem().getAmount()));
                                                            p.sendMessage("§aDu hast gerade §e" + e.getCurrentItem().getAmount() + " §aStück §c" +
                                                                    String.valueOf(obj.get("Material")).toUpperCase() + " §agekauft");
                                                            return;
                                                        }
                                                    }
                                                }
                                            }

                                            p.sendMessage("§cDu hast nicht genügen Platz in deinem Inventar");
                                        } else {
                                            p.sendMessage("§cDu hast nicht genügen Platz in deinem Inventar");
                                        }
                                    } else {
                                        p.sendMessage("§cDu hast nicht genügend §6Derps§c, um dieses Item in dieser Menge zu kaufen");
                                    }
                                }



                            } else if(e.getView().getTitle().equalsIgnoreCase("§cVerkaufen")) {
                                //<editor-fold defaultstate="collapsed" desc="-">
                                if(e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§c-")) {
                                    if(e.getClickedInventory().getContents()[4].getType().getKey().getKey().equalsIgnoreCase(String.valueOf(obj.get("Material")))) {
                                        if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c-1")) {
                                            int amount = e.getClickedInventory().getContents()[4].getAmount();
                                            if((amount - 1) > 0) {
                                                e.getClickedInventory().getContents()[4].setAmount(amount - 1);

                                                ItemMeta meta = e.getClickedInventory().getContents()[4].getItemMeta();
                                                List lore = meta.getLore();
                                                lore.remove(0);

                                                int price = Integer.parseInt(String.valueOf(obj.get("Sell")));
                                                int newPrice = price * e.getClickedInventory().getContents()[4].getAmount();

                                                lore.add("§7Preis: §e" + String.valueOf(newPrice));
                                                meta.setLore(lore);
                                                e.getClickedInventory().getContents()[4].setItemMeta(meta);
                                            }
                                        } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c-5")) {
                                            int amount = e.getClickedInventory().getContents()[4].getAmount();
                                            if((amount - 5) > 0) {
                                                e.getClickedInventory().getContents()[4].setAmount(amount - 5);

                                                ItemMeta meta = e.getClickedInventory().getContents()[4].getItemMeta();
                                                List lore = meta.getLore();
                                                lore.remove(0);

                                                int price = Integer.parseInt(String.valueOf(obj.get("Sell")));
                                                int newPrice = price * e.getClickedInventory().getContents()[4].getAmount();

                                                lore.add("§7Preis: §e" + String.valueOf(newPrice));
                                                meta.setLore(lore);
                                                e.getClickedInventory().getContents()[4].setItemMeta(meta);
                                            }
                                        } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c-10")) {
                                            int amount = e.getClickedInventory().getContents()[4].getAmount();
                                            if((amount - 10) > 0) {
                                                e.getClickedInventory().getContents()[4].setAmount(amount - 10);

                                                ItemMeta meta = e.getClickedInventory().getContents()[4].getItemMeta();
                                                List lore = meta.getLore();
                                                lore.remove(0);

                                                int price = Integer.parseInt(String.valueOf(obj.get("Sell")));
                                                int newPrice = price * e.getClickedInventory().getContents()[4].getAmount();

                                                lore.add("§7Preis: §e" + String.valueOf(newPrice));
                                                meta.setLore(lore);
                                                e.getClickedInventory().getContents()[4].setItemMeta(meta);
                                            }
                                        }
                                    }
                                    //</editor-fold>

                                    //<editor-fold defaultstate="collapsed" desc="+">
                                } else if(e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a+")) {
                                    if(e.getClickedInventory().getContents()[4].getType().getKey().getKey().equalsIgnoreCase(String.valueOf(obj.get("Material")))) {
                                        if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a+1")) {
                                            int amount = e.getClickedInventory().getContents()[4].getAmount();

                                            if((amount + 1) <= 64) {
                                                e.getClickedInventory().getContents()[4].setAmount(amount + 1);

                                                ItemMeta meta = e.getClickedInventory().getContents()[4].getItemMeta();
                                                List lore = meta.getLore();
                                                lore.remove(0);

                                                int price = Integer.parseInt(String.valueOf(obj.get("Sell")));
                                                int newPrice = price * e.getClickedInventory().getContents()[4].getAmount();

                                                lore.add("§7Preis: §e" + String.valueOf(newPrice));
                                                meta.setLore(lore);
                                                e.getClickedInventory().getContents()[4].setItemMeta(meta);
                                                return;
                                            }
                                        } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a+5")) {
                                            int amount = e.getClickedInventory().getContents()[4].getAmount();
                                            if((amount + 5) <= 64) {
                                                e.getClickedInventory().getContents()[4].setAmount(amount + 5);

                                                ItemMeta meta = e.getClickedInventory().getContents()[4].getItemMeta();
                                                List lore = meta.getLore();
                                                lore.remove(0);

                                                int price = Integer.parseInt(String.valueOf(obj.get("Sell")));
                                                int newPrice = price * e.getClickedInventory().getContents()[4].getAmount();

                                                lore.add("§7Preis: §e" + String.valueOf(newPrice));
                                                meta.setLore(lore);
                                                e.getClickedInventory().getContents()[4].setItemMeta(meta);
                                            }
                                        } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a+10")) {
                                            int amount = e.getClickedInventory().getContents()[4].getAmount();
                                            if((amount + 10) <= 64) {
                                                e.getClickedInventory().getContents()[4].setAmount(amount + 10);

                                                ItemMeta meta = e.getClickedInventory().getContents()[4].getItemMeta();
                                                List lore = meta.getLore();
                                                lore.remove(0);

                                                int price = Integer.parseInt(String.valueOf(obj.get("Sell")));
                                                int newPrice = price * e.getClickedInventory().getContents()[4].getAmount();

                                                lore.add("§7Preis: §e" + String.valueOf(newPrice));
                                                meta.setLore(lore);
                                                e.getClickedInventory().getContents()[4].setItemMeta(meta);
                                            }
                                        }
                                    }
                                    //</editor-fold>
                                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cVerkaufen") &&
                                        e.getCurrentItem().getType().getKey().getKey().equalsIgnoreCase(String.valueOf(obj.get("Material")))) {
                                    if(p.getInventory().contains(Material.matchMaterial(String.valueOf(obj.get("Material"))))) {
                                        int amount = e.getCurrentItem().getAmount();

                                        for(ItemStack item : p.getInventory().getContents()) {
                                            if(item != null) {
                                                if(item.getType().getKey().getKey().equalsIgnoreCase(String.valueOf(obj.get("Material")))) {
                                                    if((item.getAmount() - amount) == 0) {
                                                        item.setAmount(0);
                                                        break;
                                                    } else if((item.getAmount() - amount) <= 0) {
                                                        amount -= item.getAmount();

                                                        int itemStackAmount = 0;
                                                        for(ItemStack itemStack : p.getInventory().getContents()) {
                                                            if(itemStack != null) {
                                                                if(itemStack.getType().getKey().getKey().equalsIgnoreCase(String.valueOf(obj.get("Material")))) {
                                                                    itemStackAmount += itemStack.getAmount();
                                                                }
                                                            }
                                                        }

                                                        if((itemStackAmount - amount) <= 0) {
                                                            p.sendMessage("§cDu hast das Item in dieser Menge nicht in deinem Inventar");
                                                            return;
                                                        } else {
                                                            item.setAmount(0);
                                                        }
                                                    } else {
                                                        item.setAmount(item.getAmount() - amount);
                                                        break;
                                                    }
                                                }
                                            }
                                        }

                                        String priceList = e.getCurrentItem().getItemMeta().getLore().get(0).substring(11);
                                        PlayerData playerData = new PlayerData(p.getName(), this.plugin);
                                        playerData.addMoney(Integer.parseInt(priceList));

                                        p.sendMessage("§aDu hast gerade §e" + e.getCurrentItem().getAmount() + " §aStück §c" +
                                                String.valueOf(obj.get("Material")).toUpperCase() + " §averkauft");
                                    } else {
                                        p.sendMessage("§cDu hast das Item in dieser Menge nicht in deinem Inventar");
                                    }
                                }



                            } else if(obj.containsValue(String.valueOf(e.getCurrentItem().getType().getKey().getKey()))) {
                                Inventory inv = Bukkit.createInventory(null, 9, "§c" + e.getCurrentItem().getType().getKey().getKey().toUpperCase());

                                ItemStack buy = new ItemStack(Material.GREEN_WOOL);
                                ItemMeta buyMeta = buy.getItemMeta();
                                buyMeta.setDisplayName("§aKaufen");
                                List<String> buyList = new ArrayList<>();
                                buyList.add("§7Preis: §e" + String.valueOf(obj.get("Buy")));
                                buyMeta.setLore(buyList);
                                buy.setItemMeta(buyMeta);
                                inv.setItem(2, buy);

                                ItemStack sell = new ItemStack(Material.RED_WOOL);
                                ItemMeta sellMeta = sell.getItemMeta();
                                sellMeta.setDisplayName("§cVerkaufen");
                                List<String> sellList = new ArrayList<>();
                                sellList.add("§7Preis: §e" + String.valueOf(obj.get("Sell")));
                                sellMeta.setLore(sellList);
                                sell.setItemMeta(sellMeta);
                                inv.setItem(6, sell);

                                p.openInventory(inv);
                            }
                        }
                    }
                } else {
                    break;
                }
            }
        }
    }

}
