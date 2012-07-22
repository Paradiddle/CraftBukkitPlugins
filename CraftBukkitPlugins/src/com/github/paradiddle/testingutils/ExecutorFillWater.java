package com.github.paradiddle.testingutils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExecutorFillWater implements CommandExecutor
{
	private TestUtilities plugin;
	private int count;
	private int maxWaterAddCount = 1000;
	private int maxHeight;
	private Block origin;
	
	public ExecutorFillWater(TestUtilities plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage("Only players can use this command.");
			return true;
		}
		
		Player p = (Player)sender;
		
		if(args.length != 1)
		{
			return false;
		}
		
		int height;
		try
		{
			height = Integer.parseInt(args[0]);
		} catch(NumberFormatException nfe)
		{
			return false;
		}

		origin = Utilities.getLookingAtAirIncludingWater(p);
		
		maxHeight = origin.getY() + height;
		count = 0;
		
		fillWater(p, origin);
		p.sendMessage("Success! Filled " + count + " blocks of water.");
		
		return true;
	}
	
	private void fillWater(Player p, Block b)
	{
		if(count > maxWaterAddCount)
			return;
		if(b.getY() >= maxHeight)
			return;
		
		if(b.getType() == Material.WATER)
		{
			b.setType(Material.STATIONARY_WATER);
		}
		
		if (b.getType() == Material.AIR)
		{
			b.setType(Material.STATIONARY_WATER);
			count++;
		}
		else
		{
			return;
		}
		
		if (b.getY() <= maxHeight)
		{
			fillWater(p, b.getRelative(BlockFace.SOUTH));
			fillWater(p, b.getRelative(BlockFace.NORTH));
			fillWater(p, b.getRelative(BlockFace.EAST));
			fillWater(p, b.getRelative(BlockFace.WEST));			
		}
		
		if (b.getY() < maxHeight)
		{
			fillWater(p, b.getRelative(BlockFace.UP));
			fillWater(p, b.getRelative(BlockFace.DOWN));
		}
	}

}
