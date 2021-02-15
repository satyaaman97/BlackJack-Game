

// chips used by the players
public class PlayerChips {
    //private Map<Integer, Integer> chips;
    private int chipTotal = 200;
    private int[] bet = new int[2];


    public int getChipTotal(){
        return chipTotal;
    }
    
    public void setChipTotal(int chips) {
    	chipTotal = chips;
    }

    public int getBet(int betHand){
        return bet[betHand];
    }

    public void Bet(int amount, int betHand){
        this.bet[betHand] = amount;
        this.chipTotal -= amount;
    }

    public void Split(){
        this.bet[1] = this.bet[0];
        this.chipTotal -= this.bet[1];
    }

    // double the chips if the user doubles down
    public void DoubleDown(int betHand){
        this.chipTotal -= this.bet[betHand];
        this.bet[betHand] += this.bet[betHand];
    }

    // if push, get the chips back
    public void Push(int betHand){
        this.chipTotal += this.bet[betHand];
    }

    // get 1.5 chips back if the user has a blackjack, else get back 1x if not
    public void Winner(String winType, int betHand){
        if(winType == "BlackJack"){
            chipTotal += this.bet[betHand] + (int)(this.bet[betHand]*1.5);
        }
        else{
            chipTotal += 2*this.bet[betHand];
        }
    }
}