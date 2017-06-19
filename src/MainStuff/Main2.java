package MainStuff;

import RobotFunctions.Decoder;
import RobotFunctions.Robot;
import RobotFunctions.RobotUtils;
import TelnetFunctions.Telnet;
import sun.awt.Mutex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by williamjones on 6/14/17.
 * MainStuff.Main2 class for final project.
 */
public class Main2
{
    /**
     * MainStuff.Main2 method. Creates telnet and robot, and
     *  functions as the primary control of movement.
     * @param args TBA
     */
    public static final Mutex mymutex=new Mutex();
    public static boolean firstTime=true;

    public static void main (String[] args) throws IOException, InterruptedException
    {
        final Robot robot = new Robot();

        try {
            final ServerSocket serverSocket = new ServerSocket(7777);
            new Thread("Device Listener") {
                public void run() {
                    try {
                        System.out.println("Listener Running . . .");
                        Socket socket = null;
                        while ((socket = serverSocket.accept()) != null) {
                            System.out.println("| Incoming : "+ socket.toString());
                            BufferedReader myreader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String line;
                            line=myreader.readLine();
                            mymutex.lock();
                            if (firstTime) {
                                Decoder.updateTerrainField(robot, line);
                                firstTime=false;
                            }
                            else
                            {
                                Decoder.updateRobot(robot, line);
                            }
                            mymutex.unlock();
                            System.out.println(line);
                            socket.close();
                            //System.out.println("How many cities? " + robot.getMyCities().size());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Initializing program and robot...");
        Telnet telnet = new Telnet();
        //Decoder.updateTerrainField(robot, telnet.sendWhere());
        while(firstTime)
        {
            mymutex.lock();
            if(!firstTime)
            {
                Thread.sleep(5000);
                break;
            }
            mymutex.unlock();
        }
        System.out.println("How many cities? " + robot.getMyCities().size());
        robot.calculatePath();
        while(true)
        {
            robot.rotateMe(telnet);
            System.out.println("Robot's node coordinates: " +
                    RobotUtils.convertFromPixelToNode(robot.getCurrentLocation()));
            Thread.sleep(3000);
            if (robot.end())
            {
                System.out.println("Stopping...");
                telnet.sendSpeed(0,0);
                return;
            }
            //System.out.println("shouldIPause() = " + robot.shouldIPause());
            if (robot.shouldIPause())
            {
                System.out.println("I pause");
                telnet.sendSpeed(0, 0);
                Thread.sleep(5000);
            }
            telnet.sendSpeed(2,2);
        }
    }
}
