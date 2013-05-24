package BasicRobotControlGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class ButtonListener2 implements ActionListener {

    protected SensorMenu smenu;

    public ButtonListener2(SensorMenu sensorMenu) {
		// TODO Auto-generated constructor stub
    	this.smenu = sensorMenu;
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println(e);
        if (e.getSource() == smenu.sensorOnRadioButton){
        	System.out.println("HELLO");
            smenu.sensorMode = (!smenu.sensorMode) ? true : false;
            
        }else if (e.getSource() == smenu.sensorOffRadioButton) {
        	System.out.println("goodbye");
        	smenu.sensorMode = (smenu.sensorMode) ? false : true;
        }
    }
}
