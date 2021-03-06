package com.github.paradiddle.rockpaperscissors;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RockPaperScissors extends JavaPlugin implements CommandExecutor
{
	enum Sign
	{
		ROCK, PAPER, SCISSORS
	}

	LinkedList<RpsRequest> requests = new LinkedList<RpsRequest>();

	@Override
	public void onEnable()
	{
		getCommand("rps").setExecutor(this);
		super.onEnable();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Only players can play Rock Paper Scissors.");
			return true;
		}

		if (args.length != 2)
		{
			return false;
		}

		Player pFrom = (Player) sender;
		Player pTo = getServer().getPlayer(args[0]);
		
		if (pTo == null)
		{
			sender.sendMessage("Cannot find player " + args[0]);
			return true;
		}
		Sign s = getSign(args[1]);
		if (s == null)
		{
			sender.sendMessage("Invalid sign '" + args[1] + "'");
			return true;
		}

		createRequest(pFrom, pTo, s);
		return true;
	}

	private Sign getSign(String sign)
	{
		sign = sign.toLowerCase();
		if (sign.equals("rock"))
			return Sign.ROCK;
		if (sign.equals("paper"))
			return Sign.PAPER;
		if (sign.equals("scissors"))
			return Sign.SCISSORS;
		return null;
	}

	public void createRequest(Player from, Player to, Sign startingSign)
	{
		if(from.getName().equals(to.getName()))
		{
			from.sendMessage("You can't play rock paper scissors with yourself.");
			return;
		}
		
		for (RpsRequest r : requests)
		{
			if (r.getFrom().equals(from.getName())
					&& r.getTo().equals(to.getName()))
			{
				from.sendMessage("You already requested a game with "
						+ to.getName());
				return;
			}
		}

		Iterator<RpsRequest> iter = requests.iterator();
		while (iter.hasNext())
		{
			RpsRequest r = iter.next();
			if (from.getName().equals(r.getTo())
					&& to.getName().equals(r.getFrom()))
			{
				runGame(from, to, startingSign, r.getSign());
				iter.remove();
				return;
			}
		}

		to.sendMessage("Player '" + from.getName()
				+ "' wants to play rock paper scissors with you.");
		from.sendMessage("Request sent to " + to.getName() + ", you threw " + s(startingSign, false));
		requests.add(new RpsRequest(from.getName(), to.getName(), startingSign));
	}

	private void runGame(Player p1, Player p2, Sign s1, Sign s2)
	{
		if (s1 == s2)
		{
			draw(p1, p2, s1);
			return;
		}
		if ((s1 == Sign.ROCK && s2 == Sign.SCISSORS)
				|| (s1 == Sign.SCISSORS && s2 == Sign.PAPER)
				|| (s1 == Sign.PAPER && s2 == Sign.ROCK))
			finishGame(p1, p2, s1, s2);
		else
			finishGame(p2, p1, s2, s1);
	}

	private void finishGame(Player winner, Player loser, Sign winningSign,
			Sign losingSign)
	{
		winner.sendMessage("You won! " + s(winningSign, true) + " beats "
				+ s(losingSign, false));
		loser.sendMessage("You lost. " + s(winningSign, true) + " beats "
				+ s(losingSign, false));
	}

	private void draw(Player p1, Player p2, Sign s)
	{
		p1.sendMessage("Tied. You both threw " + s(s, false) + "!");
		p2.sendMessage("Tied. You both threw " + s(s, false) + "!");
	}

	private String s(Sign s, boolean firstCap)
	{
		switch (s)
		{
		case ROCK:
			return (firstCap ? "R" : "r") + "ock";
		case PAPER:
			return (firstCap ? "P" : "p") + "aper";
		case SCISSORS:
			return (firstCap ? "S" : "s") + "cissors";
		}
		return "";
	}
}
