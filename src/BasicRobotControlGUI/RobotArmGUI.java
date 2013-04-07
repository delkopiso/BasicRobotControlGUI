package BasicRobotControlGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

public class RobotArmGUI extends JFrame {
	private static final long serialVersionUID = 1L;
    /** <p> The maximum speed the robot arm can translate 
     * in meters per seconds </p>
     */
	protected final double MAX_SPEED = 500;
	/** <p> The maximum speed the robot arm can rotate 
     * in radians per seconds </p>
     */
    protected final double MAX_ANG_SPEED = 50;
	protected final int COLUMN_SIZE = 8;

    private JPanel mainPanel,jogModePanel, fileImportPanel;

    private JLabel aAngleLabel;
    private JLabel bAngleLabel;
    private JLabel cAngleLabel;
    private JLabel omegaLabel;
    private JLabel velocityLabel;
    private JLabel xPositionLabel;
    private JLabel yPositionLabel;
    private JLabel zPositionLabel;
    private JLabel statusLabel;
    private JLabel jogSwitchLabel;
    
    private double x,y,z,a,b,c,w,v;
    
    private JTextField aAngleTextField;
    private JTextField bAngleTextField;
    private JTextField cAngleTextField;
    private JTextField omegaTextField;
    private JTextField velocityTextField;
    private JTextField xPositionTextField;
    private JTextField yPositionTextField;
    private JTextField zPositionTextField;

    private JButton aAngleDecrementButton;
    private JButton aAngleIncrementButton;
    private JButton bAngleDecrementButton;
    private JButton bAngleIncrementButton;
    private JButton cAngleDecrementButton;
    private JButton cAngleIncrementButton;
    private JButton omegaDecrementButton;
    private JButton omegaIncrementButton;
    private JButton velocityDecrementButton;
    private JButton velocityIncrementButton;
    private JButton xPositionDecrementButton;
    private JButton xPositionIncrementButton;
    private JButton yPositionDecrementButton;
    private JButton yPositionIncrementButton;
    private JButton zPositionDecrementButton;
    private JButton zPositionIncrementButton;
    protected JButton goButton;
    protected JButton runButton;
    protected JButton stopButton;
    protected JButton resetButton;
    
    protected JSlider jogSpeedSlider;
    protected JSlider jogAngSpeedSlider;
    protected JSwitchBox jogSwitch;
    protected boolean jogMode;
    protected volatile boolean run;
    protected volatile int currentRow;
    /**<p> The delay in milliseconds in between sending each instruction in the file
     * to the robot arm when the run button is pressed. </p>*/
    protected final int runDelay = 1500;

    private JTable dataTable;
    protected DefaultTableModel dataTableModel;
    private JScrollPane dataTableScrollPane;
    
    private JMenuBar menuBar;
    private JMenu fileMenu;
    protected JMenuItem openItem;
    protected JMenuItem saveItem;
    protected JMenuItem exitItem;
    protected JFileChooser fileChooser;
    private FileFilter filter;

    private JTextField statusField;
    
    protected File openFile;
    protected File saveFile;
    protected String stringToSave;
    
    protected Timer timer;
    
    static RobotArmClient client;

    /**
     * Creates new form RobotArmGUI
     */
    public RobotArmGUI(RobotArmClient client) {
        RobotArmGUI.client = client;
        initComponents();
    }

    private void initComponents() {
        
        mainPanel = new JPanel();
        jogModePanel = new JPanel();
        fileImportPanel = new JPanel();

        aAngleLabel = new JLabel("a-Angle");
        bAngleLabel = new JLabel("b-Angle");
        cAngleLabel = new JLabel("c-Angle");
        omegaLabel = new JLabel("omega");
        velocityLabel = new JLabel("velocity");
        xPositionLabel = new JLabel("x-Position");
        yPositionLabel = new JLabel("y-Position");
        zPositionLabel = new JLabel("z-Position");
        jogSwitchLabel = new JLabel("JOG MODE");

        aAngleTextField = new JTextField("0");
        bAngleTextField = new JTextField("0");
        cAngleTextField = new JTextField("0");
        omegaTextField = new JTextField("0");
        velocityTextField = new JTextField("0");
        xPositionTextField = new JTextField("0");
        yPositionTextField = new JTextField("0");
        zPositionTextField = new JTextField("0");

        x = Double.parseDouble(xPositionTextField.getText());
        y = Double.parseDouble(yPositionTextField.getText());
        z = Double.parseDouble(zPositionTextField.getText());
        a = Double.parseDouble(aAngleTextField.getText());
        b = Double.parseDouble(bAngleTextField.getText());
        c = Double.parseDouble(cAngleTextField.getText());
        w = Double.parseDouble(omegaTextField.getText());
        v = Double.parseDouble(velocityTextField.getText());
        
        xPositionDecrementButton = new JButton("DOWN");
        yPositionDecrementButton = new JButton("DOWN");
        zPositionDecrementButton = new JButton("DOWN");
        velocityDecrementButton = new JButton("DOWN");
        xPositionIncrementButton = new JButton("UP");
        yPositionIncrementButton = new JButton("UP");
        zPositionIncrementButton = new JButton("UP");
        velocityIncrementButton = new JButton("UP");
        aAngleDecrementButton = new JButton("DOWN");
        aAngleIncrementButton = new JButton("UP");
        bAngleDecrementButton = new JButton("DOWN");
        cAngleDecrementButton = new JButton("DOWN");
        omegaIncrementButton = new JButton("UP");
        cAngleIncrementButton = new JButton("UP");
        bAngleIncrementButton = new JButton("UP");
        omegaDecrementButton = new JButton("DOWN");
        goButton = new JButton("GO");
        runButton = new JButton("RUN");
        stopButton = new JButton("STOP"); stopButton.setBackground(Color.RED);
        resetButton = new JButton("RESET");
        jogSpeedSlider = new JSlider(0, (int)MAX_SPEED, 0);
        jogAngSpeedSlider = new JSlider(0, (int)MAX_ANG_SPEED, 0);
        jogSwitch =  new JSwitchBox("ON", "OFF");
        jogMode = false;
        run = true;
        currentRow = 0;

        dataTable = new JTable(0,COLUMN_SIZE); dataTable.setRowHeight(15);
        dataTableModel = (DefaultTableModel) dataTable.getModel();
        dataTableModel.setColumnIdentifiers(new Object[]{"x-pos","y-pos","z-pos","a-angle","b-angle","c-angle","velocity","omega"});
        dataTableScrollPane = new JScrollPane(dataTable);
        this.add(dataTableScrollPane, BorderLayout.CENTER);
        
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        openItem = new JMenuItem("Open...");
        saveItem = new JMenuItem("Save Table Entries");
        exitItem = new JMenuItem("Exit");
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        filter = new FileFilter() {
			public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".csv");
            }
			public String getDescription() {
                return "*.csv files";
            }
        };
        fileChooser.setFileFilter(filter);
        
        statusLabel = new JLabel("CURRENT STATUS: ");
        statusField = new JTextField();
        statusField.setEditable(false);
        
        timer = new Timer(runDelay, null);

        xPositionDecrementButton.addActionListener(new DecrementButtonListener(this, xPositionTextField));
        xPositionIncrementButton.addActionListener(new IncrementButtonListener(this, xPositionTextField));
        yPositionDecrementButton.addActionListener(new DecrementButtonListener(this, yPositionTextField));
        yPositionIncrementButton.addActionListener(new IncrementButtonListener(this, yPositionTextField));
        zPositionDecrementButton.addActionListener(new DecrementButtonListener(this, zPositionTextField));
        zPositionIncrementButton.addActionListener(new IncrementButtonListener(this, zPositionTextField));
        aAngleDecrementButton.addActionListener(new DecrementButtonListener(this, aAngleTextField));
        aAngleIncrementButton.addActionListener(new IncrementButtonListener(this, aAngleTextField));
        bAngleDecrementButton.addActionListener(new DecrementButtonListener(this, bAngleTextField));
        bAngleIncrementButton.addActionListener(new IncrementButtonListener(this, bAngleTextField));
        cAngleDecrementButton.addActionListener(new DecrementButtonListener(this, cAngleTextField));
        cAngleIncrementButton.addActionListener(new IncrementButtonListener(this, cAngleTextField));
        omegaDecrementButton.addActionListener(new DecrementButtonListener(this, omegaTextField));
        omegaIncrementButton.addActionListener(new IncrementButtonListener(this, omegaTextField));
        velocityDecrementButton.addActionListener(new DecrementButtonListener(this, velocityTextField));
        velocityIncrementButton.addActionListener(new IncrementButtonListener(this, velocityTextField));

        goButton.addActionListener(new ButtonListener(this));
        jogSwitch.addActionListener(new ButtonListener(this));
        runButton.addActionListener(new ButtonListener(this));        
        stopButton.addActionListener(new ButtonListener(this));        
        resetButton.addActionListener(new ButtonListener(this));
        
        GroupLayout jogPanelLayout = new GroupLayout(jogModePanel);
        jogModePanel.setLayout(jogPanelLayout);
        
        jogPanelLayout.setAutoCreateGaps(true);
        jogPanelLayout.setAutoCreateContainerGaps(true);
        
        jogPanelLayout.setHorizontalGroup(jogPanelLayout.createParallelGroup()
                .addGroup(jogPanelLayout.createSequentialGroup()
                        .addComponent(jogSwitchLabel).addComponent(jogSwitch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jogPanelLayout.createParallelGroup()
                    .addComponent(goButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jogPanelLayout.createSequentialGroup()
                        .addGroup(jogPanelLayout.createParallelGroup()
                            .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(zPositionLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(aAngleLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bAngleLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cAngleLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(velocityLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(omegaLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(xPositionLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(yPositionLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(jogPanelLayout.createSequentialGroup()
                                .addComponent(cAngleTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                .addComponent(cAngleDecrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jogPanelLayout.createSequentialGroup()
                                .addComponent(bAngleTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                .addComponent(bAngleDecrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jogPanelLayout.createSequentialGroup()
                                .addComponent(zPositionTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                .addComponent(zPositionDecrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jogPanelLayout.createSequentialGroup()
                                .addComponent(aAngleTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                .addComponent(aAngleDecrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jogPanelLayout.createSequentialGroup()
                                .addComponent(omegaTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                .addComponent(omegaDecrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jogPanelLayout.createSequentialGroup()
                                .addComponent(velocityTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                .addComponent(velocityDecrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jogPanelLayout.createSequentialGroup()
                                .addComponent(yPositionTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                .addComponent(yPositionDecrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jogPanelLayout.createSequentialGroup()
                                .addComponent(xPositionTextField, GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                .addComponent(xPositionDecrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(aAngleIncrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bAngleIncrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cAngleIncrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(zPositionIncrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(omegaIncrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(velocityIncrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(yPositionIncrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(xPositionIncrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))));
        
        jogPanelLayout.setVerticalGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jogPanelLayout.createSequentialGroup()
                .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jogSwitch).addComponent(jogSwitchLabel))
                .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(xPositionLabel)
                    .addComponent(xPositionTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xPositionDecrementButton).addComponent(xPositionIncrementButton))
                .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(yPositionLabel)
                    .addComponent(yPositionTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(yPositionDecrementButton).addComponent(yPositionIncrementButton))
                .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(zPositionLabel)
                    .addComponent(zPositionTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(zPositionDecrementButton).addComponent(zPositionIncrementButton))
                .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(aAngleLabel)
                    .addComponent(aAngleTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(aAngleDecrementButton).addComponent(aAngleIncrementButton))
                .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(bAngleLabel)
                    .addComponent(bAngleTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bAngleDecrementButton).addComponent(bAngleIncrementButton))
                .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cAngleLabel)
                    .addComponent(cAngleTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cAngleDecrementButton).addComponent(cAngleIncrementButton))
                .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(velocityLabel)
                    .addComponent(velocityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(velocityDecrementButton).addComponent(velocityIncrementButton))
                .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(omegaLabel)
                    .addComponent(omegaTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(omegaDecrementButton).addComponent(omegaIncrementButton))
                .addComponent(goButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 32)));

        GroupLayout fileImportPanelLayout = new GroupLayout(fileImportPanel);
        fileImportPanel.setLayout(fileImportPanelLayout);
        
        fileImportPanelLayout.setAutoCreateGaps(true);
        fileImportPanelLayout.setAutoCreateContainerGaps(true);

        fileImportPanelLayout.setHorizontalGroup(fileImportPanelLayout.createParallelGroup()
                .addComponent(dataTableScrollPane, GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
                .addGroup(Alignment.CENTER,fileImportPanelLayout.createSequentialGroup()
                        .addComponent(runButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(resetButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stopButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        
        fileImportPanelLayout.setVerticalGroup(fileImportPanelLayout.createSequentialGroup()
                .addComponent(dataTableScrollPane)
                .addGroup(fileImportPanelLayout.createParallelGroup()
                        .addComponent(runButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(resetButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stopButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        
        this.add(mainPanel);
        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        
        mainPanelLayout.setAutoCreateGaps(true);
        mainPanelLayout.setAutoCreateContainerGaps(true);
        
        mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup()
                .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jogModePanel).addComponent(fileImportPanel))
                .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(statusLabel)
                        .addComponent(statusField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        
        mainPanelLayout.setVerticalGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup()
                        .addComponent(jogModePanel).addComponent(fileImportPanel))
                .addGroup(mainPanelLayout.createParallelGroup()
                        .addComponent(statusLabel)
                        .addComponent(statusField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        
        fileMenu.setText("File");
        menuBar.add(fileMenu);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        
        openItem.addActionListener(new FileOptionsListener(this));
        saveItem.addActionListener(new FileOptionsListener(this));
        exitItem.addActionListener(new FileOptionsListener(this));
        
        setJMenuBar(menuBar);
        setContentPane(mainPanel);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Populates the JTable with the contents of the imported file
     */
    public void populateTable() {
        try{
            String[][] data = MyUtil.readFromfile(openFile);
            for (String[] row : data){
                dataTableModel.addRow(row);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    /**
     * <p> Retrieves the entries in the table and saves them to a file </p>
     */
    public String pollTable() {
        String result = "";
        @SuppressWarnings("unchecked")
        Vector<Vector<String>> dataVector = (Vector<Vector<String>>)dataTableModel.getDataVector();
        for (int i=0; i<dataTableModel.getRowCount(); i++){
            Vector<String> rowVector = dataVector.elementAt(i);
            for (String s : rowVector){
                if (rowVector.indexOf(s) < rowVector.size()-1) result += s +",";
                else result += s +"\n";
            }
        }
        return result;
    }
    
    protected synchronized void sendTableEntry(int rowNumber){
        double[] values = new double[COLUMN_SIZE];
        for (int j=0; j < COLUMN_SIZE; j++){
            String val = (String) dataTableModel.getValueAt(rowNumber, j);
            values[j] = Double.parseDouble(val);
        }
//        for(double val : values){
//            System.out.println(val);
//        }
        RobotArmGUI.client.send(MyUtil.convertIntsToStringFormat(values));
    }
    
    /**
     * Retrieves the values in each text field and sends info to robot arm
     */
    protected synchronized void sendPos(){
        x = Double.parseDouble(xPositionTextField.getText());
        y = Double.parseDouble(yPositionTextField.getText());
        z = Double.parseDouble(zPositionTextField.getText());
        a = Double.parseDouble(aAngleTextField.getText());
        b = Double.parseDouble(bAngleTextField.getText());
        c = Double.parseDouble(cAngleTextField.getText());
        w = Double.parseDouble(omegaTextField.getText());
        v = Double.parseDouble(velocityTextField.getText());
        double[] array = {x,y,z,a,b,c,v,w};
        String msg = MyUtil.convertIntsToStringFormat(array);
        client.send(msg);
    }
    
    /**
     * Retrieves the mode operation of the jog panel
     * @return true if the program is in jog mode, false otherwise
     */
    protected boolean getJogMode(){
        return jogMode;
    }
    
    public void updateStatus(String msg){
        //TODO
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println(ex);
        }

        /* Create and display the form */
        SwingUtilities.invokeLater(new Runnable() {
			public void run() {
                RobotArmGUI main = new RobotArmGUI(client);
                main.setVisible(true);
            }
        });
    }

}
