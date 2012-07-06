package com.github.paradiddle.jmaze;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.paradiddle.jmaze.generators.DepthFirstSearch;
import com.github.paradiddle.jmaze.generators.MazeGenerator;
import com.github.paradiddle.jmaze.generators.RecursiveDivision;

public class SettingsPanel extends JPanel implements ActionListener
{
	private JTextField tfMazeWidth, tfMazeHeight;
	private JLabel lblMazeWidth, lblMazeHeight;
	private JButton btnGenerate;
	private JButton btnSolve;
	private JComboBox cbGenerationType;
	
	private LinkedHashMap<String, MazeGenerator> generators;
	
	private Main main;
	
	private DepthFirstSearch dfs;
	private RecursiveDivision recursiveDivision;

	public SettingsPanel(Main m)
	{
		this.main = m;
		
		this.setBorder(BorderFactory.createTitledBorder("Maze Generation Settings"));
		
		dfs = new DepthFirstSearch(main);
		recursiveDivision = new RecursiveDivision(main);
		
		generators = new LinkedHashMap<String, MazeGenerator>();
		generators.put("Recursive Division", recursiveDivision);
		generators.put("Depth First Search", dfs);
		
		String[] mazeGenerationTypes = generators.keySet().toArray(new String[0]);
		
		cbGenerationType = new JComboBox(mazeGenerationTypes);
		
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
		
		int y = 0;
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(2, 2, 2, 2);
		c.weightx = 0.5;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = y++;

		add(lblMazeWidth, c);

		c.gridx = 1;

		add(tfMazeWidth, c);

		c.gridx = 0;
		c.gridy = y++;

		add(lblMazeHeight, c);

		c.gridx = 1;

		add(tfMazeHeight, c);

		
		c.gridx = 0;
		c.gridy = y++;
		add(cbGenerationType, c);
		
		c.gridx = 1;		
		add(btnGenerate, c);

		c.gridy = y++;
		add(btnSolve, c);
	}
	
	public MazeGenerator getMazeGenerator()
	{
		return generators.get(cbGenerationType.getSelectedItem());
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
