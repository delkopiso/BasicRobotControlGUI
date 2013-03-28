package BasicRobotControlGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class IncrementButtonListener implements ActionListener {

	private JTextField field;

	public IncrementButtonListener(JTextField field){
		this.field = field;
	}

	public void actionPerformed(ActionEvent e) {
		increment(field, e);
	}

	private void increment(JTextField field, ActionEvent evt) {
		String text = field.getText();
		int num = Integer.parseInt(text);
		field.setText(String.valueOf(num+1));
	}
}
