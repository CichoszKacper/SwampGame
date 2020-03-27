import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Grid {
    private ArrayList<Row> theGrid = new ArrayList <Row> ();    
    private Row row;
    private int selectOgreColumn;
	private int selectOgreRow;
	private Random randomOgre = new Random();
	private String gridOutput = "";
    
    public Grid() {

        for(int loop = 1; loop < 5; loop++) {
            row = new Row(loop);
            this.theGrid.add(row);
        }
    }
    
    public void startGrid () {
    	//Randomise start Ogre position on grid using imported Random class
    	
    	
    	do {
			selectOgreColumn = randomOgre.nextInt(4) + 1;
			selectOgreRow = randomOgre.nextInt(4) + 1;
    	}while(selectOgreColumn + selectOgreRow <3);
    	
    	//Start grid and place Ogre in randomised before position
    	for (Row tempRow : theGrid) {
    		for (Square tempSquare : tempRow.getTheRow()) {
    			
    			String gridDisplay = String.valueOf(tempSquare.getNumber()) + String.valueOf(tempRow.getNumber());
    			
    			//place Ogre in grid
    			if(tempRow.getNumber() == selectOgreRow && tempSquare.getNumber() == selectOgreColumn) {
    				tempSquare.placeTheOgre();
    				gridDisplay = tempSquare.getOgre().getName();
    			}
    			gridOutput += gridDisplay + "   ";
    		}//end looping RowsArray
    		gridOutput += "\n";
    	}//end looping theGrid
    	setGridOutput(gridOutput);
    }//end startGrid
    
    public void displayGrid (String grid) {
    	System.out.println(grid);
    }

    
    public void updateGrid () {
    	//Select new position of Ogre which can move max one position away from his current position.
    	//Have to exclude situation when position generator will select same position as current one as Ogre has to move
    	int originalOgreColumn = selectOgreColumn;
    	int originalOgreRow = selectOgreRow;
    	do {
    		selectOgreColumn = ThreadLocalRandom.current().nextInt(originalOgreColumn -1, originalOgreColumn + 2);
    		selectOgreRow = ThreadLocalRandom.current().nextInt(originalOgreRow -1, originalOgreRow + 2);
    	}while(selectOgreColumn < 1 || selectOgreColumn > 4 || selectOgreRow < 1 || selectOgreRow > 4 || (selectOgreColumn == originalOgreColumn && selectOgreRow == originalOgreRow));
    	
    	//Make previous square empty again and select new position for Ogre
    	//(Be careful about this when you will create Enemy class)!!!
    	gridOutput = "";
    	for(Row tempRow : theGrid) {
    		for(Square tempSquare : tempRow.getTheRow()) {
    			if(!tempSquare.isEmpty()) {
    				tempSquare.setEmpty(true);
    			}
    			
    			String gridDisplay = String.valueOf(tempSquare.getNumber()) + String.valueOf(tempRow.getNumber());
    			
    			//place Ogre in grid
    			if(tempRow.getNumber() == selectOgreRow && tempSquare.getNumber() == selectOgreColumn) {
    				tempSquare.placeTheOgre();
    				gridDisplay = tempSquare.getOgre().getName();
    			}
    			
    			gridOutput += gridDisplay + "   ";
    			
    		}
    		gridOutput += "\n";
    	}
    	setGridOutput(gridOutput);
    }
    
    
    public ArrayList<Row> returnArray () {
    	return this.theGrid;
    }

	public String getGridOutput() {
		return gridOutput;
	}

	public void setGridOutput(String gridOutput) {
		this.gridOutput = gridOutput;
	}   
    
}
