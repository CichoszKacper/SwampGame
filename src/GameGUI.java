import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JPanel;


public class GameGUI {
	
	private Grid theGrid = new Grid();
	private Row theRow = new Row();
	private Square theSquare = new Square();
	private Ogre ogre;
	
	private JFrame frame;

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
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout (4,4));
		
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		
		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(200, 200);
		panel.setMinimumSize(minimumSize);
		panel_1.setMinimumSize(minimumSize);
		
//		ArrayList <JLabel> labelsList = new ArrayList<JLabel>();
//		for(Row tempRow : theGrid.returnArray()) {
//			for(Square tempSquare : tempRow.getTheRow()) {
//				if(tempSquare.isEmpty()) {
//					JLabel label = new JLabel(tempSquare.getOgre().getName());
//					labelsList.add(label);
//					label.setBorder(BorderFactory.createEtchedBorder(Color.blue, Color.black));
//					frame.getContentPane().add(label);
//				}
//				if(!tempSquare.isEmpty()) {
//					JLabel label = new JLabel(ogre.getName());
//					labelsList.add(label);
//					label.setBorder(BorderFactory.createEtchedBorder(Color.blue, Color.black));
//					frame.getContentPane().add(label);
//				}				
//			}
//		}			
	}     
}
