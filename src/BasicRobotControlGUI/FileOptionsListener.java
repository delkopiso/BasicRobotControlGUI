package BasicRobotControlGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                gui.openFile = gui.fileChooser.getSelectedFile();
                gui.populateTable();
            }
        }else if (e.getSource() == gui.saveItem){
            int retval = gui.fileChooser.showSaveDialog(gui);
            if (retval == JFileChooser.APPROVE_OPTION) {
                gui.saveFile = gui.fileChooser.getSelectedFile();
                gui.stringToSave = gui.pollTable();
                MyUtil.writeToFile(gui.saveFile, gui.stringToSave);
            }
        }else if (e.getSource() == gui.prefItem){
            gui.settingsMenu.setVisible(true);
        }else if (e.getSource() == gui.exitItem){
            RobotArmGUI.client.quit();
        }else if (e.getSource() == gui.sensorItem1){
            gui.sonarSensorMenu.setVisible(true);
        }else if (e.getSource() == gui.sensorItem2){
            gui.ir1SensorMenu.setVisible(true);
        }else if (e.getSource() == gui.sensorItem3){
            gui.ir2SensorMenu.setVisible(true);
        }else if (e.getSource() == gui.sketchItem){
//        	gui.sketchPad.init();
        	gui.getContentPane().add(gui.sketchPad.getContentPane());
//        	gui.sketchPad.start();
        }
    }

}
