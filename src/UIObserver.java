import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

// Observer pattern used for the UI to change the UI screens
public class UIObserver implements PropertyChangeListener {
	
	private final UIScreen l;
	private PlayScreen p;
	private final UIScreen pg;
	private final UIScreen n;
	
	UIObserver(ArrayList<UIScreen> screens){
		
		l = screens.get(0);
		//p = screens.get(1);
		//pg = screens.get(2);
		//n = screens.get(3);
		pg = screens.get(1);
		n = screens.get(2);
		
	}

	//switch between different screens in the UI
	public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("switch")){
        	
        	
        	JFrame frame1 =  null;
        	JFrame frame2 = null;
        	
        	switch((String) evt.getOldValue()) {
        		case "Login":

                    frame1 = l.getJFrame();

        	    break;
        		case "Play":

                    frame1 = p.getJFrame();
                   

        	    break;
          		case "PrePlay":

                    frame1 = pg.getJFrame();

        	    break;
          		case "NewGame":

                    frame1 = n.getJFrame();

        	    break;
        	}
        	
        	switch((String) evt.getNewValue()) {
	    		case "Play":
	    			
	    			//new game scenario
	            	frame1.setVisible(false);            	
	        		p = new PlayScreen();
	        		p.addPropertyChangeListener(this);
	        		BlackJack.getInstance().setPlayScreen(p);

	        		p.getJFrame().setVisible(true);

	    	    break;
        		case "Login":

                    frame2 = l.getJFrame();

        	    break;
          		case "PrePlay":

                    frame2 = pg.getJFrame();

        	    break;
          		case "NewGame":

                    frame2 = n.getJFrame();

        	    break;
        	}
    	

        	if (frame1 != null && frame2 != null) {

            	frame1.setVisible(false);
                frame2.setVisible(true);   
        	}
         
        }
        
	}
	
	
}
