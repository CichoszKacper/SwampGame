import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;

public class AppGUI implements ChangeListener  {

	private JFrame frame;
	private JPanel mainMenu;
	private JPanel sizeOfGrid;
	private JTextField txtTitle;
	private JButton btnNewGame;
	private JButton btnSaveGame;
	private JButton btnLoadGame;
	private JButton btnQuitGame;
	private JButton btnStartGame;
	private JButton playBtn;
	private JButton passiveOgre;
	private JButton grumpyOgre;
	private JButton returnToMainMenu;
	private ArrayList <JLabel> labelsList = new ArrayList <JLabel>();
	private Grid theGrid;
	private JPanel menu;
	private JPanel grid;
	private JSplitPane splitPane;
	private JSlider slider;
	private JTextArea textArea;
	private int intSizeOfGrid;
	private Color buttonColor = new Color(156, 78, 78);
	private Color bgColor = new Color(242, 218, 145);
	
	

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
	 * @throws IOException 
	 */
	public AppGUI() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		mainMenu = new JPanel();
		mainMenu.setBounds(0, 0, 886, 600);
		frame.getContentPane().add(mainMenu);
		mainMenu.setLayout(null);
		mainMenu.setBackground(bgColor);
		
		
		BufferedImage myPicture = ImageIO.read(new File("hek.png"));
		int pictureWidth = (int) (myPicture.getWidth() * 0.5);
		int pictureHeight = (int) (myPicture.getHeight() * 0.5);
		Image scaledPicture = myPicture.getScaledInstance(pictureWidth, pictureHeight, Image.SCALE_AREA_AVERAGING);
		JLabel picLabel = new JLabel(new ImageIcon(scaledPicture));
		picLabel.setBounds(-200,-150, myPicture.getWidth(),myPicture.getHeight());
		picLabel.setBackground(new Color(0, 106, 135));
		mainMenu.add(picLabel);
		
		txtTitle = new JTextField();
		txtTitle.setEditable(false);
		txtTitle.setBounds(150, 20, 600, 70);
		txtTitle.setHorizontalAlignment(SwingConstants.LEFT);
		txtTitle.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 50));
		txtTitle.setForeground(new Color(113, 133, 108));
		txtTitle.setText("Get Out Of My Swamp!");
		txtTitle.setBackground(bgColor);
		txtTitle.setBorder(BorderFactory.createEmptyBorder());
		mainMenu.add(txtTitle);
		txtTitle.setColumns(10);
		
		
		mainMenu.add(getBtnNewGame());
		mainMenu.add(getBtnLoadGame());
		mainMenu.add(getBtnQuitGame());
		
		
		sizeOfGrid = new JPanel();
		sizeOfGrid.setBounds(0, 0, 886, 600);
		frame.getContentPane().add(sizeOfGrid);
		sizeOfGrid.setLayout(null);
		sizeOfGrid.setBackground(bgColor);
		sizeOfGrid.setVisible(false);
		sizeOfGrid.add(getStartGame());
		sizeOfGrid.add(getSlider());
		
		JTextPane titleSizeGrid = new JTextPane();
		titleSizeGrid.setText("Select size of the grid");
		titleSizeGrid.setFont(new Font("Tahoma",Font.BOLD,20));
		titleSizeGrid.setBounds((int)(sizeOfGrid.getWidth()*0.38), (int)(sizeOfGrid.getHeight()*0.1), 450, 200);
		titleSizeGrid.setBackground(bgColor);
		sizeOfGrid.add(titleSizeGrid);
		
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
		grid.setBackground(bgColor);
		
		
		
		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSizeGrid = new Dimension(400, 400);
		Dimension maximumSizeMenu = new Dimension(200, 200);
		grid.setMinimumSize(minimumSizeGrid);
		menu.setMaximumSize(maximumSizeMenu);
		menu.setLayout(new GridLayout(1, 4, 0, 0));
	
		
		menu.add(getPlayBtn());
		menu.add(getPassiveOgre());
		menu.add(getGrumpyOgre());
		menu.add(getBtnSaveGame());
		menu.add(getBtnReturnToMainMenu());

		
	}
	
	private void startGame() {
		
		for(Row tempRow : theGrid.returnArray()) {
			for (Square tempSquare : tempRow.getTheRow()) {
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
			btnNewGame.setFont(new Font("Tahoma",Font.BOLD,30));
			btnNewGame.setBounds((int) (mainMenu.getWidth() * 0.55), (int) (mainMenu.getHeight() * 0.25), 250, 50);
			btnNewGame.setBackground(bgColor);
			btnNewGame.setBorder(BorderFactory.createEmptyBorder());
			btnNewGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mainMenu.setVisible(false);
					sizeOfGrid.setVisible(true);
				}
			});
		}
		return this.btnNewGame;
	}
	
	public JButton getStartGame() {
		if (btnStartGame == null) {
			btnStartGame = new JButton("Start Game with grid " + getIntSizeOfGrid() + "x" + getIntSizeOfGrid());
			btnStartGame.setFont(new Font("Tahoma",Font.BOLD,30));
			btnStartGame.setBounds((int) (mainMenu.getWidth() * 0.25), (int) (mainMenu.getHeight() * 0.55), 450, 50);
			btnStartGame.setBackground(bgColor);
			btnStartGame.setBorder(BorderFactory.createEmptyBorder());
			btnStartGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					theGrid = new Grid(getIntSizeOfGrid());
					grid.setLayout(new GridLayout(getIntSizeOfGrid(), getIntSizeOfGrid(), 0, 0));
					sizeOfGrid.setVisible(false);
					splitPane.setVisible(true);
					menu.setVisible(true);
					grid.setVisible(true);
					startGame();
					theGrid.startOgre();
					
					
				}
			});
		}
		return this.btnStartGame;
	}
	
	public JSlider getSlider () {
		if (slider == null) {
			slider = new JSlider(4,20);
			slider.setPaintTrack(true); 
			slider.setPaintTicks(true); 
			slider.setPaintLabels(true); 
			slider.setMajorTickSpacing(4); 
			slider.setMinorTickSpacing(1);
			slider.setBackground(bgColor);
			slider.setBounds((int)(sizeOfGrid.getWidth()*0.25), (int)(sizeOfGrid.getHeight()*0.2), 450, 200);
			slider.addChangeListener(this);
			
		}
		return this.slider;
	}
	
	public JButton getPlayBtn() {
		if (playBtn == null) {
			playBtn = new JButton("Play");
			playBtn.setBackground(buttonColor);
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
					if(theGrid.isEnemiesWon()) {
						//to do pop up message + return to main menu
					}
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
//		String output = "";
//		for(String enemy : theGrid.getEnemies().keySet()) {
//			for(Integer position : theGrid.getEnemies().get(enemy)) {
//				output+= enemy + " on position " + position +"\n";
//				
//			}
//		}
//		output += "Ogre on position " + ogrePosition;
//		textArea.setText(output);
	}
	
	public JButton getPassiveOgre () {
		if (passiveOgre == null) {
			passiveOgre = new JButton("Passive Mode");
			passiveOgre.setBackground(buttonColor);
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
			grumpyOgre.setBackground(buttonColor);
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
			btnSaveGame.setBackground(buttonColor);
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
			btnLoadGame.setFont(new Font("Tahoma",Font.BOLD,30));
			btnLoadGame.setBounds((int) (mainMenu.getWidth() * 0.55), (int) (mainMenu.getHeight() * 0.45), 250, 50);
			btnLoadGame.setBackground(bgColor);
			btnLoadGame.setBorder(BorderFactory.createEmptyBorder());
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
			btnQuitGame.setFont(new Font("Tahoma",Font.BOLD,30));
			btnQuitGame.setBounds((int) (mainMenu.getWidth() * 0.55), (int) (mainMenu.getHeight() * 0.65), 250, 50);
			btnQuitGame.setBackground(bgColor);
			btnQuitGame.setBorder(BorderFactory.createEmptyBorder());
			btnQuitGame.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					frame.dispose();
				}
			});
		}
		return this.btnQuitGame;
	}
	
	public JButton getBtnReturnToMainMenu () {
		if (returnToMainMenu == null) {
			returnToMainMenu = new JButton("Return To Main Menu");
			returnToMainMenu.setBackground(buttonColor);
			returnToMainMenu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mainMenu.setVisible(true);
					splitPane.setVisible(false);
					menu.setVisible(false);
					grid.setVisible(false);
					labelsList.clear();
					grid.removeAll();
				}
			});
		}
		return this.returnToMainMenu;
	}
	
	private void updateTheSizeOfGrid() {
		btnStartGame.setText("Start Game with grid " + getIntSizeOfGrid() + "x" + getIntSizeOfGrid());
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		setIntSizeOfGrid(slider.getValue());
		updateTheSizeOfGrid();
	}

	public int getIntSizeOfGrid() {
		return this.intSizeOfGrid;
	}

	public void setIntSizeOfGrid(int intSizeOfGrid) {
		this.intSizeOfGrid = intSizeOfGrid;
	}
}
