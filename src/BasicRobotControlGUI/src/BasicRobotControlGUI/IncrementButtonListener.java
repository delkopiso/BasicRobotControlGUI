package BasicRobotControlGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class IncrementButtonListener implements ActionListener {

    private RobotArmGUI gui;
    private JTextField field;
    
	public IncrementButtonListener(RobotArmGUI gui, JTextField field){
        this.gui = gui;
        this.field = field;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		increment(field, e);
		if (gui.getJogMode()){
            gui.sendPos();
        }
	}

	private void increment(JTextField field, ActionEvent evt) {
		String text = field.getText();
		double num = Double.parseDouble(text);
		field.setText(String.valueOf(num+1));
	}
}
