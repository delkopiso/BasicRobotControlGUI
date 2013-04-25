package BasicRobotControlGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class RobotArmClient {
	private final Socket socket;
    private static final String robotIP = "localhost";
    private static final int robotPORT = 27273;
    private PrintWriter out;
    private String ipAddress;
    private int portNumber;
    private RobotArmGUI gui;
    private BufferedReader in;
    private final String testData = "TEST";
    private String data = null;

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
        
        if (line.contains("|")){
            String[] fields = line.split("\\|", 7);
            String status = fields[0];
            double[] array = new double[6];
            for (int i=0; i<array.length; i++){
                array[i] = Double.parseDouble(fields[i+1]);
            }
            String format = MyUtil.convertDigitsToStringFormat(array);
            gui.updateStatus(status + "    " + format);
        }
    }
    
    public void send() {
        if (data != null){
            out.println(data);
        }else{
            out.println(testData);
        }
        out.flush();
    }
    
    public void setData(String s){
        this.data = s;
    }
/*
    *//**
     * Sends batch instructions to the Robot Arm server
     *//*
    public void sendBatch(List<String> list){
        for (String s : list){
            send(s);
        }
    }
*/
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
