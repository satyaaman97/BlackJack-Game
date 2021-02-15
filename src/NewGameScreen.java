import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeSupport;

public class NewGameScreen extends UIScreen {
	
	//default settings
	String oppChoice = "0";
	String skillChoice = "high";
	String deckChoice = "1";
	String chipChoice = "500";
	
	NewGameScreen(){
		super();
		
		//UI set up 
		setUpNewScreen();
		

	}

	// Initialize the new game screen for users to select the game settings
	private void setUpNewScreen() {
		
		f=new JFrame();//creating instance of JFrame  
		pcs = new PropertyChangeSupport(this);

		f.setLayout(new GridLayout(5,1));

		// Selecting the number of opponents buttons
		JPanel opponents = new JPanel(new GridLayout(1,2));
		opponents.add(new JLabel("Number of opponents:"));
		String[] oppList = {"0","1", "2", "3", "4", "5"};
		JList<String> oppOpts = new JList<>(oppList);
		oppOpts.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		oppOpts.setSelectedIndex(0);
		opponents.add(oppOpts);
		oppOpts.addListSelectionListener(e -> {
			JList<String> jl = (JList<String>)e.getSource();
			int index = jl.getSelectedIndex();
			oppChoice = oppList[index];
			System.out.println(oppChoice);
		});

		// select the opponent skill level buttons
		JPanel skill = new JPanel(new GridLayout(1,2));
		skill.add(new JLabel("Opponent skill level:"));
		String[] skillList = {"low", "high"};
		JList<String> skillOpts = new JList<>(skillList);
		skillOpts.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		skillOpts.setSelectedIndex(1);
		skill.add(skillOpts);
		skillOpts.addListSelectionListener(e -> {
			JList<String> jl = (JList<String>)e.getSource();
			int index = jl.getSelectedIndex();
			skillChoice = skillList[index];
			System.out.println(skillChoice);
		});

		// select the number of decks button
		JPanel decks = new JPanel(new GridLayout(1,2));
		decks.add(new JLabel("Number of Decks:"));
		String[] dList = {"1","2","3","4","5"};
		JList<String> dOpts = new JList<>(dList);
		dOpts.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		dOpts.setSelectedIndex(0);
		decks.add(dOpts);
		dOpts.addListSelectionListener(e -> {
			JList<String> jl = (JList<String>)e.getSource();
			int index = jl.getSelectedIndex();
			deckChoice = dList[index];
			System.out.println(deckChoice);
		});

		// select the number of starting chips button
		JPanel chips = new JPanel(new GridLayout(1,2));
		chips.add(new JLabel("Starting chips:"));
		String[] cList = {"500","1000","2000","3000","4000"};
		JList<String> cOpts = new JList<>(cList);
		cOpts.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		cOpts.setSelectedIndex(0);
		chips.add(cOpts);
		cOpts.addListSelectionListener(e -> {
			JList<String> jl = (JList<String>)e.getSource();
			int index = jl.getSelectedIndex();
			chipChoice = cList[index];
			System.out.println(chipChoice);
		});

		// add these characteristics to the UI
		f.add(opponents);
		f.add(skill);
		f.add(decks);
		f.add(chips);
				
		JPanel bottomPanel = new JPanel(new GridLayout(1,2));

		// Add utility buttons
        JButton back = new JButton("Back");
		back.addActionListener(e -> {
			System.out.println("Back");
			pcs.firePropertyChange("switch", "NewGame", "PrePlay");
		});
        JButton go = new JButton("Go!");

		go.addActionListener(e -> {
			System.out.println("Go!");
			//set the black jack board based on user input
			int numPlayers = Integer.parseInt(oppChoice);
			int numDecks = Integer.parseInt(deckChoice);
			String playLevel = skillChoice;
			//set the black jack board based on user input
			BlackJack.getInstance().setPlayerNum(numPlayers, playLevel);
			BlackJack.getInstance().setDeckNum(numDecks);
			BlackJack.getInstance().setInitChip((Integer.parseInt(chipChoice)));
			//switch screens
			pcs.firePropertyChange("switch", "NewGame", "Play");
			//start game
			BlackJack.getInstance().playGame();
		});
		
		bottomPanel.add(back);
		bottomPanel.add(go);
		
		f.add(bottomPanel);
		
	    f.setSize(1000,800); 
		
	}
	
}
