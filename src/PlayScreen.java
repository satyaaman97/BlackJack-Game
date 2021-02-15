import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;  
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;

// User the observer pattern to update statistics for the play screen statistics button
public class PlayScreen extends UIScreen {
	private BlackJack blackjack;

	//All the elements that can refresh in the UI display based on game state
	JLabel betCount;
	JLabel chipCount;
	JLabel userHandVal;
	JLabel dealerHandVal;
	JLabel deckLabel;
	HashMap<String,JPanel> playerHands = new HashMap<String,JPanel>(); //participantID to the ui element showing their hand
	JPanel p6; 
	int numDecks;
	JTextArea turnLabel;
	JProgressBar discardProgress;
	JProgressBar shoeProgress;
	JLabel userChipCount;
	HashMap<String,JLabel> playerChipCounts = new HashMap<String,JLabel>(); //participantID to the ui element showing their hand	

	PlayScreen(){
		super();
		this.blackjack = BlackJack.getInstance();
		//set up UI
		setUpPlayScreen();
	    
	}	

	// Create the play screen and its buttons
	private void setUpPlayScreen() {
		
        JFrame.setDefaultLookAndFeelDecorated(true); 
		this.f=new JFrame();
		this.pcs = new PropertyChangeSupport(this);
		this.addPropertyChangeListener(this.blackjack.getObserver());
		
		GridLayout gl = new GridLayout(3,3);

	    JPanel p1=new JPanel();  
	    JPanel p2=new JPanel();  
	    JPanel p3=new JPanel();  
	    JPanel p4=new JPanel();  
	    JPanel p5=new JPanel();  
	    this.p6=new JPanel();  
	    JPanel p7=new JPanel();  
	    JPanel p8 =new JPanel();  
	    JPanel p9=new JPanel(); 
	    
	    //Discard
	    p1.setLayout(new GridLayout(2,1));
	    p1.add(new JLabel("Discard:"));
	    JPanel dPanel = new JPanel();
	    this.discardProgress = new JProgressBar(0,100);
	    this.discardProgress.setValue(0);
	    this.discardProgress.setSize(260,20);
	    this.discardProgress.setStringPainted(true);
	    dPanel.add(this.discardProgress);
	    p1.add(dPanel);
	    
	    //Dealers's Hand
	    JPanel dealersHand = createHand("Dealer's Hand", "Dealer");
	    this.dealerHandVal = new JLabel("Dealer hand value: " + blackjack.dealer.hand.getCurrentHandValue());
	    p2.setLayout(new BorderLayout());
	    p2.add(dealersHand, BorderLayout.CENTER);
	    p2.add(this.dealerHandVal, BorderLayout.SOUTH);
	    
	    //Shoe
	    this.numDecks = blackjack.getDeckNum();

	    p3.setLayout(new GridLayout(2,1));
	    //p3.add(new JLabel("Shoe:"));
	    this.deckLabel = new JLabel("Shoe," + Integer.toString(this.numDecks) + " Decks:");
	    p3.add(this.deckLabel);
	    JPanel sPanel = new JPanel();
	    this.shoeProgress = new JProgressBar(0,100);
	    this.shoeProgress.setValue(100);
	    this.shoeProgress.setSize(260,20);
	    this.shoeProgress.setStringPainted(true);
	    sPanel.add(this.shoeProgress);
	    p3.add(sPanel);
	    
	    //center display
	    p5.setLayout(new BorderLayout());
	    
	    this.turnLabel = new JTextArea("Begin BlackJack");
	    JScrollPane centerScroll = new JScrollPane (this.turnLabel);
	    p5.add(centerScroll,BorderLayout.CENTER);
	    
	    this.p6.setLayout(new GridLayout(2,3));
	    
	    int numFakePlayers = blackjack.getPlayerNum();
	    for (int i=0; i<numFakePlayers;i++) {
	    	JPanel fakeArea = new JPanel(new BorderLayout());
	    	JLabel fakePLabel = new JLabel("Player " + (i+1));
	    	JLabel fakeBet = new JLabel("Bet: 50");
	    	JLabel fakeTotalChips = new JLabel("Total Chips: " + Integer.toString(blackjack.players.get(i).chips.getChipTotal()));
		    this.playerChipCounts.put("Player " + (i+1),fakeTotalChips);
	    	JPanel northPanel = new JPanel(new GridLayout(3,1));
	    	northPanel.add(fakePLabel);
	    	northPanel.add(fakeBet);
	    	northPanel.add(fakeTotalChips);
	    	JPanel fakeHand = createHand("Hand", "Player " + (i+1));
	    	fakeArea.add(fakeHand,BorderLayout.CENTER);
	    	fakeArea.add(northPanel,BorderLayout.NORTH);
	    	this.p6.add(fakeArea);
	    }
	    
	    //User game-play actions
	    p7.setLayout(new GridLayout(4,1));	    
	    /*
        JButton split =new JButton("Split");
		split.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
	            System.out.println("Split");  
	        }  
	    }); 
	    **/
        JButton newRound =new JButton("New Round");
		newRound.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				turnLabel.setText("<--New Round-->");
	            blackjack.newRound();
	        }
	    }); 

        JButton hit =new JButton("Hit");
		hit.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
	            //System.out.println("Stand");  
	            blackjack.userAction("h");
	        }  
	    }); 
        JButton stand =new JButton("Stand");
		stand.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
	            //System.out.println("Stand");  
	            blackjack.userAction("s");
	        }  
	    }); 
		

        JButton dd =new JButton("Double Down"); 
		dd.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
	            blackjack.userAction("d");
	        }  
	    }); 

	    p7.add(hit); 
	    p7.add(stand);
	    //p7.add(split); 
	    p7.add(dd);
	    p7.add(newRound);
                
	    //User's Hand 
	    p8.setLayout(new BorderLayout());	    
	    JPanel handsPanel = createHand("My Hand", "User");
	    JPanel userStuffPanel = new JPanel(new GridLayout(6,1));
	    this.userHandVal = new JLabel("Hand value: " + this.blackjack.user.hands.get(0).getCurrentHandValue());
	    userStuffPanel.add(this.userHandVal);
	    this.betCount = new JLabel("Current Bet: 50"); //to do - get from board
	    userStuffPanel.add(this.betCount); 
	    userChipCount = new JLabel("Total Chips: " + blackjack.user.chips.getChipTotal()); //to do - get from board
	    userStuffPanel.add(this.userChipCount);
	    p8.add(handsPanel, BorderLayout.CENTER);
	    p8.add(userStuffPanel, BorderLayout.EAST);
	    
	    //User general actions
	    p9.setLayout(new GridLayout(3,1));
        JButton cheat =new JButton("See Cheatsheet");
		cheat.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
	            //System.out.println("See cheatsheet");  
				
				pcs.firePropertyChange("cheatSheetView",null,null);
				
	            JFrame cheatsheet = new JFrame();
	    		cheatsheet.setSize(400,400); 
	    		cheatsheet.setLayout(new GridLayout(2,1)); 
	    		cheatsheet.add(new JLabel("Best Move:"));
	    		BasicStrategy bs = new BasicStrategy();
	    		String best = bs.getMove(BlackJack.getInstance().user.hands.get(0), BlackJack.getInstance().dealer.hand);
	    		if (best == "h"){
	    			best = "Hit";
	    		} else if (best == "s") {
	    			best = "Stand";
	    		} else if (best == "d") {
	    			best = "Double Down";
	    		} 
	    		cheatsheet.add(new JLabel(best));
	    		cheatsheet.setVisible(true);
	        }  
	    }); 
        JButton count = new JButton("See Current Count");
		count.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
	            System.out.println("See Current Count");  
	            JFrame currc = new JFrame();
	            currc.setSize(400,400); 
	    		currc.setLayout(new GridLayout(2,1)); 
	    		currc.add(new JLabel("Current Count:"));
	    		currc.add(new JLabel(Integer.toString(BlackJack.getInstance().getCount())));
	    		currc.setVisible(true);
	        }  
	    }); 
        JButton leave = new JButton("Leave");
		leave.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
	            System.out.println("Leave");  
	            JFrame leaveScreen = new JFrame();
	            leaveScreen.setSize(400,400); 
	    		leaveScreen.setLayout(new GridLayout(5,1)); 
	    		leaveScreen.add(new JLabel("Thanks for playing!"));
	    		
	    		System.out.println("calculating gain: ");
	    		int initNum = BlackJack.getInstance().getInitChip();
	    		int currNum = BlackJack.getInstance().user.chips.getChipTotal();
	    		
	    		int net = currNum - initNum;
	    		
	    		leaveScreen.add(new JLabel("Net Gain/Loss: " + net  + " chips"));
	    		leaveScreen.add(new JLabel("Optimal Move Percentage: " + Integer.toString(BlackJack.getInstance().getObserver().getOptimal()) +"%"));
	    		leaveScreen.add(new JLabel("Number Cheatsheet Checks: " + Integer.toString(BlackJack.getInstance().getObserver().getCheatSheetViews())));
	    			    		
	    		JButton exitButton = new JButton("Exit");
	    		exitButton.addActionListener(new ActionListener(){ 
	    			public void actionPerformed(ActionEvent e){  
	    	            System.out.println("Exit");  
	    	            pcs.firePropertyChange("switch", "Play", "PrePlay");
	    			}
	    		});
	    		leaveScreen.add(exitButton);
	    		leaveScreen.setVisible(true);
	        }  
	    }); 

	    p9.add(cheat); p9.add(count); p9.add(leave);
	    
	        
	    f.add(p1);f.add(p2);f.add(p3);f.add(p4);f.add(p5);  
	    f.add(this.p6);f.add(p7);f.add(p8);f.add(p9);  
		f.setLayout(gl); 	
	    f.setSize(1000,800); 
	    		
	}
	

	private JPanel createHand(String title, String participantID) {
		
	    JLabel handLabel = new JLabel(title);	 
	    
	    ArrayList<String> hand = new ArrayList<>();
	    hand.add("Default Hearts");
	    hand.add("Default Spades");
	    
	    
	    JPanel handPanel = new JPanel(new GridLayout(hand.size(),1));
	    handPanel.setSize(400,200);
		for (String cname : hand) {
			JLabel cardName = new JLabel(cname);
			if (cname.endsWith("SPADES") || cname.endsWith("CLUBS")) {
				cardName.setForeground(Color.black);
			} else {
				cardName.setForeground(Color.red);
			}
			handPanel.add(cardName);
		}
	    
	    this.playerHands.put(participantID,handPanel);
	    
	    JScrollPane hand1 = new JScrollPane(handPanel);
	    JPanel handsPanel = new JPanel(new GridLayout(2,1));
	    handsPanel.add(handLabel); handsPanel.add(hand1); 
		
	    return handsPanel;
	    
	}
	
	private void resetPanelSix() {
		this.p6.removeAll();
	    int numFakePlayers = blackjack.getPlayerNum();
	    for (int i=0; i< numFakePlayers;i++) {
	    	JPanel fakeArea = new JPanel(new BorderLayout());
	    	JLabel fakePLabel = new JLabel(("Player " + (i+1)));
	    	JLabel fakeBet = new JLabel("Bet: 50");
	  	    JLabel fakeTotalChips = new JLabel("Total Chips: " + Integer.toString(blackjack.players.get(i).chips.getChipTotal()));
		    this.playerChipCounts.put("Player " + (i+1),fakeTotalChips);
	    	JPanel northPanel = new JPanel(new GridLayout(3,1));
	    	northPanel.add(fakePLabel);
	    	northPanel.add(fakeBet);
	    	northPanel.add(fakeTotalChips);
	    	
	    	JPanel fakeHand = createHand("Hand", ("Player " + (i+1)));
	    	fakeArea.add(fakeHand,BorderLayout.CENTER);
	    	fakeArea.add(northPanel,BorderLayout.NORTH);
	    	this.p6.add(fakeArea);
	    }
		
	}
	
	public void refreshUI() {
		this.refreshPlayerHands();
		this.refreshUserHand();
		this.refreshDealerHand(blackjack.dealer.getShowAllCards());
		this.refreshShoeDiscard();
	}
	
	public void setDisplay(String d) {
		this.turnLabel.append("\n" + d);
	}
	
	private void refreshPlayerHands() {

		//the number of players was changed
		if ((blackjack.players.size()+2) != this.playerHands.size()){
			System.out.println("number of players changed");
			resetPanelSix();
		}

		for(int i = 0; i < blackjack.players.size(); i++){

			JPanel currHand = this.playerHands.get(("Player " + (i+1)));
			currHand.setVisible(true);
			Hand handStuff = blackjack.players.get(i).hands.get(0);
			currHand.removeAll();
			currHand.setLayout(new GridLayout(handStuff.getCurrentHandCards().size(),1));
		    for (int j = 0 ; j< handStuff.getCurrentHandCards().size(); j++) {
		    	String cname = handStuff.getCurrentHandCards().get(j).toString();
		    	JLabel cardName = new JLabel(cname);
		    	if (cname.endsWith("SPADES") || cname.endsWith("CLUBS")) {
		    		cardName.setForeground(Color.black);
		    	} else {
		    		cardName.setForeground(Color.red);
		    	}
		    	currHand.add(cardName);
		    }
		    
		    //refresh chip count 
		    JLabel currChipLabel = this.playerChipCounts.get(("Player " + (i+1)));
		    currChipLabel.setText("Total Chips: " + blackjack.players.get(i).chips.getChipTotal());

		}
		
	}

	private void refreshDealerHand(boolean show) {
		
		//hand contents
		JPanel dealerHand = this.playerHands.get("Dealer");
		Hand hand = blackjack.dealer.hand;

		dealerHand.removeAll();
		
		dealerHand.setLayout(new GridLayout(hand.getCurrentHandCards().size(),1));
	    for (int i = 0 ; i< hand.getCurrentHandCards().size(); i++) {
	    	String cname;
	    	if (!show && i == 0) {
	    		cname = "[Hidden]";
	    	} else {	    		
		    	cname = hand.getCurrentHandCards().get(i).toString();
	    	}	    	
	    	JLabel cardName = new JLabel(cname);
	    	if (cname.endsWith("SPADES") || cname.endsWith("CLUBS")) {
	    		cardName.setForeground(Color.black);
	    	} else {
	    		
	    		if (!show && i==0) {
		    		cardName.setForeground(Color.gray);
	    		} else {
	    			cardName.setForeground(Color.red);
	    		}
	    	}
	    	dealerHand.add(cardName);
	    }
	    
	    if (show) {
	    	this.dealerHandVal.setText("Dealer hand value: " + this.blackjack.dealer.hand.getCurrentHandValue());
	    } else {
	    	if (this.blackjack.dealer.hand.getCurrentHandCards().size() > 0) {
	    	this.dealerHandVal.setText("Dealer hand value: " + this.blackjack.dealer.hand.getCurrentHandCards().get(1).getRank().getRankValue());
	    	} else {
		    	this.dealerHandVal.setText("Dealer hand value: ");
	    	}
	    }
	}
	
	
	private void refreshUserHand() {
		
		//hand contents
		JPanel userHand = this.playerHands.get("User");
		userHand.setVisible(true);

		Hand hand = blackjack.user.hands.get(0);

		userHand.removeAll();

		
		userHand.setLayout(new GridLayout(hand.getCurrentHandCards().size(),1));
			for (int i = 0; i < hand.getCurrentHandCards().size(); i++) {
				String cname = hand.getCurrentHandCards().get(i).toString();
				//System.out.println(cname);
				JLabel cardName = new JLabel(cname);
				if (cname.endsWith("SPADES") || cname.endsWith("CLUBS")) {
					cardName.setForeground(Color.black);
				} else {
					cardName.setForeground(Color.red);
				}
				userHand.add(cardName);
			}

		this.userHandVal.setText("Hand value: " + this.blackjack.user.hands.get(0).getCurrentHandValue());
	    
		//bet count - to do, get from black jack 
		//this.betCount.setText("new");
	    //this.chipCount.setText("new");	
		this.userChipCount.setText("Total Chips: " + Integer.toString(blackjack.user.chips.getChipTotal()));	

	}
	
	private void refreshShoeDiscard() {
		
		//number of decks
		int temp = this.numDecks;
		this.numDecks = blackjack.getDeckNum();
		if (temp != this.numDecks) {
			System.out.println("number of decks changed");
		    this.deckLabel.setText("Shoe," + Integer.toString(blackjack.getDeckNum()) + " Decks:");
		}
		float totalCards = blackjack.getDeckNum()*52;
		float currSize = blackjack.getDeckSize();
		int shoeval = Math.round(currSize/totalCards*100);
		this.shoeProgress.setValue(shoeval);
		this.discardProgress.setValue(100-shoeval);
				
	}
	
	public void setShowDealerCard(boolean show) {
		this.refreshDealerHand(show);
	}
	
	

}
