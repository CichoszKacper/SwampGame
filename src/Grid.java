import java.util.ArrayList;

public class Grid {
    private ArrayList<Row> theGrid = new ArrayList <Row> ();


    public Grid() {
        Row tempRow;

        for(int loop = 1; loop < 5; loop++) {
            tempRow = new Row(loop);
            this.theGrid.add(tempRow);
        }
    }

    public void DisplayGrid () {
        String output = "";
        for (Row tempRow : theGrid){
            for (Square tempSquare : tempRow.getTheRow()){
                tempSquare.setNumber(0);
                output += tempSquare.getNumber() + "   ";
            }
            output += "\n";
        }
        System.out.println(output);
    }
}
