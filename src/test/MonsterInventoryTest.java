package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.*;

class MonsterInventoryTest {
	
	private GameEnvironment game;
	private MonsterInventory myMonsters;

	
	@BeforeEach
	void setUp() throws Exception {
		game = new GameEnvironment();
		myMonsters = game.getMyMonsters();
	}
	
	
	@Test
	public void testAddMonster1() throws InventoryFullException {
		// Blue sky
		Monster testMonster = new Chunky("Chunky", "Description", 100, 10, 10, 0, 10, 0.1, game);
		myMonsters.add(testMonster);
		ArrayList<Monster> testMonsterList = new ArrayList<Monster>();
		testMonsterList.add(testMonster);
		assertEquals(testMonsterList, myMonsters.getMonsterList());
	}
	
	
	@Test
	public void testAddMonster2() throws InventoryFullException {
		// Inventory full
		Monster testMonster = new Chunky("Chunky", "Description", 100, 10, 10, 0, 10, 0.1, game);
		for (int i = 0; i < 4; i++) {			
			myMonsters.add(testMonster);
		}
		try {    		
			myMonsters.add(testMonster);
		}
		catch (InventoryFullException e){
			assertEquals(e.getMessage(), "Monster inventory is full!");
		}
	}

	
	@Test
	public void testRemoveMonster1() throws PurchasableNotFoundException, InventoryFullException {
		// Blue sky
		Monster testMonster = new Chunky("Chunky", "Description", 100, 10, 10, 0, 10, 0.1, game);
		myMonsters.add(testMonster);
		myMonsters.remove(testMonster);
		ArrayList<Monster> testMonsterList = new ArrayList<Monster>();
		assertEquals(testMonsterList, myMonsters.getMonsterList());
	}
	
	
	@Test
	public void testRemoveMonster2() throws PurchasableNotFoundException, InventoryFullException {
		// Multiple items of the same type
		Monster testMonster1 = new Chunky("Chunky 1", "Description", 100, 10, 10, 0, 10, 0.1, game);
		Monster testMonster2 = new Chunky("Chunky 2", "Description", 100, 10, 10, 0, 10, 0.1, game);
		myMonsters.add(testMonster1);
		myMonsters.add(testMonster2);
		myMonsters.add(testMonster2);
		myMonsters.remove(testMonster2);
		myMonsters.remove(testMonster1);
		ArrayList<Monster> testMonsterList = new ArrayList<Monster>();
		testMonsterList.add(testMonster2);
		assertEquals(testMonsterList, myMonsters.getMonsterList());
	}
	
	
	@Test
	public void testRemoveMonster3() throws PurchasableNotFoundException, InventoryFullException {
		// Purchasable not found in inventory
		Monster testMonster1 = new Chunky("Chunky 1", "Description", 100, 10, 10, 0, 10, 0.1, game);
		Monster testMonster2 = new Chunky("Chunky 2", "Description", 100, 10, 10, 0, 10, 0.1, game);
		myMonsters.add(testMonster2);
		try {    		
			myMonsters.remove(testMonster1);
		}
		catch (PurchasableNotFoundException e){
			assertEquals(e.getMessage(), "Monster not found in inventory!");
		}
	}

	
	@Test
	void testMonstersFull() throws InventoryFullException {
		Monster testMonster = new Chunky("Chunky", "Description", 100, 10, 10, 0, 10, 0.1, game);
		assertFalse(myMonsters.isFull());
		myMonsters.add(testMonster);
		myMonsters.add(testMonster);
		myMonsters.add(testMonster);
		assertFalse(myMonsters.isFull());
		myMonsters.add(testMonster);
		assertTrue(myMonsters.isFull());
	}

}
