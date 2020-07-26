import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	private int[] savedInformation = {getSelectOgreColumn(), getSelectOgreRow(), getOgreMood()};
	private int sizeOfGrid;
	private boolean enemiesWon = false;


	public Grid(int intSizeOfGrid) {
		
		for(int loop = 1; loop < intSizeOfGrid; loop++) {
            row = new Row(loop, intSizeOfGrid);
            this.theGrid.add(row);
                     
        }
    }

	public void startOgre () {
    	
    	Boolean isOgreOnGrid = false;
    	
    	//Randomise start Ogre position on grid using imported Random class
    	  	
    	do {
			selectOgreColumn = random.nextInt(returnArray().size()) + 1;
			selectOgreRow = random.nextInt(returnArray().size()) + 1;
    	}while(selectOgreColumn + selectOgreRow <3);
    	
    	for (Row tempRow : theGrid) {
    		for (Square tempSquare : tempRow.getTheRow()) {
    			if(tempSquare.getName().equals("Ogre")) {
    				isOgreOnGrid = true;
    			}

    		}//end looping RowsArray

    	}//end looping theGrid
    	
    	//Start grid and place Ogre in randomised before position
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
    	//Select new position of Ogre or Enemy who can move max one position away from his current position.
    	//Have to exclude situation when position generator will generate same position as current one as Ogre or Enemy has to move
    	
    	int originalOgreColumn = selectedColumn;
    	int originalOgreRow = selectedRow;
    	do {
    		selectOgreColumn = ThreadLocalRandom.current().nextInt(originalOgreColumn -1, originalOgreColumn + 2);
    		selectOgreRow = ThreadLocalRandom.current().nextInt(originalOgreRow -1, originalOgreRow + 2);
    	}while(selectOgreColumn < 1 || selectOgreColumn > returnArray().size() || selectOgreRow < 1 || selectOgreRow > returnArray().size() || (selectOgreColumn == originalOgreColumn && selectOgreRow == originalOgreRow));
    	
    	//Make previous square empty again and select new position for Ogre

    	for(Row tempRow : theGrid) {
    		for(Square tempSquare : tempRow.getTheRow()) {
    			if(!tempSquare.isEmpty() && tempSquare.getName().equals("Ogre") ){
    				tempSquare.setEmpty(true);
    				tempSquare.setName("o");
    			}
    			
    			//place Ogre in grid
    			if(tempRow.getNumber() == selectOgreRow && tempSquare.getNumber() == selectOgreColumn) {
    				tempSquare.placeTheOgre();
    			}  			
    		}
    	}
    }
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
    	}
    }
    
    //Method to update enemy position. First take two parameters from HashTable: enemy name and enemy's position. Then randomly select new position
    //and return it as a integer.
    
    public Integer updateEnemyPosition (String enemy, Integer position) {
    	int originalEnemyColumn = 0;
    	int originalEnemyRow = 0;
    	int newEnemyColumn = 0;
    	int newEnemyRow = 0;


    	originalEnemyColumn = position/10;
    	originalEnemyRow = position%10;

    	do {
    		newEnemyColumn = ThreadLocalRandom.current().nextInt(originalEnemyColumn -1, originalEnemyColumn + 2);
    		newEnemyRow = ThreadLocalRandom.current().nextInt(originalEnemyRow -1, originalEnemyRow + 2);
    	}while(newEnemyColumn < 1 || newEnemyColumn > returnArray().size()  || newEnemyRow < 1 || newEnemyRow > returnArray().size() || (newEnemyColumn == originalEnemyColumn && newEnemyRow == originalEnemyRow));


    	for(Row tempRow : theGrid) {
    		for(Square tempSquare : tempRow.getTheRow()) {
    			if(tempSquare.getNumber() == originalEnemyColumn && tempRow.getNumber() == originalEnemyRow) {
    				tempSquare.setEmpty(true);
    			}
    			
    			
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
    			}
    		}
    	}

    	return position = Integer.valueOf((String.valueOf(newEnemyColumn) + String.valueOf(newEnemyRow)));
    }//end of updateEnemyPosition
    
    public void fight () {
    	Integer tempSquarePosition = 0;
    	ArrayList <Integer> deletePosition = new ArrayList<Integer>();

    	counter = 0;
    	for(Row tempRow : theGrid) {
    		for(Square tempSquare : tempRow.getTheRow()) {
    			if(tempSquare.getName().equalsIgnoreCase("Ogre")) {
    				if (!enemies.isEmpty()) {
        				for(String enemy : enemies.keySet()) {
        					for(Integer position : enemies.get(enemy)) {
        						tempSquarePosition = Integer.valueOf(String.valueOf(tempSquare.getNumber()) + String.valueOf(tempRow.getNumber()));
        						if(tempSquarePosition == position) {
        							counter++;
        							if(counter>ogreMood) {
//        								game over! Enemy win
        								enemiesWon = true;
        								System.out.println("Enemies won");
        							}
        						}
        						
        					}
        				}
    				}

    			}
    		}
    	}
    	if(counter <= ogreMood) {
    		for(String enemy : enemies.keySet()) {
    			for(Integer position : enemies.get(enemy)) { 	
    				if(position.equals(tempSquarePosition)) {
    					deletePosition.add(position);  					
    				}
    			}
    		}
    	}
    	for(String enemy : enemies.keySet()) {
    		enemies.get(enemy).removeAll(deletePosition);
    	}
    	System.out.println(counter);
    }
    
    public void changeOgreMood(Integer ogreMood) {
    	setOgreMood(ogreMood);
    }
    
    public void saveEnemies () throws IOException {

    	String enemiesPath = enemiesFile.getAbsolutePath();
    	ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(enemiesPath));
    	outputStream.writeObject(enemies);
    	outputStream.close();
    }
    
    @SuppressWarnings("unchecked")
	public void loadEnemies() throws ClassNotFoundException, IOException {
    	
    	String enemiesPath = enemiesFile.getAbsolutePath();
    	ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(enemiesPath));
    	
    	setEnemies((Hashtable<String,ArrayList<Integer>>)inputStream.readObject());
    	
    	inputStream.close();
    }
    
    public void saveGame() throws IOException {
    	
    	int [] information = {getSelectOgreColumn(), getSelectOgreRow(), getOgreMood()};
    	setSavedInformation(information);
    	
    	BufferedWriter outputWriter = new BufferedWriter(new FileWriter(saveGame));
    	
    	for(int i = 0; i<savedInformation.length; i++) {
    		outputWriter.write((savedInformation[i] + "\n"));
    	}
    	outputWriter.close();
    }
    
    public void loadGame() throws NumberFormatException, IOException {
    	
    	BufferedReader inputReader = new BufferedReader(new FileReader(saveGame));
    	
    	for(int i = 0; i<savedInformation.length;i++) {
    		savedInformation[i] = Integer.parseInt(inputReader.readLine());
    	}
    	
    	inputReader.close();
    }
    
    public void updateInformation() {
    	selectOgreColumn = savedInformation[0];
    	selectOgreRow = savedInformation[1];
    	setOgreMood(savedInformation[2]);
    	
    	for(Row tempRow : returnArray()) {
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
    
    public ArrayList<Row> returnArray () {
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

	public void setPassiveOgre(Integer passiveOgre) {
		this.passiveOgre = passiveOgre;
	}

	public Integer getGrumpyOgre() {
		return this.grumpyOgre;
	}

	public void setGrumpyOgre(Integer grumpyOgre) {
		this.grumpyOgre = grumpyOgre;
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
