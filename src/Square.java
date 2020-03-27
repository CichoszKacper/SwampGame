public class Square {
    private int number;
    private boolean hasOgre;
    private Ogre ogre;
    
    
   

	public Square() {
	
	}

	public Square(int number) {
        setNumber(number);
        setEmpty(true);
    }

	public void placeTheOgre () {
		this.ogre = new Ogre("Ogre");
		setEmpty(false);
	}
	
    public int getNumber() {
        return this.number;
    }
    

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isEmpty() {
        return this.hasOgre;
    }

    public void setEmpty(boolean empty) {
        this.hasOgre = empty;
    }
    
    public Ogre getOgre() {
		return ogre;
	}

}
