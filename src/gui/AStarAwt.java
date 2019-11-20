package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import moveSCV.Map;
import moveSCV.MoveTree;
import moveSCV.Scv;

class AStarAwt extends JFrame {

	private static final long serialVersionUID = 1L;
	static final int MAP_SIZE_X = 350;
	static final int MAP_SIZE_Y = 350;

	Map map;
	MoveTree currentPosition;

	JButton currentMap[][];
	JPanel mapPanel = new JPanel(new GridLayout(Map.MAX_Y, Map.MAX_X));
	JPanel controlPanel = new JPanel();
	JPanel mainPanel = new JPanel();
	JButton nextButton = new JButton("다음단계");
	JButton preButton = new JButton("이전단계");

	public AStarAwt(Map map) {
		this.map = map;

		currentMap = new JButton[Map.MAX_Y][Map.MAX_X];

		mapPanel.setBounds(0, 0, MAP_SIZE_X, MAP_SIZE_Y);
		mapPanel.setBackground(Color.BLACK);
		mainPanel.add(mapPanel);

		for (int y = 0; y < currentMap.length; y++) {
			for (int x = 0; x < currentMap[y].length; x++) {
				currentMap[y][x] = new JButton();
				currentMap[y][x].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				currentMap[y][x].setSize(MAP_SIZE_X / Map.MAX_X, MAP_SIZE_Y / Map.MAX_Y);
				if (map.map[y][x] == Map.WALL) {
					currentMap[y][x].setVisible(false);
				}
				mapPanel.add(currentMap[y][x]);
			}
		}

		printMap(map.scv);

		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!AStarAwt.this.map.route.isEmpty()) {
					currentPosition = AStarAwt.this.map.route.pop();			
					printMap(currentPosition.scv);
					if (!preButton.isVisible()) {
						preButton.setVisible(true);
					}
				}
				
				if (AStarAwt.this.map.route.isEmpty()) {
					nextButton.setVisible(false);
				}
			}
		});
		preButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (currentPosition.parent != null) {
					AStarAwt.this.map.route.push(currentPosition);
					printMap(currentPosition.parent.scv);
					currentPosition = currentPosition.parent;
					if (!nextButton.isVisible()) {
						nextButton.setVisible(true);
					}
				}
				if (currentPosition.parent == null) {
					preButton.setVisible(false);
				}
			}
		});
		preButton.setVisible(false);
		controlPanel.add(preButton);
		controlPanel.add(nextButton);
		controlPanel.setBounds(0, MAP_SIZE_Y, MAP_SIZE_X, 100);
		controlPanel.setBackground(Color.BLUE);

		mainPanel.add(controlPanel);

		mainPanel.setLayout(null);
		setBounds(100, 100, MAP_SIZE_X + (Map.MAX_X * 2) + 1, MAP_SIZE_Y + (Map.MAX_Y * 2) + 125);
		setContentPane(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void printMap(Scv unit) {
		for (int y = 0; y < currentMap.length; y++) {
			for (int x = 0; x < currentMap[y].length; x++) {
				if (unit.position.x == x && unit.position.y == y) {
					currentMap[y][x].setIcon(new ImageIcon("scv.png"));
				} else if (map.target.x == x && map.target.y == y) {
					currentMap[y][x].setIcon(null);
					currentMap[y][x].setText("×");
				} else {
					currentMap[y][x].setText("");
					currentMap[y][x].setIcon(null);
				}
			}
		}
	}
}