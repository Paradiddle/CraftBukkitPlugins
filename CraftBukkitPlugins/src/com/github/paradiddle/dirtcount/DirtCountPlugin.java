package com.github.paradiddle.dirtcount;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DirtCountPlugin extends JavaPlugin implements Listener
{
	private int dirtCount = 0;

	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		super.onEnable();
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		Block b = event.getBlock();
		if(b.getType() == Material.DIRT)
		{
			dirtCount++;
			event.getPlayer().sendMessage(dirtCount + " people think Paul is gay");
		}
	}
}
