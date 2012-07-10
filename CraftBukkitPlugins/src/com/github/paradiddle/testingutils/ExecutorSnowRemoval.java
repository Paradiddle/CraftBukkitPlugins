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
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Must be a player to execute this command.");
			return true;
		}

		Player p = (Player) sender;
		Location l = p.getLocation();
		Chunk c = l.getChunk();

		int total = 0;
		long time = System.currentTimeMillis();

		total += removeSnow(p, 200, 200);

		long timeTaken = System.currentTimeMillis() - time;
		p.sendMessage("Successfully removed " + total + " blocks of snow. (" + timeTaken + " ms)");

		return true;
	}

	public int removeSnow(Player p, int w, int h)
	{
		Block b = null;
		int numSnowRemoved = 0;
		int px = p.getLocation().getBlockX();
		int pz = p.getLocation().getBlockZ();

		for (int x = px - (w / 2); x <= px + (w / 2); x++)
		{
			for (int y = 0; y < 127; y++)
			{
				for (int z = pz - (h / 2); z <= pz + (h / 2); z++)
				{
					b = p.getWorld().getBlockAt(x, y, z);
					if (b.getType() == Material.SNOW)
					{
						b.setType(Material.AIR);
						numSnowRemoved++;
					}
				}
			}
		}
		return numSnowRemoved;
	}
}
