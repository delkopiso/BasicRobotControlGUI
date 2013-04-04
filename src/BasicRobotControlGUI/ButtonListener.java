package BasicRobotControlGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

    private RobotArmGUI gui;
    
    public ButtonListener(RobotArmGUI gui) {
        this.gui = gui;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.runButton){
            while(gui.run && gui.currentRow <gui.dataTableModel.getRowCount()){
                try{
                    Thread.sleep(3000);
                }catch(Exception ex){
                    
                }
                int[] values = new int[gui.COLUMN_SIZE];
                for (int i=0; i < gui.COLUMN_SIZE; i++){
                    String val = (String) gui.dataTableModel.getValueAt(gui.currentRow, i);
                    values[i] = Integer.parseInt(val);
                }
                for(int i : values){
                    System.out.println(i);
                }
//                RobotArmGUI.client.send(MyUtil.convertIntsToStringFormat(values));
                ++gui.currentRow;
            }
        }else if (e.getSource() == gui.goButton){
            int[] posArray = gui.getPosArray();
            String msg = MyUtil.convertIntsToStringFormat(posArray);
            System.out.println(msg);
            RobotArmGUI.client.send(msg);
        }else if (e.getSource() == gui.stopButton){
            gui.run = false;
            try{
                Thread.sleep(30);
            }catch(Exception ex){
            }
            gui.run = true;
        }else if (e.getSource() == gui.resetButton){
            gui.currentRow = 0;
        }else if (e.getSource() == gui.jogSwitch){
            JSwitchBox swBtn = (JSwitchBox)e.getSource();
            boolean value = swBtn.isSelected();
            gui.jogMode = value;
        }
    }

}
