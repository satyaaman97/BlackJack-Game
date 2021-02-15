import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

// create the UI screen
public class UIScreen {
	
	protected PropertyChangeSupport pcs;
	protected JFrame f; 

	public JFrame getJFrame() {
		return this.f;
	}
	
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.pcs.addPropertyChangeListener(pcl);
    }

}
