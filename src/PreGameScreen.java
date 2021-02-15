import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

// Ths is the pregame screen that adds the
public class PreGameScreen extends UIScreen {
	
	
	PreGameScreen(){
		super();
	
		f=new JFrame();
		pcs = new PropertyChangeSupport(this);
		f.setLayout(new GridLayout(3,3));
		
	    JPanel p1=new JPanel();  
	    JPanel p2=new JPanel();  
	    JPanel p3=new JPanel();  
	    JPanel p4=new JPanel();  
	    JPanel p5=new JPanel();  
	    JPanel p6=new JPanel();  
	    JPanel p7=new JPanel();  
	    JPanel p8 =new JPanel();  
	    JPanel p9=new JPanel(); 
	    // Setting up all the Buttons and it's layout
		p5.setLayout(new GridLayout(4,1));
        JButton newGame = new JButton("New Game");
		newGame.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
	            System.out.println("New Game");  
	            pcs.firePropertyChange("switch", "PrePlay", "NewGame");
	        }  
	    }); 
//        JButton viewStats = new JButton("View Stats");
//		viewStats.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e){
//	            System.out.println("View Stats");
//	            JFrame stats = new JFrame();
//	    		stats.setSize(400,400);
//	    		stats.setLayout(new GridLayout(2,1));
//	    		stats.add(new JLabel("User Stats:"));
//	    		stats.setVisible(true);
//	        }
//	    });
        JButton tutorial = new JButton("Tutorial");
		tutorial.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
	            System.out.println("Tutorial");
	            JFrame tut = new JFrame();
	            tut.setSize(400,400);
	    		tut.setLayout(new GridLayout(1,1));
	    		tut.add(new JLabel("<html><br/>Tutorial: The minimum bet is indicated on the placard in the corner of the table.<br/>" +
						"\n" +
						"If you are playing online blackjack, then choosing a seat doesn't apply.<br/>" +
						"\n" +
						"At the beginning of each hand, the player places a bet. After all player bets are placed, the dealer will then deal each player, and themselves, two cards. Both of the players' cards are dealt face up while one of the dealer's cards, called the \"hole card\", is dealt face down.<br/> The value of cards is as follows:<br/>\n" +
						"\n" +
						"Cards 2-10 represent their own numerical value.<br/>\n" +
						"Jacks, Queens, and Kings are worth 10.<br/>\n" +
						"Aces are worth either 1 or 11, whichever the player chooses.<br/>"
						+ "Read more at: https://www.homepokergames.com/blackjacktut.php <br/>"
						));
	    		tut.setVisible(true);
	        }
	    });
        JButton logout = new JButton("Log Out");
		logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
	            System.out.println("Log Out");
	            pcs.firePropertyChange("switch", "PrePlay", "Login");
	        }
	    });
	    p5.add(newGame);
	//    p5.add(viewStats);
	    p5.add(tutorial);
	    p5.add(logout);
        
	    f.add(p1);
	    f.add(p2);
	    f.add(p3);
	    f.add(p4);
	    f.add(p5);
	    f.add(p6);
	    f.add(p7);
	    f.add(p8);
	    f.add(p9);
	    f.setSize(1000,800); 
			
	}

}
