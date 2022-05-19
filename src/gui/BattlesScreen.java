package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;

import main.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

/**
 * Displays the battle inventory in a new window.
 * @author Farzad and Daniel
 */
public class BattlesScreen {

	private JFrame window;
	private GraphicalUserInterface gui;
	
	private GameEnvironment game;
	private BattleInventory battles;
	
	private JButton selectedButton;
	private Battle selectedBattle;
	private ArrayList<JLabel> statsLabels;
	private ArrayList<JLabel> statsLabelValues;
	
	
	/**
	 * Closes the window.
	 */
	public void closeWindow() {
		window.dispose();
	}
	
	
	/**
	 * Call the gui to close this screen.
	 */
	public void finishedWindow() {
		gui.closeBattlesScreen(this);
	}

	
	/**
	 * Create a new BattlesScreen object.
	 * @param gui the given gui
	 */
	public BattlesScreen(GraphicalUserInterface gui) {
		this.gui = gui;
		game = gui.getGame();
		battles = game.getBattles();
		initialize();
		window.setVisible(true);
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		window = new JFrame();
		window.setTitle("MonsterFighter - Battles");
		window.setResizable(false);
		window.setBounds(100, 100, 800, 600);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel("BATTLES");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(250, 20, 300, 50);
		window.getContentPane().add(titleLabel);
		
		BackButton backButton = new BackButton();
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.launchHomeScreen();
				finishedWindow();
			}
		});
		window.getContentPane().add(backButton);
		
		JPanel battlesPanel = new JPanel();
		battlesPanel.setBounds(20, 105, 360, 440);
		window.getContentPane().add(battlesPanel);
		battlesPanel.setLayout(null);
		
		if (battles.getList().size() > 0) {
			Battle battle = battles.getList().get(0);
			JButton battle1Button = new JButton(battle.getName());
			battle1Button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedBattle = battle;
					updateStatsPanel(battle1Button);
				}
			});
			battle1Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
			battle1Button.setBounds(10, 40, 160, 90);
			battlesPanel.add(battle1Button);
		}
		
		if (battles.getList().size() > 1) {
			Battle battle = battles.getList().get(1);
			JButton battle2Button = new JButton(battle.getName());
			battle2Button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedBattle = battle;
					updateStatsPanel(battle2Button);
				}
			});
			battle2Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
			battle2Button.setBounds(190, 40, 160, 90);
			battlesPanel.add(battle2Button);
		}
			
		if (battles.getList().size() > 2) {
			Battle battle = battles.getList().get(2);
			JButton battle3Button = new JButton(battle.getName());
			battle3Button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedBattle = battle;
					updateStatsPanel(battle3Button);
				}
			});
			battle3Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
			battle3Button.setBounds(10, 175, 160, 90);
			battlesPanel.add(battle3Button);
		}
		
		if (battles.getList().size() > 3) {
			Battle battle = battles.getList().get(3);
			JButton battle4Button = new JButton(battle.getName());
			battle4Button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedBattle = battle;
					updateStatsPanel(battle4Button);
				}
			});
			battle4Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
			battle4Button.setBounds(190, 175, 160, 90);
			battlesPanel.add(battle4Button);
		}
		
		if (battles.getList().size() > 4) {
			Battle battle = battles.getList().get(4);
			JButton battle5Button = new JButton(battle.getName());
			battle5Button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedBattle = battle;
					updateStatsPanel(battle5Button);
				}
			});
			battle5Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
			battle5Button.setBounds(100, 310, 160, 90);
			battlesPanel.add(battle5Button);
		}
		
		JPanel statsPanel = new JPanel();
		statsPanel.setBounds(405, 105, 360, 440);
		window.getContentPane().add(statsPanel);
		statsPanel.setLayout(null);
		
		JLabel statsLabel = new JLabel("<html><u>Battle Stats</u></html>");
		statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statsLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		statsLabel.setBounds(76, 11, 151, 40);
		statsPanel.add(statsLabel);
		
		statsLabels = new ArrayList<JLabel>();
		int yPos = 80;
		
		for (int i = 0; i < 4; i++) {
			JLabel monsterLabel = new JLabel(String.format("Monster %s:", i + 1));
			monsterLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
			monsterLabel.setBounds(10, yPos, 136, 40);
			monsterLabel.setVisible(false);
			statsPanel.add(monsterLabel);
			statsLabels.add(monsterLabel);
			yPos += 80;
		}
		
		statsLabelValues = new ArrayList<JLabel>();
		yPos = 80;
		
		for (int i = 0; i < 4; i++) {
			JLabel monsterLabelValue = new JLabel("");
			monsterLabelValue.setFont(new Font("Tahoma", Font.PLAIN, 20));
			monsterLabelValue.setBounds(140, yPos, 200, 40);
			statsPanel.add(monsterLabelValue);
			statsLabelValues.add(monsterLabelValue);
			yPos += 80;
		}
		
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedBattle != null) {					
					gui.launchBattleScreen(selectedBattle);
					finishedWindow();
				}
			}
		});
		nextButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		nextButton.setBounds(237, 380, 119, 44);
		statsPanel.add(nextButton);
	}
	
	
	/**
	 * Update the stats panel and highlight the selected button.
	 * @param button the given button
	 */
	public void updateStatsPanel(JButton button) {
		if(selectedButton != null) {
			selectedButton.setBackground(null);
		}
		selectedButton = button;
		selectedButton.setBackground(Color.lightGray);
		for (int i = 0; i < statsLabelValues.size(); i++) {
			JLabel label = statsLabels.get(i);
			JLabel labelvalue = statsLabelValues.get(i);
			if (selectedBattle.getMonsters().getList().size() > i) {				
				Monster monster = selectedBattle.getMonsters().getList().get(i);
				label.setVisible(true);
				labelvalue.setText(String.valueOf(monster.getName()) + " - Lvl " + String.valueOf(monster.getLevel()));			
			}
			else {
				labelvalue.setText("");
				label.setVisible(false);
			}
		}
	}
	
}
