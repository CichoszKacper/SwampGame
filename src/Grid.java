import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;



public class Grid {
	private ArrayList<Row> theGrid = new ArrayList <Row> ();    
	private Row row;
	private int selectOgreColumn;
	private int selectOgreRow;
	private Random random = new Random();
	private String gridOutput = "";
	private Hashtable<String, ArrayList<Integer>> enemies = new Hashtable<String,ArrayList<Integer>>();
	private Integer counter = 0;
	private Integer ogreMood = 1;
	private Integer passiveOgre = 1;
	private Integer grumpyOgre = 2;
	private File enemiesFile = new File("EnemiesFile.txt");
	private File saveGame = new File("SaveGame.txt");
	private int[] savedInformation = {getSelectOgreColumn(), getSelectOgreRow(), getOgreMood(), getSizeOfGrid()};
	private int sizeOfGrid;
	private boolean enemiesWon = false;

	
	
	//Constructor to create full grid with one variable "intSizeOfGrid" choose by player
	public Grid(int intSizeOfGrid) {

		for(int loop = 1; loop < intSizeOfGrid; loop++) {
			row = new Row(loop, intSizeOfGrid);
			this.theGrid.add(row);

		}
	}

	//Method to place ogre on the grid
	public void startOgre () {

		Boolean isOgreOnGrid = false;

		//Randomise start Ogre position on grid using imported Random class. 
		//Function is in the loop as long as position to enter is 1x1.

		do {
			selectOgreColumn = random.nextInt(returnRows().size()) + 1;
			selectOgreRow = random.nextInt(returnRows().size()) + 1;
		}while(selectOgreColumn + selectOgreRow <3);

		for (Row tempRow : theGrid) {
			for (Square tempSquare : tempRow.getTheRow()) {
				if(tempSquare.getName().equals("Ogre")) {
					isOgreOnGrid = true;
				}

			}//end looping RowsArray

		}//end looping theGrid

		//Start grid and place Ogre in position which was randomised before
		if(isOgreOnGrid.equals(false)) {
			for (Row tempRow : theGrid) {
				for (Square tempSquare : tempRow.getTheRow()) {

					//place Ogre in grid
					if(tempRow.getNumber() == selectOgreRow && tempSquare.getNumber() == selectOgreColumn) {
						tempSquare.placeTheOgre();
					}

				}//end looping RowsArray

			}//end looping theGrid
		}
	}//end startGrid


	public void updateOgrePosition (int selectedColumn, int selectedRow) {
		//Select new position for Ogre who can move max one position away from his current position.
		//Have to exclude situation when position generator will generate same position as current one as Ogre has to move
		//Have to make the range of move for both column and row from -1 to 1 of the previous position. 

		int originalOgreColumn = selectedColumn;
		int originalOgreRow = selectedRow;
		do {
			selectOgreColumn = ThreadLocalRandom.current().nextInt(originalOgreColumn -1, originalOgreColumn + 2); //original position + 2 because Random function doesn't include last number in range
			selectOgreRow = ThreadLocalRandom.current().nextInt(originalOgreRow -1, originalOgreRow + 2);
		}while(selectOgreColumn < 1 || selectOgreColumn > returnRows().size() || selectOgreRow < 1 || selectOgreRow > returnRows().size() || (selectOgreColumn == originalOgreColumn && selectOgreRow == originalOgreRow));

		//Make previous square empty again and select new position for Ogre

		for(Row tempRow : theGrid) {
			for(Square tempSquare : tempRow.getTheRow()) {
				if(!tempSquare.isEmpty() && tempSquare.getName().equals("Ogre") ){
					tempSquare.setEmpty(true);
					tempSquare.setName("o");
				}//end of inside if

				//place Ogre in grid
				if(tempRow.getNumber() == selectOgreRow && tempSquare.getNumber() == selectOgreColumn) {
					tempSquare.placeTheOgre();
				}//end placing ogre			
			}//end looping through squares
		}//end looping through rows
	}//end method

	//Method to place a new enemy. Works similar way to method placing Ogre in the beginning of the game.
	public void placeNewEnemy () {
		int randomEnemy = random.nextInt(3);
		int chanceToEnter = random.nextInt(3);

		if (chanceToEnter == 0) {
			for (Row tempRow : theGrid) {
				for (Square tempSquare : tempRow.getTheRow()) {
					if(tempRow.getNumber() == 1 && tempSquare.getNumber() == 1) {

						tempSquare.placeNewEnemy(randomEnemy);

						ArrayList<Integer> tempList = null;
						if (enemies.containsKey(tempSquare.getName())) {
							tempList = enemies.get(tempSquare.getName());
							if(tempList == null)
								tempList = new ArrayList<Integer>();
							tempList.add(11);  
						} else {
							tempList = new ArrayList<Integer>();
							tempList.add(11);               
						}
						enemies.put(tempSquare.getName(),tempList);
					}   			
				}//end looping RowsArray 
			}//end looping theGrid
		}//end if with chance to enter
	}//end method

	
	
	//Method to update enemy position. First take two parameters from HashTable: enemy name and enemy's position. 
	//Then randomly select new position and return it as a integer.

	public Integer updateEnemyPosition (String enemy, Integer position) {
		int originalEnemyColumn = 0;
		int originalEnemyRow = 0;
		int newEnemyColumn = 0;
		int newEnemyRow = 0;

		//Get separate column and row number from position
		originalEnemyColumn = position/10;
		originalEnemyRow = position%10;
		
		//Randomly select new position
		do {
			newEnemyColumn = ThreadLocalRandom.current().nextInt(originalEnemyColumn -1, originalEnemyColumn + 2);
			newEnemyRow = ThreadLocalRandom.current().nextInt(originalEnemyRow -1, originalEnemyRow + 2);
		}while(newEnemyColumn < 1 || newEnemyColumn > returnRows().size()  || newEnemyRow < 1 || newEnemyRow > returnRows().size() || (newEnemyColumn == originalEnemyColumn && newEnemyRow == originalEnemyRow));

		
		for(Row tempRow : theGrid) {
			for(Square tempSquare : tempRow.getTheRow()) {
				//Empty previously occupied square
				if(tempSquare.getNumber() == originalEnemyColumn && tempRow.getNumber() == originalEnemyRow) {
					tempSquare.setEmpty(true);
				}
				//Move enemy to new position which was previously randomised
				if(tempSquare.getNumber() == newEnemyColumn && tempRow.getNumber() == newEnemyRow) {
					if(enemy == "Donkey") {
						tempSquare.placeNewEnemy(0);
					}
					if(enemy == "Snake") {
						tempSquare.placeNewEnemy(1);
					}
					if(enemy == "Parrot") {
						tempSquare.placeNewEnemy(2);
					}
				}//end of moving enemy
			}//end looping squares
		}//end of looping rows

		
		//Return new position as integer value
		return position = Integer.valueOf((String.valueOf(newEnemyColumn) + String.valueOf(newEnemyRow)));
		
	}//end of updateEnemyPosition

	
	//Method for combat between ogre and enemies
	public void fight () {
		Integer tempSquarePosition = 0;
		ArrayList <Integer> deletePosition = new ArrayList<Integer>();

		//Create flag for enemies victory and counter for number of enemies on same spot as ogre
		enemiesWon = false;
		counter = 0;
		
		//Looping through the grid and enemies to check if they are on the same square
		for(Row tempRow : theGrid) {
			for(Square tempSquare : tempRow.getTheRow()) {
				if(tempSquare.getName().equalsIgnoreCase("Ogre")) {
					if (!enemies.isEmpty()) {
						for(String enemy : enemies.keySet()) {
							for(Integer position : enemies.get(enemy)) {
								//Change value of tempSquare position into integer in order to compare it with enemies position
								tempSquarePosition = Integer.valueOf(String.valueOf(tempSquare.getNumber()) + String.valueOf(tempRow.getNumber()));
								//Verifying if positions are the same
								if(tempSquarePosition == position) {
									//Adding enemy to the counter
									counter++;
									//If number of enemies is higher then value of Ogre Mood (1 or 2) change the flag into true
									if(counter>ogreMood) {
										enemiesWon = true;

									}
								}
							}//end looping enemies positions
						}//end looping enemies
					}//end checking if enemies exist
				}//end if square named Ogre
			}//end looping squares
		}//end looping rows
		
		
		//In case there is an enemy/enemies on the same square but not enough to kick hek out
		//adding position to temporary ArrayList.
		int countDefeatedEnemies = 0;
		if(counter <= ogreMood) {
			for(String enemy : enemies.keySet()) {
				for(Integer position : enemies.get(enemy)) { 	
					if(position.equals(tempSquarePosition)) {
						deletePosition.add(position);
						countDefeatedEnemies++;
					}
				}
			}
		}
		
		
		//Creating pop up message informing player about number of enemies defeated
		String output = "Hek kicked out ";
		if(countDefeatedEnemies == 1) {
			output += "one enemy from the swamp!";
			JOptionPane.showMessageDialog(null, output);
		}
		if( 1 < countDefeatedEnemies && countDefeatedEnemies <= ogreMood) {
			output += ogreMood + " enemies from the swamp!";
			JOptionPane.showMessageDialog(null, output);
		}


		//Deleting all enemies on same square as Hek if defeated
		for(String enemy : enemies.keySet()) {
			enemies.get(enemy).removeAll(deletePosition);
		}
	}

	
	//Method to to save enemies as OutputStream
	public void saveEnemies () throws IOException {

		String enemiesPath = enemiesFile.getAbsolutePath();
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(enemiesPath));
		outputStream.writeObject(enemies);
		outputStream.close();
	}

	
	//Method to load enemies from InputStream
	@SuppressWarnings("unchecked")
	public void loadEnemies() throws ClassNotFoundException, IOException {

		String enemiesPath = enemiesFile.getAbsolutePath();
		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(enemiesPath));

		setEnemies((Hashtable<String,ArrayList<Integer>>)inputStream.readObject());

		inputStream.close();
	}
	
	//Method to save game data like: ogre position and ogre mood
	public void saveGame() throws IOException {

		int [] information = {getSelectOgreColumn(), getSelectOgreRow(), getOgreMood(), getSizeOfGrid()};

		BufferedWriter outputWriter = new BufferedWriter(new FileWriter(saveGame));

		for(int i = 0; i<information.length; i++) {
			outputWriter.write((information[i] + "\n"));
		}
		outputWriter.close();
	}

	//Method to load game data. Data are stored in variable "savedInformation"
	public void loadGame() throws NumberFormatException, IOException {

		BufferedReader inputReader = new BufferedReader(new FileReader(saveGame));

		for(int i = 0; i<savedInformation.length;i++) {
			savedInformation[i] = Integer.parseInt(inputReader.readLine());
			System.out.println(savedInformation[i]);
		}

		inputReader.close();
	}

	
	//Method to update all loaded information
	public void updateInformation() {
		selectOgreColumn = savedInformation[0];
		selectOgreRow = savedInformation[1];
		setOgreMood(savedInformation[2]);
		setSizeOfGrid(savedInformation[3]);
		
		for(Row tempRow : returnRows()) {
			for(Square tempSquare : tempRow.getTheRow()) {
				for(String enemy : enemies.keySet()) {
					for(Integer position : enemies.get(enemy)){
						Integer originalEnemyColumn = position/10;
						Integer originalEnemyRow = position%10;

						if(tempRow.getNumber() == originalEnemyRow && tempSquare.getNumber() == originalEnemyColumn) {
							tempSquare.setName(enemy);
						}
					}
				}
			}
		}
	}
	
	
	//Getters and setters
	public ArrayList<Row> returnRows () {
		return this.theGrid;
	}

	public String getGridOutput() {
		return this.gridOutput;
	}

	public void setGridOutput(String gridOutput) {
		this.gridOutput = gridOutput;
	}

	public int getSelectOgreColumn() {
		return this.selectOgreColumn;
	}

	public int getSelectOgreRow() {
		return this.selectOgreRow;
	}

	public void setEnemies(Hashtable<String, ArrayList<Integer>> enemies) {
		this.enemies = enemies;
	}

	public Hashtable<String, ArrayList<Integer>> getEnemies() {
		return this.enemies;
	}

	public Integer getOgreMood() {
		return ogreMood;
	}

	public void setOgreMood(Integer ogreMood) {
		this.ogreMood = ogreMood;
	}

	public Integer getPassiveOgre() {
		return passiveOgre;
	}

	public Integer getGrumpyOgre() {
		return this.grumpyOgre;
	}

	public boolean isEnemiesWon() {
		return this.enemiesWon;
	}

	public int[] getSavedInformation() {
		return this.savedInformation;
	}

	public void setSavedInformation(int[] savedInformation) {
		this.savedInformation = savedInformation;
	}

	public int getSizeOfGrid() {
		return this.sizeOfGrid;
	}

	public void setSizeOfGrid(int sizeOfGrid) {
		this.sizeOfGrid = sizeOfGrid;
	}

}
