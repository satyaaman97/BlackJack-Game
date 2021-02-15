import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// Observer pattern used to create a stats tracker
public class UserStatsObserver implements PropertyChangeListener{

	float totalMoves;
	float totalOptimal; 
	int percentOptimal;
	int cheatSheetViews;

	// property change listener for the stats observer
	public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("UserAction")){
        	
        	System.out.println("user action here");
        	
        	totalMoves = totalMoves + 1;
        	
        	if ((boolean)evt.getNewValue()) {
            	//user made optimal move
        		totalOptimal = totalOptimal + 1;
        	} 
        	
        	percentOptimal = Math.round(totalOptimal/totalMoves*100);
        	System.out.println("optimal: " + percentOptimal);
        }
        if (evt.getPropertyName().equals("cheatSheetView")) {
        	
        	cheatSheetViews = cheatSheetViews + 1;
        	
        }
	}

	
	public int getOptimal() {
		return percentOptimal;
	}
	
	
	public int getCheatSheetViews() {
		return cheatSheetViews;
	}
	
}
