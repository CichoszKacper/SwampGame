public class SimpleFactory{
	
	
	//Simple factory class created to make the process of selecting enemies easier
	public Enemy createEnemy(Integer newEnemyType){

		Enemy newEnemy = null;
		
		
		if (newEnemyType == 0){

			return new Donkey("Donkey");

		} else 

		if (newEnemyType == 1){

			return new Snake("Snake");

		} else 

		if (newEnemyType == 2){

			return new Parrot("Parrot");

		} else return null;

	}
}

