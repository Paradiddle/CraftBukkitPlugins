package com.github.paradiddle.jmaze.generators;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.github.paradiddle.jmaze.Maze;
import com.github.paradiddle.jmaze.Utilities;

public class DepthFirstSearch implements MazeGenerator
{
	private Maze maze;
	
	private Stack<Point> stack;
	
	private enum Dir
	{
		NORTH, SOUTH, EAST, WEST;
	}
	
	public void generateMaze(Maze maze)
	{
		this.maze = maze;

		for(int i = 0; i < maze.width(); i++)		
		{
			for(int j = 0; j < maze.height(); j++)
			{
				maze.set(i, j, 1);
			}
		}
		stack = new Stack<Point>();
		
		Point startPos = new Point(1, 1);
		maze.set(startPos.x, startPos.y, 0);
		stack.push(startPos);
		
		while(stack.size() > 0)
		{
			expandMaze();
		}
	}
	
	private void expandMaze()
	{
		Point curP = stack.peek();
		List<Dir> dirs = getOpenDirections(curP);
		if(dirs.size() < 1)
		{
			stack.pop();
			return;
		}
		
		Dir rDir = dirs.remove(Utilities.rand.nextInt(dirs.size()));
		Point oneStep = curP.getRelativePoint(rDir, 1);
		maze.set(oneStep.x, oneStep.y, 0);
		Point twoStep = curP.getRelativePoint(rDir, 2);
		maze.set(twoStep.x, twoStep.y, 0);
		stack.push(twoStep);
	}
	
	private List<Dir> getOpenDirections(Point p)
	{
		LinkedList<Dir> dirs = new LinkedList<Dir>();
		if(isOpen(p.getRelativePoint(Dir.NORTH, 2)))
			dirs.add(Dir.NORTH);
		if(isOpen(p.getRelativePoint(Dir.SOUTH, 2)))
			dirs.add(Dir.SOUTH);
		if(isOpen(p.getRelativePoint(Dir.EAST, 2)))
			dirs.add(Dir.EAST);
		if(isOpen(p.getRelativePoint(Dir.WEST, 2)))
			dirs.add(Dir.WEST);
		return dirs;
	}
	
	private boolean isOpen(Point p)
	{
		if(p.x < 0 || p.y < 0 || p.x >= maze.width() || p.y >= maze.height())
			return false;
		return maze.get(p.x, p.y) == 1;
	}
	
	private class Point
	{
		public int x, y;
		
		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public Point getRelativePoint(Dir d, int distance)
		{
			switch(d)
			{
			case NORTH:
				return new Point(x, y - distance);
			case SOUTH:
				return new Point(x, y + distance);
			case EAST:
				return new Point(x + distance, y);
			case WEST:
				return new Point(x - distance, y);
			}
			return null;
		}
	}
}
