package BasicRobotControlGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingsMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private RobotArmGUI armGUI;
    private JPanel limitPanel;
    private JLabel nullLabel, window, xlabel, ylabel, zlabel, alabel, blabel, clabel;
    private JTextField xTextField, yTextField, zTextField, aTextField, bTextField, cTextField;
    private JLabel mm1, degrees1, mm2, degrees2, mm3, degrees3;
    protected JButton OKButton;
    private Preferences preferences;
    private final String X_PREFS = "X-Pos Window";
    private final String Y_PREFS = "Y-Pos Window";
    private final String Z_PREFS = "Y-Pos Window";
    private final String A_PREFS = "A-Angle Window";
    private final String B_PREFS = "B-Angle Window";
    private final String C_PREFS = "C-Angle Window";
    private String X_DEFAULT = "100";
    private String Y_DEFAULT = "100";
    private String Z_DEFAULT = "100";
    private String A_DEFAULT = "180";
    private String B_DEFAULT = "90";
    private String C_DEFAULT = "180";

    public SettingsMenu(RobotArmGUI gui) {
        this.armGUI = gui;
        initComponents();
    }
    
    private void initComponents(){        
        preferences = Preferences.userNodeForPackage(RobotArmGUI.class);
        X_DEFAULT = preferences.get(X_PREFS, X_DEFAULT);
        Y_DEFAULT = preferences.get(Y_PREFS, Y_DEFAULT);
        Z_DEFAULT = preferences.get(Z_PREFS, Z_DEFAULT);
        A_DEFAULT = preferences.get(A_PREFS, A_DEFAULT);
        B_DEFAULT = preferences.get(B_PREFS, B_DEFAULT);
        C_DEFAULT = preferences.get(C_PREFS, C_DEFAULT);
        
        limitPanel = new JPanel();
        window = new JLabel("Window");
        nullLabel = new JLabel(" ");
        mm1 = new JLabel("millimeters");
        degrees1 = new JLabel("degrees");
        mm2 = new JLabel("millimeters");
        degrees2 = new JLabel("degrees");
        mm3 = new JLabel("millimeters");
        degrees3 = new JLabel("degrees");
        xlabel = new JLabel("X-Position");
        ylabel = new JLabel("Y-Position");
        zlabel = new JLabel("Z-Position");
        alabel = new JLabel("A-Angle");
        blabel = new JLabel("B-Angle");
        clabel = new JLabel("C-Angle");

        xTextField = new JTextField();
        yTextField = new JTextField();
        zTextField = new JTextField();
        aTextField = new JTextField();
        bTextField = new JTextField();
        cTextField = new JTextField();
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
                            .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(zlabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(alabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(blabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(clabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(xlabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ylabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(window, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                .addComponent(cTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(bTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(zTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(aTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(yTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(xTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)))
                        .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(nullLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                .addComponent(degrees3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(degrees2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(mm3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(degrees1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(mm2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(mm1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))));
        
        limitPanelLayout.setVerticalGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, limitPanelLayout.createSequentialGroup()
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(window).addComponent(nullLabel))
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(xlabel)
                    .addComponent(xTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mm1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(ylabel)
                    .addComponent(yTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mm2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(zlabel)
                    .addComponent(zTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mm3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(alabel)
                    .addComponent(aTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(degrees1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(blabel)
                    .addComponent(bTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(degrees2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(clabel)
                    .addComponent(cTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(degrees3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(OKButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 32)));
        
        setContentPane(limitPanel);
        setResizable(false);
        setTitle("Settings");
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
        xTextField.setText(X_DEFAULT);
        yTextField.setText(Y_DEFAULT);
        zTextField.setText(Z_DEFAULT);
        aTextField.setText(A_DEFAULT);
        bTextField.setText(B_DEFAULT);
        cTextField.setText(C_DEFAULT);
    }

    private void setTextFieldToPrefs() {
        preferences.put(X_PREFS, xTextField.getText());
        preferences.put(Y_PREFS, yTextField.getText());
        preferences.put(Z_PREFS, zTextField.getText());
        preferences.put(A_PREFS, aTextField.getText());
        preferences.put(B_PREFS, bTextField.getText());
        preferences.put(C_PREFS, cTextField.getText());
    }
    
    public double[] getWindowValues(){
        double[] array = {0,0,0,0,0,0};
        array[0] = Double.parseDouble(X_DEFAULT);
        array[1] = Double.parseDouble(Y_DEFAULT);
        array[2] = Double.parseDouble(Z_DEFAULT);
        array[3] = Double.parseDouble(A_DEFAULT);
        array[4] = Double.parseDouble(B_DEFAULT);
        array[5] = Double.parseDouble(C_DEFAULT);
        return array;
    }

}
