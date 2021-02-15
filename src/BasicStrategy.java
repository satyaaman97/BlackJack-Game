
import java.util.HashMap;
import java.util.Map;
// This strategy implements a basic blackjack strategy giving the best move given the users card and the dealer's up card
public class BasicStrategy implements Strategy{
        private final Map<String, String> table = populateTable();

        public String getMove(Hand player, Hand dealer) {
                String key = combine(player, dealer);
                String move;
                if (table.containsKey(key)) {
                	move = table.get(key);
                } else {
                	move = "s";
                }
                return move;
        }


        public String combine(Hand player, Hand dealer){
                // First check if it is the initial hand
                if(player.getCurrentHandCards().size() == 2) {
                        // If initial hand, check if the cards are a double
                        if(player.getCurrentHandCards().get(0).getRank() == player.getCurrentHandCards().get(1).getRank()) {
                                return String.format("d%s,%s", player.getCurrentHandSoftValue(), dealer.getCurrentHandCards().get(1).getRank().getRankValue());
                        }
                        // If not a double, check if there is an Ace in the hand
                        else if(player.checkForAce()) {
                                return String.format("s%s,%s", player.getCurrentHandSoftValue(), dealer.getCurrentHandCards().get(1).getRank().getRankValue());
                        }
                        // Else use the hand value
                        else{
                                return String.format("%s,%s", player.getCurrentHandValue(), dealer.getCurrentHandCards().get(1).getRank().getRankValue());
                        }
                }
                // If it is not the initial hand, doubles don't matter
                else {
                        // Check for Ace
                        if(player.checkForAce()) {
                                return String.format("s%s,%s", player.getCurrentHandSoftValue(), dealer.getCurrentHandCards().get(1).getRank().getRankValue());
                        }
                        else {
                                // Else use the hand value
                                return String.format("%s,%s", player.getCurrentHandValue(), dealer.getCurrentHandCards().get(1).getRank().getRankValue());
                        }
                }
        }

    public Map<String, String> populateTable(){
            final Map<String, String> hashMap = new HashMap<>();
            // 20
            hashMap.put("20,2", "s");
            hashMap.put("20,3", "s");
            hashMap.put("20,4", "s");
            hashMap.put("20,5", "s");
            hashMap.put("20,6", "s");
            hashMap.put("20,7", "s");
            hashMap.put("20,8", "s");
            hashMap.put("20,9", "s");
            hashMap.put("20,10", "s");
            hashMap.put("20,A", "s");
            // 19
            hashMap.put("19,2", "s");
            hashMap.put("19,3", "s");
            hashMap.put("19,4", "s");
            hashMap.put("19,5", "s");
            hashMap.put("19,6", "s");
            hashMap.put("19,7", "s");
            hashMap.put("19,8", "s");
            hashMap.put("19,9", "s");
            hashMap.put("19,10", "s");
            hashMap.put("19,A", "s");
            // 18
            hashMap.put("18,2", "s");
            hashMap.put("18,3", "s");
            hashMap.put("18,4", "s");
            hashMap.put("18,5", "s");
            hashMap.put("18,6", "s");
            hashMap.put("18,7", "s");
            hashMap.put("18,8", "s");
            hashMap.put("18,9", "s");
            hashMap.put("18,10", "s");
            hashMap.put("18,A", "s");
            // 17
            hashMap.put("17,2", "s");
            hashMap.put("17,3", "s");
            hashMap.put("17,4", "s");
            hashMap.put("17,5", "s");
            hashMap.put("17,6", "s");
            hashMap.put("17,7", "s");
            hashMap.put("17,8", "s");
            hashMap.put("17,9", "s");
            hashMap.put("17,10", "s");
            hashMap.put("17,A", "s");
            // 16
            hashMap.put("16,2", "s");
            hashMap.put("16,3", "s");
            hashMap.put("16,4", "s");
            hashMap.put("16,5", "s");
            hashMap.put("16,6", "s");
            hashMap.put("16,7", "h");
            hashMap.put("16,8", "h");
            hashMap.put("16,9", "h");
            hashMap.put("16,10", "h");
            hashMap.put("16,A", "h");
            // 15
            hashMap.put("15,2", "s");
            hashMap.put("15,3", "s");
            hashMap.put("15,4", "s");
            hashMap.put("15,5", "s");
            hashMap.put("15,6", "s");
            hashMap.put("15,7", "h");
            hashMap.put("15,8", "h");
            hashMap.put("15,9", "h");
            hashMap.put("15,10", "h");
            hashMap.put("15,A", "h");
            // 14
            hashMap.put("14,2", "s");
            hashMap.put("14,3", "s");
            hashMap.put("14,4", "s");
            hashMap.put("14,5", "s");
            hashMap.put("14,6", "s");
            hashMap.put("14,7", "h");
            hashMap.put("14,8", "h");
            hashMap.put("14,9", "h");
            hashMap.put("14,10", "h");
            hashMap.put("14,A", "h");
            // 13
            hashMap.put("13,2", "s");
            hashMap.put("13,3", "s");
            hashMap.put("13,4", "s");
            hashMap.put("13,5", "s");
            hashMap.put("13,6", "s");
            hashMap.put("13,7", "h");
            hashMap.put("13,8", "h");
            hashMap.put("13,9", "h");
            hashMap.put("13,10", "h");
            hashMap.put("13,A", "h");
            // 12
            hashMap.put("12,2", "h");
            hashMap.put("12,3", "h");
            hashMap.put("12,4", "s");
            hashMap.put("12,5", "s");
            hashMap.put("12,6", "s");
            hashMap.put("12,7", "h");
            hashMap.put("12,8", "h");
            hashMap.put("12,9", "h");
            hashMap.put("12,10", "h");
            hashMap.put("12,A", "h");
            // 11
            hashMap.put("11,2", "d");
            hashMap.put("11,3", "d");
            hashMap.put("11,4", "d");
            hashMap.put("11,5", "d");
            hashMap.put("11,6", "d");
            hashMap.put("11,7", "d");
            hashMap.put("11,8", "d");
            hashMap.put("11,9", "d");
            hashMap.put("11,10", "d");
            hashMap.put("11,A", "d");
            // 10
            hashMap.put("10,2", "d");
            hashMap.put("10,3", "d");
            hashMap.put("10,4", "d");
            hashMap.put("10,5", "d");
            hashMap.put("10,6", "d");
            hashMap.put("10,7", "d");
            hashMap.put("10,8", "d");
            hashMap.put("10,9", "d");
            hashMap.put("10,10", "h");
            hashMap.put("10,A", "h");
            // 9
            hashMap.put("9,2", "d");
            hashMap.put("9,3", "d");
            hashMap.put("9,4", "d");
            hashMap.put("9,5", "d");
            hashMap.put("9,6", "d");
            hashMap.put("9,7", "h");
            hashMap.put("9,8", "h");
            hashMap.put("9,9", "h");
            hashMap.put("9,10", "h");
            hashMap.put("9,A", "h");
            // 8
            hashMap.put("8,2", "h");
            hashMap.put("8,3", "h");
            hashMap.put("8,4", "h");
            hashMap.put("8,5", "d");
            hashMap.put("8,6", "d");
            hashMap.put("8,7", "h");
            hashMap.put("8,8", "h");
            hashMap.put("8,9", "h");
            hashMap.put("8,10", "h");
            hashMap.put("8,A", "h");
            // 7
            hashMap.put("7,2", "h");
            hashMap.put("7,3", "h");
            hashMap.put("7,4", "h");
            hashMap.put("7,5", "h");
            hashMap.put("7,6", "h");
            hashMap.put("7,7", "h");
            hashMap.put("7,8", "h");
            hashMap.put("7,9", "h");
            hashMap.put("7,10", "h");
            hashMap.put("7,A", "h");
            // 6
            hashMap.put("6,2", "h");
            hashMap.put("6,3", "h");
            hashMap.put("6,4", "h");
            hashMap.put("6,5", "h");
            hashMap.put("6,6", "h");
            hashMap.put("6,7", "h");
            hashMap.put("6,8", "h");
            hashMap.put("6,9", "h");
            hashMap.put("6,10", "h");
            hashMap.put("6,A", "h");
            // 5
            hashMap.put("5,2", "h");
            hashMap.put("5,3", "h");
            hashMap.put("5,4", "h");
            hashMap.put("5,5", "h");
            hashMap.put("5,6", "h");
            hashMap.put("5,7", "h");
            hashMap.put("5,8", "h");
            hashMap.put("5,9", "h");
            hashMap.put("5,10", "h");
            hashMap.put("5,A", "h");
            // A,2
            hashMap.put("a3,2", "h");
            hashMap.put("s3,3", "h");
            hashMap.put("s3,4", "d");
            hashMap.put("s3,5", "d");
            hashMap.put("s3,6", "d");
            hashMap.put("s3,7", "h");
            hashMap.put("s3,8", "h");
            hashMap.put("s3,9", "h");
            hashMap.put("s3,10", "h");
            hashMap.put("s3,A", "h");
            // A,3
            hashMap.put("s4,2", "h");
            hashMap.put("s4,3", "h");
            hashMap.put("s4,4", "d");
            hashMap.put("s4,5", "d");
            hashMap.put("s4,6", "d");
            hashMap.put("s4,7", "h");
            hashMap.put("s4,8", "h");
            hashMap.put("s4,9", "h");
            hashMap.put("s4,10", "h");
            hashMap.put("s4,A", "h");
            // A,4
            hashMap.put("s5,2", "h");
            hashMap.put("s5,3", "h");
            hashMap.put("s5,4", "d");
            hashMap.put("s5,5", "d");
            hashMap.put("s5,6", "d");
            hashMap.put("s5,7", "h");
            hashMap.put("s5,8", "h");
            hashMap.put("s5,9", "h");
            hashMap.put("s5,10", "h");
            hashMap.put("s5,A", "h");
            // A,5
            hashMap.put("s6,2", "h");
            hashMap.put("s6,3", "h");
            hashMap.put("s6,4", "d");
            hashMap.put("s6,5", "d");
            hashMap.put("s6,6", "d");
            hashMap.put("s6,7", "h");
            hashMap.put("s6,8", "h");
            hashMap.put("s6,9", "h");
            hashMap.put("s6,10", "h");
            hashMap.put("s6,A", "h");
            // A,6
            hashMap.put("s7,2", "d");
            hashMap.put("s7,3", "d");
            hashMap.put("s7,4", "d");
            hashMap.put("s7,5", "d");
            hashMap.put("s7,6", "d");
            hashMap.put("s7,7", "h");
            hashMap.put("s7,8", "h");
            hashMap.put("s7,9", "h");
            hashMap.put("s7,10", "h");
            hashMap.put("s7,A", "h");
            // A,7
            hashMap.put("s8,2", "s");
            hashMap.put("s8,3", "d");
            hashMap.put("s8,4", "d");
            hashMap.put("s8,5", "d");
            hashMap.put("s8,6", "d");
            hashMap.put("s8,7", "s");
            hashMap.put("s8,8", "s");
            hashMap.put("s8,9", "h");
            hashMap.put("s8,10", "h");
            hashMap.put("s8,A", "s");
            // A,8
            hashMap.put("s9,2", "s");
            hashMap.put("s9,3", "s");
            hashMap.put("s9,4", "s");
            hashMap.put("s9,5", "s");
            hashMap.put("s9,6", "d");
            hashMap.put("s9,7", "s");
            hashMap.put("s9,8", "s");
            hashMap.put("s9,9", "s");
            hashMap.put("s9,10", "s");
            hashMap.put("s9,A", "s");
            // A,9
            hashMap.put("s10,2", "s");
            hashMap.put("s10,3", "s");
            hashMap.put("s10,4", "s");
            hashMap.put("s10,5", "s");
            hashMap.put("s10,6", "s");
            hashMap.put("s10,7", "s");
            hashMap.put("s10,8", "s");
            hashMap.put("s10,9", "s");
            hashMap.put("s10,10", "s");
            hashMap.put("s10,A", "s");
            // Double A
            hashMap.put("d2,2", "h");
            hashMap.put("d2,3", "h");
            hashMap.put("d2,4", "h");
            hashMap.put("d2,5", "h");
            hashMap.put("d2,6", "h");
            hashMap.put("d2,7", "h");
            hashMap.put("d2,8", "h");
            hashMap.put("d2,9", "h");
            hashMap.put("d2,10", "h");
            hashMap.put("d2,A", "h");
            // Double 10
            hashMap.put("d20,2", "s");
            hashMap.put("d20,3", "s");
            hashMap.put("d20,4", "s");
            hashMap.put("d20,5", "s");
            hashMap.put("d20,6", "s");
            hashMap.put("d20,7", "s");
            hashMap.put("d20,8", "s");
            hashMap.put("d20,9", "s");
            hashMap.put("d20,10", "s");
            hashMap.put("d20,A", "s");
            // Double 9
            hashMap.put("d18,2", "h");
            hashMap.put("d18,3", "h");
            hashMap.put("d18,4", "h");
            hashMap.put("d18,5", "h");
            hashMap.put("d18,6", "h");
            hashMap.put("d18,7", "s");
            hashMap.put("d18,8", "h");
            hashMap.put("d18,9", "h");
            hashMap.put("d18,10", "s");
            hashMap.put("d18,A", "s");
            // Double 8
            hashMap.put("d16,2", "h");
            hashMap.put("d16,3", "h");
            hashMap.put("d16,4", "h");
            hashMap.put("d16,5", "h");
            hashMap.put("d16,6", "h");
            hashMap.put("d16,7", "h");
            hashMap.put("d16,8", "h");
            hashMap.put("d16,9", "h");
            hashMap.put("d16,10", "h");
            hashMap.put("d16,A", "h");
            // Double 7
            hashMap.put("d14,2", "h");
            hashMap.put("d14,3", "h");
            hashMap.put("d14,4", "h");
            hashMap.put("d14,5", "h");
            hashMap.put("d14,6", "h");
            hashMap.put("d14,7", "h");
            hashMap.put("d14,8", "h");
            hashMap.put("d14,9", "h");
            hashMap.put("d14,10", "s");
            hashMap.put("d14,A", "h");
            // Double 6
            hashMap.put("d12,2", "h");
            hashMap.put("d12,3", "h");
            hashMap.put("d12,4", "h");
            hashMap.put("d12,5", "h");
            hashMap.put("d12,6", "h");
            hashMap.put("d12,7", "h");
            hashMap.put("d12,8", "h");
            hashMap.put("d12,9", "h");
            hashMap.put("d12,10", "h");
            hashMap.put("d12,A", "h");
            // Double 5
            hashMap.put("d10,2", "d");
            hashMap.put("d10,3", "d");
            hashMap.put("d10,4", "d");
            hashMap.put("d10,5", "d");
            hashMap.put("d10,6", "d");
            hashMap.put("d10,7", "d");
            hashMap.put("d10,8", "d");
            hashMap.put("d10,9", "d");
            hashMap.put("d10,10", "h");
            hashMap.put("d10,A", "h");
            // Double 4
            hashMap.put("d8,2", "h");
            hashMap.put("d8,3", "h");
            hashMap.put("d8,4", "h");
            hashMap.put("d8,5", "d");
            hashMap.put("d8,6", "d");
            hashMap.put("d8,7", "h");
            hashMap.put("d8,8", "h");
            hashMap.put("d8,9", "h");
            hashMap.put("d8,10", "h");
            hashMap.put("d8,A", "h");
            // Double 3
            hashMap.put("d6,2", "h");
            hashMap.put("d6,3", "h");
            hashMap.put("d6,4", "h");
            hashMap.put("d6,5", "h");
            hashMap.put("d6,6", "h");
            hashMap.put("d6,7", "h");
            hashMap.put("d6,8", "h");
            hashMap.put("d6,9", "h");
            hashMap.put("d6,10", "h");
            hashMap.put("d6,A", "h");
            // Double 2
            hashMap.put("d4,2", "h");
            hashMap.put("d4,3", "h");
            hashMap.put("d4,4", "h");
            hashMap.put("d4,5", "h");
            hashMap.put("d4,6", "h");
            hashMap.put("d4,7", "h");
            hashMap.put("d4,8", "h");
            hashMap.put("d4,9", "h");
            hashMap.put("d4,10", "h");
            hashMap.put("d4,A", "h");

            return hashMap;
    }
}


