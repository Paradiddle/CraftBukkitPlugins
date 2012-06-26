package com.github.paradiddle.jmaze;
public class RecursiveDivision
{
	public static void generateMaze(Maze maze)
	{
		RecursiveDivision.divide(maze, 0, 0, maze.width(), maze.height(), true);
	}

	private static void divide(Maze m, int x, int y, int width, int height, boolean lastHoriz)
	{
		boolean horizontal = Main.rand.nextInt(2) == 0 ? true : false;
		if (!lastHoriz)
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
					m.set(i, vPosAbs, 1);
			}
			divide(m, x, vPosAbs, width, height - vPosRel, true);
			divide(m, x, y, width, height - (height - vPosRel), true);
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
					m.set(hPosAbs, i, 1);
			}
			divide(m, hPosAbs, y, width - hPosRel, height, false);
			divide(m, x, y, width - (width - hPosRel), height, false);
		}
	}
}