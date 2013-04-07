package BasicRobotControlGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class ButtonListener implements ActionListener {

    protected RobotArmGUI gui;
    
    public ButtonListener(RobotArmGUI gui) {
        this.gui = gui;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.runButton){
            SwingWorker<Integer,Void> tableRunner = new SwingWorker<Integer,Void>(){
                @Override
                protected Integer doInBackground() throws Exception {
                    int numRows = gui.dataTableModel.getRowCount();
                    int current = gui.currentRow;
                    while(gui.run && current <numRows){
                        gui.sendTableEntry(current);
                        ++current;
                        Thread.sleep(gui.runDelay);
                    }
                    return current;
                }
                @Override
                protected void done(){
                    try {
                        gui.currentRow = get();
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    } catch (ExecutionException e) {
                        System.out.println(e);
                    }
                    gui.run = true;
                }
            };
            tableRunner.execute();
        }else if (e.getSource() == gui.goButton){
            gui.sendPos();
        }else if (e.getSource() == gui.stopButton){
            System.out.println("STOP BUTTON PRESSED");
            gui.run = false;
        }else if (e.getSource() == gui.resetButton){
            System.out.println("RESET BUTTON PRESSED");
            gui.currentRow = 0;
        }else if (e.getSource() == gui.jogSwitch){
            JSwitchBox swBtn = (JSwitchBox)e.getSource();
            boolean value = swBtn.isSelected();
            gui.jogMode = value;
        }
    }

}
