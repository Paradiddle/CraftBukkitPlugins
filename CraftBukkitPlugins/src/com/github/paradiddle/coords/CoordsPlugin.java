package com.github.paradiddle.coords;

import org.bukkit.plugin.java.JavaPlugin;

public class CoordsPlugin extends JavaPlugin
{

	private ExecutorCoordsBroadcast coordsBroadcastExecutor = new ExecutorCoordsBroadcast(this);
	
	@Override
	public void onDisable()
	{
		// TODO Auto-generated method stub
		super.onDisable();
	}

	@Override
	public void onEnable()
	{
		getCommand("coords").setExecutor(coordsBroadcastExecutor);
		
		super.onEnable();
	}

}
