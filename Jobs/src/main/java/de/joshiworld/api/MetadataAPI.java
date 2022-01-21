package de.joshiworld.api;

import de.joshiworld.main.Jobs;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class MetadataAPI {
    private static Jobs plugin;

    public static boolean hasMetadata(Block block, String metadata) {
        return block.hasMetadata(metadata);
    }

    public static void setMetadata(Block block, String metadata) {
        block.setMetadata(metadata, new FixedMetadataValue(plugin, metadata));
    }

    public static boolean metadataCheck(Block block, String metadata) {
        if(hasMetadata(block, metadata)) return true;

        setMetadata(block, metadata);
        return false;
    }

}
