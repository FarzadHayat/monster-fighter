package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import exceptions.InvalidValueException;
import exceptions.StatMaxedOutException;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;

import main.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Displays the selected item in a new window.
 * @author Farzad and Daniel
 */
public class ItemScreen {

	private JFrame window;
	private GraphicalUserInterface gui;
	
	private GameEnvironment game = GameEnvironment.getInstance();
	private Player player;
	private MonsterInventory monsters;
	private JButton selectedButton;
	private Monster selectedMonster;
	
	private Item item;
	
	
	/**
	 * Close the window.
	 */
	public void closeWindow() {
		window.dispose();
	}
	
	
	/**
	 * Call the gui to close this screen.
	 */
	public void finishedWindow() {
		gui.closeItemScreen(this);
	}
	

	/**
	 * Create a new ItemScreen object.
	 * @param gui the given gui
	 * @param item the given item
	 */
	public ItemScreen(GraphicalUserInterface gui, Item item) {
		this.item = item;
		this.gui = gui;
		player = game.getPlayer();
		game.getScoreSystem();
		monsters = player.getMonsters();
		initialize();
		window.setVisible(true);
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		window = new JFrame();
		window.setTitle("MonsterFighter - Item");
		window.setResizable(false);
		window.setBounds(100, 100, 800, 600);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel(item.getName().toUpperCase());
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(250, 20, 300, 50);
		window.getContentPane().add(titleLabel);
		
		BalanceLabel balanceLabel = new BalanceLabel(100, 25);
		window.getContentPane().add(balanceLabel);
		
		BackButton backButton = new BackButton();
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.launchHomeScreen();
				finishedWindow();
			}
		});
		window.getContentPane().add(backButton);
		
		JButton btnUse = new JButton("Use");
		btnUse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedMonster != null) {
					try {
						int result = AlertBox.yesNo(String.format("Are you sure you want to use\n%s item on %s?", item.getName(), selectedMonster.getName()));
						if(result == 0) {
							item.use(selectedMonster);
							AlertBox.infoBox(String.format("%s item used on %s.", item.getName(), selectedMonster.getName()),"Item used!");
							gui.launchHomeScreen();
							finishedWindow();
						}
					} catch (StatMaxedOutException e1) {
						AlertBox.infoBox("Monster stat maxed out, choose another monster.", "Try again!");
					}
				}
			}
		});
		btnUse.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnUse.setBounds(631, 508, 119, 44);
		window.getContentPane().add(btnUse);
		
		JButton btnSell = new JButton("Sell");
		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = AlertBox.yesNo(String.format("Are you sure you want to sell\n%s item for $%s?", item.getName(), (int) (item.getCost()*item.getRefundAmount())));
				if(result == 0) {
					try {
						AlertBox.infoBox(item.sell(), "Item Sold!");
						gui.launchHomeScreen();
						finishedWindow();
					} catch (InvalidValueException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnSell.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnSell.setBounds(631, 453, 119, 44);
		window.getContentPane().add(btnSell);
		
		SpriteLabel spriteLabel = new SpriteLabel(item, 550, 200);
		window.getContentPane().add(spriteLabel);
		
		MonstersPanel monstersPanel = new MonstersPanel(monsters, 120, 80, 2);
		monstersPanel.setLayout(null);
		monstersPanel.setBounds(10, 87, 766, 465);
		window.getContentPane().add(monstersPanel);
		
		JLabel txtDescription = new JLabel();
		txtDescription.setVerticalAlignment(SwingConstants.TOP);
		txtDescription.setHorizontalAlignment(SwingConstants.CENTER);
		txtDescription.setBounds(226, 11, 325, 85);
		monstersPanel.add(txtDescription);
		txtDescription.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtDescription.setText(item.getDescription());
		txtDescription.setBackground(null);
		
		if(monsters.isEmpty()) {
			JLabel txtNoMonster = new JLabel();
			txtNoMonster.setVerticalAlignment(SwingConstants.TOP);
			txtNoMonster.setHorizontalAlignment(SwingConstants.CENTER);
			txtNoMonster.setFont(new Font("Tahoma", Font.PLAIN, 17));
			txtNoMonster.setBounds(120, 107, 325, 85);
			txtNoMonster.setText("No monster in player's inventory!");
			monstersPanel.add(txtNoMonster);
		}
		else {
			for (int i = 0; i < monsters.size(); i++) {
				Monster monster = monsters.get(i);
				MonsterButton monsterButton = (MonsterButton) monstersPanel.getComponent(i);
				monsterButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selectedMonster = monster;
						if(selectedButton != null) {
							selectedButton.setBackground(null);
						}
						selectedButton = monsterButton;
						selectedButton.setBackground(Color.lightGray);
					}
				});
			}
		}
	}
	
}
