// create the ranks for the cards and the value that is assigned to it

public enum Rank {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10),
    ACE(1);

    private final int rankValue;
    private final int countValue;

    Rank(final int rankValue) {
        this.rankValue = rankValue;
        
        if (this.rankValue >= 2 && this.rankValue <= 6) {
        	this.countValue = 1;
        } else if (this.rankValue >= 7 && this.rankValue <= 9) {
        	this.countValue = 0;
        } else if (this.rankValue >= 10) {
        	this.countValue = -1;
        } else if (this.rankValue == 1) {
        	this.countValue = -1;
        } else {
        	this.countValue = 0;
        }
        
    }

    public int getRankValue() {
        return this.rankValue;
    }
    
    public int getCountValue() {
        return this.countValue;
    }
}
