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
			setMaterialLocation(p, m, lookingAt);
			p.sendMessage("Success! Placed " + count + " " + m + " as a floor.");
		}
		else
		{
			p.sendMessage("Cannot place this type of block.");
			return true;
		}

		return true;
	}

	private void setMaterialLocation(Player p, Material mat, Block b)
	{
		if (count > max_count)
			return;
		if (b.getType() == Material.AIR)
		{
			b.setType(mat);
			count++;

			setMaterialLocation(p, mat, b.getRelative(BlockFace.SOUTH));
			setMaterialLocation(p, mat, b.getRelative(BlockFace.NORTH));
			setMaterialLocation(p, mat, b.getRelative(BlockFace.EAST));
			setMaterialLocation(p, mat, b.getRelative(BlockFace.WEST));
		}
	}

}
