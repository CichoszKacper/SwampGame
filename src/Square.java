public class Square {
    private int number;
    private boolean empty;
    private Ogre ogre;
    private Donkey donkey;
    private Snake snake;
    private Parrot parrot;
    private String name;
   

	public Square() {
	
	}

	public Square(int number) {
        setNumber(number);
        setEmpty(true);
        setName("o");
    }

	public void placeTheOgre () {
		this.ogre = new Ogre("Ogre");
		setEmpty(false);
		setName(this.ogre.getName());
	}
	
	public void placeNewEnemy (Integer selector) {
		
		SimpleFactory enemyFactory = new SimpleFactory();
		
		Enemy theEnemy = null;
		Integer numberOfEnemies = 3;
		
		for(int i=0; i < numberOfEnemies; i++) {
			theEnemy = enemyFactory.createEnemy(selector);
			setEmpty(false);
			setName(theEnemy.getName());
		}
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
        this.name = "0";
    }
    
    public Ogre getOgre() {
		return this.ogre;
	}

	public Donkey getDonkey() {
		return this.donkey;
	}

	public Snake getSnake() {
		return this.snake;
	}


	public Parrot getParrot() {
		return this.parrot;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
	
	
}
