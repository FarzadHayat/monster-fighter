package items;

import exceptions.StatMaxedOutException;
import main.*;

public class HealUp extends Item {
    
	/**
	 * Fields
	 */
	private static int healAmount = 20;
	private static String name = "Heal Up";
	private static String description = "Heal a monster for " + healAmount + " health.";
	private static int cost = 20;
	
	
	/**
	 * Constructors
	 */
	
	/**
     * Create a new HealUp object.
     * Set the value of game to the given GameEnvironment object.
     * @param game the given GameEnvironment object.
     */
	public HealUp (GameEnvironment game) {
		super(name, description, cost, game);
	};
	
	
	/**
	 * Getters and setters
	 */
	
	/**
	 * Get the value of healAmount
	 * @return the value of healAmount
	 */
	public static int getHealAmount() {
		return healAmount;
	}
	
	
	/**
	 * Set the value of healAmount
	 * @param healAmount the new value of healAmount
	 */
	public static void setHealAmount(int healthIncrease) {
		HealUp.healAmount = healthIncrease;
	}

	
    /**
     * Functional
     */

    /**
     * Heal a monster for healAmount health.
     * @param monster the given monster
     * @throws StatMaxedOutException if the monster is already max health
     */
    public void use(Monster monster) throws StatMaxedOutException
    {
    	if (monster.getHealth() == monster.getMaxHealth()) {
    		throw new StatMaxedOutException("Health is already full!");
    	}
    	
    	monster.heal(healAmount);
    	player.getItems().remove(this);
    }
    
    
    /**
     * Get a new instance of the HealUp class.
     * @return item the new HealUp object.
     */
    public Item clone() {
    	return new HealUp(game);
    }
    
}