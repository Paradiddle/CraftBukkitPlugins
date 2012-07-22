package com.github.paradiddle.testingutils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExecutorShamwow implements CommandExecutor
{
	private TestUtilities plugin;

	private Block origin;
	private int count = 0;
	private int maxWaterRemoveCount = 1000;
	public ExecutorShamwow(TestUtilities plugin)
	{
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Only players can use this command.");
			return true;
		}

		Player p = (Player) sender;
		
		origin = Utilities.getLookingAtBlockIncludingWater(p);
		
		if(origin == null)
		{
			sender.sendMessage("Error finding block looking at.");
			return true;
		}
		
		count = 0;
		if (label.equals("shamwow"))
		{
			deleteWater(p, origin);
			p.sendMessage("Success! Cleared " + count + " blocks of water.");
			return true;
		}
		return true;
	}
	
	private void deleteWater(Player p, Block b)
	{
		if(count > maxWaterRemoveCount)
			return;
		if (b.getType() == Material.STATIONARY_WATER || b.getType() == Material.WATER)
		{
			b.setType(Material.AIR);
			count++;

			deleteWater(p, b.getRelative(BlockFace.SOUTH));
			deleteWater(p, b.getRelative(BlockFace.NORTH));
			deleteWater(p, b.getRelative(BlockFace.EAST));
			deleteWater(p, b.getRelative(BlockFace.WEST));
			deleteWater(p, b.getRelative(BlockFace.UP));
			deleteWater(p, b.getRelative(BlockFace.DOWN));
		}
	}
}
