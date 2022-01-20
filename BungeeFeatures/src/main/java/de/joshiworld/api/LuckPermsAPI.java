package de.joshiworld.api;


import de.joshiworld.bukkit.main.Paper;
import de.joshiworld.bungee.main.Bungee;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.node.types.PrefixNode;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class LuckPermsAPI {
    private Bungee plugin;
    private Paper paplugin;
    private LuckPerms luckPerms;

    public LuckPermsAPI(Bungee plugin) {
        this.plugin = plugin;
        this.luckPerms=this.plugin.luckPerms;
    }
    public LuckPermsAPI(Paper plugin) {
        this.paplugin = plugin;
        this.luckPerms=this.paplugin.luckPerms;
    }
    // Get User
    public User getUser(String player) {
        return luckPerms.getUserManager().getUser(player);
    }
    public User getUser(UUID uuid) {
        return luckPerms.getUserManager().getUser(uuid);
    }

    // Get Group
    public Group getGroup(String player) {
        return luckPerms.getGroupManager().getGroup(getUser(player).getPrimaryGroup());
    }
    public Group getGroup(UUID uuid) {
        return luckPerms.getGroupManager().getGroup(getUser(uuid).getPrimaryGroup());
    }

    // Get Prefix
    public String getGroupPrefix(String player) {
        return getGroup(player).getNodes().stream().filter(NodeType.PREFIX::matches).map(NodeType.PREFIX::cast).map(PrefixNode::getMetaValue).distinct().toArray()[0].toString();
    }
    public String getGroupPrefix(UUID uuid) {
        return getGroup(uuid).getNodes().stream().filter(NodeType.PREFIX::matches).map(NodeType.PREFIX::cast).map(PrefixNode::getMetaValue).distinct().toArray()[0].toString();
    }

    // Get Group Permission
    public boolean hasPermissionGroup(String permission, String player) {
        Set<String> permissions = getGroup(player).getNodes(NodeType.PERMISSION).stream().map(PermissionNode::getPermission).collect(Collectors.toSet());
        if(permissions.contains(permission)) return true;
        else return false;
    }
    public boolean hasPermissionGroup(String permission, UUID uuid) {
        Set<String> permissions = getGroup(uuid).getNodes(NodeType.PERMISSION).stream().map(PermissionNode::getPermission).collect(Collectors.toSet());
        if(permissions.contains(permission)) return true;
        else return false;
    }

}