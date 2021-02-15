import java.util.*;

public class Hand {
    private ArrayList<Card> currentHand;
    private int currentHandValue;
    private Boolean activeHand;
    private String handStatus;

    // initialize the hand
    public Hand(){
        this.currentHand = new ArrayList<>();
        this.currentHandValue = 0;
        this.handStatus = "Loser";
        this.activeHand = true;
    }

    // add a card to the hand
    public void dealCard(Card card){
        this.currentHand.add(card);
        this.currentHandValue += card.getRank().getRankValue();
    }

    // reset the hand after a round
    public void resetHand(){
        this.currentHand.clear();
        this.currentHandValue = 0;
        this.handStatus = "Loser";
        this.activeHand = true;
    }

    public void addCard(Card card){
        this.currentHand.add(card);
    }

    public void removeCard(int cardIndex){
        if(currentHand.size() <= cardIndex) {
            this.currentHand.remove(cardIndex);
        }
    }

    public void recomputeHandValue(){
        this.currentHandValue = 0;
        for(Card card : currentHand){
            this.currentHandValue += card.getRank().getRankValue();
        }
    }

    public void setHandStatus(String winStatus){
        this.handStatus = winStatus;
    }

    // check if the hand is a winner or not
    public String getHandStatus(){
        return this.handStatus;
    }

    // get the value of the current hand
    public int getCurrentHandValue(){
        // If an Ace is involved return the highest one.
        if(checkForAce()){
            if(this.getCurrentHandHardValue() <= 21){
                return this.getCurrentHandHardValue();
            }
            return this.getCurrentHandSoftValue();
        }
        return this.currentHandValue;
    }

    // get the soft value when you have an ace
    public int getCurrentHandSoftValue(){
        return this.currentHandValue;
    }

    // get the hard value when you have an ace
    public int getCurrentHandHardValue(){
        return this.currentHandValue + 10;
    }

    // check if the hand is active or not in order to keep gameplay going on this hand
    public boolean isHandActive(){
        return this.activeHand;
    }

    public void setCurrentHandValue(int i){
        this.currentHandValue = i;
    }

    public void setIsHandActive(boolean active){
        this.activeHand = active;
    }

    // Check if the hand is a blackjack
    public boolean isBlackJack(){
        if((getCurrentHandValue() == 21) && (getCurrentHandCards().size() == 2)){
            return true;
        }
        return false;
    }

    // get the cards from the hand to analyze or display
    public ArrayList<Card> getCurrentHandCards(){
        return this.currentHand;
    }

    // check if the hand has an ace for special edge cases
    public boolean checkForAce(){
        for (Card card : this.currentHand) {
            if (card.getRank() == Rank.ACE) {
                return true;
            }
        }
        return false;
    }

}