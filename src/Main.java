import javax.swing.*;  
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		//Initialize all the screens
		UIScreen login = new LoginScreen();
		//PlayScreen play = new PlayScreen();
		UIScreen preplay =  new PreGameScreen();
		UIScreen newGame = new NewGameScreen();
		ArrayList<UIScreen> screens = new ArrayList<UIScreen>();
		screens.add(login);
		//screens.add(play);
		screens.add(preplay);
		screens.add(newGame);
		
		//Initialize UI observer and send it all the screens
		UIObserver ui = new UIObserver(screens);
		login.addPropertyChangeListener(ui);
		//play.addPropertyChangeListener(ui);
		preplay.addPropertyChangeListener(ui);
		newGame.addPropertyChangeListener(ui);
		
		//BlackJack.getInstance().addPropertyChangeListener(play);				
	}

}
