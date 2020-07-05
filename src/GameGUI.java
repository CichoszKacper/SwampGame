import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;


public class GameGUI {
	
	private Grid theGrid = new Grid();
	private JButton startGameBtn;
	private JFrame frame;
	private JButton playBtn;
	private JButton passiveOgre;
	private JButton grumpyOgre;
	private JPanel menu;
	private JPanel grid;
	private ArrayList <JLabel> labelsList = new ArrayList <JLabel>();
	private JTextArea textArea;
	private JButton saveGame;
	private JButton loadGame;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI window = new GameGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameGUI() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout (1,1));		
		
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane);
		menu = new JPanel();
		grid = new JPanel();
		splitPane.setLeftComponent(menu);		
		splitPane.setRightComponent(grid);
		
		
		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(300, 300);
		menu.setMinimumSize(minimumSize);
		grid.setMinimumSize(minimumSize);
		grid.setLayout(new GridLayout(theGrid.returnArray().size(), theGrid.returnArray().size(), 0, 0));
		menu.setLayout(new GridLayout(4, 4, 0, 0));
	
		
		
		menu.add(getPlayBtn());
		menu.add(getStartGameBtn());
		menu.add(getPassiveOgre());
		menu.add(getGrumpyOgre());
		menu.add(getSaveGame());
		menu.add(getLoadGame());
		
		textArea = new JTextArea();
		menu.add(textArea);		
		
	}

	/**
	 * Initialise the contents of the frame.
	 */
	
	private void startGame() {

		for(Row tempRow : theGrid.returnArray()) {
			for (Square tempSquare : tempRow.getTheRow()) {
				System.out.println(tempRow.getTheRow().size());
				JLabel label = new JLabel();
				label.setText(tempSquare.getName());
				labelsList.add(label);
				grid.add(label);
			}
		}
		grid.setVisible(true);
	
	}
	
	
	private void updateGrid ()	{
		String ogrePosition = String.valueOf(theGrid.getSelectOgreColumn()) + String.valueOf(theGrid.getSelectOgreRow());
		int counter = 0;
		for(Row tempRow : theGrid.returnArray()) {
			for (Square tempSquare : tempRow.getTheRow()) {
				labelsList.get(counter).setText(tempSquare.getName());
				counter++;
			}
		}
		String output = "";
		for(String enemy : theGrid.getEnemies().keySet()) {
			for(Integer position : theGrid.getEnemies().get(enemy)) {
				output+= enemy + " on position " + position +"\n";
				
			}
		}
		output += "Ogre on position " + ogrePosition;
		textArea.setText(output);
	}
	
	public JButton getPlayBtn() {
		if (playBtn == null) {
			playBtn = new JButton("Play");
			playBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					
					
					for(String enemy : theGrid.getEnemies().keySet()) {
			        	for(Integer position : theGrid.getEnemies().get(enemy)) {
			        		
			        		theGrid.getEnemies().get(enemy).set(theGrid.getEnemies().get(enemy).indexOf(position), theGrid.updateEnemyPosition(enemy, position));
			        	}		        	
			        }
					theGrid.updateOgrePosition(theGrid.getSelectOgreColumn(), theGrid.getSelectOgreRow());
					theGrid.placeNewEnemy();
					theGrid.fight();
					updateGrid();
					
				}
			});
		}
		return playBtn;
	}
	

	public ArrayList<JLabel> getLabelsList() {
		return this.labelsList;
	}

	public JButton getStartGameBtn() {
		if (startGameBtn == null) {
			startGameBtn = new JButton("Start Game");
			startGameBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					startGame();
					theGrid.startOgre();
				}
			});
		}
		return this.startGameBtn;
	}
	
	public JButton getPassiveOgre () {
		if (passiveOgre == null) {
			passiveOgre = new JButton("Passive Mode");
			passiveOgre.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					theGrid.setOgreMood(theGrid.getPassiveOgre());
					theGrid.changeOgreMood(theGrid.getOgreMood());
				}
			});
		}
		return this.passiveOgre;
	}
	
	public JButton getGrumpyOgre () {
		if (grumpyOgre == null) {
			grumpyOgre = new JButton("Grumpy Mode");
			grumpyOgre.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					theGrid.setOgreMood(theGrid.getGrumpyOgre());
					theGrid.changeOgreMood(theGrid.getOgreMood());
				}
			});
		}
		return this.grumpyOgre;
	}
	
	public JButton getSaveGame() {
		if (saveGame == null) {
			saveGame = new JButton("Save Game");
			saveGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						theGrid.saveEnemies();
						theGrid.saveGame();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		return this.saveGame;
	}
	
	public JButton getLoadGame() {
		if (loadGame == null) {
			loadGame = new JButton("Load Game");
			loadGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						theGrid.loadGame();
						theGrid.loadEnemies();
						theGrid.updateInformation();
						updateGrid();
					} catch (IOException | ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		return this.loadGame;
	}
}
