import static org.junit.Assert.*;

import org.junit.Test;

public class SquareTest {
	
	private Grid grid;
	
	@Test
	public void testPlaceTheOgre() {
		grid = new Grid();
		int ogreRow = 2;
		int ogreColumn = 2;
		
		for(Row tempRow : grid.returnArray()) {
			for(Square tempSquare : tempRow.getTheRow()) {
				if(tempSquare.getNumber() == ogreColumn && tempRow.getNumber() == ogreRow) {
					tempSquare.placeTheOgre();
				}
			}
		}
		
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
	public void testPlaceNewEnemy() {
		grid = new Grid();
		SimpleFactory enemyFactory = new SimpleFactory();
		
		int enemyRow = 1;
		int enemyColumn = 1;
		int enemyType = 1; // Number 1 should return "Snake"
		String enemyName = "";
		Enemy theEnemy = enemyFactory.createEnemy(enemyType);
		
		for(Row tempRow : grid.returnArray()) {
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
