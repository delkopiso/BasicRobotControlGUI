package BasicRobotControlGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class RobotArmGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	/**<p> The delay in milliseconds in between sending each instruction in the file
     * to the robot arm when the run button is pressed. </p>*/
    protected final long runDelay = 1500;
    /** <p> The maximum speed the robot arm can translate 
     * in meters per seconds </p>
     */
	protected final double MAX_SPEED = 500;
	/** <p> The maximum speed the robot arm can rotate 
     * in radians per seconds </p>
     */
    protected final double MAX_ANG_SPEED = 50;
	protected final int COLUMN_SIZE = 8;
	private final String[] COLUMN_HEADINGS = {"x-pos","y-pos","z-pos","a-angle","b-angle","c-angle","velocity","omega"};
	
    private JPanel mainPanel,jogModePanel, fileImportPanel;

    private JLabel aAngleLabel;
    private JLabel bAngleLabel;
    private JLabel cAngleLabel;
    private JLabel xPositionLabel;
    private JLabel yPositionLabel;
    private JLabel zPositionLabel;
    private JLabel statusLabel;
    private JLabel jogSpeedLabel;
    private JLabel jogAngSpeedLabel;
    private JLabel jogSwitchLabel;
    
    private double x,y,z,a,b,c,w,v;
    
    private JTextField aAngleTextField;
    private JTextField bAngleTextField;
    private JTextField cAngleTextField;
    private JTextField xPositionTextField;
    private JTextField yPositionTextField;
    private JTextField zPositionTextField;

    private JButton aAngleDecrementButton;
    private JButton aAngleIncrementButton;
    private JButton bAngleDecrementButton;
    private JButton bAngleIncrementButton;
    private JButton cAngleDecrementButton;
    private JButton cAngleIncrementButton;
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
    protected ButtonGroup jogButtonGroup;
    protected JRadioButton jogOnRadioButton;
    protected JRadioButton jogOffRadioButton;
    private JSlider jogSpeedSlider;
    private JSlider jogAngSpeedSlider;
    protected boolean jogMode;
    protected volatile AtomicBoolean run;
    protected volatile AtomicInteger currentRow;
    
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
        xPositionLabel = new JLabel("x-Position");
        yPositionLabel = new JLabel("y-Position");
        zPositionLabel = new JLabel("z-Position");
        jogSwitchLabel = new JLabel("JOG MODE");
        jogSpeedLabel = new JLabel("Jog Speed");
        jogAngSpeedLabel = new JLabel("Angular Speed");

        aAngleTextField = new JTextField("0");
        bAngleTextField = new JTextField("0");
        cAngleTextField = new JTextField("0");
        xPositionTextField = new JTextField("0");
        yPositionTextField = new JTextField("0");
        zPositionTextField = new JTextField("0");

        x = Double.parseDouble(xPositionTextField.getText());
        y = Double.parseDouble(yPositionTextField.getText());
        z = Double.parseDouble(zPositionTextField.getText());
        a = Double.parseDouble(aAngleTextField.getText());
        b = Double.parseDouble(bAngleTextField.getText());
        c = Double.parseDouble(cAngleTextField.getText());
        
        xPositionDecrementButton = new JButton("DOWN");
        yPositionDecrementButton = new JButton("DOWN");
        zPositionDecrementButton = new JButton("DOWN");
        xPositionIncrementButton = new JButton("UP");
        yPositionIncrementButton = new JButton("UP");
        zPositionIncrementButton = new JButton("UP");
        aAngleDecrementButton = new JButton("DOWN");
        aAngleIncrementButton = new JButton("UP");
        bAngleDecrementButton = new JButton("DOWN");
        cAngleDecrementButton = new JButton("DOWN");
        cAngleIncrementButton = new JButton("UP");
        bAngleIncrementButton = new JButton("UP");
        goButton = new JButton("GO");
        runButton = new JButton("RUN");
        stopButton = new JButton("STOP"); stopButton.setForeground(Color.RED);
        resetButton = new JButton("RESET");
        jogOnRadioButton = new JRadioButton("ON");
        jogOnRadioButton.setActionCommand("ON");
        jogOffRadioButton = new JRadioButton("OFF");
        jogOffRadioButton.setActionCommand("OFF");
        jogOffRadioButton.setSelected(true);
        jogButtonGroup = new ButtonGroup();
        jogButtonGroup.add(jogOnRadioButton);
        jogButtonGroup.add(jogOffRadioButton);
        jogSpeedSlider = new JSlider(0, 100, 0);
        jogSpeedSlider.setMajorTickSpacing(20);
        jogSpeedSlider.setMinorTickSpacing(5);
        jogSpeedSlider.setPaintTicks(true);
        jogSpeedSlider.setPaintLabels(true);
        v = jogSpeedSlider.getValue();
        jogAngSpeedSlider = new JSlider(0, 100, 0);
        jogAngSpeedSlider.setMajorTickSpacing(20);
        jogAngSpeedSlider.setMinorTickSpacing(5);
        jogAngSpeedSlider.setPaintTicks(true);
        jogAngSpeedSlider.setPaintLabels(true);
        w = jogAngSpeedSlider.getValue();
        jogMode = false;
        run = new AtomicBoolean(true);
        currentRow = new AtomicInteger(0);
        
        dataTableModel = new DefaultTableModel(COLUMN_HEADINGS, 0);
        dataTable = new JTable(dataTableModel){
            private static final long serialVersionUID = 1L;
            public Component prepareRenderer (TableCellRenderer renderer,int Index_row, int Index_col) {
                Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
                if (Index_row == currentRow.get()) {
                    comp.setBackground(Color.GREEN);
                } else {
                    comp.setBackground(Color.WHITE);
                }
                return comp;
            }
        };
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

        goButton.addActionListener(new ButtonListener(this));
        jogOnRadioButton.addActionListener(new ButtonListener(this));
        jogOffRadioButton.addActionListener(new ButtonListener(this));
        runButton.addActionListener(new ButtonListener(this));
        stopButton.addActionListener(new ButtonListener(this));
        resetButton.addActionListener(new ButtonListener(this));
        
        GroupLayout jogPanelLayout = new GroupLayout(jogModePanel);
        jogModePanel.setLayout(jogPanelLayout);
        
        jogPanelLayout.setAutoCreateGaps(true);
        jogPanelLayout.setAutoCreateContainerGaps(true);
        
        jogPanelLayout.setHorizontalGroup(jogPanelLayout.createParallelGroup()
                .addGroup(jogPanelLayout.createParallelGroup()
                        .addGroup(jogPanelLayout.createSequentialGroup()
                            .addGroup(jogPanelLayout.createParallelGroup()
                                .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(jogSwitchLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jogSpeedLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jogAngSpeedLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(jogPanelLayout.createSequentialGroup()
                                            .addComponent(jogOnRadioButton).addComponent(jogOffRadioButton))
                                        .addGroup(jogPanelLayout.createSequentialGroup()
                                                .addComponent(jogSpeedSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jogPanelLayout.createSequentialGroup()
                                                .addComponent(jogAngSpeedSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGroup(jogPanelLayout.createParallelGroup()
                    .addComponent(goButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jogPanelLayout.createSequentialGroup()
                        .addGroup(jogPanelLayout.createParallelGroup()
                            .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(zPositionLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(aAngleLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bAngleLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cAngleLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                            .addComponent(yPositionIncrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(xPositionIncrementButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))));
        
        jogPanelLayout.setVerticalGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jogPanelLayout.createSequentialGroup()
                .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jogSwitchLabel).addComponent(jogOnRadioButton).addComponent(jogOffRadioButton))
                .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jogSpeedLabel)
                    .addComponent(jogSpeedSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jogPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jogAngSpeedLabel)
                    .addComponent(jogAngSpeedSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        for(double val : values){
            System.out.println(val);
        }
//        RobotArmGUI.client.send(MyUtil.convertIntsToStringFormat(values));
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
        w = jogAngSpeedSlider.getValue();
        v = jogSpeedSlider.getValue();
        double[] array = {x,y,z,a,b,c,v,w};
        String msg = MyUtil.convertDigitsToStringFormat(array);
        System.out.println(msg);
//        client.send(msg);
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
        System.out.println(msg);
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
