package BasicRobotControlGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class DecrementButtonListener implements ActionListener {

	private JTextField field;

	public DecrementButtonListener(JTextField field){
		this.field = field;
	}

	public void actionPerformed(ActionEvent e) {
		decrement(field, e);
	}

	private void decrement(JTextField field, ActionEvent evt) {
		String text = field.getText();
		int num = Integer.parseInt(text);
		field.setText(String.valueOf(num-1));
	}

}
