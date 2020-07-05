import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JButton;

public class AppGUI {

	private JFrame frame;
	private JPanel mainMenu;
	private JTextField txtWelcomeToSwamp;
	private JButton btnNewGame;
	private JButton btnSaveGame;
	private JButton btnLoadGame;
	private JButton btnQuitGame;
	private JButton playBtn;
	private JButton passiveOgre;
	private JButton grumpyOgre;
	private ArrayList <JLabel> labelsList = new ArrayList <JLabel>();
	private Grid theGrid = new Grid();
	private JPanel menu;
	private JPanel grid;
	private JSplitPane splitPane;
	private JTextArea textArea;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppGUI window = new AppGUI();
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
	public AppGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		mainMenu = new JPanel();
		mainMenu.setBounds(0, 0, 886, 553);
		frame.getContentPane().add(mainMenu);
		mainMenu.setLayout(null);
		
		txtWelcomeToSwamp = new JTextField();
		txtWelcomeToSwamp.setEditable(false);
		txtWelcomeToSwamp.setHorizontalAlignment(SwingConstants.CENTER);
		txtWelcomeToSwamp.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		txtWelcomeToSwamp.setText("Welcome to Swamp Game");
		txtWelcomeToSwamp.setBounds(267, 105, 293, 71);
		mainMenu.add(txtWelcomeToSwamp);
		txtWelcomeToSwamp.setColumns(10);
		
		
		mainMenu.add(getBtnNewGame());
		mainMenu.add(getBtnLoadGame());
		mainMenu.add(getBtnQuitGame());
		
//		btnLoadGame = new JButton("Load Game");
//		btnLoadGame.setBounds(372, 297, 107, 37);
//		mainMenu.add(btnLoadGame);
//		
//		btnQuitGame = new JButton("Quit Game");
//		btnQuitGame.setBounds(572, 297, 107, 37);
//		mainMenu.add(btnQuitGame);
		
		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setBounds(0, 0, 886, 553);
		frame.getContentPane().add(splitPane);
		splitPane.setVisible(false);
		menu = new JPanel();
		grid = new JPanel();
		menu.setVisible(false);
		grid.setVisible(false);
		splitPane.setTopComponent(grid);		
		splitPane.setBottomComponent(menu);
		
		
		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSizeGrid = new Dimension(400, 400);
		Dimension maximumSizeMenu = new Dimension(200, 200);
		grid.setMinimumSize(minimumSizeGrid);
		menu.setMaximumSize(maximumSizeMenu);
		grid.setLayout(new GridLayout(theGrid.returnArray().size(), theGrid.returnArray().size(), 0, 0));
		menu.setLayout(new GridLayout(1, 4, 0, 0));
	
		
		menu.add(getPlayBtn());
		menu.add(getPassiveOgre());
		menu.add(getGrumpyOgre());
		menu.add(getBtnSaveGame());
		textArea = new JTextArea();
		menu.add(textArea);
		
	}
	
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
	
	public JButton getBtnNewGame() {
		if (btnNewGame == null) {
			btnNewGame = new JButton("New Game");
			btnNewGame.setBounds(177, 297, 99, 37);
			btnNewGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mainMenu.setVisible(false);
					splitPane.setVisible(true);
					menu.setVisible(true);
					grid.setVisible(true);
					startGame();
					theGrid.startOgre();
					
				}
			});
		}
		return this.btnNewGame;
	}
	
	
	public JButton getPlayBtn() {
		if (playBtn == null) {
			playBtn = new JButton("Play");
			
			playBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					System.out.println(theGrid.getEnemies());
		
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
	
	public JButton getBtnSaveGame() {
		if (btnSaveGame == null) {
			btnSaveGame = new JButton("Save Game");
			btnSaveGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						theGrid.saveEnemies();
						theGrid.saveGame();
					} catch (IOException e) {
					
						e.printStackTrace();
					}
				}
			});
		}
		return this.btnSaveGame;
	}
	
	public JButton getBtnLoadGame() {
		if (btnLoadGame == null) {
			btnLoadGame = new JButton("Load Game");
			btnLoadGame.setBounds(372, 297, 107, 37);
			btnLoadGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mainMenu.setVisible(false);
						splitPane.setVisible(true);
						menu.setVisible(true);
						grid.setVisible(true);
						theGrid.loadGame();
						theGrid.loadEnemies();
						theGrid.updateInformation();
						
						startGame();
						updateGrid();
						
					} catch (IOException | ClassNotFoundException e) {
						
						e.printStackTrace();
					}
				}
			});
		}
		return this.btnLoadGame;
	}
	
	public JButton getBtnQuitGame() {
		if (btnQuitGame == null) {
			btnQuitGame = new JButton("Quit Game");
			btnQuitGame.setBounds(572, 297, 107, 37);
			btnQuitGame.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					frame.dispose();
				}
			});
		}
		return this.btnQuitGame;
	}
	
}
