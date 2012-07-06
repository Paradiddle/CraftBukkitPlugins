package com.github.paradiddle.jmaze;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.paradiddle.jmaze.generators.DepthFirstSearch;
import com.github.paradiddle.jmaze.generators.RecursiveDivision;

public class ExecutorMaze implements CommandExecutor
{
	private MazePlugin plugin;

	public ExecutorMaze(MazePlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player p = null;
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Only players can execute this command.");
			return true;
		}

		p = (Player) sender;

		if (args.length != 2)
			return false;
		int w = 0, h = 0;
		try
		{
			w = Integer.parseInt(args[0]);
			h = Integer.parseInt(args[1]);
		} catch (NumberFormatException nfe)
		{
			return false;
		}
		sender.sendMessage("Creating maze of size " + w + " x " + h);
		Maze m = new Maze(w, h);
		DepthFirstSearch dfs = new DepthFirstSearch();
		
		dfs.generateMaze(m);

		Location l = p.getLocation();
		l = l.getBlock().getRelative(BlockFace.DOWN).getLocation();
		sender.sendMessage("Block X: " + l.getBlockX() + " Block Y: " + l.getBlockY() + " Block Z: " + l.getBlockZ());
		l.getBlock().setType(Material.AIR);
		World world = p.getWorld();
		int bx = l.getBlockX();
		int by = l.getBlockY();
		int bz = l.getBlockZ();
		generateLevel(world, bx, by, bz, m, Material.STONE, Material.STONE, Material.STONE, false);
		generateLevel(world, bx, by + 1, bz, m, Material.AIR, Material.STONE, Material.AIR, false);
		generateLevel(world, bx, by + 2, bz, m, Material.AIR, Material.GLASS, Material.AIR, false);
		generateLevel(world, bx, by + 3, bz, m, Material.GLASS, Material.GLASS, Material.AIR, false);

		generateLevel(world, bx, by + 4, bz, m, Material.AIR, Material.STONE, Material.AIR, false);
		generateLevel(world, bx, by + 5, bz, m, Material.AIR, Material.GLASS, Material.AIR, false);
		generateLevel(world, bx, by + 6, bz, m, Material.GLASS, Material.GLOWSTONE, Material.AIR, false);
		generateLevel(world, bx, by + 7, bz, m, Material.STONE, Material.STONE, Material.STONE, false);
		

		
		sender.sendMessage("Done");

		return true;
	}
	
	public void generateLevel(World world, int offsetX, int offsetY, int offsetZ, Maze m, Material emptyMaterial, Material wallMaterial, Material pathMaterial, boolean stoneBorder)
	{
		for (int i = 0; i < m.width(); i++)
		{
			for (int j = 0; j < m.height(); j++)
			{
				Block c = world.getBlockAt(i + offsetX, offsetY, j + offsetZ);
				if(stoneBorder)
				{
					if(i == 0 || j == 0 || i == m.width() - 1 || j == m.height() - 1)
					{
						c.setType(Material.STONE);
						continue;
					}
				}
				if(m.get(i, j) == 0)
					c.setType(emptyMaterial);
				if(m.get(i, j) == 1)
					c.setType(wallMaterial);
				if(m.get(i, j) == 2)
					c.setType(pathMaterial);
			}
		}
	}
}
