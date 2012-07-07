package com.github.paradiddle.testingutils;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ExecutorBlockInformation implements CommandExecutor, Listener
{
	HashMap<Player, Boolean> printBlockStats = new HashMap<Player, Boolean>();
	
	private TestUtilities plugin;
	
	public ExecutorBlockInformation(TestUtilities plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		String world = null;
		if(sender instanceof Player)
		{
			Player p = (Player)sender;
			
			if(args.length == 1)
			{
				boolean enable;
				if(args[0].equals("true"))
					enable = true;
				else if(args[0].equals("false"))
					enable = false;
				else
					return false;
				printBlockStats.put(p, enable);
			}
			
			world = p.getWorld().getName();

			if (args.length != 3)
				return false;
		}
		else
		{
			if(args.length != 4)
				return false;
			world = args[3];
		}
		
		int x, y, z;
		try
		{
			x = Integer.parseInt(args[0]);
			y = Integer.parseInt(args[1]);
			z = Integer.parseInt(args[2]);
		} catch (NumberFormatException nfe)
		{
			return false;
		}
		
		printBlockInformation(sender, x, y, z, world);
		return true;
	}
	
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent e)
	{
		Player p = e.getPlayer();
		if(printBlockStats.get(p) == null)
			printBlockStats.put(p, true);
		
		if(printBlockStats.get(p) && e.isSneaking() && p.getItemInHand().getType() == Material.WATER_BUCKET)
		{
			Block lookingAt = Utilities.getLookingAtBlock(p);
			if(lookingAt.getType() == Material.AIR)
				return;
			for(int x = lookingAt.getX() - 1; x < lookingAt.getX() + 2; x++)
			{
				for(int z = lookingAt.getZ() - 1; z < lookingAt.getZ() + 2; z++)
				{
					p.getWorld().getBlockAt(x, lookingAt.getY(), z).setType(Material.WATER);
				}
			}
		}
	}
	
	private void printBlockInformation(CommandSender sender, int x, int y, int z, String worldName)
	{
		World w = plugin.getServer().getWorld(worldName);
		if(w == null)
		{
			try
			{
				int wnum = Integer.parseInt(worldName);
				wnum--;
				List<World> worlds = plugin.getServer().getWorlds();
				if(wnum < worlds.size() && wnum >= 0)
					w = worlds.get(wnum);
			}
			catch (NumberFormatException nfe) {}
			
			if(w == null)
			{
				sender.sendMessage("Cannot find world '" + worldName + "'");
				return;
			}
		}
		Block b = w.getBlockAt(x, y, z);
		
		printBlockInformation(sender, b);
	}
	
	private void printBlockInformation(CommandSender sender, Block b)
	{
		sender.sendMessage("Type: " + b.getType());
		sender.sendMessage("Type ID: " + b.getTypeId());
		sender.sendMessage("Biome: " + b.getBiome());
	}
}
