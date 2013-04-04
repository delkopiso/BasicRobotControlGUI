package BasicRobotControlGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class IncrementButtonListener implements ActionListener {

    private RobotArmGUI gui;
    private RobotArmClient client;
    private JTextField field;
    
	public IncrementButtonListener(RobotArmGUI gui, RobotArmClient client, JTextField field){
        this.gui = gui;
        this.client = client;
        this.field = field;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		increment(field, e);
		if (gui.getJogMode()){
            int[] posArray = gui.getPosArray();
            String msg = MyUtil.convertIntsToStringFormat(posArray);
            System.out.println(msg);
            client.send(msg);
        }
	}

	private void increment(JTextField field, ActionEvent evt) {
		String text = field.getText();
		int num = Integer.parseInt(text);
		field.setText(String.valueOf(num+1));
	}
}
