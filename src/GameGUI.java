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
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;


public class GameGUI {
	
	private Grid theGrid = new Grid();
	private JButton startGameBtn;
	private JFrame frame;
	private JButton playBtn;
	private JPanel menu;
	private JPanel grid;
	private ArrayList <JLabel> labelsList = new ArrayList <JLabel>();
	private JTextArea textArea;



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
		frame.setBounds(100, 100, 450, 300);
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
		grid.setLayout(new GridLayout(4, 4, 0, 0));
	
		
		
		menu.add(getPlayBtn());
		menu.add(getStartGameBtn());
		
		textArea = new JTextArea();
		menu.add(textArea);
		
		
		
	}

	/**
	 * Initialise the contents of the frame.
	 */
	
	private void startGame() {

		for(Row tempRow : theGrid.returnArray()) {
			for (Square tempSquare : tempRow.getTheRow()) {
				System.out.println("grid dziala");
				JLabel label = new JLabel();
				label.setText(tempSquare.getName());
				labelsList.add(label);
				grid.add(label);
			}
		}
		grid.setVisible(true);
	}
	
	
	private void updateGrid ()	{

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
		textArea.setText(output);
	}
	
	public JButton getPlayBtn() {
		if (playBtn == null) {
			playBtn = new JButton("Play");
			playBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					updateGrid();
					for(String enemy : theGrid.getEnemies().keySet()) {
			        	for(Integer position : theGrid.getEnemies().get(enemy)) {
			        		
			        		theGrid.getEnemies().get(enemy).set(theGrid.getEnemies().get(enemy).indexOf(position), theGrid.updateEnemyPosition(enemy, position));
			        	}		        	
			        }
					theGrid.placeNewEnemy();
					theGrid.updateOgrePosition(theGrid.getSelectOgreColumn(), theGrid.getSelectOgreRow());
					theGrid.fight();
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
				}
			});
		}
		return this.startGameBtn;
	}
	
	
	
}
