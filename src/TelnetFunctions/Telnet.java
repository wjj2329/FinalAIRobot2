package TelnetFunctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by williamjones on 5/21/17.
 * Telnet client for communicating with the robot via Bluetooth.
 */
public class Telnet
{
    /**
     * Socket connection
     */
    private Socket pingSocket;

    private PrintWriter myWriter;
    private BufferedReader myReader;

    /**
     * Default constructor
     * @throws IOException if error processing
     */
    public Telnet() throws IOException
    {

    }
    public void sendStuffToOtherRobot(String json) throws IOException {
        pingSocket =new Socket("localhost", 8080);
        myWriter =new PrintWriter(pingSocket.getOutputStream(), true);
        myWriter.println(json);

        //get stuff from myReader .readline()

        pingSocket.close();
        myWriter.close();

    }

    /**
     * Sending the WHERE command in order to obtain the robot's current information
     */
    public String sendWhere() throws IOException
    {
        pingSocket =new Socket("localhost", 55555);
        myWriter =new PrintWriter(pingSocket.getOutputStream(), true);
        myReader =new BufferedReader((new InputStreamReader(pingSocket.getInputStream())));
        myWriter.println("where");
        StringBuilder response=new StringBuilder();
        String line;

        line= myReader.readLine();
        response.append(myReader.readLine());
        //get stuff from myReader .readline()

        myWriter.close();
        myReader.close();
        pingSocket.close();
        return response.toString();

    }

    /**
     * Moving the robot
     * @param speed1 speed for first wheel
     * @param speed2 speed for second wheel
     */
    public void sendSpeed(int speed1, int speed2) throws IOException
    {
        pingSocket =new Socket("localhost", 55555);
        myWriter =new PrintWriter(pingSocket.getOutputStream(), true);
        System.out.println("I SEND IT THESE SPEEDS "+speed1+" "+speed2);
        myWriter.println("speed "+speed1+" "+speed2);

        //get stuff from myReader .readline()

        pingSocket.close();
        myWriter.close();
    }

    /**
     * Shutting down the connection.
     */
    public boolean shutDown() throws IOException
    {
        pingSocket =new Socket("localhost", 55555);
        myWriter =new PrintWriter(pingSocket.getOutputStream(), true);
        myReader =new BufferedReader((new InputStreamReader(pingSocket.getInputStream())));

        myWriter.println("shutDown");
        myWriter.close();
        myReader.close();
        pingSocket.close();
        return true;
    }
}