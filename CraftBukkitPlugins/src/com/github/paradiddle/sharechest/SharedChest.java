package com.github.paradiddle.sharechest;

import org.bukkit.Location;

public class SharedChest
{
	private String name;
	private String creator;
	private String world;
	private Location location;
	private boolean locked = false;
		
	public SharedChest(String name, String world, String creator, Location location)
	{
		this.name = name;
		this.world = world;
		this.location = location;
		this.creator = creator;
	}
	
	public String getWorld()
	{
		return world;
	}
	
	public void lock()
	{
		locked = true;
	}
	
	public void unlock()
	{
		locked = false;
	}
	
	public boolean isLocked()
	{
		return locked;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getCreator()
	{
		return creator;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public boolean overlapping(Location loc)
	{
		return loc.getX() == location.getX() && loc.getY() == location.getY() && loc.getZ() == location.getZ();
	}
}
