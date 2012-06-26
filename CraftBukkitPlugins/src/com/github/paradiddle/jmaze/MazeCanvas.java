package com.github.paradiddle.jmaze;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;

public class MazeCanvas extends Canvas
{
	private Main main;

	public MazeCanvas(Main m)
	{
		this.main = m;
		setSize(main.getMazeWidth() * main.getBlockWidth(),
				main.getMazeHeight() * main.getBlockHeight());
		addKeyListener(m);
	}

	public void paint(Graphics g)
	{
		main.paintMaze(g);
	}

}
