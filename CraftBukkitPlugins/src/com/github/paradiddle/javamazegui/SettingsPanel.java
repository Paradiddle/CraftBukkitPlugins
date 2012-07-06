package com.github.paradiddle.javamazegui;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingsPanel extends JPanel implements ActionListener
{
	private JTextField tfMazeWidth, tfMazeHeight;
	private JLabel lblMazeWidth, lblMazeHeight;
	private JButton btnGenerate;
	private JButton btnSolve;

	private Main main;

	public SettingsPanel(Main m)
	{
		this.main = m;

		tfMazeWidth = new JTextField(5);
		tfMazeHeight = new JTextField(5);
		
		tfMazeWidth.setText("" + main.getMazeWidth());
		tfMazeHeight.setText("" + main.getMazeHeight());

		lblMazeWidth = new JLabel("Maze Width");
		lblMazeHeight = new JLabel("Maze Height");

		btnGenerate = new JButton("Generate");
		btnSolve = new JButton("Solve");

		btnGenerate.addActionListener(this);
		btnSolve.addActionListener(this);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(2, 2, 2, 2);
		c.weightx = 0.5;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 0;

		add(lblMazeWidth, c);

		c.gridx = 1;

		add(tfMazeWidth, c);

		c.gridx = 0;
		c.gridy = 1;

		add(lblMazeHeight, c);

		c.gridx = 1;

		add(tfMazeHeight, c);

		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 2;
		add(btnGenerate, c);

		c.gridy = 3;
		add(btnSolve, c);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnGenerate)
		{
			int w = 0, h = 0;
			try
			{
				w = Integer.parseInt(tfMazeWidth.getText());
				h = Integer.parseInt(tfMazeHeight.getText());

				if (w < 5 || h < 5)
				{
					throw new NumberFormatException();
				}
			} catch (NumberFormatException nfe)
			{
				tfMazeWidth.setText("" + main.getMazeWidth());
				tfMazeHeight.setText("" + main.getMazeHeight());
				return;
			}
			
			if(w % 2 == 0)
				w++;
			if(h % 2 == 0)
				h++;
			
			
			main.setMazeWidth(w);
			main.setMazeHeight(h);
			tfMazeWidth.setText("" + w);
			tfMazeHeight.setText("" + h);

			main.generate();
		} else if (e.getSource() == btnSolve)
		{
			main.solve();
		}
	}
}
