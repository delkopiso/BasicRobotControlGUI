package BasicRobotControlGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class RobotArmClient{
    private final Socket socket;
    private static final String robotIP = "localhost";
    private static final int robotPORT = 27273;
    private PrintWriter out;
    private String ipAddress;
    private int portNumber;
    private RobotArmGUI gui;
    private BufferedReader in;

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
            read();
        }
    }

    /**
     * Processes the server response and sends it to the gui
     * @throws IOException 
     */
    public void read() throws IOException{
        String line = in.readLine();
        gui.updateStatus(line);
    }
    
    /**
     * Sends individual instructions to the Robot Arm server
     * @param msg the instruction to be sent to the server
     */
    public void send(String msg) {
        out.println(msg);
        out.flush();
    }

    /**
     * Sends batch instructions to the Robot Arm server
     */
    public void sendBatch(List<String> list){
        for (String s : list){
            send(s);
        }
    }

    public void quit() {
        try{
            socket.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * Start a RobotArmGUI client.
     */
    public static void main(String[] args) {        
        try{
            RobotArmClient client = new RobotArmClient(robotIP, robotPORT);
            client.run();
        }catch(Exception e){
            System.out.println(e);
        }
    }

}