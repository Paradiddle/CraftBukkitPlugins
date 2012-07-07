package com.github.paradiddle.testingutils;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class TestUtilities extends JavaPlugin
{
	
	ExecutorBlockInformation binfoExecutor = new ExecutorBlockInformation(this);
	ExecutorListWorlds worldlistExecutor = new ExecutorListWorlds(this);
	ExecutorFillFloor fillFloorExecutor = new ExecutorFillFloor(this);
	
	@Override
	public void onEnable()
	{
		getCommand("binfo").setExecutor(binfoExecutor);
		getCommand("worlds").setExecutor(worldlistExecutor);
		getCommand("floor").setExecutor(fillFloorExecutor);
		getServer().getPluginManager().registerEvents(binfoExecutor, this);
		
		super.onEnable();
	}
	
	public void log(Level level, String message)
	{
		getLogger().log(level, message);
	}
}
