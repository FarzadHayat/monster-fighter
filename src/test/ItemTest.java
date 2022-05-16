package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.InsufficientFundsException;
import exceptions.InvalidValueException;
import exceptions.InventoryFullException;
import items.HealUp;
import main.*;

class ItemTest {
	
	private GameEnvironment game;
	private ItemInventory myItems;
	private Player player;
	private Shop shop;
	
	
	@BeforeEach
	void setUp() throws Exception {
		game = new GameEnvironment();
		game.setupGame();
		player = game.getPlayer();
		myItems = player.getItems();
		game.setShop(new Shop(game));
		shop = game.getShop();
	}

	
	@Test
	public void testBuy1() throws InsufficientFundsException, InventoryFullException, InvalidValueException {
		// Blue sky
		Item testItem = new HealUp(game);
		player.setBalance(testItem.getCost());
		shop.getItems().add(testItem);
		testItem.buy();
		ArrayList<Item> testItemList = new ArrayList<Item>();
		testItemList.add(testItem);
		assertEquals(0, player.getBalance());
		assertEquals(testItemList, myItems.getList());
	}
	
	
	@Test
	public void testBuy2() throws InsufficientFundsException, InventoryFullException, InvalidValueException {
		// Insufficient funds
		Item testItem = new HealUp(game);
		player.setBalance(testItem.getCost() / 2);
		try {    		
			testItem.buy();
		}
		catch (InsufficientFundsException e){
			assertEquals(e.getMessage(), "Insufficient funds!");
		}
	}
	
	
	@Test
	public void testBuy3() throws InsufficientFundsException, InventoryFullException, InvalidValueException {
		// Inventory full	
		Item testItem = new HealUp(game);
		player.setBalance(testItem.getCost() * (myItems.getMaxSize() + 1));
		
		for (int i = 0; i < myItems.getMaxSize(); i++) {
			shop.getItems().add(testItem);
			testItem.buy();
		}
		try {
			shop.getItems().add(testItem);
			testItem.buy();
		}
		catch (InventoryFullException e){
			assertEquals(e.getMessage(), "Item inventory is full!");
		}
	}
	
	
	@Test
	public void testSell1() throws InventoryFullException, InsufficientFundsException, InvalidValueException {
		// Blue sky
		Item testItem = new HealUp(game);
		player.setBalance(testItem.getCost());
		shop.getItems().add(testItem);
		testItem.buy();
		testItem.sell();
		ArrayList<Item> testItemList = new ArrayList<Item>();
		assertEquals(testItem.getCost() * testItem.getRefundAmount(), player.getBalance());
		assertEquals(testItemList, myItems.getList());
	}
	
	
	@Test
	public void testSell2() throws InventoryFullException, InsufficientFundsException, InvalidValueException {
		// Multiple items of the same type
		Item testItem1 = new HealUp(game);
		Item testItem2 = new HealUp(game);
		player.setBalance(testItem1.getCost() * 3);
		shop.getItems().add(testItem1);
		shop.getItems().add(testItem2);
		shop.getItems().add(testItem2);
		testItem1.buy();
		testItem2.buy();
		testItem2.buy();
		testItem2.sell();
		testItem1.sell();
		ArrayList<Item> testItemList = new ArrayList<Item>();
		testItemList.add(testItem2);
		assertEquals(testItem1.getCost(), player.getBalance());
		assertEquals(testItemList, myItems.getList());
	}
	
	
	@Test
	public void testToString() {
		Item testItem = new HealUp(game);
		String myStr = testItem.toString();
		assertEquals(myStr, "%-20s    cost: %-3s    %-50s".formatted(testItem.getName(), testItem.getCost(), testItem.getDescription()));
	}
	
	
	@Test
	public void testView() {
		Item testItem = new HealUp(game);
		String myStr = testItem.view();
		
		String result = "";
    	if (game.getShop().getItems().getList().contains(testItem)) {
    		result += String.format("\nBalance: %s\n\n", player.getBalance());
    	}
    	result += "Item: " + testItem.getName() + "\n";
    	result += testItem.getDescription() + "\n";
    	result += "Cost: " + testItem.getCost() + "\n";
    	if (player.getItems().getList().contains(testItem)) {
    		result += "\n1: Use";
    		result += "\n2: Sell";
    		result += "\n3: Go back";
    	}
    	if (game.getShop().getItems().getList().contains(testItem)) {    		
    		result += "\n1: Buy";
    		result += "\n2: Go back";
    	}
    	assertEquals(myStr, result);
	}

}