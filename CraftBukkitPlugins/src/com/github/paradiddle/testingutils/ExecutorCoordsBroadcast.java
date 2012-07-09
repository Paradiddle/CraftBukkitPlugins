package com.github.paradiddle.testingutils;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExecutorCoordsBroadcast implements CommandExecutor
{
	private TestUtilities plugin;
	
	public ExecutorCoordsBroadcast(TestUtilities plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage("Must be a player to broadcast your coordinates.");
			return true;
		}
		
		Player p = (Player)sender;
		Location l = p.getLocation();
		plugin.getServer().broadcastMessage("My coordinates are X: " + l.getBlockX() + " Y: " + l.getBlockY() + " Z: " + l.getBlockZ());
		
		return true;
	}
	
	
}
