package com.github.paradiddle.testingutils;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExecutorSnowRemoval implements CommandExecutor
{
	private TestUtilities plugin;
	
	private static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
	
	public ExecutorSnowRemoval(TestUtilities plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage("Must be a player to execute this command.");
			return true;
		}
		
		Player p = (Player)sender;
		Location l = p.getLocation();
		Chunk c = l.getChunk();
		
		int total = 0;
		long time = System.currentTimeMillis();
		
		total += removeSnow(c);
		total += removeSnow(getRelativeChunk(c, NORTH));
		total += removeSnow(getRelativeChunk(c, SOUTH));
		total += removeSnow(getRelativeChunk(c, EAST));
		total += removeSnow(getRelativeChunk(c, WEST));

		total += removeSnow(getRelativeChunk(c, NORTH, EAST));
		total += removeSnow(getRelativeChunk(c, SOUTH, WEST));
		total += removeSnow(getRelativeChunk(c, EAST, SOUTH));
		total += removeSnow(getRelativeChunk(c, WEST, NORTH));
	
		long timeTaken = System.currentTimeMillis() - time;
		p.sendMessage("Successfully removed " + total + " blocks of snow. (" + timeTaken + " ms)");
		
		return true;
	}
	
	public int removeSnow(Chunk c)
	{
		Block b = null;
		int numSnowRemoved = 0;
		
		for(int x = 0; x <= 15; x++)
		{
			for(int y = 0; y <= 127; y++)
			{
				for(int z = 0; z <= 15; z++)
				{
					b = c.getBlock(z, y, z);
					if(b.getType() == Material.SNOW)
					{
						b.setType(Material.AIR);
						numSnowRemoved++;
					}
				}
			}
		}
		return numSnowRemoved;
	}
	
	public Chunk getRelativeChunk(Chunk c, int... dirs)
	{
		World w = c.getWorld();
		int xOffset = 0, zOffset = 0;
		for(int dir: dirs)
		{
			switch(dir)
			{
			case NORTH:
				zOffset += 16;
				break;
			case EAST:
				xOffset += 16;
				break;
			case SOUTH:
				zOffset -= 16;
				break;
			case WEST:
				xOffset -= 16;
				break;
			}
		}
		return w.getChunkAt(c.getX() + xOffset, c.getZ() + zOffset);	
	}
}
