

// Singleton for creating one dealer
public class Dealer{
    public Hand hand;
    private static Dealer dealerInstance = null;
    boolean showAllCards;

    private Dealer()
    {
        hand = new Hand();
        showAllCards = false;
    }


    // Singleton pattern for creating the dealer
    public static Dealer Dealer()
    {
        if (dealerInstance == null)
        {
            dealerInstance = new Dealer();
        }
        return dealerInstance;
    }

    public void setShowAllCards(){
        showAllCards = !showAllCards;
    }

    public boolean getShowAllCards(){
        return this.showAllCards;
    }

    // display hand function for the dealer which takes into account the up card
    public void displayHand() {
        if (showAllCards) {
            System.out.println("The Dealer's Hand is ");
            for (Card card : hand.getCurrentHandCards()) {
                System.out.println(card.toString());
            }
            System.out.println("The Dealer's Hand value is " + hand.getCurrentHandValue());
        } else {
            System.out.println("The Dealer's up card is the " + hand.getCurrentHandCards().get(1).toString());
            if (hand.getCurrentHandCards().get(1).getRank() == Rank.ACE) {
                System.out.println("The Dealer's hard hand value is " + 11);
                System.out.println("The Dealer's soft hand value is " + 1);
            } else {
                System.out.println("The Dealer's hand value is " + hand.getCurrentHandCards().get(1).getRank().getRankValue());
            }
        }
        System.out.println();
    }
}