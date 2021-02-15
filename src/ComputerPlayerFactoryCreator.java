// Implementing a factory class in case we want to add different types of computer players
public class ComputerPlayerFactoryCreator extends ComputerPlayerFactory{
    public ComputerPlayer createComputerPlayer(Strategy strategy) {
        return new ComputerPlayer(strategy);
    }
}