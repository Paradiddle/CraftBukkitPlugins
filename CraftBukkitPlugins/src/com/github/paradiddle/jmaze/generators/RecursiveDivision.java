package com.github.paradiddle.jmaze.generators;

import com.github.paradiddle.jmaze.Main;
import com.github.paradiddle.jmaze.Maze;

public class RecursiveDivision implements MazeGenerator
{
	private Main main;
	private Maze maze;

	public RecursiveDivision()
	{
		this.main = null;
	}
	
	public RecursiveDivision(Main main)
	{
		this.main = main;
	}
	
	public void generateMaze(Maze maze)
	{
		this.maze = maze;
		divide(0, 0, maze.width(), maze.height());
	}

	private void divide(int x, int y, int width, int height)
	{
		boolean horizontal = Main.rand.nextInt(2) == 0 ? true : false;
		if (horizontal)
		{
			int vPosRel, vPosAbs;
			if(width <= 3)
				return;
			if (height <= 3)
			{
				return;
			} else if (height < 7)
			{
				vPosRel = 2;
				vPosAbs = y + 2;
			} else
			{
				vPosRel = Main.rand.nextInt(((height - 3) / 2) - 1) + 1;
				vPosRel *= 2;
				vPosAbs = y + vPosRel;
			}
			int randSpace = Main.rand.nextInt((width - 1) / 2) * 2 + 1;
			for (int i = x; i < width + x; i++)
			{
				if (i != x + randSpace)
					maze.set(i, vPosAbs, 1);
			}
			divide(x, vPosAbs, width, height - vPosRel);
			divide(x, y, width, height - (height - vPosRel));
		} else
		{
			int hPosRel, hPosAbs;
			if(height <= 3)
				return;
			if (width <= 3)
			{
				return;
			} else if (width < 7)
			{
				hPosRel = 2;
				hPosAbs = x + 2;
			}
			else
			{
				hPosRel = Main.rand.nextInt(((width - 3) / 2) - 1) + 1;
				hPosRel *= 2;
				hPosAbs = x + hPosRel;
			}
			int randSpace = Main.rand.nextInt((height - 1) / 2) * 2 + 1;
			for (int i = y; i < height + y; i++)
			{
				if(i != y + randSpace)
					maze.set(hPosAbs, i, 1);
			}
			divide(hPosAbs, y, width - hPosRel, height);
			divide(x, y, width - (width - hPosRel), height);
		}
	}
}