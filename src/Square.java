public class Square {
    private int number;
    private boolean empty;

    public Square(int number) {
        setNumber(number);
        setEmpty(true);
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
