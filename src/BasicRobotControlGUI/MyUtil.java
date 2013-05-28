package BasicRobotControlGUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MyUtil {
    final static char[] valueChars = { 'x', 'y', 'z', 'a', 'b', 'c', 'v', 'w' };

    /**
     * <p>
     * Parses an input file into a 2-Dimensional array
     * </p>
     * 
     * @param f
     *            the file object to be parsed.
     * @return a 2-D array that contains the table data in the file.
     * @throws FileFormatException
     * @throws IOException
     */
    public static String[][] readFromfile(File f) throws FileFormatException,
            IOException {
        int lines = 0;
        // Check for empty files
        FileInputStream fis = new FileInputStream(f);
        int b = fis.read();
        if (b == -1) {
            fis.close();
            throw new FileFormatException("Empty file!");
        }
        fis.close();
        // get number of lines in file
        try {
            FileReader testFile = new FileReader(f);
            Scanner testScan = new Scanner(testFile);
            while (testScan.hasNextLine()) {
                lines++;
                @SuppressWarnings("unused")
                String line = testScan.nextLine();
            }
            testScan.close();
            testFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        String[][] commands = new String[lines][valueChars.length];
        // actually parse the file
        try {
            FileReader file = new FileReader(f);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                for (int i = 0; i < lines; i++) {
                    String line = scanner.nextLine();
                    String[] values = line.split(",");
                    commands[i] = values;
                }
            }
            scanner.close();
            file.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return commands;
    }

    /**
     * Writes an input string to a specified file object.
     * 
     * @param file
     *            the file object to be written to.
     * @param content
     *            the string value of the data to be written to the file.
     */
    public static void writeToFile(File file, String content) {
        try {
            if (!file.exists()) { // if file doesn't exists, then create it
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * <p>
     * Converts the array containing the command points for the robot arm into a
     * String representation of the Python dictionary input accepted by the
     * server.
     * </p>
     * 
     * @param array
     *            the array of the digits to be sent to the server.
     * @return the String value of the correct format accepted by the server.
     */
    public static String convertDigitsToStringFormat(double[] array) {
        String s = "{";
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1)
                s += "'" + valueChars[i] + "'" + ":" + array[i] + "}";
            else
                s += "'" + valueChars[i] + "'" + ":" + array[i] + ",";
        }
        return s;
    }

    /**
     * Exception used for signaling format errors not matching File grammar
     */
    public static class FileFormatException extends Exception {
        private static final long serialVersionUID = 1L;

        public FileFormatException(String msg) {
            super(msg);
        }
    }
}
