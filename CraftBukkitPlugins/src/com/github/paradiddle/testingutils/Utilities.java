package com.github.paradiddle.testingutils;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Utilities
{
	public static Block getLookingAtBlock(Player p)
	{
		List<Block> lookingAt = p.getLineOfSight(null, 20);
		if(lookingAt.size() > 0)
			return lookingAt.get(lookingAt.size() - 1);
		return null;
	}
}
