import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
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



	public Grid() {
        
		for(int loop = 1; loop < 5; loop++) {
            row = new Row(loop);
            this.theGrid.add(row);
        }
    }
    
    public void startOgre () {
    	//Randomise start Ogre position on grid using imported Random class
    	  	
    	do {
			selectOgreColumn = random.nextInt(4) + 1;
			selectOgreRow = random.nextInt(4) + 1;
    	}while(selectOgreColumn + selectOgreRow <3);
    	
    	//Start grid and place Ogre in randomised before position
    	for (Row tempRow : theGrid) {
    		for (Square tempSquare : tempRow.getTheRow()) {
   			
    			//place Ogre in grid
    			if(tempRow.getNumber() == selectOgreRow && tempSquare.getNumber() == selectOgreColumn) {
    				tempSquare.placeTheOgre();
    			}

    		}//end looping RowsArray

    	}//end looping theGrid

    }//end startGrid
    
    public void displayGrid () {
    	String gridOutput = "";
    	for (Row tempRow : theGrid) {
    		for (Square tempSquare : tempRow.getTheRow()) {
    			gridOutput += tempSquare.getName() + "   ";
    		
    		}//end looping RowsArray
    		gridOutput += "\n";
    	}//end looping theGrid
    	System.out.println(gridOutput);
    }

    
    public void updateOgrePosition (int selectedColumn, int selectedRow) {
    	//Select new position of Ogre or Enemy who can move max one position away from his current position.
    	//Have to exclude situation when position generator will generate same position as current one as Ogre or Enemy has to move
    	
    	int originalOgreColumn = selectedColumn;
    	int originalOgreRow = selectedRow;
    	do {
    		selectOgreColumn = ThreadLocalRandom.current().nextInt(originalOgreColumn -1, originalOgreColumn + 2);
    		selectOgreRow = ThreadLocalRandom.current().nextInt(originalOgreRow -1, originalOgreRow + 2);
    	}while(selectOgreColumn < 1 || selectOgreColumn > 4 || selectOgreRow < 1 || selectOgreRow > 4 || (selectOgreColumn == originalOgreColumn && selectOgreRow == originalOgreRow));
    	
    	//Make previous square empty again and select new position for Ogre
    	//(Be careful about this when you will create Enemy class)!!!

    	for(Row tempRow : theGrid) {
    		for(Square tempSquare : tempRow.getTheRow()) {
    			if(!tempSquare.isEmpty() && tempSquare.getName().equals("Ogre") ){
    				tempSquare.setEmpty(true);
    				tempSquare.setName("0");
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
        				if(randomEnemy == 0) {
        					tempSquare.placeTheDonkey();       					
        				}
        				if(randomEnemy == 1) {
        					tempSquare.placeTheSnake();
        				}
        				if(randomEnemy == 2) {
        					tempSquare.placeTheParrot();
        				}
        				
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
    	}while(newEnemyColumn < 1 || newEnemyColumn > 4 || newEnemyRow < 1 || newEnemyRow > 4 || (newEnemyColumn == originalEnemyColumn && newEnemyRow == originalEnemyRow));


    	for(Row tempRow : theGrid) {
    		for(Square tempSquare : tempRow.getTheRow()) {
    			if(tempSquare.getNumber() == originalEnemyColumn && tempRow.getNumber() == originalEnemyRow) {
    				tempSquare.setEmpty(true);
    			}
    			
    			
    			if(tempSquare.getNumber() == newEnemyColumn && tempRow.getNumber() == newEnemyRow) {
    				if(enemy == "Donkey") {
    					tempSquare.placeTheDonkey();
    				}
    				if(enemy == "Snake") {
    					tempSquare.placeTheSnake();
    				}
    				if(enemy == "Parrot") {
    					tempSquare.placeTheParrot();
    				}
    			}
    		}
    	}

    	return position = Integer.valueOf((String.valueOf(newEnemyColumn) + String.valueOf(newEnemyRow)));
    }//end of updateEnemyPosition
    
    public void fight () {
    	Integer tempSquarePosition = 0;
    	ArrayList <Integer> deletePosition = new ArrayList<Integer>();
    	for(String key : enemies.keySet()) {
    		System.out.println(key + ": " + enemies.get(key));
    	}
    	
    	for(Row tempRow : theGrid) {
    		for(Square tempSquare : tempRow.getTheRow()) {
    			if(tempSquare.getName().equalsIgnoreCase("Ogre")) {
    				if (!enemies.isEmpty()) {
        				for(String enemy : enemies.keySet()) {
        					for(Integer position : enemies.get(enemy)) {
        						tempSquarePosition = Integer.valueOf(String.valueOf(tempSquare.getNumber()) + String.valueOf(tempRow.getNumber()));
        						if(tempSquarePosition == position) {
        							counter++;
        							if(counter>2) {
//        								game over! Enemy win
        								System.out.println("Enemies won");
        							}
        						}
        						
        					}
        				}
    				}

    			}
    		}
    	}
    	if(counter == 1 || counter == 2) {
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

	public Hashtable<String, ArrayList<Integer>> getEnemies() {
		return this.enemies;
	}  
	
	
	
}
