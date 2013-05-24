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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingsMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private RobotArmGUI armGUI;
    private JPanel limitPanel;
    private JLabel nullLabel, minimum, maximum, xlabel, ylabel, zlabel, alabel, blabel, clabel;
    private JTextField xMinTextField, yMinTextField, zMinTextField, aMinTextField, bMinTextField, cMinTextField;
    private JTextField xMaxTextField, yMaxTextField, zMaxTextField, aMaxTextField, bMaxTextField, cMaxTextField;
    private JLabel mm1, degrees1, mm2, degrees2, mm3, degrees3;
    protected JButton OKButton;
    private Preferences preferences;
    private final String X_MIN_PREFS = "X-Pos Min";
    private final String Y_MIN_PREFS = "Y-Pos Min";
    private final String Z_MIN_PREFS = "Z-Pos Min";
    private final String A_MIN_PREFS = "A-Angle Min";
    private final String B_MIN_PREFS = "B-Angle Min";
    private final String C_MIN_PREFS = "C-Angle Min";
    private final double xAbsoluteMin = -700;
    private final double yAbsoluteMin = -700;
    private final double zAbsoluteMin = 0;
    private final double aAbsoluteMin = -180;
    private final double bAbsoluteMin = -45;
    private final double cAbsoluteMin = -90;
    private final double xAbsoluteMax = 700;
    private final double yAbsoluteMax = 700;
    private final double zAbsoluteMax = 700;
    private final double aAbsoluteMax = 180;
    private final double bAbsoluteMax = 45;
    private final double cAbsoluteMax = 90;
    private double X_MIN_DEFAULT = xAbsoluteMin;
    private double Y_MIN_DEFAULT = yAbsoluteMin;
    private double Z_MIN_DEFAULT = zAbsoluteMin;
    private double A_MIN_DEFAULT = aAbsoluteMin;
    private double B_MIN_DEFAULT = bAbsoluteMin;
    private double C_MIN_DEFAULT = cAbsoluteMin;
    private final String X_MAX_PREFS = "X-Pos Max";
    private final String Y_MAX_PREFS = "Y-Pos Max";
    private final String Z_MAX_PREFS = "Z-Pos Max";
    private final String A_MAX_PREFS = "A-Angle Max";
    private final String B_MAX_PREFS = "B-Angle Max";
    private final String C_MAX_PREFS = "C-Angle Max";
    private double X_MAX_DEFAULT = xAbsoluteMax;
    private double Y_MAX_DEFAULT = yAbsoluteMax;
    private double Z_MAX_DEFAULT = zAbsoluteMax;
    private double A_MAX_DEFAULT = aAbsoluteMax;
    private double B_MAX_DEFAULT = bAbsoluteMax;
    private double C_MAX_DEFAULT = cAbsoluteMax;


    public SettingsMenu(RobotArmGUI gui) {
        this.armGUI = gui;
        initComponents();
    }
    
    private void initComponents(){
        preferences = Preferences.userNodeForPackage(RobotArmGUI.class);
        X_MIN_DEFAULT = preferences.getDouble(X_MIN_PREFS, X_MIN_DEFAULT);
        Y_MIN_DEFAULT = preferences.getDouble(Y_MIN_PREFS, Y_MIN_DEFAULT);
        Z_MIN_DEFAULT = preferences.getDouble(Z_MIN_PREFS, Z_MIN_DEFAULT);
        A_MIN_DEFAULT = preferences.getDouble(A_MIN_PREFS, A_MIN_DEFAULT);
        B_MIN_DEFAULT = preferences.getDouble(B_MIN_PREFS, B_MIN_DEFAULT);
        C_MIN_DEFAULT = preferences.getDouble(C_MIN_PREFS, C_MIN_DEFAULT);
        X_MAX_DEFAULT = preferences.getDouble(X_MAX_PREFS, X_MAX_DEFAULT);
        Y_MAX_DEFAULT = preferences.getDouble(Y_MAX_PREFS, Y_MAX_DEFAULT);
        Z_MAX_DEFAULT = preferences.getDouble(Z_MAX_PREFS, Z_MAX_DEFAULT);
        A_MAX_DEFAULT = preferences.getDouble(A_MAX_PREFS, A_MAX_DEFAULT);
        B_MAX_DEFAULT = preferences.getDouble(B_MAX_PREFS, B_MAX_DEFAULT);
        C_MAX_DEFAULT = preferences.getDouble(C_MAX_PREFS, C_MAX_DEFAULT);
        
        limitPanel = new JPanel();
        minimum = new JLabel("Minimum");
        maximum = new JLabel("Maximum");
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

        xMinTextField = new JTextField();
        yMinTextField = new JTextField();
        zMinTextField = new JTextField();
        aMinTextField = new JTextField();
        bMinTextField = new JTextField();
        cMinTextField = new JTextField();
        xMaxTextField = new JTextField();
        yMaxTextField = new JTextField();
        zMaxTextField = new JTextField();
        aMaxTextField = new JTextField();
        bMaxTextField = new JTextField();
        cMaxTextField = new JTextField();
        setPrefsToTextField();

        OKButton = new JButton("OK");
        OKButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validEntries()){
                    setTextFieldToPrefs();
                    setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(null, "The specified ranges are not physically possible", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
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
                                    .addComponent(minimum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(maximum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                .addComponent(cMinTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                .addComponent(cMaxTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(bMinTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                    .addComponent(bMaxTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(zMinTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                    .addComponent(zMaxTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(aMinTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                    .addComponent(aMaxTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(yMinTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                    .addComponent(yMaxTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                            .addGroup(limitPanelLayout.createSequentialGroup()
                                    .addComponent(xMinTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                    .addComponent(xMaxTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)))
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
                    .addComponent(minimum).addComponent(maximum).addComponent(nullLabel))
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(xlabel)
                    .addComponent(xMinTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xMaxTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mm1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(ylabel)
                    .addComponent(yMinTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(yMaxTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mm2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(zlabel)
                    .addComponent(zMinTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(zMaxTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mm3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(alabel)
                    .addComponent(aMinTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(aMaxTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(degrees1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(blabel)
                    .addComponent(bMinTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bMaxTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(degrees2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(limitPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(clabel)
                    .addComponent(cMinTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cMaxTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    
    private boolean validEntries() {
        boolean valid = false;
        double x_Min,y_Min,z_Min,a_Min,b_Min,c_Min,x_Max,y_Max,z_Max,a_Max,b_Max,c_Max;
        x_Max=new Double(xMaxTextField.getText());y_Max=new Double(yMaxTextField.getText());z_Max=new Double(zMaxTextField.getText());
        a_Max=new Double(aMaxTextField.getText());b_Max=new Double(bMaxTextField.getText());c_Max=new Double(cMaxTextField.getText());
        x_Min=new Double(xMinTextField.getText());y_Min=new Double(yMinTextField.getText());z_Min=new Double(zMinTextField.getText());
        a_Min=new Double(aMinTextField.getText());b_Min=new Double(bMinTextField.getText());c_Min=new Double(cMinTextField.getText());
        if (x_Min < x_Max && y_Min < y_Max && z_Min < z_Max && a_Min < a_Max && b_Min < b_Max && c_Min < c_Max &&
                x_Min>=xAbsoluteMin && y_Min>=yAbsoluteMin && z_Min>=zAbsoluteMin && a_Min>=aAbsoluteMin && b_Min>=bAbsoluteMin && c_Min>=cAbsoluteMin &&
                xAbsoluteMax>=x_Max && yAbsoluteMax>=y_Max && zAbsoluteMax>=z_Max && aAbsoluteMax>=a_Max && bAbsoluteMax>=b_Max && cAbsoluteMax>=c_Max){
            valid = true;
        }
        return valid;
    }

    private void setPrefsToTextField() {
        xMinTextField.setText(""+X_MIN_DEFAULT);xMaxTextField.setText(""+X_MAX_DEFAULT);
        yMinTextField.setText(""+Y_MIN_DEFAULT);yMaxTextField.setText(""+Y_MAX_DEFAULT);
        zMinTextField.setText(""+Z_MIN_DEFAULT);zMaxTextField.setText(""+Z_MAX_DEFAULT);
        aMinTextField.setText(""+A_MIN_DEFAULT);aMaxTextField.setText(""+A_MAX_DEFAULT);
        bMinTextField.setText(""+B_MIN_DEFAULT);bMaxTextField.setText(""+B_MAX_DEFAULT);
        cMinTextField.setText(""+C_MIN_DEFAULT);cMaxTextField.setText(""+C_MAX_DEFAULT);
    }

    private void setTextFieldToPrefs() {
        preferences.putDouble(X_MIN_PREFS, new Double(xMinTextField.getText()));preferences.putDouble(X_MAX_PREFS, new Double(xMaxTextField.getText()));
        preferences.putDouble(Y_MIN_PREFS, new Double(yMinTextField.getText()));preferences.putDouble(Y_MAX_PREFS, new Double(yMaxTextField.getText()));
        preferences.putDouble(Z_MIN_PREFS, new Double(zMinTextField.getText()));preferences.putDouble(Z_MAX_PREFS, new Double(zMaxTextField.getText()));
        preferences.putDouble(A_MIN_PREFS, new Double(aMinTextField.getText()));preferences.putDouble(A_MAX_PREFS, new Double(aMaxTextField.getText()));
        preferences.putDouble(B_MIN_PREFS, new Double(bMinTextField.getText()));preferences.putDouble(B_MAX_PREFS, new Double(bMaxTextField.getText()));
        preferences.putDouble(C_MIN_PREFS, new Double(cMinTextField.getText()));preferences.putDouble(C_MAX_PREFS, new Double(cMaxTextField.getText()));
    }
    
    public double[] getWindowMaxValues(){
        double[] array = {X_MAX_DEFAULT,Y_MAX_DEFAULT,Z_MAX_DEFAULT,A_MAX_DEFAULT,B_MAX_DEFAULT,C_MAX_DEFAULT};
        return array;
    }
    
    public double[] getWindowMinValues(){
        double[] array = {X_MIN_DEFAULT,Y_MIN_DEFAULT,Z_MIN_DEFAULT,A_MIN_DEFAULT,B_MIN_DEFAULT,C_MIN_DEFAULT};
        return array;
    }

}
