package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Chunky;
import main.GameEnvironment;
import main.InsufficientFundsException;
import main.InventoryFullException;
import main.Monster;
import main.PurchasableNotFoundException;

class MonsterTest {

	private GameEnvironment game;
	private Monster monster;
	
	@BeforeEach
	void setUp() throws Exception {
		game = new GameEnvironment();
		monster = new Chunky("Test", "Testing description", 100, 20, 20, 1, 10, 0.1, game);
	}

	@Test
	public void testTakeDamage1() {
		//Taking damage less than total health 
		monster.takeDamage(10);
		assertEquals(90, monster.getHealth());
	}
	
	@Test
	public void testTakeDamage2() {
		//Taking 0 damage 
		monster.takeDamage(0);
		assertEquals(100, monster.getHealth());
	}
	
	@Test 
	public void testTakeDamage3() {
		//Taking damage equivalent to total health 
		monster.takeDamage(100);
		assertEquals(0, monster.getHealth());
		assertEquals(true, monster.getIsFainted());
	}
	
	@Test 
	public void testTakeDamage4() {
		//Taking more damage than total health 
		monster.takeDamage(120);
		assertEquals(0, monster.getHealth());
		assertEquals(true, monster.getIsFainted());
	}
	
	@Test
	public void testHeal1() {
		//Healing when total health is at max 
		monster.heal();
		assertEquals(100, monster.getHealth());
	}
	
	@Test 
	public void testHeal2() {
		//Healing to exactly max health 
		monster.takeDamage(10);
		monster.heal();
		assertEquals(100, monster.getHealth());
	}
	
	@Test
	public void testHeal3() {
		//Healing to less than max health 
		monster.takeDamage(20);
		monster.heal();
		assertEquals(90, monster.getHealth());
	}
	
	@Test
	public void testHeal4() {
		//Health exceed max health after healing 
		monster.takeDamage(5);
		monster.heal();
		assertEquals(100, monster.getHealth());
	}
	
	@Test
	public void testAttack1() {
		//Attacking and dealing damage to an enemy 
		Monster enemy = new Chunky("Enemy", "Enemy description", 100, 20, 20, 1, 10, 0.1, game);
		monster.attack(enemy);
		assertEquals(80, enemy.getHealth());
		assertEquals(100, monster.getHealth());
	}
	
	@Test
	public void testBuy1() throws InsufficientFundsException, InventoryFullException {
		//Blue sky
		game.setBalance(20);
		monster.buy();
		ArrayList<Monster> monsterList = new ArrayList<Monster>();
		monsterList.add(monster);
		assertEquals(0, game.getBalance());
		assertEquals(monsterList, game.getInventory().getMyMonsters());
	}
	
	@Test
	public void testBuy2() throws InsufficientFundsException, InventoryFullException {
		//Insufficient fund in player's balance 
		game.setBalance(10);
		try {
			monster.buy();
		}
		catch(InsufficientFundsException e) {
			assertEquals(e.getMessage(), "Insufficient funds!");
		}
	}
	
	@Test
	public void testBuy3() throws InsufficientFundsException, InventoryFullException {
		//Inventory full
		game.setBalance(100);
		for(int i = 0; i < 4; i++) {
			monster.buy();
		}
		try {
			monster.buy();
		}
		catch(InventoryFullException e) {
			assertEquals(e.getMessage(), "Monster inventory is full!");
		}
	}
	
	@Test
	public void testSell1() throws PurchasableNotFoundException, InventoryFullException, InsufficientFundsException {
		//Blue sky
		game.setBalance(20);
		Monster testMonster = new Chunky("Test1", "Testing description1", 100, 20, 20, 1, 10, 0.1, game);
		testMonster.buy();
		testMonster.sell();
		ArrayList<Monster> monsterList = new ArrayList<Monster>();
		assertEquals(10, game.getBalance());
		assertEquals(monsterList, game.getInventory().getMyMonsters());
	}
	
	@Test
	public void testSell2() throws PurchasableNotFoundException, InventoryFullException, InsufficientFundsException {
		//Multiple items of same type
		game.setBalance(60);
		Monster testMonster = new Chunky("Test1", "Testing description1", 100, 20, 20, 1, 10, 0.1, game);
		monster.buy();
		testMonster.buy();
		testMonster.buy();
		testMonster.sell();
		monster.sell();
		ArrayList<Monster> monsterList = new ArrayList<Monster>();
		monsterList.add(testMonster);
		assertEquals(20, game.getBalance());
		assertEquals(monsterList, game.getInventory().getMyMonsters());
	}
	
	@Test
	public void testSell3() throws PurchasableNotFoundException, InventoryFullException, InsufficientFundsException {
		//Purchasable not found in inventory
		game.setBalance(20);
		try {
			monster.sell();
		}
		catch(PurchasableNotFoundException e) {
			assertEquals(e.getMessage(), "Monster not found in inventory!");
		}
	}

}