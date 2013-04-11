package BasicRobotControlGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class DecrementButtonListener implements ActionListener {

    private RobotArmGUI gui;
	private JTextField field;

	public DecrementButtonListener(RobotArmGUI gui, JTextField field){
	    this.gui = gui;
	    this.field = field;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    decrement(field, e);
	    if (gui.getJogMode()){
	        gui.sendPos();
	    }
	}

	private void decrement(JTextField field, ActionEvent evt) {
		String text = field.getText();
		int num = Integer.parseInt(text);
		field.setText(String.valueOf(num-1));
	}

}
