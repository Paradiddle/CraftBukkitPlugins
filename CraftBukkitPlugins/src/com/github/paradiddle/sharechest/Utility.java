package com.github.paradiddle.sharechest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Utility
{	
	public static Block getLookingAtBlock(Player p)
	{
		List<Block> lookingAt = p.getLineOfSight(null, 20);
		if(lookingAt.size() > 0)
			return lookingAt.get(lookingAt.size() - 1);
		return null;
	}
}
