package com.github.paradiddle.javamazegui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;

import com.github.paradiddle.jmaze.Maze;
import com.github.paradiddle.jmaze.MazeSolver;
import com.github.paradiddle.jmaze.generators.RecursiveDivision;

public class Main extends JFrame implements KeyListener
{
	private int blockWidth = 5;
	private int blockHeight = 5;

	private int mazeWidth = 71;
	private int mazeHeight = 71;

	private Maze maze;

	private MazeCanvas mCanvas;
	private SettingsPanel settings;
	
	public static Random rand = new Random();

	public Main()
	{
		super("Maze Generation Testing ~ Alan Streit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		maze = new Maze(mazeWidth, mazeHeight);
		RecursiveDivision.generateMaze(maze);

		Container c = getContentPane();

		mCanvas = new MazeCanvas(this);
		settings = new SettingsPanel(this);

		c.setLayout(new BorderLayout());
		c.add(mCanvas, BorderLayout.CENTER);
		c.add(settings, BorderLayout.LINE_END);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public Maze getMaze()
	{
		return maze;
	}

	public int getBlockWidth()
	{
		return blockWidth;
	}

	public int getBlockHeight()
	{
		return blockHeight;
	}

	public int getMazeWidth()
	{
		return mazeWidth;
	}

	public int getMazeHeight()
	{
		return mazeHeight;
	}
	
	public void setMazeWidth(int w)
	{
		this.mazeWidth = w;
	}
	
	public void setMazeHeight(int h)
	{
		this.mazeHeight = h;
	}

	public static void main(String[] args)
	{
		new Main();
	}
	
	public void generate()
	{
		mCanvas.setSize(mazeWidth * blockWidth, mazeHeight * blockHeight);
		pack();
		maze = new Maze(mazeWidth, mazeHeight);
		maze.reset();
		maze.addBorder();
		RecursiveDivision.generateMaze(maze);
		mCanvas.repaint();
	}
	
	public void solve()
	{
		MazeSolver.solve(maze);
		mCanvas.repaint();
	}

	public void paintMaze(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < maze.width(); i++)
		{
			for (int j = 0; j < maze.height(); j++)
			{
				if (maze.get(i, j) == 1)
				{
					g2.setColor(Color.LIGHT_GRAY);
					g2.fillRect(i * blockWidth, j * blockWidth, blockWidth, blockHeight);
				} else if (maze.get(i, j) == 3)
				{
					g2.setColor(Color.GREEN);
					g2.fillRect(i * blockWidth, j * blockWidth, blockWidth, blockHeight);
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE)
		{
			generate();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
	}

}
