package com.github.paradiddle.testingutils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExecutorFillFloor implements CommandExecutor
{
	private TestUtilities plugin;

	private int count = 0;
	private int max_count = 128;

	public ExecutorFillFloor(TestUtilities plugin)
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

		Block lookingAt = Utilities.getLookingAtAir(p);

		Material m = p.getItemInHand().getType();
		if (m.isBlock())
		{
			count = 0;
			if (label.equals("floor"))
				fillFloor(p, m, lookingAt);
			else if(label.equals("wall"))
			{
				Block look = Utilities.getLookingAtBlock(p);
				if(look.getX() == lookingAt.getX() && look.getZ() == lookingAt.getZ())
				{
					p.sendMessage("Cannot built a wall here.");
					return true;
				}
				else
				{
					if(look.getX() == lookingAt.getX())
					{
						// Z-axis wall
						fillWall(p, m, lookingAt.getX(), lookingAt.getY(), lookingAt.getZ(), 0, 1);
					}
					else
					{
						// X-axis wall
						fillWall(p, m, lookingAt.getX(), lookingAt.getY(), lookingAt.getZ(), 1, 0);						
					}
				}
			}
			p.sendMessage("Success! Placed " + count + " " + m + " as a floor.");
		} else
		{
			p.sendMessage("Cannot place this type of block.");
			return true;
		}

		return true;
	}

	private void fillFloor(Player p, Material mat, Block b)
	{
		if (count > max_count)
			return;
		if (b.getType() == Material.AIR)
		{
			b.setType(mat);
			count++;

			fillFloor(p, mat, b.getRelative(BlockFace.SOUTH));
			fillFloor(p, mat, b.getRelative(BlockFace.NORTH));
			fillFloor(p, mat, b.getRelative(BlockFace.EAST));
			fillFloor(p, mat, b.getRelative(BlockFace.WEST));
		}
	}

	private void fillWall(Player p, Material mat, int x, int y, int z, int xOffset, int zOffset)
	{
		Block b = p.getWorld().getBlockAt(x, y, z);
		
		if (count > max_count)
			return;
		if (b.getType() == Material.AIR)
		{
			b.setType(mat);
			count++;

			fillWall(p, mat, x, y - 1, z, xOffset, zOffset);
			fillWall(p, mat, x, y + 1, z, xOffset, zOffset);
			fillWall(p, mat, x + xOffset, y, z + zOffset, xOffset, zOffset);
			fillWall(p, mat, x - xOffset, y, z - zOffset, xOffset, zOffset);
		}
	}
}
