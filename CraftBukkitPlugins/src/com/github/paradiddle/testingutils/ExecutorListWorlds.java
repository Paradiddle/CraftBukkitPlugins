package com.github.paradiddle.testingutils;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ExecutorListWorlds implements CommandExecutor
{
	private TestUtilities plugin;
	
	public ExecutorListWorlds(TestUtilities plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args)
	{
		if(args.length != 0)
			return false;
		List<World> worlds = plugin.getServer().getWorlds();
		sender.sendMessage(ChatColor.RED + "Worlds:");
		int count = 1;
		for(World w: worlds)
		{
			sender.sendMessage("(" + count + ") " + w.getName());
			count++;
		}
		return true;
	}

}
