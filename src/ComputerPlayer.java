public class ComputerPlayer extends Player{
    private Strategy strategy;

    public ComputerPlayer(Strategy strategy)
    {
        hands.add(new Hand());
        this.strategy = strategy;
    }

    // Use the strategy pattern to get the moves
    public String executeStrategy(Hand player, Hand dealer){
        return strategy.getMove(player, dealer);
    }


    // Display the hand of the computer player
    public void displayHand() {
        // Display each card in the hand
        for(int i = 0; i < hands.size(); i++){
            System.out.println("The Computer Player's Hand " + i + " is ");
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