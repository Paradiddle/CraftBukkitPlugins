package com.github.paradiddle.jmaze;

import java.util.Random;

public class Maze
{
	private int[][] maze;

	public static Random rand = new Random();
	
	public Maze(int width, int height)
	{
		maze = new int[width][height];
		
		addBorder();
	}
	
	public void reset()
	{
		maze = new int[maze.length][maze[0].length];
	}

	public void addBorder()
	{
		for (int i = 0; i < width(); i++)
		{
			for (int j = 0; j < height(); j++)
			{
				if (i == 0 || j == 0 || i == width() - 1 || j == height() - 1)
					maze[i][j] = 1;
			}
		}
		maze[1][0] = 0;
		maze[width() - 2][height() - 1] = 0;
	}

	public int width()
	{
		return maze.length;
	}

	public int height()
	{
		if (maze.length > 0)
			return maze[0].length;
		return 0;
	}
	
	public int get(int x, int y)
	{
		return maze[x][y];
	}
	
	public void set(int x, int y, int value)
	{
		maze[x][y] = value;
	}
}
