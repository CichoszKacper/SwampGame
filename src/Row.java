import java.util.ArrayList;

public class Row {

    private int number;
    private ArrayList <Square> theRow = new ArrayList <Square>();
    
    //Constructor to create a row
	public Row(int number, int squaresInRow) {
        Square tempSquare;
        setNumber(number);
        for(int loop = 1; loop < squaresInRow ; loop++) {
            tempSquare = new Square(loop);
            this.theRow.add(tempSquare);
        }
    }
	
	//Method to return the Row
    public ArrayList<Square> getTheRow() {
        return this.theRow;
    }
    
    
    //Getters and setters
    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }    
    
}
