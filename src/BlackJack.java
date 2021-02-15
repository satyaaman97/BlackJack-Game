//import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;

// This class controls the core game play flow and keeps track of the current board state
// We use the Singleton Pattern to instantiate the BlackJack object
// We use the Factory pattern to create the computer players for our game
// We also have the observer pattern in this class to update the user statistics 
public class BlackJack {

	private static BlackJack theBlackJack;
    public Dealer dealer;
    public RealPlayer user;
    public ArrayList<Player> players;
    private ComputerPlayerFactoryCreator computerPlayerFactory;

    private int numOfDecks;
	private int numOfPlayers;
	private int initChips;
	private Boolean userIsPlaying;
    Deck deck;
    String currPlayer;
    String display;
    BasicStrategy bs = new BasicStrategy();

    //Playscreen
    PlayScreen ps;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);;
    UserStatsObserver observer;


    //current card count
	private int count;

    private BlackJack(int initDeckNum, int initPlayerNum){
        this.computerPlayerFactory = new ComputerPlayerFactoryCreator();

        this.dealer = Dealer.Dealer();
        this.dealer.setShowAllCards();
        this.user = RealPlayer.RealPlayer(new ChanceStrategy());
        this.players = new ArrayList<>();

        for(int i = 1; i <= initPlayerNum; i++){
            this.players.add(computerPlayerFactory.createComputerPlayer(new ChanceStrategy()));
        }

        this.deck = Deck.newDeck(initDeckNum);
        this.currPlayer = "User";
		this.display = "Your Turn!";
		this.observer = new UserStatsObserver();
		this.addPropertyChangeListener(this.observer);
	    this.display = "Your Turn!";

	}

    //This class implements the Singleton design pattern
    public static synchronized BlackJack getInstance() {
        if (theBlackJack == null) {
            //default initialization is 1 deck, 0 player
            theBlackJack = new BlackJack(1, 0);
        }
        return theBlackJack;
    }

    // Set the play screen
    public void setPlayScreen(PlayScreen play) {
		this.ps = play;
	}
	
	   //Start a round of gameplay
    public void newRound() {
        //All Bet 50
        this.user.chips.Bet(50, 0);
        for (Player player : players) {
            player.chips.Bet(50, 0);
        }

        //reset dealer hand
        this.dealer.hand.resetHand();
        this.dealer.setShowAllCards();
        ps.setShowDealerCard(false);

        // clear user hand
        if (user.hands.size() == 2) {
            user.hands.remove(1);
        }
        user.hands.get(0).resetHand();


        //reset player hand to size of 1 if they split at some point
        for (Player player : players) {
            if (player.hands.size() == 2) {
                player.hands.remove(1);
            }
            // reset the current hand
            player.hands.get(0).resetHand();
        }
        //start playing

        this.initialDeal();
        this.displayAllHands();

        userIsPlaying = false;

        // If the dealer has blackjack in th beginning check who pushes and who loses
        if (this.checkDealerBlackJack(this.dealer)) {
            delayAndDisplay("Dealer has BlackJack");
            checkPreLoss();
            finishRound();
        } else {
            //Computer players play
        	System.out.println("player size:" +  this.players.size());
            for (int i = 0; i < this.players.size(); i++) {
                currPlayer = "Computer Player " + (i+1);
                delayAndDisplay(currPlayer + " begins their turn");
                while(players.get(i).hands.get(0).isHandActive()){
                	System.out.println("player " + i);
                	System.out.println(players.get(i));
                    computerAction(players.get(i), i);
                }
            }
            delayAndDisplay("User's Turn");

            //User is now allowed to play
            userIsPlaying = true;
            currPlayer = "User";
        }
    }
    //Computer actions coming from the UI
    public void computerAction(Player player, int i){
        //can only take action when they are allowed
        for(Hand hand : player.hands) {
            if (checkBlackJack(player) != 0) {
                delayAndDisplay("Computer Player " + (i+1) + " has blackjack");
                hand.setIsHandActive(false); // LVK trying
            }
            else if(hand.isHandActive() && (checkBlackJack(user) == 0)) {
                if (player.executeStrategy(hand, dealer.hand).equals("h")) {
                    delayAndDisplay("Computer Player " + (i+1) + " hits.");
                    this.Hit(hand);
                } else if (player.executeStrategy(hand, dealer.hand).equals("s")) {
                    delayAndDisplay("Computer Player " + (i+1) + " stands with " + hand.getCurrentHandValue() + ". Their turn ends.");
                    this.Stand(hand);
                }
                else if (player.executeStrategy(hand, dealer.hand).equals("d")) {
                    if(player.hands.get(0).getCurrentHandCards().size() != 2){
                        delayAndDisplay("Computer Player " + (i+1) + " hits.");
                        this.Hit(hand);
                    }
                    else {
                        delayAndDisplay("Computer Player " + (i + 1) + " doubles down with " + hand.getCurrentHandValue() + ". Their turn ends.");
                        player.chips.DoubleDown(0);
                        this.Hit(hand);
                        hand.isHandActive();
                        this.ps.refreshUI();
                    }
                }

                player.displayHand();
                if(this.checkPlayerHand(hand).equals("Bust")){
                    delayAndDisplay("Computer Player " + (i+1) + " busts with " + hand.getCurrentHandValue() + ". Their turn ends.");
                }
                this.ps.refreshUI();
            } else {
            	System.out.println("else case");
            	hand.setIsHandActive(false); //LVK trying
            }
        }
    }

    // Check if the hand is a blackjack, bust or nothing
    public String checkPlayerHand(Hand participant){
        if(participant.getCurrentHandValue() == 21){
            if(participant.isHandActive()){
                participant.setIsHandActive(false);
            }
            return "BlackJack";
        }
        else if(participant.getCurrentHandValue() > 21){
            System.out.println("User busts with " + participant.getCurrentHandValue());
            if(participant.isHandActive()){
                participant.setIsHandActive(false);
            }
            return "Bust";
        }
        return "None";
    }

    //User actions coming from the UI
    public void userAction(String action){
    	//can only take action when they are allowed
    	if (userIsPlaying) {
    	    for(Hand hand : user.hands) {
    	        if (checkBlackJack(user) != 0) {
                    delayAndDisplay("User has blackjack");
                    this.finishRound();
                }
    	        else if(hand.isHandActive() && (checkBlackJack(user) == 0)) {

    	    		boolean optimal = false;
    	    		if (action.equals(bs.getMove(this.user.hands.get(0), this.dealer.hand))) {
    	    			optimal = true;
    	    		}
    	            pcs.firePropertyChange("UserAction",null,optimal);


                    if (action.equals("h")) {
                        delayAndDisplay("User hits.");
                        this.Hit(hand);
                    } else if (action.equals("s")) {
                        delayAndDisplay("User stands with " + hand.getCurrentHandValue() + ". Their turn ends.");
                        this.Stand(hand);
                    } else if (action.equals("d")) {
                        if(user.hands.get(0).getCurrentHandCards().size() != 2){
                            delayAndDisplay("User Cannot Double Down, hit triggered instead");
                            delayAndDisplay("User hits.");
                            this.Hit(hand);
                        }else {
                            delayAndDisplay("User doubles down");
                            this.Hit(hand);
                            this.user.chips.DoubleDown(0);
                            this.ps.refreshUI();
                            this.finishRound();
                        }
                    }

                    user.displayHand();
                    this.checkPlayerHand(hand); //check bust or blackjack
                    //this.pcs.firePropertyChange("refreshUI",null,null); //Update UI
                    this.ps.refreshUI();

                    //if hand is inactive after playing (stand, bust, or blackjack)

                    if (!hand.isHandActive()) {
                        this.finishRound(); //need to figure out the exiting
                    }
                }
    	    }
        }
    	else {
            userIsPlaying = false;
    		System.out.println("User cannot take action right now");
    	}
    }

    public void setPlayerNum(int p, String skillLevel) {
        numOfPlayers  = p;
        players.clear();
        for(int i = 1; i <= numOfPlayers; i++){
            if(skillLevel.equals("Low")) {
                this.players.add(computerPlayerFactory.createComputerPlayer(new ChanceStrategy()));
            }
            else{
                this.players.add(computerPlayerFactory.createComputerPlayer(new BasicStrategy()));
            }
        }
    }
    
    private void finishRound() {
    	
        currPlayer = "Dealer";
    	
        //System.out.println("----------------Dealer Round Begin----------------");
        dealer.setShowAllCards();
        ps.setShowDealerCard(true);
        
       // delayAndDisplay("The Dealer's Full Hand is: ");
        //for (Card card : this.dealer.getHandCards()) {
            //delayAndDisplay(card.toString());
        //}
        //delayAndDisplay("The Dealer's Full Hand value is " + this.dealer.getCurrentHandValue());
        
        delayAndDisplay("Dealer Round Begins");



        if(this.checkBusts()) {
            dealer.displayHand();
            this.dealersTurn();
        }
        this.checkForWinners();
        ps.refreshUI();

	    // Repopulate deck if it is small
	    if(this.deck.checkDeck()){
	        delayAndDisplay("Deck low on cards. Resetting the deck.");
	        this.resetDeck();
	    }   
	        
	    //this.newRound();
    	
    }
	
	public void setDeckNum(int d) {
		numOfDecks = d;
        this.deck = Deck.newDeck(numOfDecks);

	}
	
	public int getDeckNum() {
		return numOfDecks;
	}

	/**
	public void setPlayerNum(int p) {
		numOfPlayers  = p;
		players.clear();
        for(int i = 1; i <= numOfPlayers; i++){
            this.players.add();
        }
	}
	**/

	public void setInitChip(int c) {
		this.initChips = c;
	}
	public int getInitChip() {
		return this.initChips;
	}

	public int getPlayerNum() {
		return numOfPlayers;
	}

	//Deal two cards each to start the game 
    public void initialDeal(){
        //this.dealer.dealCard(this.deck.deal(false));
        dealCardWithCount(this.dealer.hand, false);
        //this.user.dealCard(this.deck.deal(true));
        dealCardWithCount(this.user.hands.get(0), true);
        for (Player player : this.players) {
            //hand.dealCard(this.deck.deal(true));
            dealCardWithCount(player.hands.get(0), true);
        }
        //this.dealer.dealCard(this.deck.deal(true));
        dealCardWithCount(this.dealer.hand, true);
        //this.user.dealCard(this.deck.deal(true));
        dealCardWithCount(this.user.hands.get(0), true);
        for (Player player : this.players) {
            //player.dealCard(this.deck.deal(true));
            dealCardWithCount(player.hands.get(0), true);
        }

    }
    
	//Start a new game
    public void playGame(){

    	//initialize chips
    	this.user.chips.setChipTotal(initChips);
        for (Player player : this.players) {
            //player.dealCard(this.deck.deal(true));
            player.chips.setChipTotal(initChips);
        }
    	ps.refreshUI();

        this.newRound();
    }

    public void resetDeck(){
        this.deck = Deck.newDeck(this.numOfDecks);
    }

    public void Stand(Hand participant){
         participant.setIsHandActive(false);
    }

    public void Hit(Hand participant){
        //participant.dealCard(this.deck.deal());
        dealCardWithCount(participant, true);
    }

    // Returns the number of blackjacks in a hand since a split can return 2 blackjacks
    public boolean checkDealerBlackJack(Dealer dealer){
        return (dealer.hand.getCurrentHandValue() == 21) && (dealer.hand.getCurrentHandCards().size() == 2);
    }

    // Check if there are any hands that haven't busted so we can compute winners
    public boolean checkBusts(){
        for (Player player : players)
            for (int x = 0; x < player.hands.size(); x++) {
                if (player.hands.get(x).getCurrentHandValue() <= 21) {
                    return true;
                }
            }
        return false;
    }

    // Returns the number of blackjacks in a hand since a split can return 2 blackjacks
    public int checkBlackJack(Player player){
        int numOfBlackJacks = 0;
        for(int i = 0; i < player.hands.size(); i++) {
            if ((player.hands.get(i).getCurrentHandValue() == 21) && (player.hands.get(0).getCurrentHandCards().size() == 2)) {
                player.hands.get(i).setHandStatus("BlackJack");
                player.hands.get(i).setIsHandActive(false);
                numOfBlackJacks++;
            }
        }
        return numOfBlackJacks;
    }

    // Per blackjack rules, if the hand is less than or equal to 16, the dealer must hit.
    public void dealersTurn(){
        while(this.dealer.hand.getCurrentHandValue() <= 16){
            delayAndDisplay("Dealer Hits");
            Hit(this.dealer.hand);
            delayAndDisplay("New Dealer Hand Value is " + this.dealer.hand.getCurrentHandValue());
            this.ps.refreshUI();
        }
        delayAndDisplay("Dealer ends with " + this.dealer.hand.getCurrentHandValue());
    }

    public void checkPreLoss(){
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).hands.get(0).getCurrentHandValue() == 21) {
                players.get(i).hands.get(0).setHandStatus("Push");
                System.out.println("Computer Player " + (i+1) +" pushes with the dealer.");
            } else {
                System.out.println("Computer Player " + (i+1) + " loses to the dealer.");
            }
        }
        if (user.hands.get(0).getCurrentHandValue() == 21) {
            user.hands.get(0).setHandStatus("Push");
            System.out.println("User pushes with the dealer.");
        } else {
            System.out.println("User loses to the dealer.");
        }
    }

    public boolean Bust(Hand participant){
        return participant.getCurrentHandValue() > 21;
    }

    // Displays all of the hands at the beginning of the game
    public void displayAllHands(){
        dealer.displayHand();
        user.displayHand();
        for (Player player : players) {
            player.displayHand();
        }
        this.ps.refreshUI();
    }

    public void checkForWinners() {
        boolean dealerBust = Bust(this.dealer.hand);
        int dealerHandValue = this.dealer.hand.getCurrentHandValue();
        // If dealer busted, everyone who didn't bust wins
        if (dealerBust) {
            for (int k = 0; k < players.size(); k++) {
                for (int i = 0; i < players.get(k).hands.size(); i++)
                    if (!players.get(k).hands.get(i).getHandStatus().equals("BlackJack")) {
                        if (!Bust(players.get(k).hands.get(i))) {
                            players.get(k).hands.get(i).setHandStatus("Winner");
                            players.get(k).chips.Winner("Winner", i);
                            delayAndDisplay("Computer Player "+ (k+1) +" beats the dealer.");
                        }
                    } else {
                        players.get(k).chips.Winner("BlackJack", i);
                    }
            }
        } else {
            for (int k = 0; k < players.size(); k++) {
                for (int i = 0; i < players.get(k).hands.size(); i++) {

                    if (!Bust(players.get(k).hands.get(i))) {
                        // if the player got a blackjack, they won
                        if (players.get(k).hands.get(i).isBlackJack()) {
                            players.get(k).hands.get(i).setHandStatus("BlackJack");
                            players.get(k).chips.Winner("BlackJack", i);
                            delayAndDisplay("Computer Player "+(k+1) +" beats the dealer.");
                        }
                        // If the dealer and player have the same hand value it is a push
                        else if (dealerHandValue == players.get(k).hands.get(i).getCurrentHandValue()) {
                            players.get(k).hands.get(i).setHandStatus("Push");
                            players.get(k).chips.Push(i);
                            delayAndDisplay("Computer Player "+ (k+1) + " pushes with the dealer.");
                        } else if (dealerHandValue <players.get(k).hands.get(i).getCurrentHandValue()) {
                            players.get(k).hands.get(i).setHandStatus("Winner");
                            players.get(k).chips.Winner("Winner", i);
                            delayAndDisplay("Computer Player "+ (k+1) + " beats the dealer.");
                        } else {
                            delayAndDisplay("Computer Player "+ (k+1) +" loses to the dealer.");
                        }

                    } else {
                        delayAndDisplay("Computer Player "+ (k+1) + " loses to the dealer.");
                    }
                }
            }
        }
        // If dealer busted, everyone who didn't bust wins
        if (dealerBust) {
                for (int i = 0; i < user.hands.size(); i++)
                    if (!user.hands.get(i).getHandStatus().equals("BlackJack")) {
                        if (!Bust(user.hands.get(i))) {
                            user.hands.get(i).setHandStatus("Winner");
                            user.chips.Winner("Winner", i);
                            delayAndDisplay("User beats the dealer.");
                        }
                    } else {
                        user.chips.Winner("BlackJack", i);
                    }
        } else {
                for (int i = 0; i < user.hands.size(); i++) {
                    if (!Bust(user.hands.get(i))) {
                        // if the player got a blackjack, they won
                        if (user.hands.get(i).isBlackJack()) {
                            user.hands.get(i).setHandStatus("BlackJack");
                            user.chips.Winner("BlackJack", i);
                            delayAndDisplay("User beats the dealer.");
                        }
                        // If the dealer and player have the same hand value it is a push
                        else if (dealerHandValue == user.hands.get(i).getCurrentHandValue()) {
                            user.hands.get(i).setHandStatus("Push");
                            user.chips.Push(i);
                            delayAndDisplay("User pushes with the dealer.");
                        } else if (dealerHandValue < user.hands.get(i).getCurrentHandValue()) {
                            user.hands.get(i).setHandStatus("Winner");
                            user.chips.Winner("Winner", i);
                            delayAndDisplay("User beats the dealer.");
                        } else {
                            delayAndDisplay("User loses to the dealer.");
                        }

                    } else {
                        delayAndDisplay("User loses to the dealer.");
                    }
                }
        }
	}

    
    public String getDisplay() {
    	return display;
    }
    

    
    private void delayAndDisplay(String disp) {
        this.display = disp; //send to UI
        System.out.println(disp);
        this.ps.setDisplay(disp);
    }
    
    public int getDeckSize() {
    	return deck.getDeckSize();
    }

    private void dealCardWithCount(Hand hand, boolean yesCount) {
    	Card tempcard = this.deck.deal();
        hand.dealCard(tempcard);
        if (yesCount) {
        	this.updateCount(tempcard);
        }
    }

    private void updateCount(Card dealt) {

		this.count = this.count + dealt.getRank().getCountValue();

    }

    public int getCount() {
    	return this.count;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.pcs.addPropertyChangeListener(pcl);
    }

    // implement observer class
    public UserStatsObserver getObserver() {
    	if (this.observer == null) {
    		System.out.println("observer is null");
    	}
    	return this.observer;
    }


}

