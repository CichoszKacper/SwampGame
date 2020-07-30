import static org.junit.Assert.*;

import org.junit.Test;

public class SquareTest {
	
	private Grid grid;
	
	@Test
	public void testPlaceTheOgre() {
		grid = new Grid(8);
		int ogreRow = 2;
		int ogreColumn = 2;
		
		
		//Placing ogre on the grid
		for(Row tempRow : grid.returnRows()) {
			for(Square tempSquare : tempRow.getTheRow()) {
				if(tempSquare.getNumber() == ogreColumn && tempRow.getNumber() == ogreRow) {
					tempSquare.placeTheOgre();
				}
			}
		}
		
		//Checking if ogre is on the grid
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
	public void testPlaceNewEnemy() {
		grid = new Grid(8);
		SimpleFactory enemyFactory = new SimpleFactory();
		
		int enemyRow = 1;
		int enemyColumn = 1;
		int enemyType = 1; // Number 1 should return "Snake"
		String enemyName = "";
		Enemy theEnemy = enemyFactory.createEnemy(enemyType);
		
		
		//Placing Snake on the grid
		for(Row tempRow : grid.returnRows()) {
			for(Square tempSquare : tempRow.getTheRow()) {
				if(tempSquare.getNumber() == enemyColumn && tempRow.getNumber() == enemyRow) {
					tempSquare.placeNewEnemy(enemyType);
					enemyName = tempSquare.getName();
				}
			}
		}
		
		assertTrue(enemyName.equals(theEnemy.getName()));
		
	}

}
