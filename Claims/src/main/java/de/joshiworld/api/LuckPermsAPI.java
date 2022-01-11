package de.joshiworld.api;

import de.joshiworld.main.Claims;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;

import java.util.UUID;

public class LuckPermsAPI {
    private Claims plugin;

    public LuckPermsAPI(Claims plugin) {
        this.plugin = plugin;
    }

    // Get User
    public User getUser(String player) {
        return this.plugin.getLuckperms().getUserManager().getUser(player);
    }
    public User getUser(UUID uuid) {
        return this.plugin.getLuckperms().getUserManager().getUser(uuid);
    }

    // Get Group
    public Group getGroup(String player) {
        return this.plugin.getLuckperms().getGroupManager().getGroup(getUser(player).getPrimaryGroup());
    }
    public Group getGroup(UUID uuid) {
        return this.plugin.getLuckperms().getGroupManager().getGroup(getUser(uuid).getPrimaryGroup());
    }

    // Get Prefix
    public String getGroupPrefix(String player) {
        return getGroup(player).getNodes().stream().filter(NodeType.PREFIX::matches).map(NodeType.PREFIX::cast).map(PrefixNode::getMetaValue).distinct().toArray()[0].toString();
    }
    public String getGroupPrefix(UUID uuid) {
        return getGroup(uuid).getNodes().stream().filter(NodeType.PREFIX::matches).map(NodeType.PREFIX::cast).map(PrefixNode::getMetaValue).distinct().toArray()[0].toString();
    }

}
