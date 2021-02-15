import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyUnitTest {
    @Test // Check for dealer bust
    public void testDealerBust() {
        BlackJack game = BlackJack.getInstance();
        game.setPlayerNum(1, "Low");
        game.setDeckNum(5);

        game.dealer.hand.setCurrentHandValue(22);
        assertTrue(game.Bust(game.dealer.hand), "Dealer should have busted");
    }

    @Test // Check chip total
    public void testChipTotal() {
        BlackJack game = BlackJack.getInstance();
        game.setPlayerNum(1, "Low");
        game.setDeckNum(5);

        assertEquals(200, game.players.get(0).chips.getChipTotal(), "Chip total should be 200");
    }

    @Test // Check player num changes
    public void testPlayerSize() {
        BlackJack game = BlackJack.getInstance();
        game.setPlayerNum(5, "Low");
        game.setDeckNum(5);

        assertEquals(5, game.players.size(), "Player size should be 5");
    }

    @Test // Check hit method
    public void testHit() {
        BlackJack game = BlackJack.getInstance();
        game.setPlayerNum(5, "Low");
        game.setDeckNum(5);
        game.user.hands.add(new Hand());
        game.Hit(game.user.hands.get(0));
        assertEquals(1, game.user.hands.get(0).getCurrentHandCards().size(), "Player hand size should be 1");
    }

    @Test // Check stand method
    public void testStand() {
        BlackJack game = BlackJack.getInstance();
        game.setPlayerNum(5, "Low");
        game.setDeckNum(5);
        game.user.hands.clear();
        game.user.hands.add(new Hand());
        game.Stand(game.user.hands.get(0));
        assertEquals(0, game.user.hands.get(0).getCurrentHandCards().size(), "Player hand size should be 0");
    }

    @Test // Check chip bet of 50
    public void testBet() {
        BlackJack game = BlackJack.getInstance();
        game.setPlayerNum(5, "Low");
        game.setDeckNum(5);
        game.user.hands.clear();
        game.user.hands.add(new Hand());
        game.user.chips.Bet(50, 0);
        assertEquals(150, game.user.chips.getChipTotal(), "Player chip total should be 100");
    }

    @Test // Check chips double down method
    public void testDoubleDownChips() {
        BlackJack game = BlackJack.getInstance();
        game.setPlayerNum(5, "Low");
        game.setDeckNum(5);
        game.players.get(1).hands.add(new Hand());
        game.players.get(1).chips.Bet(50, 0);
        game.players.get(1).chips.DoubleDown(0);
        assertEquals(100,  game.players.get(1).chips.getChipTotal(), "Player chip total should be 100");
    }

    @Test // CheckforWinners
    public void testPlayerLose() {
        // Make sure I can buy a roll correctly
        BlackJack game = BlackJack.getInstance();
        game.setPlayerNum(4, "Low");
        game.setDeckNum(5);

        game.dealer.hand.setCurrentHandValue(21);
        game.players.get(0).hands.get(0).setCurrentHandValue(2);
        game.players.get(1).hands.get(0).setCurrentHandValue(22);


        assertEquals("Loser", game.players.get(0).hands.get(0).getHandStatus(), "Player should be loser");
        assertEquals("Loser", game.players.get(1).hands.get(0).getHandStatus(), "Player should be loser");

    }

    @Test // Check for blackjack and deactivate hand
    public void testBlackJack() {
        // Make sure I can buy a roll correctly
        BlackJack game = BlackJack.getInstance();
        game.setPlayerNum(2, "Low");
        game.setDeckNum(5);
        game.dealer.hand.setCurrentHandValue(21);
        game.players.get(0).hands.get(0).addCard(Card.getCard(Rank.ACE, Suit.HEARTS));
        game.players.get(0).hands.get(0).addCard(Card.getCard(Rank.KING, Suit.HEARTS));
        game.players.get(1).hands.get(0).addCard(Card.getCard(Rank.ACE, Suit.HEARTS));
        game.players.get(1).hands.get(0).addCard(Card.getCard(Rank.KING, Suit.HEARTS));
        game.players.get(1).hands.get(0).setCurrentHandValue(21);
        game.players.get(0).hands.get(0).setCurrentHandValue(21);
        game.checkBlackJack(game.players.get(0));
        game.checkBlackJack(game.players.get(1));



        assertEquals("BlackJack", game.players.get(0).hands.get(0).getHandStatus(), "Player should be winner");
        assertEquals("BlackJack", game.players.get(1).hands.get(0).getHandStatus(), "Player should be winner");
        assertFalse(game.players.get(0).hands.get(0).isHandActive(), "Player hand should be inactive");
        assertFalse(game.players.get(1).hands.get(0).isHandActive(), "Player hand should be inactive");

    }

    @Test // Check deck amount
    public void testDeckSize() {
        // Make sure I can buy a roll correctly
        BlackJack game = BlackJack.getInstance();
        game.setPlayerNum(1, "Low");
        game.setDeckNum(5);
        assertEquals(260, game.deck.getDeckSize(), "Deck amount should be 208");
        game.deck.deal();
        assertEquals(259, game.deck.getDeckSize(), "Deck amount should be 207");
        for(int i = 0; i < 250; i++){
            game.deck.deal();
        }
        assertTrue(game.deck.checkDeck(), "Deck amount should be too small now");
    }

    @Test // Check deck amount
    public void testCardInHand() {
        BlackJack game = BlackJack.getInstance();
        game.setPlayerNum(2, "Low");
        game.setDeckNum(5);
        game.players.get(0).hands.get(0).addCard(Card.getCard(Rank.ACE, Suit.HEARTS));
        game.players.get(0).hands.get(0).addCard(Card.getCard(Rank.KING, Suit.HEARTS));
        game.players.get(1).hands.get(0).addCard(Card.getCard(Rank.ACE, Suit.HEARTS));
        game.players.get(1).hands.get(0).addCard(Card.getCard(Rank.KING, Suit.HEARTS));
        assertEquals(2, game.players.get(0).hands.get(0).getCurrentHandCards().size(), "Player 1 hand size should be two");
        assertEquals(2, game.players.get(1).hands.get(0).getCurrentHandCards().size(), "Player 2 hand size should be two");
    }

}