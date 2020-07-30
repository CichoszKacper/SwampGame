import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class GridTest {
	
	private Grid grid;
	

	@Test
	public void testStartOgre() {
		grid = new Grid(8);
		grid.startOgre();
		
		//Checking is ogre is on the grid
		boolean isOgreOnGrid = false;
		for(Row tempRow : grid.returnRows()) {
			for(Square tempSquare : tempRow.getTheRow()) {
				if(tempSquare.getName().equals("Ogre")) {
					isOgreOnGrid = true;
				}
			}
		}
		assertTrue(isOgreOnGrid);
	}

	@Test
	public void testUpdateOgrePosition() {
		int ogreColumn = 0;
		int ogreRow = 0;
		grid = new Grid(8);
		grid.updateOgrePosition(3, 3);
		for(Row tempRow : grid.returnRows()) {
    		for(Square tempSquare : tempRow.getTheRow()) {
    			if(!tempSquare.isEmpty() && tempSquare.getName().equals("Ogre") ) {
    				ogreColumn = tempSquare.getNumber();
    				ogreRow = tempRow.getNumber();
    			}
    		}
    	}
		assertTrue(1< ogreColumn && ogreColumn <5);
		assertTrue(1< ogreRow && ogreRow < 5);
	}

	@Test
	public void testPlaceNewEnemy() {
		grid = new Grid(8);
		for(int i=0 ; i < 20 ; i++) {
			grid.placeNewEnemy();
		}
		int counter = grid.getEnemies().size();
		assertTrue(counter>0);
	}

	@Test
	public void testUpdateEnemyPosition() {
		grid = new Grid(8);
		int newPosition = grid.updateEnemyPosition("Donkey", 33);
		
		assertTrue(21 < newPosition && newPosition < 45);
	}
	
	@Test
	public void testFight () {
		grid = new Grid (8);
		grid.startOgre();
		int ogreColumn = 0;
		int ogreRow = 0;
		//Return the column and row of the ogre
		for(Row tempRow : grid.returnRows()) {
    		for(Square tempSquare : tempRow.getTheRow()) {
    			if(!tempSquare.isEmpty() && tempSquare.getName().equals("Ogre") ) {
    				ogreColumn = tempSquare.getNumber();
    				ogreRow = tempRow.getNumber();
    			}
    		}
    	}
		//Change column and row of ogre into int value
		int ogrePosition = Integer.parseInt(String.valueOf(ogreColumn) + String.valueOf(ogreRow));
		
		//Placing two enemies on the same position as ogre
		ArrayList <Integer> tempList = new ArrayList <Integer>();
		tempList.add(ogrePosition);
		grid.getEnemies().put("Donkey", tempList);
		grid.getEnemies().put("Snake", tempList);
		
		grid.fight();
		
		assertTrue(grid.isEnemiesWon());
	}
	
	@Test
	public void testLoadGame() {
		grid = new Grid(8);
		
		grid.startOgre();
		grid.setOgreMood(grid.getGrumpyOgre());
		
		try {
			grid.saveGame();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(2, grid.getSavedInformation()[2]);
	}
	
	@Test
	public void testUpdateInformation() {
		grid = new Grid(8);

		int [] tempInformation = {3,4,1};
		grid.setSavedInformation(tempInformation);
		grid.setOgreMood(2);
		grid.startOgre();
		grid.updateInformation();
		
		assertEquals(3, grid.getSelectOgreColumn());
		assertEquals(4, grid.getSelectOgreRow());
		assertEquals(Integer.valueOf(1), grid.getOgreMood());
	
	}
	

}
