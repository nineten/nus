import java.io.*;
import java.util.*;
import java.net.*;

public class SecurityConsoleThread extends Thread
{
    protected DatagramSocket socket = null;
    protected BufferedReader in = null;

    private int alarmCount = 0;
    private int dataCount = 0;
    private boolean schedule_flag = false;
    public int updatedTime = 2;
    public int sendedTime = 2;

    public SecurityConsoleThread()
    {
        this("SecurityConsole");
    }

    public SecurityConsoleThread(String name)
    {
        super(name);
        try
        {
            socket = new DatagramSocket(1337);
        }
        catch(Exception e)
        {
        }
    }

    public void run() 
    {
        try
        {
            Scanner in = new Scanner(System.in);
            while (true)
            {
                byte[] buf = new byte[2048];
                byte[] data = null;

                DatagramPacket packet = new DatagramPacket(buf,buf.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                data = packet.getData();

                System.out.println("------------------------------------------------------------");
                System.out.println("Packet recieved");
                System.out.println("Type:"+data[0]);
                System.out.println("Drop packet?(1/0)");
                if (in.nextInt()==1)continue;
                System.out.println("Send Schedule?(1/0)");
                if (in.nextInt()==1)
                {
                    schedule_flag=true;
                    updatedTime = in.nextInt();
                    sendedTime = in.nextInt();
                }
                else
                    schedule_flag = false;


                switch(data[0])
                {
                    case 0:
                        // alarm
                        alarmCount = data[1];
                        FileOutputStream out1 = new FileOutputStream("recorded/alarm_alert"+alarmCount+".txt");

                        for (int x=3;x<data[2]+3;x++)
                        {
                            out1.write(data[x]);
                        }
                        out1.close();
                        out1 = new FileOutputStream("recorded/alarm_camera"+dataCount+".jpg");
                        for (int x=data[2]+3;x<data.length;x++)
                        {
                            out1.write(data[x]);
                        }
                        out1.close();

                        byte[] reply = null;

                        if (schedule_flag)
                        {
                            // with schedule ack is 3
                            // 3 <alarm_count> <updated_time>
                            reply = new byte[4];
                            reply[0] = 3;
                            reply[1] = (byte)alarmCount;
                            reply[2] = (byte)updatedTime;
                            reply[3] = (byte)sendedTime;
                        }
                        else
                        {
                            // no schedule ack is 4
                            reply = new byte[2];
                            reply[0] = 4;
                            reply[1] = (byte)alarmCount;
                        }
                        packet = new DatagramPacket(reply,reply.length,address,port);
                        System.out.println("Sent - " + reply[0]);
                        socket.send(packet);
                        break;
                    case 1:
                        // monitor
                        dataCount = data[1];

                        FileOutputStream out2 = new FileOutputStream("recorded/monitor_data"+dataCount+".txt");

                        for (int x=3;x<data[2]+3;x++)
                        {
                            out2.write(data[x]);
                        }
                        out2.close();
                        out2 = new FileOutputStream("recorded/monitor_pic"+dataCount+".jpg");
                        for (int x=data[2]+3;x<data.length;x++)
                        {
                            out2.write(data[x]);
                        }
                        out2.close();

                        if (schedule_flag)
                        {
                            // with schedule ack is 5
                            // 3 <alarm_count> <updated_time>
                            reply = new byte[4];
                            reply[0] = 5;
                            reply[1] = (byte)dataCount;
                            reply[2] = (byte)updatedTime;
                            reply[3] = (byte)sendedTime;
                        }
                        else
                        {
                            // no schedule ack is 6
                            reply = new byte[2];
                            reply[0] = 6;
                            reply[1] = (byte)dataCount;
                        }
                        System.out.println("Sent - " + reply[0]);
                        packet = new DatagramPacket(reply,reply.length,address,port);
                        socket.send(packet);
                        break;

                }// end switch
            }// end while
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        //        socket.close();
    }// end run
}
