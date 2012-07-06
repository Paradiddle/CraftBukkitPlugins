package com.github.paradiddle.jmaze;


public class MazeSolver
{

	public static void solve(Maze m)
	{
		boolean filledDeadEnd = true;
		do
		{
			filledDeadEnd = fillDeadEnd(m);
		} while (filledDeadEnd);
		
		for(int i = 0; i < m.width(); i++)
		{
			for(int j = 0; j < m.height(); j++)
			{
				if(m.get(i, j) == 0)
					m.set(i, j, 3);
				else if(m.get(i, j) == 2)
					m.set(i, j, 0);
			}
		}
	}

	private static boolean fillDeadEnd(Maze m)
	{
		for (int i = 1; i < m.width() - 1; i++)
		{
			for (int j = 1; j < m.height() - 1; j++)
			{
				if (m.get(i, j) == 0)
				{
					if (oneOpenAround(m, i, j))
					{
						fillDeadEnd(m, i, j);
						return true;
					}
				}
			}
		}
		return false;
	}

	private static boolean oneOpenAround(Maze m, int x, int y)
	{
		int count = 0;
		if (m.get(x - 1, y) == 0)
			count++;
		if (m.get(x + 1, y) == 0)
			count++;
		if (m.get(x, y - 1) == 0)
			count++;
		if (m.get(x, y + 1) == 0)
			count++;
		return count == 1;
	}

	private static void fillDeadEnd(Maze m, int x, int y)
	{
		if (oneOpenAround(m, x, y))
		{
			m.set(x, y, 2);
			if (m.get(x - 1, y) == 0)
			{
				fillDeadEnd(m, x - 1, y);
			} else if (m.get(x + 1, y) == 0)
			{
				fillDeadEnd(m, x + 1, y);
			} else if (m.get(x, y - 1) == 0)
			{
				fillDeadEnd(m, x, y - 1);
			} else if (m.get(x, y + 1) == 0)
			{
				fillDeadEnd(m, x, y + 1);
			}
		}
	}
}
