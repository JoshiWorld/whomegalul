package de.joshiworld.bungee.interfaces;

public class LocationInterface {
    public String world;
    public double x;
    public double y;
    public double z;
    public float pitch;
    public float yaw;

    public LocationInterface(String world, double x, double y, double z, float pitch, float yaw){
        this.world=world;
        this.x=x;
        this.y=y;
        this.z=z;
        this.pitch = pitch;
        this.yaw=yaw;
    }
}

