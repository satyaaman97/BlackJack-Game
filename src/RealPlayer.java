// Use Singleton to create a real player
public class RealPlayer extends Player {
    private static RealPlayer realPlayerInstance = null;
    private Strategy strategy;

    private RealPlayer(Strategy strategy)
    {
        hands.add(new Hand());
        this.strategy = strategy;
    }

    public String executeStrategy(Hand player, Hand dealer){
        return strategy.getMove(player, dealer);
    }

    // Singleton pattern for creating the real player
    public static RealPlayer RealPlayer(Strategy strategy)
    {
        if (realPlayerInstance == null)
        {
            realPlayerInstance = new RealPlayer(strategy);
        }
        return realPlayerInstance;
    }

    public void displayHand() {
        // Display each card in the hand
        for(int i = 0; i < hands.size(); i++){
            System.out.println("The Real Player's Hand " + i + " is ");
            for(Card card : hands.get(i).getCurrentHandCards()){
                System.out.println(card.toString());
            }
            // If ace in hand and not busting with hard value display
            if (hands.get(i).checkForAce() && (hands.get(i).getCurrentHandHardValue() < 21)) {
                System.out.println("The Hand hard value is " + hands.get(i).getCurrentHandHardValue());
                System.out.println("The Hand soft value is " + hands.get(i).getCurrentHandSoftValue());
            } else {
                System.out.println("The Hand value is " + hands.get(i).getCurrentHandValue());
            }
            System.out.println();
        }
    }
}