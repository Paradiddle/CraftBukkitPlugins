package com.github.paradiddle.coords;

import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.paradiddle.testingutils.TestUtilities;

public class ExecutorCoordsBroadcast implements CommandExecutor
{
	private CoordsPlugin plugin;
	private LinkedList<CoordinatesRequest> requests = new LinkedList<CoordinatesRequest>();

	public ExecutorCoordsBroadcast(CoordsPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args)
	{
		Player toPrint = null;

		if (args.length == 1)
		{
			toPrint = plugin.getServer().getPlayerExact(args[0]);

			// player not found
			if (toPrint == null)
			{
				OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
				if (offlinePlayer.getLastPlayed() == 0)
					sender.sendMessage("Cannot find player '" + args[0] + "'");
				else
				{
					sender.sendMessage(offlinePlayer.getName() + " is offline.");
				}
				return true;
			}

			// player is found and online
			printPlayerCoordinates(toPrint);
			return true;
		}

		if (args.length != 0)
			return false;

		if (!(sender instanceof Player))
		{
			sender.sendMessage("Must be a player to broadcast your coordinates.");
			return true;
		}

		toPrint = (Player) sender;
		printPlayerCoordinates(toPrint);
		return true;
	}

	private void printPlayerCoordinates(Player p)
	{
		Location l = p.getLocation();
		plugin.getServer().broadcastMessage(
				p.getName() + "'s coordinates are X: " + l.getBlockX() + " Y: "
						+ l.getBlockY() + " Z: " + l.getBlockZ());
	}

}
