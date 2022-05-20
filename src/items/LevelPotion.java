package items;

import exceptions.StatMaxedOutException;
import main.*;

/**
 * Item that can be used on a monster to level them up to increase their stats.
 * @author Farzad and Daniel
 */
public class LevelPotion extends Item {
	
	/**
	 * Fields
	 */
	private static String name = "Level Potion";
	private static String description = "Level up a monster by one level.";
	private static int cost = 50;
	
	
	/**
	 * Constructors
	 */
	
	/**
     * Create a new LevelPotion object.
     * Set the value of game to the given GameEnvironment object.
     * @param game the given GameEnvironment object.
     */
    public LevelPotion (GameEnvironment game) {
    	super(name, description, cost, game);
    };
    
    
    /**
     * Functional
     */

    /**
     * Level up the monster by one level.
     * @param monster the given monster
     * @throws StatMaxedOutException if the monster is already max level
     */
    public void use(Monster monster) throws StatMaxedOutException
    {
    	monster.levelUp();
    	player.getItems().remove(this);
    }
    
    
    /**
     * Get a new instance of the LevelPotion class.
     * @return item the new LevelPotion object.
     */
    public Item clone() {
    	return new LevelPotion(game);
    }

}