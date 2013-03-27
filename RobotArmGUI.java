package BasicRobotControlGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kopiso
 */
public class RobotArmGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel mainPanel,jogModePanel,fileImportPanel;

    private JLabel aAngleLabel;
    private JLabel bAngleLabel;
    private JLabel cAngleLabel;
    private JLabel omegaLabel;
    private JLabel velocityLabel;
    private JLabel xPositionLabel;
    private JLabel yPositionLabel;
    private JLabel zPositionLabel;
    private JLabel statusLabel;
    
    private int x,y,z,a,b,c,w,v;
    
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
    private JButton goButton;
    private JButton runButton;
    private JButton stopButton;
    private JButton resetButton;

    private JTable dataTable;
    private DefaultTableModel dataTableModel;
    private JScrollPane dataTableScrollPane;
    
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openItem;
    private JMenuItem exitItem;
    private JFileChooser fileChooser;
    private FileFilter filter;

    private JTextField statusField;
    
    private File currentFile;
    
    private static RobotArmClient client;

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

        aAngleTextField = new JTextField("0");
        bAngleTextField = new JTextField("0");
        cAngleTextField = new JTextField("0");
        omegaTextField = new JTextField("0");
        velocityTextField = new JTextField("0");
        xPositionTextField = new JTextField("0");
        yPositionTextField = new JTextField("0");
        zPositionTextField = new JTextField("0");

        x = Integer.parseInt(xPositionTextField.getText());
        y = Integer.parseInt(yPositionTextField.getText());
        z = Integer.parseInt(zPositionTextField.getText());
        a = Integer.parseInt(aAngleTextField.getText());
        b = Integer.parseInt(bAngleTextField.getText());
        c = Integer.parseInt(cAngleTextField.getText());
        w = Integer.parseInt(omegaTextField.getText());
        v = Integer.parseInt(velocityTextField.getText());
        
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

        dataTable = new JTable(0,8); dataTable.setRowHeight(15);
        dataTableModel = (DefaultTableModel) dataTable.getModel();
        dataTableModel.setColumnIdentifiers(new Object[]{"x-pos","y-pos","z-pos","a-angle","b-angle","c-angle","velocity","omega"});
        dataTableScrollPane = new JScrollPane(dataTable);
        this.add(dataTableScrollPane, BorderLayout.CENTER);
        
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        openItem = new JMenuItem("Open...");
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

        xPositionDecrementButton.addActionListener(new DecrementButtonListener(xPositionTextField));
        xPositionIncrementButton.addActionListener(new IncrementButtonListener(xPositionTextField));
        yPositionDecrementButton.addActionListener(new DecrementButtonListener(yPositionTextField));
        yPositionIncrementButton.addActionListener(new IncrementButtonListener(yPositionTextField));
        zPositionDecrementButton.addActionListener(new DecrementButtonListener(zPositionTextField));
        zPositionIncrementButton.addActionListener(new IncrementButtonListener(zPositionTextField));
        aAngleDecrementButton.addActionListener(new DecrementButtonListener(aAngleTextField));
        aAngleIncrementButton.addActionListener(new IncrementButtonListener(aAngleTextField));
        bAngleDecrementButton.addActionListener(new DecrementButtonListener(bAngleTextField));
        bAngleIncrementButton.addActionListener(new IncrementButtonListener(bAngleTextField));
        cAngleDecrementButton.addActionListener(new DecrementButtonListener(cAngleTextField));
        cAngleIncrementButton.addActionListener(new IncrementButtonListener(cAngleTextField));
        omegaDecrementButton.addActionListener(new DecrementButtonListener(omegaTextField));
        omegaIncrementButton.addActionListener(new IncrementButtonListener(omegaTextField));
        velocityDecrementButton.addActionListener(new DecrementButtonListener(velocityTextField));
        velocityIncrementButton.addActionListener(new IncrementButtonListener(velocityTextField));

        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] posArray = getPosArray();
                String msg = MyUtil.convertIntsToStringFormat(posArray);
                System.out.println(msg);
                client.send(msg);
            }
        });
        
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
        
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
        
        GroupLayout jogPanelLayout = new GroupLayout(jogModePanel);
        jogModePanel.setLayout(jogPanelLayout);
        
        jogPanelLayout.setAutoCreateGaps(true);
        jogPanelLayout.setAutoCreateContainerGaps(true);
        
        jogPanelLayout.setHorizontalGroup(jogPanelLayout.createParallelGroup()
                .addGroup(jogPanelLayout.createSequentialGroup()
                        .addComponent(xPositionLabel).addComponent(xPositionTextField, 32, GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(xPositionIncrementButton).addComponent(xPositionDecrementButton))
                .addGroup(jogPanelLayout.createSequentialGroup()
                        .addComponent(yPositionLabel).addComponent(yPositionTextField, 32, GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(yPositionIncrementButton).addComponent(yPositionDecrementButton))
                .addGroup(jogPanelLayout.createSequentialGroup()
                        .addComponent(zPositionLabel).addComponent(zPositionTextField, 32, GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(zPositionIncrementButton).addComponent(zPositionDecrementButton))
                .addGroup(jogPanelLayout.createSequentialGroup()
                        .addComponent(aAngleLabel).addComponent(aAngleTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(aAngleIncrementButton).addComponent(aAngleDecrementButton))
                .addGroup(jogPanelLayout.createSequentialGroup()
                        .addComponent(bAngleLabel).addComponent(bAngleTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(bAngleIncrementButton).addComponent(bAngleDecrementButton))
                .addGroup(jogPanelLayout.createSequentialGroup()
                        .addComponent(cAngleLabel).addComponent(cAngleTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(cAngleIncrementButton).addComponent(cAngleDecrementButton))
                .addGroup(jogPanelLayout.createSequentialGroup()
                        .addComponent(velocityLabel).addComponent(velocityTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(velocityIncrementButton).addComponent(velocityDecrementButton))
                .addGroup(jogPanelLayout.createSequentialGroup()
                        .addComponent(omegaLabel).addComponent(omegaTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(omegaIncrementButton).addComponent(omegaDecrementButton))
                .addComponent(goButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 600));
        
        jogPanelLayout.setVerticalGroup(jogPanelLayout.createSequentialGroup()
                .addGroup(jogPanelLayout.createParallelGroup()
                        .addComponent(xPositionLabel).addComponent(xPositionTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(xPositionIncrementButton).addComponent(xPositionDecrementButton))
                .addGroup(jogPanelLayout.createParallelGroup()
                        .addComponent(yPositionLabel).addComponent(yPositionTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(yPositionIncrementButton).addComponent(yPositionDecrementButton))
                .addGroup(jogPanelLayout.createParallelGroup()
                        .addComponent(zPositionLabel).addComponent(zPositionTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(zPositionIncrementButton).addComponent(zPositionDecrementButton))
                .addGroup(jogPanelLayout.createParallelGroup()
                        .addComponent(aAngleLabel).addComponent(aAngleTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(aAngleIncrementButton).addComponent(aAngleDecrementButton))
                .addGroup(jogPanelLayout.createParallelGroup()
                        .addComponent(bAngleLabel).addComponent(bAngleTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(bAngleIncrementButton).addComponent(bAngleDecrementButton))
                .addGroup(jogPanelLayout.createParallelGroup()
                        .addComponent(cAngleLabel).addComponent(cAngleTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(cAngleIncrementButton).addComponent(cAngleDecrementButton))
                .addGroup(jogPanelLayout.createParallelGroup()
                        .addComponent(velocityLabel).addComponent(velocityTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(velocityIncrementButton).addComponent(velocityDecrementButton))
                .addGroup(jogPanelLayout.createParallelGroup()
                        .addComponent(omegaLabel).addComponent(omegaTextField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(omegaIncrementButton).addComponent(omegaDecrementButton))
                .addComponent(goButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 32));
        
        GroupLayout fileImportPanelLayout = new GroupLayout(fileImportPanel);
        fileImportPanel.setLayout(fileImportPanelLayout);
        
        fileImportPanelLayout.setAutoCreateGaps(true);
        fileImportPanelLayout.setAutoCreateContainerGaps(true);

        fileImportPanelLayout.setHorizontalGroup(fileImportPanelLayout.createParallelGroup()
                .addComponent(dataTableScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGroup(fileImportPanelLayout.createSequentialGroup()
                        .addComponent(runButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(resetButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(stopButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 32))
                .addComponent(statusLabel)
                .addComponent(statusField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE));
        
        fileImportPanelLayout.setVerticalGroup(fileImportPanelLayout.createSequentialGroup()
                .addComponent(dataTableScrollPane)
                .addGroup(fileImportPanelLayout.createParallelGroup()
                        .addComponent(runButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(resetButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 32)
                        .addComponent(stopButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 32))
                .addComponent(statusLabel)
                .addComponent(statusField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 32));
        
        this.add(mainPanel);
        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        
        mainPanelLayout.setAutoCreateGaps(true);
        mainPanelLayout.setAutoCreateContainerGaps(true);
        
        mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup()
                .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jogModePanel).addComponent(fileImportPanel)));
        
        mainPanelLayout.setVerticalGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup()
                        .addComponent(jogModePanel).addComponent(fileImportPanel)));
        
        fileMenu.setText("File");
        menuBar.add(fileMenu);
        fileMenu.add(openItem);
        fileMenu.add(exitItem);
        
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int retval = fileChooser.showOpenDialog(RobotArmGUI.this);
                if (retval == JFileChooser.APPROVE_OPTION) {
                    currentFile = fileChooser.getSelectedFile();
                    populateTable();
                }
            }
        });
        
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                client.quit();
            }
        });
        
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
    private void populateTable() {
        try{
            String[][] data = MyUtil.readFromfile(currentFile);
            for (String[] row : data){
                dataTableModel.addRow(row);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    /**
     * Retrieves the values in each text field and return an int array of the values
     * @return an int array with the values in the text fields
     */
    private int[] getPosArray(){
        x = Integer.parseInt(xPositionTextField.getText());
        y = Integer.parseInt(yPositionTextField.getText());
        z = Integer.parseInt(zPositionTextField.getText());
        a = Integer.parseInt(aAngleTextField.getText());
        b = Integer.parseInt(bAngleTextField.getText());
        c = Integer.parseInt(cAngleTextField.getText());
        w = Integer.parseInt(omegaTextField.getText());
        v = Integer.parseInt(velocityTextField.getText());
        int[] array = {x,y,z,a,b,c,v,w};
        return array;
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RobotArmGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                RobotArmGUI main = new RobotArmGUI(client);
                main.setVisible(true);
            }
        });
    }
}
