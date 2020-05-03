public class Demo {

    public static void main (String [] args){
        Grid grid = new Grid();
//        
//        grid.startOgre();
//        grid.displayGrid();
//        
        for(String enemy : grid.getEnemies().keySet()) {
        	for(Integer position : grid.getEnemies().get(enemy)) {
        		
        		grid.getEnemies().get(enemy).set(grid.getEnemies().get(enemy).indexOf(position), grid.updateEnemyPosition(enemy, position));
        	}
        	
        }
//        grid.placeNewEnemy();
//        grid.updateOgrePosition(grid.getSelectOgreColumn(), grid.getSelectOgreRow());
//        grid.fight();
//        grid.displayGrid();	       
//        for(String enemy : grid.getEnemies().keySet()) {
//        	for(Integer position : grid.getEnemies().get(enemy)) {
//        		
//        		grid.getEnemies().get(enemy).set(grid.getEnemies().get(enemy).indexOf(position), grid.updateEnemyPosition(enemy, position));
//        	}        	
//        }       
    	
    	GameGUI gameGUI = new GameGUI();
    	gameGUI.main(args);
    }
}
