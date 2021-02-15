import java.util.ArrayList;

// Player class that is used by the real player and computer player
// This class holds the hand and the chips
public abstract class Player {
    public ArrayList<Hand> hands = new ArrayList<>();
    public PlayerChips chips = new PlayerChips();

    public abstract String executeStrategy(Hand player, Hand dealer);
    public abstract void displayHand();
    public void displayBet(){
        for(int i = 0; i < hands.size(); i++) {
            System.out.println("Bet for hand " + i + " is " + chips.getBet(i));
        }
    }
}