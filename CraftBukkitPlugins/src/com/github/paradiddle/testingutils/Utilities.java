package com.github.paradiddle.testingutils;

import java.util.HashSet;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Utilities
{
	static HashSet<Byte> trans = new HashSet<Byte>();
	static
	{
		trans.add(new Byte((byte) Material.LONG_GRASS.getId()));
		trans.add(new Byte((byte) Material.AIR.getId()));
		trans.add(new Byte((byte) Material.WATER.getId()));
		trans.add(new Byte((byte) Material.STATIONARY_WATER.getId()));
	}
	public static Block getLookingAtBlock(Player p)
	{
		List<Block> lookingAt = p.getLineOfSight(trans, 20);
		if(lookingAt.size() > 0)
			return lookingAt.get(lookingAt.size() - 1);
		return null;
	}
	
	public static Block getLookingAtBlockIncludingWater(Player p)
	{
		List<Block> lookingAt = p.getLineOfSight(null, 20);
		if(lookingAt.size() > 0)
			return lookingAt.get(lookingAt.size() - 1);
		return null;
	}
	
	public static Block getLookingAtAirIncludingWater(Player p)
	{
		List<Block> lookingAt = p.getLineOfSight(null, 20);
		if(lookingAt.size() > 1)
			return lookingAt.get(lookingAt.size() - 2);
		return null;
	}
	
	public static Block getLookingAtAir(Player p)
	{
		List<Block> lookingAt = p.getLineOfSight(trans, 20);
		if(lookingAt.size() > 1)
			return lookingAt.get(lookingAt.size() - 2);
		return null;
	}
}
