package BasicRobotControlGUI;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SensorMenu2  extends JFrame {

    private static final long serialVersionUID = 1L;
    private RobotArmGUI armGUI;
    private JPanel limitPanel;
	private JPanel limitPanel2;
    private JLabel nullLabel, window, window2, xlabel, ylabel, zlabel;

    private JCheckBox sonarBox, ir1box, ir2box;
    private JTextField xTextField, yTextField, zTextField;
    private JLabel mm1, mm2, mm3;
    protected JButton OKButton;
    private Preferences preferences;
    private final String X_PREFS = "X-Pos Window";
    private final String Y_PREFS = "Y-Pos Window";
    private final String Z_PREFS = "Y-Pos Window";
    private String SonarSensor_DEFAULT = "500";//cm
    private String IR1_DEFAULT = "100";//cm
    private String IR2_DEFAULT = "100";//cm
    
    protected JRadioButton sonarSensorOn;
    protected JRadioButton sonarSensorOff;
    protected JRadioButton ir1SensorOn;
    protected JRadioButton ir1SensorOff;
    protected JRadioButton ir2SensorOn;
    protected JRadioButton ir2SensorOff;
	private ButtonGroup sonarButtonGroup;
	private ButtonGroup ir2SensorGroup;
	private ButtonGroup ir1SensorGroup;
    private JLabel SonarLabel;
    private JLabel ir1Label;
    private JLabel ir2Label;
	private JPanel mainPanel;
	private JLabel nullLabel2;

    public SensorMenu2 (RobotArmGUI gui) {
        this.armGUI = gui;
        initComponents();
    }
    
    private void initComponents(){        
        preferences = Preferences.userNodeForPackage(RobotArmGUI.class);
        SonarSensor_DEFAULT = preferences.get(X_PREFS, SonarSensor_DEFAULT);
        IR1_DEFAULT = preferences.get(Y_PREFS, IR1_DEFAULT);
        IR2_DEFAULT = preferences.get(Z_PREFS, IR2_DEFAULT);

        mainPanel = new JPanel();
        limitPanel2 = new JPanel();
        
        limitPanel = new JPanel();
        window = new JLabel("Set Distance");
        window2 = new JLabel("Sensors");
        nullLabel = new JLabel(" ");
        nullLabel2 = new JLabel(" ");
        mm1 = new JLabel("millimeters");
        mm2 = new JLabel("millimeters");
        mm3 = new JLabel("millimeters");
        xlabel = new JLabel("Sonar Distance");
        ylabel = new JLabel("IR1 Distance");
        zlabel = new JLabel("IR2 Distance");


        xTextField = new JTextField();
        yTextField = new JTextField();
        zTextField = new JTextField();
        
        sonarBox = new JCheckBox("Sonar Sensor");
        ir1box = new JCheckBox("IR1 Sensor");
        ir2box = new JCheckBox("IR2 Sensor");
        
        SonarLabel= new JLabel("Sonar");
        ir1Label = new JLabel("ir1");
        ir2Label = new JLabel("ir2");
        
        sonarSensorOn = new JRadioButton("ON");
        sonarSensorOn.setActionCommand("ON");
        sonarSensorOff = new JRadioButton("OFF");
        sonarSensorOff.setActionCommand("OFF");
        sonarButtonGroup = new ButtonGroup();
        sonarButtonGroup.add(sonarSensorOn);
        sonarButtonGroup.add(sonarSensorOff);
        
        ir1SensorOn = new JRadioButton("ON");
        ir1SensorOn.setActionCommand("ON");
        ir1SensorOff = new JRadioButton("OFF");
        ir1SensorOff.setActionCommand("OFF");
        ir1SensorGroup = new ButtonGroup();
        ir1SensorGroup.add(ir1SensorOn);
        ir1SensorGroup.add(ir1SensorOff);
        
        
        ir2SensorOn = new JRadioButton("ON");
        ir2SensorOn.setActionCommand("ON");
        ir2SensorOff = new JRadioButton("OFF");
        ir2SensorOff.setActionCommand("OFF");
        ir2SensorGroup = new ButtonGroup();
        ir2SensorGroup.add(ir2SensorOn);
        ir2SensorGroup.add(ir2SensorOff);
        setPrefsToTextField();

        OKButton = new JButton("OK");
        OKButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTextFieldToPrefs();
                setVisible(false);
            }
        });
        
        GroupLayout limitPanelLayout = new GroupLayout(limitPanel);
        limitPanel.setLayout(limitPanelLayout);
        
        limitPanelLayout.setAutoCreateGaps(true);
        limitPanelLayout.setAutoCreateContainerGaps(true);
        
        limitPanelLayout.setHorizontalGroup(limitPanelLayout.createParallelGroup()
                .addGroup(limitPanelLayout.createParallelGroup()
                    .addComponent(OKButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(limitPanelLayout.createSequentialGroup()
                         .addGroup(limitPanelLayout.createParallelGroup()
                        		 
  /*                      		 
                        		 
                            .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            		.addComponent(SonarLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ir1Label, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ir2Label, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                       
          */                          
/*                    		Edit Start Adding 
                            .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            	.addGroup(limitPanelLayout.createSequentialGroup()
                            		.addComponent(sonarSensorOn).addComponent(sonarSensorOff))
                            	.addGroup(limitPanelLayout.createSequentialGroup()                            		
                            		.addComponent(ir1SensorOn).addComponent(ir1SensorOff))
                            	.addGroup(limitPanelLayout.createSequentialGroup()                            		
                            		.addComponent(ir2SensorOn).addComponent(ir2SensorOff)))       
                            		                    		                            		
                                 
                         Edit End*/
                            .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(ir2Label, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ir1Label, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(SonarLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(window2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(ir2SensorOn, GroupLayout.PREFERRED_SIZE, 60, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(ir1SensorOn, GroupLayout.PREFERRED_SIZE, 60, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(sonarSensorOn, GroupLayout.PREFERRED_SIZE, 60, Short.MAX_VALUE)))
                        .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(nullLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                           
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(ir2SensorOff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(ir1SensorOff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(sonarSensorOff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))));
        
        limitPanelLayout.setVerticalGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, limitPanelLayout.createSequentialGroup()
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(window2).addComponent(nullLabel2))
/*  Edit Start                   
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(SonarLabel).addComponent(ir1Label).addComponent(ir2Label))
                    
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(sonarSensorOn).addComponent(ir1SensorOn).addComponent(ir2SensorOn))
                 
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(sonarSensorOff).addComponent(ir1SensorOff).addComponent(ir2SensorOff))   
                    
  Edit End      */
                    
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(SonarLabel)
                    .addComponent(sonarSensorOn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sonarSensorOff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(ir1Label)
                    .addComponent(ir1SensorOn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ir1SensorOff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(ir2Label)
                    .addComponent(ir2SensorOn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ir2SensorOff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(OKButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 32)));

        /*Edit:Second Panel START*/
        
        GroupLayout limitPanelLayout2 = new GroupLayout(limitPanel2);
        limitPanel2.setLayout(limitPanelLayout2);
        
        limitPanelLayout2.setAutoCreateGaps(true);
        limitPanelLayout2.setAutoCreateContainerGaps(true);
        
        limitPanelLayout2.setHorizontalGroup(limitPanelLayout2.createParallelGroup()
                .addGroup(limitPanelLayout2.createParallelGroup()
                    .addComponent(OKButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(limitPanelLayout2.createSequentialGroup()
                         .addGroup(limitPanelLayout2.createParallelGroup()
                    		
/*                    		Edit Start Adding 
                            .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            	.addGroup(limitPanelLayout.createSequentialGroup()
                            		.addComponent(sonarSensorOn).addComponent(sonarSensorOff))
                            	.addGroup(limitPanelLayout.createSequentialGroup()                            		
                            		.addComponent(ir1SensorOn).addComponent(ir1SensorOff))
                            	.addGroup(limitPanelLayout.createSequentialGroup()                            		
                            		.addComponent(ir2SensorOn).addComponent(ir2SensorOff)))       
                            		                    		                            		
                                 
                         Edit End*/
                            .addGroup(limitPanelLayout2.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(zlabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(xlabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ylabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(limitPanelLayout2.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(limitPanelLayout2.createSequentialGroup()
                                    .addComponent(window, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout2.createSequentialGroup()
                                    .addComponent(zTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout2.createSequentialGroup()
                                    .addComponent(yTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout2.createSequentialGroup()
                                    .addComponent(xTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)))
                        .addGroup(limitPanelLayout2.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(limitPanelLayout2.createSequentialGroup()
                                    .addComponent(nullLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                           
                            .addGroup(limitPanelLayout2.createSequentialGroup()
                                    .addComponent(mm3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout2.createSequentialGroup()
                                    .addComponent(mm2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout2.createSequentialGroup()
                                    .addComponent(mm1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))));
        
        limitPanelLayout2.setVerticalGroup(limitPanelLayout2.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, limitPanelLayout2.createSequentialGroup()
                .addGroup(limitPanelLayout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(window).addComponent(nullLabel))
/*  Edit Start                   
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(SonarLabel).addComponent(ir1Label).addComponent(ir2Label))
                    
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(sonarSensorOn).addComponent(ir1SensorOn).addComponent(ir2SensorOn))
                 
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(sonarSensorOff).addComponent(ir1SensorOff).addComponent(ir2SensorOff))   
                    
  Edit End      */                 

                .addGroup(limitPanelLayout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(xlabel)
                    .addComponent(xTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mm1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(limitPanelLayout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(ylabel)
                    .addComponent(yTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mm2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(limitPanelLayout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(zlabel)
                    .addComponent(zTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mm3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(OKButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 32)));
        /*Edit:Second Panel END*/
        
        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        
        mainPanelLayout.setAutoCreateGaps(true);
        mainPanelLayout.setAutoCreateContainerGaps(true);
        
        mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup()
                .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(limitPanel).addComponent(limitPanel2)));
        
        mainPanelLayout.setVerticalGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup()
                        .addComponent(limitPanel).addComponent(limitPanel2)));

        setContentPane(mainPanel);
        setResizable(false);
        setTitle("Sensors");
        pack();
        setLocationRelativeTo(armGUI);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame)e.getSource();
                frame.setVisible(false);
            }
        });
    }

    private void setPrefsToTextField() {
        xTextField.setText(SonarSensor_DEFAULT);
        yTextField.setText(IR1_DEFAULT);
        zTextField.setText(IR2_DEFAULT);
    }

    private void setTextFieldToPrefs() {
        preferences.put(X_PREFS, xTextField.getText());
        preferences.put(Y_PREFS, yTextField.getText());
        preferences.put(Z_PREFS, zTextField.getText());
    }
    
    public double[] getWindowValues(){
        double[] array = {0,0,0,0,0,0};
        array[0] = Double.parseDouble(SonarSensor_DEFAULT);
        array[1] = Double.parseDouble(IR1_DEFAULT);
        array[2] = Double.parseDouble(IR2_DEFAULT);
        return array;
    }

}
