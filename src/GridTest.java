import static org.junit.Assert.*;

import java.util.ArrayList;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class GridTest {
	
	private Grid grid;
	

	@Test
	public void testStartOgre() {
		grid = new Grid();
		grid.startOgre();
		boolean isOgreOnGrid = false;
		for(Row tempRow : grid.returnArray()) {
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
		grid = new Grid();
		grid.updateOgrePosition(3, 3);
		for(Row tempRow : grid.returnArray()) {
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
		grid = new Grid();
		for(int i=0 ; i < 20 ; i++) {
			grid.placeNewEnemy();
		}
		int counter = grid.getEnemies().size();
		assertTrue(counter>0);
	}

	@Test
	public void testUpdateEnemyPosition() {
		grid = new Grid();
		int newPosition = grid.updateEnemyPosition("Donkey", 33);
		
		assertTrue(21 < newPosition && newPosition < 45);
	}
	
	@Test
	public void testFight () {
		grid = new Grid ();
		grid.startOgre();
		int ogreColumn = 0;
		int ogreRow = 0;
		for(Row tempRow : grid.returnArray()) {
    		for(Square tempSquare : tempRow.getTheRow()) {
    			if(!tempSquare.isEmpty() && tempSquare.getName().equals("Ogre") ) {
    				ogreColumn = tempSquare.getNumber();
    				ogreRow = tempRow.getNumber();
    			}
    		}
    	}
		int ogrePosition = Integer.parseInt(String.valueOf(ogreColumn) + String.valueOf(ogreRow));
		ArrayList <Integer> tempList = new ArrayList <Integer>();
		tempList.add(ogrePosition);
		grid.getEnemies().put("Donkey", tempList);
		grid.getEnemies().put("Snake", tempList);
		
		grid.fight();
		
		assertTrue(grid.isEnemiesWon());
	}
	
	@Test
	public void testChangeOgreMood () {
		grid = new Grid();
		grid.changeOgreMood(2);
		assertEquals(grid.getOgreMood(), Integer.valueOf(2));
	}
	
	@Test
	public void testUpdateInformation() {
		grid = new Grid();
		ArrayList <Integer> tempList = new ArrayList <Integer>();
		tempList.add(22);
		grid.getEnemies().put("Donkey", tempList); 
		grid.setOgreMood(2);
		grid.startOgre();
		grid.updateInformation();
		
		assertEquals(2, grid.getSelectOgreColumn());
		assertEquals(2, grid.getSelectOgreRow());
		assertEquals(Integer.valueOf(2), grid.getOgreMood());
	
	}
}
