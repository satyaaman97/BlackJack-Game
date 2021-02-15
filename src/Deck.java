import java.util.*;

public class Deck {
    private final Stack<Card> deck;

    public Deck(int numOfDecks){
        this.deck = initDeck(numOfDecks);
    }

    // initialize the deck and get the cards from the card factory
    private Stack<Card> initDeck(int deckNumber){
        final Stack<Card> deck = new Stack<>();
        for(int i = 0; i < deckNumber; i ++) {
            for (final Suit suit : Suit.values()) {
                for (final Rank rank : Rank.values()) {
                    deck.push(Card.getCard(rank, suit));
                }
            }
        }

        Collections.shuffle(deck);
        return deck;
    }

    // get a new deck
    public static Deck newDeck(int numOfDecks) {
        return new Deck(numOfDecks);
    }

    // deal the deck
    public Card deal(){
        Card top =  this.deck.pop();
        return top;      
    }

    public boolean checkDeck(){
        return deck.size() < 15;
    }

    public int getDeckSize(){
        return deck.size();
    }
    


}
