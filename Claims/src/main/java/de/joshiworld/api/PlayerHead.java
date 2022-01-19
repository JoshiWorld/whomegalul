package de.joshiworld.api;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

/**
 *
 * @author JoshiWorld
 */
public class PlayerHead {
    
    public static ItemStack getHeadByURL(String displayName, String url, int amount) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setDisplayName(displayName);
        
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encodeToString(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes()).getBytes();
        profile.getProperties().add(new ProfileProperty("textures", new String(encodedData)));

        meta.setPlayerProfile(profile);
        item.setItemMeta(meta);
        return item;
    }

}
