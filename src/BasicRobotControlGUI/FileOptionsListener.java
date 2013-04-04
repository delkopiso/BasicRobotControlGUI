package BasicRobotControlGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFileChooser;

public class FileOptionsListener implements ActionListener {
    
    private RobotArmGUI gui;
    
    public FileOptionsListener(RobotArmGUI gui){
        this.gui = gui;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.openItem){
            int retval = gui.fileChooser.showOpenDialog(gui);
            if (retval == JFileChooser.APPROVE_OPTION) {
                gui.currentFile = gui.fileChooser.getSelectedFile();
                gui.populateTable();
            }
        }else if (e.getSource() == gui.saveItem){
            int retval = gui.fileChooser.showSaveDialog(gui);
            if (retval == JFileChooser.APPROVE_OPTION) {
                File file = gui.fileChooser.getSelectedFile();
                try {
                    PrintStream ps = new PrintStream(file);
                    ps.print(gui.pollTable());
                    ps.close();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        }else if (e.getSource() == gui.exitItem){
            RobotArmGUI.client.quit();
        }
    }

}
