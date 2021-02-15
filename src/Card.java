import java.util.HashMap;
import java.util.Map;

// Card code adapted from https://www.youtube.com/watch?v=5QyU35ct6M0

public class Card {
    private final Rank rank;
    private final Suit suit;

    private final static Map<String, Card> cards = cardFactory();

    // create the cards using a factory
    private static Map<String, Card> cardFactory() {
        final Map<String, Card> hashMap = new HashMap<>();
        // Make 5 Decks
        for(int i = 0; i< 5; i++) {
            for (final Suit suit : Suit.values()) {
                for (final Rank rank : Rank.values()) {
                    hashMap.put(cardKey(rank, suit), new Card(rank, suit));
                }
            }
        }
        return hashMap;
    }

    public static Card getCard(final Rank rank, final Suit suit) {
        return cards.get(cardKey(rank, suit));
    }

    private static String cardKey(final Rank rank, final Suit suit) {
        return rank + " of " + suit;
    }

    private Card(final Rank rank, final Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String toString() {
        return String.format("%s of %s", this.rank, this.suit);
    }

    public Rank getRank(){
        return this.rank;
    }

    public Suit getSuit(){
        return this.suit;
    }
}
