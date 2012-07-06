package com.github.paradiddle.jmaze;

import org.bukkit.plugin.java.JavaPlugin;

public class MazePlugin extends JavaPlugin
{
	private ExecutorMaze mazeExecutor;
	
	@Override
	public void onEnable()
	{
		mazeExecutor = new ExecutorMaze(this);
		
		getCommand("maze").setExecutor(mazeExecutor);
		
		super.onEnable();
	}
	
	@Override
	public void onDisable()
	{
		super.onDisable();
	}
}
