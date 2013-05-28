package BasicRobotControlGUI;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class RobotArmClient implements SerialPortEventListener {
	private final Socket socket;
    private static final String robotIP = "localhost";
    private static final int robotPORT = 27273;
    private PrintWriter out;
    private String ipAddress;
    private int portNumber;
    private RobotArmGUI gui;
    private BufferedReader in;
    private final String testData = "TEST";
    /**<p> The data that's being sent to the Python server </p>*/
    private String data = null;
    
    SerialPort serialPort;
    /** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
		"/dev/tty.usbmodemfd1211",
		//"/dev/tty.usbmodemfd121",
		//"/dev/tty.usbserial-A9007UX1", // Mac OS X
		"/dev/ttyUSB0", // Linux
		"COM3", // Windows
	};
	/**
	 * A BufferedReader which will be fed by a InputStreamReader 
	 * converting the bytes into characters 
	 * making the displayed results codepage independent
	 */
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

    /**
     * Constructor for the Robot Arm GUI client
     * @param ip the IP address to connect to
     * @param port the port on which the socket connection is to be established
     * @throws UnknownHostException
     * @throws IOException
     */
    public RobotArmClient(String ip, int port) throws UnknownHostException, IOException{
        ipAddress = ip;
        portNumber = port;
        socket = new Socket(ipAddress, portNumber);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        gui = new RobotArmGUI(this);
        gui.setVisible(true);        
    }

    public void run() throws IOException{
        while(true){
            send();
            read();
        }
    }

    /**
     * Processes the server response and sends it to the gui
     * @throws IOException 
     */
    public void read() throws IOException{
        String line = in.readLine();
        String[] fields = line.split("\\|", 7);
        String status = fields[0];
        double[] array = new double[6];
        for (int i=0; i<array.length; i++){
        	array[i] = Double.parseDouble(fields[i+1]);
        }
        gui.updateCurrentPosition(array);
        String format = MyUtil.convertDigitsToStringFormat(array);
        gui.updateCommandStatus(status + "    " + format);
    }
    
    /**
     * <p> Transmits data, when available, to the Python server. When 
     * the data is unavailable, then the TEST parameter is sent instead. </p>
     */
    public void send() {
        if (data != null){
            out.println(data);
            setData(null);
        }else{
            out.println(testData);
        }
        out.flush();
    }
    
    /**
     * <p> Allows the user to set the data to be sent </p>
     * @param s the data value
     */
    public void setData(String s){
        this.data = s;
    }

    /**
     * <p> Exits the program and closes all open sockets. </p>
     */
    public void quit() {
        try{
            System.exit(1);
            socket.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    //// Start Serial Sensor Stuff ////
    public void initialize() {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener( this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine=input.readLine();
		        gui.updateSensorStatus("Sensor"+inputLine);
				System.out.println(inputLine);
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}///// End Serial Sensor Stuff /////
    
    
    /**
     * Start a RobotArmGUI client.
     */
    public static void main(String[] args) {   	
        try{
            RobotArmClient client = new RobotArmClient(robotIP, robotPORT);
            client.initialize();
            client.run();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
