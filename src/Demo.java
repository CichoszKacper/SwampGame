public class Demo {

    public static void main (String [] args){
        Grid grid = new Grid();
        
        grid.startGrid();
        grid.displayGrid(grid.getGridOutput());
        grid.updateGrid();
        grid.displayGrid(grid.getGridOutput());
                
    }
}
